package gameLogic

import attt.*
import geometry.Line
import geometry.abstractions.Coordinates
import players.PlayerModel
import players.PlayerProvider
import utilities.Log

/**
 * this is the main contacting point for any game UI. the game is fully controlled with this singleton.
 * a game session can be started & finished, each time new to be clear from any possible remains.
 */
internal class GameSession(
    is3D: Boolean, desiredFieldSize: Int, desiredMaxLineLength: Int, desiredPlayerNumber: Int
) : Game {

    internal var gameField: GameField = GameField(is3D, desiredFieldSize)
    private var gameProgress: GameProgress = GameProgress(desiredMaxLineLength)

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
        val requestedPosition = gameField.getCoordinatesFor(x, y, z)
        return if (requestedPosition.existsWithin(gameField.sideLength)) {
            makeMove(requestedPosition)
        } else {
            PlayerProvider.activePlayer
        }
    }

    /**
     * this function is actually the only place for making moves and thus changing the game field
     */
    // @formatter:off
    internal fun makeMove(
        where: Coordinates, what: Player = PlayerProvider.activePlayer
    ): Player =
        if (gameField.placeNewMark(where, what)) {
            gameField.chosenAlgorithm.getMaxLengthAchievedForThisMove(
                where,
                saveNewLine = { player, line -> gameProgress.saveNewLine(player, line) },
                addNewMark = { player, coordinates -> gameProgress.addToRecentLine(player, coordinates) }
            )?.let {
                Log.pl("makeMove: maxLength for this move of player with id: ${what.id} is: $it")
                // this cast is secure as PlayerModel is the only inheritor to Player interface
                (what as PlayerModel).tryToSetMaxLineLength(it)
                gameProgress.updatePlayerScore(what, it)
            }
            PlayerProvider.prepareNextPlayer(gameProgress.isGameWon())
            PlayerProvider.activePlayer
        } else {
            what // current player's mark was not successfully placed - prepared player stays the same
        }
    // @formatter:on

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

    override fun isGameFinished(): Boolean = isGameWon() || gameField.isCompletelyOccupied()

    override fun getCurrentFieldAsAString(): String {
        // reasonable sideLength here is 1 -> minIndex = 0 -> only one layer in Z dimension will exist
        return gameField.prepareForPrinting3dIn2d()
    }

    override fun getLinesAsAStringFor(player: Player): String {
        val allExistingLinesForThisPlayer = gameProgress.allPlayersLines[player]
        return gameField.prepareForPrintingPlayerLines(player, allExistingLinesForThisPlayer)
    }

    override fun getLinesAsAStringForLeader(): String =
        getLinesAsAStringFor(gameProgress.getLeadingPlayer())

    override fun getLinesAsAStringForWinner(): String =
        getLinesAsAStringFor(gameProgress.getWinner())

    override fun getTheWinningLineAsAString(): String {
        val winningLine: Line = gameProgress.getWinningLine() ?: return ""
        return gameField.prepareTheWinningLineForPrinting(gameProgress.getWinner(), winningLine)
    }

    /**
     * I decided to return the field state as a map of Triple Integers to Player
     * instead of just giving the existing map of Coordinates to Player
     * to prevent from opening inner implementation details on a public API level.
     * even if Coordinates was made public and not abstract - it would not be possible to get a Player by key
     * as this key would be something like Coordinates(0,0,0) which is NOT the same as Coordinates3D(0,0,0)
     * due to specifics of generics & collections implementation in Java & Kotlin.
     */
    override fun getCurrentField(): Map<XYZ, Player> =
        gameField.theMap.mapKeys { entry -> XYZ(entry.key.x, entry.key.y, entry.key.z) }

    override fun getCurrentLayer(z: Int): Map<XY, Player> {
        return if (z > 0) { // just an optimization to avoid excess filtering for Z=0 case
            gameField.getSliceForZ(z)
        } else {
            gameField.theMap // by default its coordinates as pairs are processed only for the base Z=0 slice
        }.mapKeys { entry -> XY(entry.key.x, entry.key.y) }
    }

    override fun getLinesFor(player: Player): List<OneLine> {
        val allExistingLinesForThisPlayer: MutableSet<Line?>? = gameProgress.allPlayersLines[player]
        return allExistingLinesForThisPlayer?.flatMap { oneLine -> // outer List (of lines) is created here
            // every line is a set of marks
            listOf(oneLine?.marks?.map { oneMark -> // inner List (of marks in the line) is created here
                // every mark should be converted from Coordinates to Triple of integers
                XYZ(oneMark.x, oneMark.y, oneMark.z)
            } ?: emptyList())
        } ?: emptyList()
    }

    override fun getLinesForLeader(): List<OneLine> =
        getLinesFor(gameProgress.getLeadingPlayer())

    override fun getLinesForWinner(): List<OneLine> =
        getLinesFor(gameProgress.getWinner())

    override fun getTheWinningLine(): OneLine =
        gameProgress.getWinningLine()?.marks?.map { oneMark -> XYZ(oneMark.x, oneMark.y, oneMark.z) } ?: emptyList()
}