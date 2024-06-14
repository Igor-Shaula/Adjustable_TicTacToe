package gameLogic

import geometry.abstractions.Coordinates
import geometry.abstractions.OneMoveProcessing
import geometry.concept2D.NearestAreaScanWith2D
import geometry.concept3D.NearestAreaScanWith3D
import geometry.conceptXY.NearestAreaScanWithXY
import players.PlayerModel
import players.PlayerProvider
import publicApi.AtttGame
import publicApi.AtttPlayer
import utilities.Log

/**
 * this is the main contacting point for any game UI. the game is fully controlled with this singleton.
 * a game session can be started & finished, each time new to be clear from any possible remains.
 */
internal class GameSession(
    desiredFieldSize: Int, desiredMaxLineLength: Int, private val is3D: Boolean, desiredPlayerNumber: Int
) : AtttGame {

    // to distinguish between older XY-based logic and new multi-axis-based approach for coordinates processing
    private val useNewDimensionsBasedLogic = true

    internal var gameField: GameField = GameField(desiredFieldSize)
    private var gameRules: GameRules = GameRules(desiredMaxLineLength)

    // the only place for switching between kinds of algorithms for every move processing
    internal val chosenAlgorithm: OneMoveProcessing =
        if (is3D) NearestAreaScanWith3D(gameField) // NewDimensionsBasedLogic is used as the only option here
        else if (useNewDimensionsBasedLogic) NearestAreaScanWith2D(gameField) else NearestAreaScanWithXY(gameField)

    init {
        PlayerProvider.prepareNewPlayersInstances(desiredPlayerNumber)
        PlayerProvider.prepareNextPlayer() // this invocation sets the activePlayer to the starting Player among others
    }

    /**
     * the same as makeMove(...) - this reduction is made for convenience as this method is the most frequently used
     */
    override fun mm(x: Int, y: Int, z: Int) = makeMove(x, y, z)

    /**
     * this is the only way for a client to make progress in the game.
     * there is no need to set active player - it's detected & returned automatically, like the next cartridge in revolver.
     */
    override fun makeMove(x: Int, y: Int, z: Int): AtttPlayer {
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
    internal fun makeMove(where: Coordinates, what: AtttPlayer = PlayerProvider.activePlayer): AtttPlayer =
        if (gameField.placeNewMark(where, what)) {
            chosenAlgorithm.getMaxLengthAchievedForThisMove(where)?.let {
                Log.pl("makeMove: maxLength for this move of player ${what.getName()} is: $it")
                // this cast is secure as PlayerModel is direct inheritor to AtttPlayer
                (what as PlayerModel).tryToSetMaxLineLength(it)
                gameRules.updatePlayerScore(what, it)
            }
            PlayerProvider.prepareNextPlayer(gameRules.isGameWon())
            PlayerProvider.activePlayer
        } else {
            what // current player's mark was not successfully placed - prepared player stays the same
        }

    /**
     * a client might want to know the currently leading player at any time during the game
     */
    override fun getLeader(): AtttPlayer = gameRules.getLeadingPlayer()

    /**
     * there can be only one winner - Player.None is returned until the winner not yet detected
     */
    override fun getWinner(): AtttPlayer = gameRules.getWinner()

    /**
     * after the winner is detected there is no way to modify the game field, so the game state is preserved
     */
    override fun isGameWon() = gameRules.isGameWon()

    override fun isGameFinished(): Boolean = isGameWon() || gameField.isCompletelyOccupied(is3D)

    /**
     * prints the current state of the game in 2d on console
     */
    override fun printCurrentFieldIn2d() {
        // reasonable sideLength here is 1 -> minIndex = 0 -> only one layer in Z dimension will exist
        val zAxisSize = if (is3D) gameField.sideLength else 1
        // not using Log.pl here as this action is intentional & has not be able to switch off
        println(gameField.prepareForPrinting3dIn2d(chosenAlgorithm, zAxisSize))
    }
}
