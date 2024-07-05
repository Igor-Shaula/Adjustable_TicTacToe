package gameLogic

import attt.Player
import constants.MAX_GAME_FIELD_SIDE_SIZE
import constants.MIN_GAME_FIELD_SIDE_SIZE
import constants.SYMBOL_FOR_ABSENT_MARK
import constants.SYMBOL_FOR_DIVIDER
import geometry.Line
import geometry.abstractions.Coordinates
import geometry.abstractions.OneMoveProcessing
import geometry.concept2D.NearestAreaScanWith2D
import geometry.concept3D.NearestAreaScanWith3D
import geometry.conceptXY.NearestAreaScanWithXY
import players.PlayerModel
import utilities.Log

/**
 * represents the area/space where all players' marks are placed and exist through one active game session
 */
internal class GameField(
    private val is3D: Boolean, sideLength: Int // it's impossible to add private setter for sideLength here
) {
    // to distinguish between older XY-based logic and new multi-axis-based approach for coordinates processing
    private val useNewDimensionsBasedLogic = true

    // the only place for switching between kinds of algorithms for every move processing
    internal val chosenAlgorithm: OneMoveProcessing =
        if (is3D) NearestAreaScanWith3D(this) // NewDimensionsBasedLogic is used as the only option here
        else if (useNewDimensionsBasedLogic) NearestAreaScanWith2D(this) else NearestAreaScanWithXY(this)

    // let's NOT write default marks into the initial field for the game - to save memory & speed-up a new game start
    private val theMap: MutableMap<Coordinates, Player> = mutableMapOf() // initially empty to save memory

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

    internal fun getCoordinatesFor(x: Int, y: Int, z: Int): Coordinates = chosenAlgorithm.getCoordinatesFor(x, y, z)

    /**
     * returns beautiful & simple String representation of the current state of game field
     */
    internal fun prepareForPrinting3dIn2d(
        givenMap: MutableMap<Coordinates, Player> = theMap
    ): String = buildString {
        val zAxisSize: Int = if (is3D) sideLength else 1 // this default value works for tests
        for (y in 0 until sideLength) { // Y is outer just for convenience when printing slices by Z axis
            appendLine()
            for (z in 0 until zAxisSize) { // will work only once for 2D
                for (x in 0 until sideLength) {
                    append(givenMap[chosenAlgorithm.getCoordinatesFor(x, y, z)]?.symbol ?: SYMBOL_FOR_ABSENT_MARK)
                    append(SYMBOL_FOR_DIVIDER) // between adjacent marks inside one field slice
                }
                repeat(2) { append(SYMBOL_FOR_DIVIDER) } // between the fields for each slice of every Z axis value
            }
        }
    }

    internal fun prepareForPrintingPlayerLines(
        player: Player, allExistingLinesForThisPlayer: MutableSet<Line?>?
    ): String {
        val onePlayerMap: MutableMap<Coordinates, Player> = mutableMapOf() // initially empty to save memory
        allExistingLinesForThisPlayer?.forEach { line ->
            line?.marks?.forEach { mark ->
                onePlayerMap[mark] = player
            }
        }
        return prepareForPrinting3dIn2d(onePlayerMap)
    }

    internal fun prepareTheWinningLineForPrinting(player: Player, winningLine: Line): String {
        val winnerLineOnMap: MutableMap<Coordinates, Player> = mutableMapOf() // initially empty to save memory
        val winner = PlayerModel.markWinner(player)
        winningLine.marks.forEach { mark -> // each mark has coordinates relevant to chosenAlgorithm
            winnerLineOnMap[mark] = winner
        }
        return prepareForPrinting3dIn2d(winnerLineOnMap)
    }

    /**
     * allows to see what's inside this game field space for the given coordinates
     */
    internal fun getCurrentMarkAt(coordinates: Coordinates): Player? = theMap[coordinates]

    internal fun containsTheSameMark(what: Player?, potentialSpot: Coordinates) = what == theMap[potentialSpot]

    internal fun belongToTheSameRealPlayer(givenPlace: Coordinates, potentialSpot: Coordinates): Boolean {
        val newMark = theMap[potentialSpot] // optimization to do finding in map only once
        return newMark != null && newMark != PlayerModel.None && newMark == theMap[givenPlace]
    }

    /**
     * ensures that the game field has correct size & is clear, so it is safe to use it for a new game
     */
    internal fun isReady(): Boolean =
        sideLength in MIN_GAME_FIELD_SIDE_SIZE..MAX_GAME_FIELD_SIDE_SIZE && theMap.isEmpty()

    internal fun placeNewMark(where: Coordinates, what: Player): Boolean = if (theCellIsVacant(where)) {
        theMap[where] = what
        true // new mark is successfully placed
    } else {
        Log.pl("attempting to set a mark for player $what at the occupied coordinates: $where")
        // later we can also emit a custom exception here - to be caught on the UI side and ask for another point
        false // new mark is not placed because the space has been already occupied
    }

    internal fun isCompletelyOccupied(): Boolean {
        Log.pl("isCompletelyOccupied: theMap.size = ${theMap.size}")
        val maxNumberOfSpaces = if (is3D) {
            sideLength * sideLength * sideLength
        } else {
            sideLength * sideLength
        }
        Log.pl("maxNumberOfSpaces = $maxNumberOfSpaces")
        return theMap.size >= maxNumberOfSpaces
    }

    internal fun getField() = theMap as Map<Coordinates, Player> // strictly immutable, of course

    internal fun getLayerForZ(z: Int): Map<Coordinates, Player> = theMap.filter { entry -> entry.key.z == z }

    /**
     * purpose of this function is to save performance on finding the value by the key -
     * it's done only once instead of twice - with help of "let" function.
     * also there is no guarantee that None player mark cannot be written anywhere on the field,
     * so it was decided to check it as well - and for this we did "theMap.get(where)" twice previously.
     */
    private fun theCellIsVacant(where: Coordinates) = theMap[where]?.let { PlayerModel.None == it } ?: true
}
