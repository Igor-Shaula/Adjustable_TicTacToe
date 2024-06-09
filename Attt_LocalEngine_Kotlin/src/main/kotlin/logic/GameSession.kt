package logic

import elements.Coordinates
import elements.Player
import publicApi.AtttGame
import publicApi.AtttPlayer
import utilities.Log

/**
 * this is the main contacting point for any game UI. the game is fully controlled with this singleton.
 * a game session can be started & finished, each time new to be clear from any possible remains.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class GameSession(desiredFieldSize: Int, desiredMaxLineLength: Int, desiredPlayerNumber: Int) : AtttGame {

    internal var gameField: GameField = GameField(desiredFieldSize)
    private var gameRules: GameRules = GameRules(desiredMaxLineLength)

    init {
        PlayerProvider.prepareNewPlayersInstances(desiredPlayerNumber)
        PlayerProvider.presetNextPlayer() // this invocation sets the activePlayer to the starting Player among others
    }

    // -------
    // region PUBLIC API

    /**
     * the same as makeMove(...) - this reduction is made for convenience as this method is the most frequently used
     */
    override fun mm(x: Int, y: Int) = makeMove(x, y)

    /**
     * this is the only way for a client to make progress in the game.
     * there is no need to set active player - it's detected & returned automatically, like the next cartridge in revolver.
     */
    override fun makeMove(x: Int, y: Int): AtttPlayer = if (gameField.isCorrectPosition(x, y)) {
        makeMove(Coordinates(x, y))
    } else {
        PlayerProvider.activePlayer
    }

    /**
     * this function is actually the only place for making moves and thus changing the game field
     */
    internal fun makeMove(where: Coordinates, what: AtttPlayer = PlayerProvider.activePlayer): AtttPlayer =
        if (gameField.placeNewMark(where, what)) {
            gameField.detectMaxLineLengthForNewMark(where)?.let {
                Log.pl("makeMove: maxLength for this move of player ${what.getName()} is: $it")
                (what as Player).tryToSetMaxLineLength(it) // this cast is secure as Player is direct inheritor to AtttPlayer
                updateGameScore(what, it)
            }
            PlayerProvider.presetNextPlayer()
        } else {
            what
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

    /**
     * prints the current state of the game in 2d on console
     */
    override fun printCurrentFieldIn2d() {
        // not using Log.pl here as this action is intentional & has not be able to switch off
        println(gameField.prepareForPrintingIn2d())
    }

    // endregion PUBLIC API
    // --------
    // region ALL PRIVATE

    /**
     * gameRules data is updated only here
     */
    private fun updateGameScore(whichPlayer: AtttPlayer, detectedLineLength: Int) {
        gameRules.updatePlayerScore(whichPlayer, detectedLineLength)
        if (gameRules.isGameWon()) {
            Log.pl("player ${gameRules.getWinner().getId()} wins with detectedLineLength: $detectedLineLength")
        }
    }

    // endregion ALL PRIVATE
}
