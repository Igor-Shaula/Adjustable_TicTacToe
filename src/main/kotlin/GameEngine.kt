/**
 * this class is the main contacting point for any game UI.
 * as any UI is meant to use only one game logic - let it be a singleton.
 */
object GameEngine {

    // let's not consume RAM with game objects until the game is not yet started - that's why these are nullable
    private var gameField: GameField? = null
    private var gameRules: GameRules? = null

    // -------
    // region PUBLIC API

    /**
     * create & provide the UI with a new game field, adjustability starts here - in the parameters
     */
    fun prepare(
        newGameField: GameField, newGameRules: GameRules
    ) {
        clear() // for all possible resources that could be used previously
        gameField = newGameField
        gameRules = newGameRules
    }

    // stop right now, count the achieved score for all players and show the result
    fun finish() {
        // todo: count and show the score here - a bit later
        clear()
    }

    fun save() { // todo: here we might specify a filename
        // later
    }

    fun restore() { // todo: specify what exactly to restore, a file for example
        // later
    }

    // this function is actually the single place for making moves and thus changing the game field
    fun makeNewMove(where: Coordinates, what: WhichPlayer) {
        if (gameField?.placeNewDot(where, what) == true) {
            // analyze this new dot & detect if it creates or changes any lines
            val lineDirection = checkNewDotArea(where, what)
            println("makeNewMove: detected existing line in direction: $lineDirection")
            if (lineDirection != LineDirection.None) {
                // here we already have a detected line of 2 minimum dots, now let's measure its full potential length
                // we also have a proven placed dot of the same player in the detected line direction
                // so, we only have to inspect next potential dot of the same direction -> let's prepare the coordinates:
                val checkedNearCoordinates = getTheNextSpotFor(where, lineDirection)
                measureLineFrom(checkedNearCoordinates, lineDirection, 2)
            }
        }
    }

    fun isRunning() = gameField != null && gameRules != null

    // needed for UI to draw current state of the game, or simply to update the UI before making a new move
    fun getCurrentField() = gameField?.theMap

    // endregion PUBLIC API
    // --------
    // region ALL PRIVATE

    // immediately clear if anything is running at the moment
    private fun clear() {
        gameField?.clear()
        gameField = null
        gameRules = null
    }

    private fun checkNewDotArea(where: Coordinates, what: WhichPlayer): LineDirection {
        val x = where.x
        val y = where.y
        gameField?.let { field ->
            val minIndex = field.minIndex
            val maxIndex = field.maxIndex
            return when {
                x > minIndex && field.theMap[Coordinates(x - 1, y)] == what -> LineDirection.XmY0
                x < maxIndex && field.theMap[Coordinates(x + 1, y)] == what -> LineDirection.XpY0
                y > minIndex && field.theMap[Coordinates(x, y - 1)] == what -> LineDirection.X0Ym
                y < maxIndex && field.theMap[Coordinates(x, y + 1)] == what -> LineDirection.X0Yp
                x > minIndex && y > minIndex && field.theMap[Coordinates(x - 1, y - 1)] == what -> LineDirection.XmYm
                x < maxIndex && y < maxIndex && field.theMap[Coordinates(x + 1, y + 1)] == what -> LineDirection.XpYp
                x > minIndex && y < maxIndex && field.theMap[Coordinates(x - 1, y + 1)] == what -> LineDirection.XmYp
                x < maxIndex && y > minIndex && field.theMap[Coordinates(x + 1, y - 1)] == what -> LineDirection.XpYm
                else -> LineDirection.None
            }
        }
        return LineDirection.None // this line should not ever be reached
    }

    internal fun measureLineFrom(start: Coordinates, lineDirection: LineDirection, startingLength: Int): Int {
        // firstly measure in the given direction and then in the opposite, also recursively
        gameField?.let { field ->
            val nextCoordinates = getTheNextSpotFor(start, lineDirection)
            println("measureLineFrom: start coordinates: $start")
            println("measureLineFrom: next coordinates: $nextCoordinates")
            if (field.theMap[nextCoordinates] == field.theMap[start]) {
                return measureLineFrom(nextCoordinates, lineDirection, startingLength + 1)
            } // else the given startingLength is returned
        }
        return startingLength
    }

    // endregion ALL PRIVATE
}

internal fun getTheNextSpotFor(start: Coordinates, lineDirection: LineDirection) =
    Coordinates(x = start.x + lineDirection.dx, y = start.y + lineDirection.dy)

/**
 * describes all possible directions for the simplest line of 2 dots on a 2d field
 * m -> minus, p -> plus, 0 -> no change along this axis
 */
enum class LineDirection(val dx: Int, val dy: Int) {
    XmY0(-1, 0),
    XpY0(+1, 0),
    X0Ym(0, -1),
    X0Yp(0, +1),
    XmYm(-1, -1),
    XpYp(+1, +1),
    XmYp(-1, +1),
    XpYm(+1, -1),
    None(0, 0)
}
