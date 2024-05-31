/**
 * AtttEngine = Adjustable TicTacToe Engine
 * this is the main contacting point for any game UI. the game is fully controlled with this singleton.
 */
@Suppress("unused")
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
    override fun makeMove(x: Int, y: Int): AtttPlayer =
        if (gameField.isCorrectPosition(x, y)) {
            makeMove(Coordinates(x, y))
        } else {
            activePlayer
        }

    // this function is actually the single place for making moves and thus changing the game field
    internal fun makeMove(where: Coordinates, what: AtttPlayer = activePlayer): AtttPlayer { // to avoid breaking tests
        if (gameField.placeNewMark(where, what)) {
            // analyze this new dot & detect if it creates or changes any lines
            val lineDirection = gameField.detectPossibleLineDirectionNearThePlacedMark(where, what)
            Log.pl("makeNewMove: detected existing line in direction: $lineDirection")
            if (lineDirection != LineDirection.None) {
                // here we already have a detected line of 2 minimum dots, now let's measure its full potential length
                // we also have a proven placed dot of the same player in the detected line direction
                // so, we only have to inspect next potential dot of the same direction -> let's prepare the coordinates:
                val checkedNearCoordinates = getTheNextSafeSpotFor(where, lineDirection)
                if (checkedNearCoordinates is Coordinates) {
                    val lineTotalLength =
                        measureLineFrom(checkedNearCoordinates, lineDirection, 2) +
                                measureLineFrom(where, lineDirection.opposite(), 0)
                    Log.pl("makeNewMove: lineTotalLength = $lineTotalLength")
                    updateGameScore(what, lineTotalLength)
                } // else checkedNearCoordinates cannot be Border or anything else from Coordinates type
            }
        }
        return prepareNextPlayer()
    }

    override fun isActive() = gameField.theMap.isNotEmpty()

    // needed for UI to draw current state of the game, or simply to update the UI before making a new move
    internal fun getCurrentField() = gameField.theMap

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

    internal fun measureLineFrom(start: Coordinates, lineDirection: LineDirection, startingLength: Int): Int {
        Log.pl("measureLineFrom: startingLength: $startingLength")
        // firstly measure in the given direction and then in the opposite, also recursively
        val nextCoordinates = getTheNextSafeSpotFor(start, lineDirection)
        Log.pl("measureLineFrom: start coordinates: $start")
        Log.pl("measureLineFrom: next coordinates: $nextCoordinates")
        return if (nextCoordinates is Coordinates && gameField.theMap[nextCoordinates] == gameField.theMap[start]) {
            measureLineFrom(nextCoordinates, lineDirection, startingLength + 1)
        } else {
            Log.pl("measureLineFrom: ELSE -> exit: $startingLength")
            startingLength
        }
    }

    internal fun getTheNextSafeSpotFor(start: Coordinates, lineDirection: LineDirection): GameSpace {
        @Suppress("SimplifyBooleanWithConstants")
        when {
            false || // just for the following cases' alignment
                    start.x <= gameField.minIndex && lineDirection == LineDirection.XmYm ||
                    start.x <= gameField.minIndex && lineDirection == LineDirection.XmY0 ||
                    start.x <= gameField.minIndex && lineDirection == LineDirection.XmYp ||
                    start.y <= gameField.minIndex && lineDirection == LineDirection.XmYm ||
                    start.y <= gameField.minIndex && lineDirection == LineDirection.X0Ym ||
                    start.y <= gameField.minIndex && lineDirection == LineDirection.XpYm ||
                    start.x >= gameField.maxIndex && lineDirection == LineDirection.XpYm ||
                    start.x >= gameField.maxIndex && lineDirection == LineDirection.XpY0 ||
                    start.x >= gameField.maxIndex && lineDirection == LineDirection.XpYp ||
                    start.y >= gameField.maxIndex && lineDirection == LineDirection.XmYp ||
                    start.y >= gameField.maxIndex && lineDirection == LineDirection.X0Yp ||
                    start.y >= gameField.maxIndex && lineDirection == LineDirection.XpYp ->
                return Border
        }
        return Coordinates(x = start.x + lineDirection.dx, y = start.y + lineDirection.dy)
    }

    private fun updateGameScore(whichPlayer: AtttPlayer, detectedLineLength: Int) {
        if (gameRules.isGameWon(detectedLineLength)) {
            Log.pl("player $whichPlayer wins with detectedLineLength: $detectedLineLength")
            finish()
        }
    }

    // endregion ALL PRIVATE
}
