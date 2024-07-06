package geometry.conceptXY

import geometry.abstractions.Border
import geometry.abstractions.Coordinates
import geometry.abstractions.GameSpace
import geometry.abstractions.LineDirection

/**
 * describes every place on the game field, later add 3rd dimension here.
 * coordinates for every axis (x & y for now) should exist only here.
 */
internal data class CoordinatesXY(override val x: Int, override val y: Int) : Coordinates(x, y) {

    /**
     * returns the next possible Coordinate within the given line direction - should be exactly CoordinatesXY
     */
    override fun getNextInTheDirection(lineDirection: LineDirection): CoordinatesXY {
        lineDirection as LineDirectionForXY
        return CoordinatesXY(x + lineDirection.dx, y + lineDirection.dy)
    }

    override fun getTheNextSpaceFor(lineDirection: LineDirection, sideLength: Int): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants") when {
            false || // just for the following cases alignment
                    x <= minIndex && lineDirection == LineDirectionForXY.XmYm || // X is out of game field
                    x <= minIndex && lineDirection == LineDirectionForXY.XmY0 || // X is out of game field
                    x <= minIndex && lineDirection == LineDirectionForXY.XmYp || // X is out of game field
                    y <= minIndex && lineDirection == LineDirectionForXY.XmYm || // Y is out of game field
                    y <= minIndex && lineDirection == LineDirectionForXY.X0Ym || // Y is out of game field
                    y <= minIndex && lineDirection == LineDirectionForXY.XpYm || // Y is out of game field
                    x >= maxIndex && lineDirection == LineDirectionForXY.XpYm || // X is out of game field
                    x >= maxIndex && lineDirection == LineDirectionForXY.XpY0 || // X is out of game field
                    x >= maxIndex && lineDirection == LineDirectionForXY.XpYp || // X is out of game field
                    y >= maxIndex && lineDirection == LineDirectionForXY.XmYp || // Y is out of game field
                    y >= maxIndex && lineDirection == LineDirectionForXY.X0Yp || // Y is out of game field
                    y >= maxIndex && lineDirection == LineDirectionForXY.XpYp -> // Y is out of game field
                return Border
        }
        lineDirection as LineDirectionForXY
        return CoordinatesXY(x + lineDirection.dx, y + lineDirection.dy)
    }
}