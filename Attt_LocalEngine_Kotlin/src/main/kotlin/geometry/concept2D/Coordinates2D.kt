package geometry.concept2D

import geometry.abstractions.Border
import geometry.abstractions.Coordinates
import geometry.abstractions.GameSpace
import geometry.LineDirectionFor1Axis

// extensible alternative for CoordinatesXY
data class Coordinates2D(val xAxis: Int, val yAxis: Int) : Coordinates(xAxis, yAxis) {

    internal fun getNextInTheDirection(
        xAxisDirection: LineDirectionFor1Axis, yAxisDirection: LineDirectionFor1Axis
    ) = Coordinates2D( // should be exactly Coordinates2D
        xAxis + xAxisDirection.deltaOne, yAxis + yAxisDirection.deltaOne
    )

    internal fun getTheNextSpaceFor(
        xAxisDirection: LineDirectionFor1Axis, yAxisDirection: LineDirectionFor1Axis, sideLength: Int
    ): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants")
        when {
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