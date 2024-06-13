package geometry.conceptXY

import elements.Border
import elements.Coordinates
import elements.GameSpace

/**
 * describes every place on the game field, later add 3rd dimension here.
 * coordinates for every axis (x & y for now) should exist only here.
 */
data class CoordinatesXY(override val x: Int, override val y: Int) : Coordinates(x, y) {

    /**
     * returns the next possible Coordinate within the given line direction
     */
    internal fun getNextInTheDirection(lineDirectionForXY: LineDirectionForXY): CoordinatesXY = // should be exactly CoordinatesXY
        CoordinatesXY(x + lineDirectionForXY.dx, y + lineDirectionForXY.dy)

    internal fun getTheNextSpaceFor(lineDirectionForXY: LineDirectionForXY, sideLength: Int): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants") when {
            false || // just for the following cases alignment
                    x <= minIndex && lineDirectionForXY == LineDirectionForXY.XmYm || // X is out of game field
                    x <= minIndex && lineDirectionForXY == LineDirectionForXY.XmY0 || // X is out of game field
                    x <= minIndex && lineDirectionForXY == LineDirectionForXY.XmYp || // X is out of game field
                    y <= minIndex && lineDirectionForXY == LineDirectionForXY.XmYm || // Y is out of game field
                    y <= minIndex && lineDirectionForXY == LineDirectionForXY.X0Ym || // Y is out of game field
                    y <= minIndex && lineDirectionForXY == LineDirectionForXY.XpYm || // Y is out of game field
                    x >= maxIndex && lineDirectionForXY == LineDirectionForXY.XpYm || // X is out of game field
                    x >= maxIndex && lineDirectionForXY == LineDirectionForXY.XpY0 || // X is out of game field
                    x >= maxIndex && lineDirectionForXY == LineDirectionForXY.XpYp || // X is out of game field
                    y >= maxIndex && lineDirectionForXY == LineDirectionForXY.XmYp || // Y is out of game field
                    y >= maxIndex && lineDirectionForXY == LineDirectionForXY.X0Yp || // Y is out of game field
                    y >= maxIndex && lineDirectionForXY == LineDirectionForXY.XpYp -> // Y is out of game field
                return Border
        }
        return CoordinatesXY(x + lineDirectionForXY.dx, y + lineDirectionForXY.dy)
    }
}