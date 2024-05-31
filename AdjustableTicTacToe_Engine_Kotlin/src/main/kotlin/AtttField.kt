/**
 * AtttField = Adjustable TicTacToe Field
 * represents the area/space where all players' marks are placed.
 */
@Suppress("UNUSED_PARAMETER")
class AtttField(
    private var sideLength: Int, // the only required parameter
    dimensions: Int = MIN_GAME_FIELD_DIMENSIONS, // simplest variant of a 2d game
    numberOfPlayers: Int = MIN_NUMBER_OF_PLAYERS, // this is obvious, can't be less
) {
    private val minIndex = 0 // this is obvious but let it be here for consistency
    private val maxIndex = sideLength - 1 // constant for the given game field
    internal val theMap: MutableMap<Coordinates, AtttPlayer> =
        mutableMapOf() // not nullable but needs to be configured later

    init {
        // here we're doing possible corrections that may be needed to keep the game rules reasonable
        if (sideLength > MAX_GAME_FIELD_SIDE_SIZE) sideLength = MAX_GAME_FIELD_SIDE_SIZE
        else if (sideLength < MIN_GAME_FIELD_SIDE_SIZE) sideLength = MIN_GAME_FIELD_SIDE_SIZE
        // let's create the initial board or field for the game
        for (x in 0 until sideLength) {
            for (y in 0 until sideLength) {
                theMap[Coordinates(x, y)] = AtttPlayer.None
            }
        }
    }

    internal fun clear() {
        theMap.clear()
    }

    internal fun placeNewMark(where: Coordinates, what: AtttPlayer): Boolean = if (theMap[where] == AtttPlayer.None) {
        theMap[where] = what
        true
    } else {
        Log.pl("attempting to set a mark for player $what on the occupied coordinates: $where")
        // later we can also emit a custom exception here - to be caught on the UI side and ask for another point
        false
    }

    internal fun detectPossibleLineDirectionNearThePlacedMark(fromWhere: Coordinates, what: AtttPlayer): LineDirection {
        val x = fromWhere.x
        val y = fromWhere.y
        Log.pl("checkPlacedMarkArea: x, y = $x, $y")
        return when {
            x > minIndex && theMap[Coordinates(x - 1, y)] == what -> LineDirection.XmY0
            x < maxIndex && theMap[Coordinates(x + 1, y)] == what -> LineDirection.XpY0
            y > minIndex && theMap[Coordinates(x, y - 1)] == what -> LineDirection.X0Ym
            y < maxIndex && theMap[Coordinates(x, y + 1)] == what -> LineDirection.X0Yp
            x > minIndex && y > minIndex && theMap[Coordinates(x - 1, y - 1)] == what -> LineDirection.XmYm
            x < maxIndex && y < maxIndex && theMap[Coordinates(x + 1, y + 1)] == what -> LineDirection.XpYp
            x > minIndex && y < maxIndex && theMap[Coordinates(x - 1, y + 1)] == what -> LineDirection.XmYp
            x < maxIndex && y > minIndex && theMap[Coordinates(x + 1, y - 1)] == what -> LineDirection.XpYm
            else -> LineDirection.None
        }
    }

    internal fun getTheNextSafeSpaceFor(start: Coordinates, lineDirection: LineDirection): GameSpace {
        @Suppress("SimplifyBooleanWithConstants")
        when {
            false || // just for the following cases' alignment
                    start.x <= minIndex && lineDirection == LineDirection.XmYm ||
                    start.x <= minIndex && lineDirection == LineDirection.XmY0 ||
                    start.x <= minIndex && lineDirection == LineDirection.XmYp ||
                    start.y <= minIndex && lineDirection == LineDirection.XmYm ||
                    start.y <= minIndex && lineDirection == LineDirection.X0Ym ||
                    start.y <= minIndex && lineDirection == LineDirection.XpYm ||
                    start.x >= maxIndex && lineDirection == LineDirection.XpYm ||
                    start.x >= maxIndex && lineDirection == LineDirection.XpY0 ||
                    start.x >= maxIndex && lineDirection == LineDirection.XpYp ||
                    start.y >= maxIndex && lineDirection == LineDirection.XmYp ||
                    start.y >= maxIndex && lineDirection == LineDirection.X0Yp ||
                    start.y >= maxIndex && lineDirection == LineDirection.XpYp ->
                return Border
        }
        val x = start.x + lineDirection.dx
        val y = start.y + lineDirection.dy
        Log.pl("lineDirection = $lineDirection")
        Log.pl("start: x, y = ${start.x}, ${start.y}")
        Log.pl("dx, dy = ${lineDirection.dx}, ${lineDirection.dy}")
        Log.pl("x, y = $x, $y")
        return Coordinates(x, y)
    }

    /**
     * returns beautiful & simple String representation of the current state of game field
     */
    fun prepareForPrintingIn2d(): String {
        val sb: StringBuilder = StringBuilder(sideLength * (sideLength + 1))
        for (y in 0 until sideLength) {
            sb.append("\n")
            for (x in 0 until sideLength) {
                sb.append(theMap[Coordinates(x, y)]?.symbol).append(' ')
            }
        }
        return sb.toString()
    }

    /**
     * detects if given coordinates are correct in the currently active game field
     */
    fun isCorrectPosition(x: Int, y: Int): Boolean = x in 0 until sideLength && y in 0 until sideLength

    internal fun areMarksOfTheSamePlayer(start: Coordinates, nextCoordinates: Coordinates) =
        theMap[nextCoordinates] == theMap[start]

    fun exists() = theMap.isNotEmpty()
}
