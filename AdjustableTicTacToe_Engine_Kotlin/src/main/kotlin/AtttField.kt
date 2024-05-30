/**
 * AtttField = Adjustable TicTacToe Field
 * represents the area/space where all players' marks are placed.
 */
@Suppress("UNUSED_PARAMETER")
class AtttField(
    private var sideLength: Int, // for a good old 3x3 game
    dimensions: Int = MIN_GAME_FIELD_DIMENSIONS, // simplest variant of a 2d game
    numberOfPlayers: Int = MIN_NUMBER_OF_PLAYERS, // this is obvious
) {
    internal val minIndex = 0 // this is obvious but let it be here for consistency
    internal val maxIndex = sideLength - 1 // constant for the given game field
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
}
