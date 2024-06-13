package geometry.concept2D

import geometry.*
import geometry.Border
import geometry.LineDirectionFor1Axis

// extensible alternative for CoordinatesXY
data class Coordinates2D(val xAxis: OneAxis, val yAxis: OneAxis) : Coordinates(xAxis.l, yAxis.l) {

    constructor(x: Int, y: Int) : this(OneAxis(x), OneAxis(y))

    internal fun getNextInTheDirection(
        xAxisDirection: LineDirectionFor1Axis, yAxisDirection: LineDirectionFor1Axis
    ) = Coordinates2D( // should be exactly Coordinates2D
        OneAxis(xAxis.l + xAxisDirection.deltaOne), OneAxis(yAxis.l + yAxisDirection.deltaOne)
    )

    internal fun getTheNextSpaceFor(
        xAxisDirection: LineDirectionFor1Axis, yAxisDirection: LineDirectionFor1Axis, sideLength: Int
    ): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants")
        when {
            false || // just for the following cases alignment
                    xAxis.l <= minIndex && xAxisDirection == LineDirectionFor1Axis.Minus || // X is out of game field
                    xAxis.l >= maxIndex && xAxisDirection == LineDirectionFor1Axis.Plus || // X is out of game field
                    yAxis.l <= minIndex && yAxisDirection == LineDirectionFor1Axis.Minus || // X is out of game field
                    yAxis.l >= maxIndex && yAxisDirection == LineDirectionFor1Axis.Plus -> // X is out of game field
                return Border
        }
        return Coordinates2D(
            OneAxis(xAxis.l + xAxisDirection.deltaOne), OneAxis(yAxis.l + yAxisDirection.deltaOne)
        )
    }
}