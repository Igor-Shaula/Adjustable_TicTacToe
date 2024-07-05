package geometry.concept3D

import geometry.LineDirectionFor1Axis
import geometry.abstractions.Border
import geometry.abstractions.Coordinates
import geometry.abstractions.GameSpace
import geometry.abstractions.LineDirection

/**
 * 3d as simple as 2d :)
 */
internal data class Coordinates3D(
    val xAxis: Int, val yAxis: Int, val zAxis: Int
) : Coordinates(xAxis, yAxis, zAxis) {

    override fun getNextInTheDirection(lineDirection: LineDirection): Coordinates3D {
        lineDirection as LineDirectionFor3Axes
        return getNextInTheDirection(lineDirection.xAxisLD, lineDirection.yAxisLD, lineDirection.zAxisLD)
    }

    override fun getTheNextSpaceFor(lineDirection: LineDirection, sideLength: Int): GameSpace {
        lineDirection as LineDirectionFor3Axes
        return getTheNextSpaceFor(lineDirection.xAxisLD, lineDirection.yAxisLD, lineDirection.zAxisLD, sideLength)
    }

    private fun getNextInTheDirection(
        xAxisDirection: LineDirectionFor1Axis,
        yAxisDirection: LineDirectionFor1Axis,
        zAxisDirection: LineDirectionFor1Axis
    ) = Coordinates3D( // should be exactly Coordinates3D
        xAxis + xAxisDirection.deltaOne, yAxis + yAxisDirection.deltaOne, zAxis + zAxisDirection.deltaOne
    )

    private fun getTheNextSpaceFor(
        xAxisDirection: LineDirectionFor1Axis,
        yAxisDirection: LineDirectionFor1Axis,
        zAxisDirection: LineDirectionFor1Axis,
        sideLength: Int
    ): GameSpace {
        val minIndex = 0 // this is obvious but let it be here for consistency
        val maxIndex = sideLength - 1 // constant for the given game field
        @Suppress("SimplifyBooleanWithConstants") when {
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
            xAxis + xAxisDirection.deltaOne, yAxis + yAxisDirection.deltaOne, zAxis + zAxisDirection.deltaOne
        )
    }
}