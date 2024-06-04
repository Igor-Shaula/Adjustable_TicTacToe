import internalElements.Coordinates
import publicApi.AtttGame
import utilities.Log

/**
 * AtttEngine = Adjustable TicTacToe Engine
 * this is the main contacting point for any game UI. the game is fully controlled with this singleton.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
internal object AtttEngine : AtttGame {

    // let's not consume RAM with game objects until the game is not yet started - that's why these are nullable
    internal var gameField: AtttField = AtttField(MIN_GAME_FIELD_SIDE_SIZE)
    private var gameRules: AtttRules = AtttRules(MIN_WINNING_LINE_LENGTH)

    // this should be used only internally, for now there's no need to show it to a client
    internal var activePlayer: AtttPlayer = AtttPlayer.None

    // -------
    // region PUBLIC API

    /**
     * create & provide the UI with a new game field, adjustability starts here - in the parameters
     */
    override fun prepare(
        newGameField: AtttField, newGameRules: AtttRules
    ): AtttPlayer { // game engine client must know who's the next to make a move on the board
        clear() // for all possible resources that could be used previously
        gameField = newGameField
        gameRules = newGameRules
        return prepareNextPlayer()
    }

    // stop right now, count the achieved score for all players and show the result
    @Suppress("MemberVisibilityCanBePrivate")
    override fun finish() {
        // todo: count and show the score here - a bit later
        Log.pl("the game is finished in the given state: ${gameField.prepareForPrintingIn2d()}")
        clear()
    }

    override fun mm(x: Int, y: Int) = makeMove(x, y)

    /**
     * this is the only way to make progress in the game.
     * there is no need to set active player - it's detected & returned automatically, like the next cartridge in revolver.
     */
    override fun makeMove(x: Int, y: Int): AtttPlayer = if (gameField.isCorrectPosition(x, y)) {
        makeMove(Coordinates(x, y))
    } else {
        activePlayer
    }

    // this function is actually the single place for making moves and thus changing the game field
    internal fun makeMove(where: Coordinates, what: AtttPlayer = activePlayer): AtttPlayer { // to avoid breaking tests
        if (gameField.placeNewMark(where, what)) {
            // analyze this new dot & detect if it creates or changes any lines in all possible directions
            val existingLineDirections = gameField.detectAllExistingLineDirectionsFromThePlacedMark(where)
            val maxLengthForThisMove = existingLineDirections.maxOfOrNull { lineDirection ->
                gameField.measureFullLengthForExistingLineFrom(where, lineDirection)
            }
            Log.pl("makeMove: maxLength for this move of player $what is: $maxLengthForThisMove")
            maxLengthForThisMove?.let { updateGameScore(what, it) }
            return prepareNextPlayer() // todo: check all possible & impossible cases of forcing moves by different players
        } else {
            return activePlayer
        }
    }

    override fun isActive() = gameField.exists() // todo: add checks for gameRules here as well

    /**
     * prints the current state of the game in 2d on console
     */
    override fun printCurrentFieldIn2d() {
        println(gameField.prepareForPrintingIn2d())
    }

    // endregion PUBLIC API
    // --------
    // region ALL PRIVATE

    // sets the currently active player, for which a move will be made & returns the player for the next move
    private fun prepareNextPlayer(): AtttPlayer {
        activePlayer =
            if (activePlayer == AtttPlayer.A) AtttPlayer.B else AtttPlayer.A // A is set after None case as well
        return activePlayer
    }

    // immediately clear if anything is running at the moment
    private fun clear() {
        gameField.clear()
        activePlayer = AtttPlayer.None
    }

    private fun updateGameScore(whichPlayer: AtttPlayer, detectedLineLength: Int) {
        if (gameRules.isGameWon(detectedLineLength)) {
            Log.pl("player $whichPlayer wins with detectedLineLength: $detectedLineLength")
            finish()
        }
    }

    // endregion ALL PRIVATE
}
