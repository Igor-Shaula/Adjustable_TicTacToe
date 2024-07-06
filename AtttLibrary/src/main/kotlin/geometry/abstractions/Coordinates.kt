package geometry.abstractions

/**
 * describes any real position which may exist on the game field.
 * has to be a class because we definitely need many instances of it.
 */
internal abstract class Coordinates(open val x: Int, open val y: Int, open val z: Int = 0) : GameSpace {

    /**
     * detects if given coordinates are correct in the currently active game field
     */
    internal fun existsWithin(sideLength: Int): Boolean =
        x in 0 until sideLength && y in 0 until sideLength && z in 0 until sideLength

    abstract fun getNextInTheDirection(lineDirection: LineDirection): Coordinates

    abstract fun getTheNextSpaceFor(lineDirection: LineDirection, sideLength: Int): GameSpace
}