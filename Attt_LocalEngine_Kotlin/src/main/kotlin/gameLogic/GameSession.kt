package gameLogic

import attt.Game
import attt.Player
import geometry.Line
import geometry.abstractions.Coordinates
import geometry.abstractions.OneMoveProcessing
import geometry.concept2D.NearestAreaScanWith2D
import geometry.concept3D.NearestAreaScanWith3D
import geometry.conceptXY.NearestAreaScanWithXY
import players.PlayerModel
import players.PlayerProvider
import utilities.Log

/**
 * this is the main contacting point for any game UI. the game is fully controlled with this singleton.
 * a game session can be started & finished, each time new to be clear from any possible remains.
 */
internal class GameSession(
    desiredFieldSize: Int, desiredMaxLineLength: Int, private val is3D: Boolean, desiredPlayerNumber: Int
) : Game {

    // to distinguish between older XY-based logic and new multi-axis-based approach for coordinates processing
    private val useNewDimensionsBasedLogic = true

    internal var gameField: GameField = GameField(desiredFieldSize)
    internal var gameProgress: GameProgress = GameProgress(desiredMaxLineLength)

    // the only place for switching between kinds of algorithms for every move processing
    internal val chosenAlgorithm: OneMoveProcessing =
        if (is3D) NearestAreaScanWith3D(gameField) // NewDimensionsBasedLogic is used as the only option here
        else if (useNewDimensionsBasedLogic) NearestAreaScanWith2D(gameField) else NearestAreaScanWithXY(gameField)

    init {
        PlayerProvider.prepareNewPlayersInstances(desiredPlayerNumber)
        PlayerProvider.prepareNextPlayer() // this invocation sets the activePlayer to the starting Player among others
    }

    /**
     * this is the only way for a client to make progress in the game.
     * there is no need to set active player - it's detected & returned automatically, like the next cartridge in revolver.
     */
    override fun makeMove(x: Int, y: Int, z: Int): Player {
        Log.pl("makeMove: x = $x, y = $y, z = $z")
        val requestedPosition = chosenAlgorithm.getCoordinatesFor(x, y, z)
        return if (requestedPosition.existsWithin(gameField.sideLength)) {
            makeMove(requestedPosition)
        } else {
            PlayerProvider.activePlayer
        }
    }

    /**
     * this function is actually the only place for making moves and thus changing the game field
     */
    internal fun makeMove(where: Coordinates, what: Player = PlayerProvider.activePlayer): Player =
        if (gameField.placeNewMark(where, what)) {
            chosenAlgorithm.getMaxLengthAchievedForThisMove(where,
                saveNewLine = { player, line -> gameProgress.saveNewLine(player, line) },
                addNewMark = { player, coordinates -> gameProgress.addToRecentLine(player, coordinates) })?.let {
                Log.pl("makeMove: maxLength for this move of player ${what.name} is: $it")
                // this cast is secure as PlayerModel is direct inheritor to AtttPlayer
                (what as PlayerModel).tryToSetMaxLineLength(it)
                gameProgress.updatePlayerScore(what, it)
            }
            PlayerProvider.prepareNextPlayer(gameProgress.isGameWon())
            PlayerProvider.activePlayer
        } else {
            what // current player's mark was not successfully placed - prepared player stays the same
        }

    /**
     * a client might want to know the currently leading player at any time during the game
     */
    override fun getLeader(): Player = gameProgress.getLeadingPlayer()

    /**
     * there can be only one winner - Player.None is returned until the winner not yet detected
     */
    override fun getWinner(): Player = gameProgress.getWinner()

    /**
     * after the winner is detected there is no way to modify the game field, so the game state is preserved
     */
    override fun isGameWon() = gameProgress.isGameWon()

    override fun isGameFinished(): Boolean = isGameWon() || gameField.isCompletelyOccupied(is3D)

    /**
     * I decided to return the field state as a map of Triple Integers to Player
     * instead of just giving the existing map of Coordinates to Player
     * to prevent from opening inner implementation details on a public API level.
     * even if Coordinates was made public and not abstract - it would not be possible to get a Player by key
     * as this key would be something like Coordinates(0,0,0) which is NOT the same as Coordinates3D(0,0,0)
     * due to specifics of generics & collections implementation in Java & Kotlin.
     */
    override fun getCurrentFieldAsMapOfTriples(): Map<Triple<Int, Int, Int>, Player> =
        gameField.theMap.mapKeys { entry -> Triple(entry.key.x, entry.key.y, entry.key.z) }

    override fun getCurrentFieldAsMapOfPairs(z: Int): Map<Pair<Int, Int>, Player> {
        return if (z > 0) { // just an optimization to avoid excess filtering for Z=0 case
            gameField.getSliceForZ(z)
        } else {
            gameField.theMap // by default its coordinates as pairs are processed only for the base Z=0 slice
        }.mapKeys { entry -> Pair(entry.key.x, entry.key.y) }
    }

    override fun getCurrentFieldIn2dAsAString(): String {
        // reasonable sideLength here is 1 -> minIndex = 0 -> only one layer in Z dimension will exist
        val zAxisSize = if (is3D) gameField.sideLength else 1
        return gameField.prepareForPrinting3dIn2d(chosenAlgorithm, zAxisSize)
    }

    override fun printCurrentFieldIn2d() {
        // not using Log.pl here as this action is intentional & has not be able to switch off
        println(getCurrentFieldIn2dAsAString())
    }

    override fun getExistingLinesForGivenPlayer(player: Player): List<List<Triple<Int, Int, Int>>?>? {
        val allExistingLinesForThisPlayer: MutableSet<Line?>? = gameProgress.allPlayersLines[player]
        return allExistingLinesForThisPlayer?.flatMap { oneLine -> // outer List (of lines) is created here
            // every line is a set of marks
            listOf(oneLine?.marks?.map { oneMark -> // inner List (of marks in the line) is created here
                // every mark should be converted from Coordinates to Triple of integers
                Triple(oneMark.x, oneMark.y, oneMark.z)
            })
        }
    }

    override fun getExistingLinesForGivenPlayerAsAString(player: Player): String {
        val allExistingLinesForThisPlayer = gameProgress.allPlayersLines[player]
        val zAxisSize = if (is3D) gameField.sideLength else 1 // only one layer in Z dimension will exist
        return gameField.prepareForPrintingPlayerLines(
            player, allExistingLinesForThisPlayer, chosenAlgorithm, zAxisSize
        )
    }

    override fun printExistingLinesForGivenPlayer(player: Player) {
        println(getExistingLinesForGivenPlayerAsAString(player))
    }

    override fun getExistingLinesForLeadingPlayerAsAString(): String =
        getExistingLinesForGivenPlayerAsAString(gameProgress.getLeadingPlayer())

    override fun printExistingLinesForLeadingPlayer() {
        println(getExistingLinesForLeadingPlayerAsAString())
    }

    override fun getExistingLinesForTheWinnerAsAString(): String =
        getExistingLinesForGivenPlayerAsAString(gameProgress.getWinner())

    override fun printExistingLinesForTheWinner() {
        println(getExistingLinesForTheWinnerAsAString())
    }

    override fun getTheWinningLineAsListOfTriples(): List<Triple<Int, Int, Int>>? =
        gameProgress.getWinningLine()?.marks?.map { oneMark -> Triple(oneMark.x, oneMark.y, oneMark.z) }

    override fun getTheWinningLineAsAString(): String {
        val winningLine: Line = gameProgress.getWinningLine() ?: return ""
        val zAxisSize = if (is3D) gameField.sideLength else 1 // only one layer in Z dimension will exist
        return gameField.prepareTheWinningLineForPrinting(
            gameProgress.getWinner(), winningLine, chosenAlgorithm, zAxisSize
        )
    }

    override fun printTheWinningLine() {
        println(getTheWinningLineAsAString())
    }
}