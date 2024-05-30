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
    val minIndex = 0 // this is obvious but let it be here for consistency
    val maxIndex = sideLength - 1 // constant for the given game field
    val theMap: MutableMap<AtttPlace, AtttPlayer> = mutableMapOf() // not nullable but needs to be configured later

    init {
        // here we're doing possible corrections that may be needed to keep the game rules reasonable
        if (sideLength > MAX_GAME_FIELD_SIDE_SIZE) sideLength = MAX_GAME_FIELD_SIDE_SIZE
        else if (sideLength < MIN_GAME_FIELD_SIDE_SIZE) sideLength = MIN_GAME_FIELD_SIDE_SIZE
        // let's create the initial board or field for the game
        for (x in 0..<sideLength) {
            for (y in 0..<sideLength) {
                theMap[AtttPlace(x, y)] = AtttPlayer.None
            }
        }
    }

    fun clear() {
        theMap.clear()
    }

    fun placeNewDot(where: AtttPlace, what: AtttPlayer): Boolean = if (theMap[where] == AtttPlayer.None) {
        theMap[where] = what
        true
    } else {
        Log.pl("attempting to set a mark for player $what on the occupied coordinates: $where")
        // later we can also emit a custom exception here - to be caught on the UI side and ask for another point
        false
    }

    fun prepareForPrintingIn2d(): String {
        val sb: StringBuilder = StringBuilder(sideLength * (sideLength + 1))
        for (y in 0..<sideLength) {
            sb.append("\n")
            for (x in 0..<sideLength) {
                sb.append(theMap[AtttPlace(x, y)]?.symbol).append(' ')
            }
        }
        return sb.toString()
    }
}
