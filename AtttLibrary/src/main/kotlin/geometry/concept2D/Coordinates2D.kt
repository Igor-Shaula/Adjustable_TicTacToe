package geometry.concept2D

import geometry.LineDirectionFor1Axis
import geometry.abstractions.Border
import geometry.abstractions.Coordinates
import geometry.abstractions.GameSpace
import geometry.abstractions.LineDirection

/**
 * extensible alternative for CoordinatesXY
 */
internal data class Coordinates2D(val xAxis: Int, val yAxis: Int) : Coordinates(xAxis, yAxis) {

    override fun getNextInTheDirection(lineDirection: LineDirection): Coordinates2D {
        lineDirection as LineDirectionFor2Axes
        return getNextInTheDirection(lineDirection.xAxisLD, lineDirection.yAxisLD)
    }

    override fun getTheNextSpaceFor(lineDirection: LineDirection, sideLength: Int): GameSpace {
        lineDirection as LineDirectionFor2Axes
        return getTheNextSpaceFor(lineDirection.xAxisLD, lineDirection.yAxisLD, sideLength)
    }

    private fun getNextInTheDirection(
        xAxisDirection: LineDirectionFor1Axis, yAxisDirection: LineDirectionFor1Axis
    ) = Coordinates2D( // should be exactly Coordinates2D
        xAxis + xAxisDirection.deltaOne, yAxis + yAxisDirection.deltaOne
    )

    private fun getTheNextSpaceFor(
        xAxisDirection: LineDirectionFor1Axis, yAxisDirection: LineDirectionFor1Axis, sideLength: Int
    ): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants") when {
            false || // just for the following cases alignment
                    xAxis <= minIndex && xAxisDirection == LineDirectionFor1Axis.Minus || // X is out of game field
                    xAxis >= maxIndex && xAxisDirection == LineDirectionFor1Axis.Plus || // X is out of game field
                    yAxis <= minIndex && yAxisDirection == LineDirectionFor1Axis.Minus || // X is out of game field
                    yAxis >= maxIndex && yAxisDirection == LineDirectionFor1Axis.Plus -> // X is out of game field
                return Border
        }
        return Coordinates2D(
            xAxis + xAxisDirection.deltaOne, yAxis + yAxisDirection.deltaOne
        )
    }
}