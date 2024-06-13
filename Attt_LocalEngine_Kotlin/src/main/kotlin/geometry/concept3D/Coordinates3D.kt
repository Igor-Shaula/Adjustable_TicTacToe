package geometry.concept3D

import geometry.abstractions.Border
import geometry.abstractions.Coordinates
import geometry.abstractions.GameSpace
import geometry.LineDirectionFor1Axis

// 3d as simple as 2d :)
data class Coordinates3D(
    val xAxis: Int, val yAxis: Int, val zAxis: Int
) : Coordinates(xAxis, yAxis, zAxis) {

    internal fun getNextInTheDirection(
        xAxisDirection: LineDirectionFor1Axis,
        yAxisDirection: LineDirectionFor1Axis,
        zAxisDirection: LineDirectionFor1Axis
    ) = Coordinates3D( // should be exactly Coordinates3D
        xAxis + xAxisDirection.deltaOne,
        yAxis + yAxisDirection.deltaOne,
        zAxis + zAxisDirection.deltaOne
    )

    internal fun getTheNextSpaceFor(
        xAxisDirection: LineDirectionFor1Axis,
        yAxisDirection: LineDirectionFor1Axis,
        zAxisDirection: LineDirectionFor1Axis,
        sideLength: Int
    ): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants")
        when {
            false || // just for the following cases alignment
                    xAxis <= minIndex && xAxisDirection == LineDirectionFor1Axis.Minus || // X is out of game field
                    xAxis >= maxIndex && xAxisDirection == LineDirectionFor1Axis.Plus || // X is out of game field
                    yAxis <= minIndex && yAxisDirection == LineDirectionFor1Axis.Minus || // Y is out of game field
                    yAxis >= maxIndex && yAxisDirection == LineDirectionFor1Axis.Plus || // Y is out of game field
                    zAxis <= minIndex && zAxisDirection == LineDirectionFor1Axis.Minus || // Z is out of game field
                    zAxis >= maxIndex && zAxisDirection == LineDirectionFor1Axis.Plus -> // Z is out of game field
                return Border
        }
        return Coordinates3D(
            xAxis + xAxisDirection.deltaOne,
            yAxis + yAxisDirection.deltaOne,
            zAxis + zAxisDirection.deltaOne
        )
    }
}