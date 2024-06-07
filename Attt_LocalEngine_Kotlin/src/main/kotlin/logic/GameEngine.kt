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
class GameEngine(desiredFieldSize: Int, desiredMaxLineLength: Int) : AtttGame {

    // let's not consume RAM with game objects until the game is not yet started - that's why these are nullable
    internal var gameField: GameField? = null
    private var gameRules: GameRules? = null

    // this is a part of inner game logic - it should be used only internally, for now there's no need to show it to a client
    internal var activePlayer: AtttPlayer = Player.None

    init {
        gameField = GameField(desiredFieldSize)
        gameRules = GameRules(desiredMaxLineLength)
        prepareNextPlayer()
    }

    // -------
    // region PUBLIC API

    /**
     * create & provide the UI with a new game field, adjustability starts here - in the parameters
     */
//    override fun prepareGame(desiredFieldSize: Int, desiredMaxLineLength: Int): AtttPlayer {
//        if (gameExists()) finish() // any previous game should be stopped before a new one is launched on this GameEngine
//        gameField = GameField(desiredFieldSize)
//        gameRules = GameRules(desiredMaxLineLength)
//        return prepareNextPlayer() // absolutely necessary to have this invocation here - it prepares the first player's move
//    }

    /**
     * stop right now and clear all occupied resources, this game session gets impossible for any use
     */
    @Suppress("MemberVisibilityCanBePrivate")
    override fun finish() {
        Log.pl("the game is finished in the given state: ${gameField?.prepareForPrintingIn2d()}")
        gameField?.clear()
        gameField = null
        gameRules?.clear()
        gameRules = null
        activePlayer = Player.None
    }

    /**
     * the same as makeMove(...) - this reduction is made for convenience as this method is the most frequently used
     */
    override fun mm(x: Int, y: Int) = makeMove(x, y)

    /**
     * this is the only way for a client to make progress in the game.
     * there is no need to set active player - it's detected & returned automatically, like the next cartridge in revolver.
     */
    override fun makeMove(x: Int, y: Int): AtttPlayer = if (gameField?.isCorrectPosition(x, y) == true) {
        makeMove(Coordinates(x, y))
    } else {
        activePlayer
    }

    /**
     * this function is actually the only place for making moves and thus changing the game field
     */
    internal fun makeMove(where: Coordinates, what: AtttPlayer = activePlayer): AtttPlayer =
        gameField?.let { field ->
            if (field.placeNewMark(where, what)) {
                // analyze this new dot & detect if it creates or changes any lines in all possible directions
                val existingLineDirections = field.detectAllExistingLineDirectionsFromThePlacedMark(where)
                val maxLengthForThisMove = existingLineDirections.maxOfOrNull { lineDirection ->
                    field.measureFullLengthForExistingLineFrom(where, lineDirection)
                }
                Log.pl("makeMove: maxLength for this move of player $what is: $maxLengthForThisMove")
                maxLengthForThisMove?.let {
                    (what as Player).tryToSetMaxLineLength(it) // this cast is secure as Player is direct inheritor to AtttPlayer
                    updateGameScore(what, it)
                }
                return prepareNextPlayer() // todo: check all possible & impossible cases of forcing moves by different players
            } else {
                return what
            }
        } ?: Player.None // when there is no field - there should be no player

    /**
     * a client might want to know the currently leading player at any time during the game
     */
    override fun getLeader(): AtttPlayer = gameRules?.getLeadingPlayer() ?: Player.None

    /**
     * there can be only one winner - Player.None is returned until the winner not yet detected
     */
    override fun getWinner(): AtttPlayer = gameRules?.getWinner() ?: Player.None

    /**
     * after the winner is detected there is no way to modify the game field, so the game state is preserved
     */
    override fun isGameWon() = gameRules?.isGameWon() == true

    /**
     * prints the current state of the game in 2d on console
     */
    override fun printCurrentFieldIn2d() {
        println(gameField?.prepareForPrintingIn2d())
    }

    // endregion PUBLIC API
    // --------
    // region ALL PRIVATE

    /**
     * if a game session exists - than we have an actively running game which can have a winner or not
     */
    private fun gameExists() = gameField != null && gameRules != null

    /**
     * sets the currently active player, for which a move will be made & returns the player for the next move
     */
    private fun prepareNextPlayer(): AtttPlayer {
        activePlayer = if (activePlayer == Player.A) Player.B else Player.A // A is set after None & null case as well
        return activePlayer
    }

    /**
     * gameRules data is updated only here
     */
    private fun updateGameScore(whichPlayer: AtttPlayer, detectedLineLength: Int) {
        gameRules?.let { rules ->
            rules.updatePlayerScore(whichPlayer, detectedLineLength)
            if (rules.isGameWon()) {
                Log.pl("player ${rules.getWinner()} wins with detectedLineLength: $detectedLineLength")
            }
        }
    }

    // endregion ALL PRIVATE
}
