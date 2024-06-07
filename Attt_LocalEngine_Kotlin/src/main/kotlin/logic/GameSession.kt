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
class GameSession(desiredFieldSize: Int, desiredMaxLineLength: Int) : AtttGame {

    internal var gameField: GameField = GameField(desiredFieldSize)
    private var gameRules: GameRules = GameRules(desiredMaxLineLength)

    // this is a part of inner game logic - it should be used only internally, for now there's no need to show it to a client
    internal var activePlayer: AtttPlayer = PlayerProvider.None

    init {
        PlayerProvider.prepareNewPlayersInstances()
        prepareNextPlayer() // this invocation sets the activePlayer to the first Player among others
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
        activePlayer
    }

    /**
     * this function is actually the only place for making moves and thus changing the game field
     */
    internal fun makeMove(where: Coordinates, what: AtttPlayer = activePlayer): AtttPlayer =
        if (gameField.placeNewMark(where, what)) {
            // analyze this new dot & detect if it creates or changes any lines in all possible directions
            val existingLineDirections = gameField.detectAllExistingLineDirectionsFromThePlacedMark(where)
            val maxLengthForThisMove = existingLineDirections.maxOfOrNull { lineDirection ->
                gameField.measureFullLengthForExistingLineFrom(where, lineDirection)
            }
            Log.pl("makeMove: maxLength for this move of player $what is: $maxLengthForThisMove")
            maxLengthForThisMove?.let {
                (what as Player).tryToSetMaxLineLength(it) // this cast is secure as Player is direct inheritor to AtttPlayer
                updateGameScore(what, it)
            }
            prepareNextPlayer()
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
    override fun getWinner(): AtttPlayer = gameRules.getWinner() ?: PlayerProvider.None

    /**
     * after the winner is detected there is no way to modify the game field, so the game state is preserved
     */
    override fun isGameWon() = gameRules.isGameWon()

    /**
     * prints the current state of the game in 2d on console
     */
    override fun printCurrentFieldIn2d() {
        println(gameField.prepareForPrintingIn2d())
    }

    // endregion PUBLIC API
    // --------
    // region ALL PRIVATE

    /**
     * sets the currently active player, for which a move will be made & returns the player for the next move
     */
    private fun prepareNextPlayer(): AtttPlayer {
        activePlayer =
            if (activePlayer == PlayerProvider.A) PlayerProvider.B
            else PlayerProvider.A // A is set after None & null case as well
        return activePlayer
    }

    /**
     * gameRules data is updated only here
     */
    private fun updateGameScore(whichPlayer: AtttPlayer, detectedLineLength: Int) {
        gameRules.updatePlayerScore(whichPlayer, detectedLineLength)
        if (gameRules.isGameWon()) {
            Log.pl("player ${gameRules.getWinner()} wins with detectedLineLength: $detectedLineLength")
        }
    }

    // endregion ALL PRIVATE
}
