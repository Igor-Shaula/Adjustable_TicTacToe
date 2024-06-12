package logic

import elements.*
import publicApi.AtttPlayer
import utilities.Log

/**
 * represents the area/space where all players' marks are placed and exist through one active game session
 */
internal class GameField(
    sideLength: Int // the only required parameter, by the way it's impossible to add private setter here
) {
    internal var sideLength = 42 // for some specifics of Kotlin this value is correctly set only inside init-block
        private set(value) { // I'm doing this for prevent from changing anywhere outside this class
            // here we're applying all possible corrections that may be needed to keep the game rules reasonable
            field = if (value > MAX_GAME_FIELD_SIDE_SIZE) MAX_GAME_FIELD_SIDE_SIZE
            else if (value < MIN_GAME_FIELD_SIDE_SIZE) MIN_GAME_FIELD_SIDE_SIZE
            else value
            Log.pl("sideLength setter: initial value = $value, assigned to the field: $field")
        }

    init {
        this.sideLength = sideLength // this is not obvious but absolutely needed here - proven by tests
    }

    // let's NOT write default marks into the initial field for the game - to save memory & speed-up a new game start
    private val theMap: MutableMap<Coordinates, AtttPlayer> = mutableMapOf() // initially empty to save memory

    /**
     * returns beautiful & simple String representation of the current state of game field
     */
    internal fun prepareForPrinting3dIn2d(
        chosenAlgorithm: OneMoveProcessing = NearestAreaScanWithXY(this)
    ): String {
        val sb = StringBuilder(sideLength * (sideLength + 2) * (sideLength + 1)) // for: y * (z+2) * (x+1)
        for (y in 0 until sideLength) {
            sb.append(SYMBOL_FOR_NEW_LINE)
            for (z in 0 until sideLength) { // will work only once for 2D
                for (x in 0 until sideLength) {
                    sb.append(theMap[chosenAlgorithm.getCoordinatesFor(x, y, z)]?.getSymbol() ?: SYMBOL_FOR_ABSENT_MARK)
                        .append(SYMBOL_FOR_DIVIDER) // between adjacent marks inside one field slice
                }
                repeat(2) { sb.append(SYMBOL_FOR_DIVIDER) } // between the fields for each slice of every Z axis value
            }
        }
        return sb.toString()
    }

    /**
     * allows to see what's inside this game field space for the given coordinates
     */
    internal fun getCurrentMarkAt(coordinates: Coordinates): AtttPlayer? = theMap[coordinates]

    internal fun containsTheSameMark(what: AtttPlayer?, potentialSpot: Coordinates) = what == theMap[potentialSpot]

    internal fun belongToTheSameRealPlayer(givenPlace: Coordinates, potentialSpot: Coordinates): Boolean {
        val newMark = theMap[potentialSpot] // optimization to do finding in map only once
        return newMark != null && newMark != PlayerProvider.None && newMark == theMap[givenPlace]
    }

    /**
     * ensures that the game field has correct size & is clear, so it is safe to use it for a new game
     */
    internal fun isReady(): Boolean =
        sideLength in MIN_GAME_FIELD_SIDE_SIZE..MAX_GAME_FIELD_SIDE_SIZE && theMap.isEmpty()

    internal fun placeNewMark(where: Coordinates, what: AtttPlayer): Boolean =
        if (theMap[where] == null || theMap[where] == PlayerProvider.None) { // PlayerProvider.None - to ensure all cases
            theMap[where] = what
            true // new mark is successfully placed
        } else {
            Log.pl("attempting to set a mark for player $what on the occupied coordinates: $where")
            // later we can also emit a custom exception here - to be caught on the UI side and ask for another point
            false // new mark is not placed because the space has been already occupied
        }
}
