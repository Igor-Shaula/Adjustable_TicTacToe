package elements

internal interface LineDirection {
    fun opposite(): LineDirection
}

sealed interface GameSpace

/**
 * a spot with any coordinate which does not exist on the reasonably sized field - is considered to be its border
 */
internal data object Border : GameSpace

open class Coordinates(open val x: Int, open val y: Int) : GameSpace {

    /**
     * detects if given coordinates are correct in the currently active game field
     */
    internal fun existsWithin(sideLength: Int): Boolean =
        x in 0 until sideLength && y in 0 until sideLength
}

data class OneAxis(val l: Int)