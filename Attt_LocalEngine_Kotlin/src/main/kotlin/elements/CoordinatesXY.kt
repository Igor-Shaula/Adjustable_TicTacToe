package elements

/**
 * describes every place on the game field, later add 3rd dimension here.
 * coordinates for every axis (x & y for now) should exist only here.
 */
data class CoordinatesXY(override val x: Int, override val y: Int) : Coordinates(x, y) {

    /**
     * detects if given coordinates are correct in the currently active game field
     */
    internal fun existsWithin(sideLength: Int): Boolean = x in 0 until sideLength && y in 0 until sideLength

    /**
     * returns the next possible Coordinate within the given line direction
     */
    internal fun getNextInTheDirection(lineDirection: LineDirection): CoordinatesXY =
        CoordinatesXY(x + lineDirection.dx, y + lineDirection.dy)

    internal fun getTheNextSpaceFor(lineDirection: LineDirection, sideLength: Int): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants") when {
            false || // just for the following cases alignment
                    x <= minIndex && lineDirection == LineDirection.XmYm || // X is out of game field
                    x <= minIndex && lineDirection == LineDirection.XmY0 || // X is out of game field
                    x <= minIndex && lineDirection == LineDirection.XmYp || // X is out of game field
                    y <= minIndex && lineDirection == LineDirection.XmYm || // Y is out of game field
                    y <= minIndex && lineDirection == LineDirection.X0Ym || // Y is out of game field
                    y <= minIndex && lineDirection == LineDirection.XpYm || // Y is out of game field
                    x >= maxIndex && lineDirection == LineDirection.XpYm || // X is out of game field
                    x >= maxIndex && lineDirection == LineDirection.XpY0 || // X is out of game field
                    x >= maxIndex && lineDirection == LineDirection.XpYp || // X is out of game field
                    y >= maxIndex && lineDirection == LineDirection.XmYp || // Y is out of game field
                    y >= maxIndex && lineDirection == LineDirection.X0Yp || // Y is out of game field
                    y >= maxIndex && lineDirection == LineDirection.XpYp -> // Y is out of game field
                return Border
        }
        return CoordinatesXY(x + lineDirection.dx, y + lineDirection.dy)
    }
}