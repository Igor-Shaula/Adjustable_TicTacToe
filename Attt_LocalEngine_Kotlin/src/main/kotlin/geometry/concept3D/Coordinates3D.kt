package geometry.concept3D

import geometry.*
import geometry.Border
import geometry.LineDirectionFor1Axis

// 3d as simple as 2d :)
data class Coordinates3D(
    val xAxis: OneAxis, val yAxis: OneAxis, val zAxis: OneAxis
) : Coordinates(xAxis.l, yAxis.l, zAxis.l) {

    constructor(x: Int, y: Int, z: Int) : this(OneAxis(x), OneAxis(y), OneAxis(z))

    internal fun getNextInTheDirection(
        xAxisDirection: LineDirectionFor1Axis,
        yAxisDirection: LineDirectionFor1Axis,
        zAxisDirection: LineDirectionFor1Axis
    ) = Coordinates3D( // should be exactly Coordinates3D
        OneAxis(xAxis.l + xAxisDirection.deltaOne),
        OneAxis(yAxis.l + yAxisDirection.deltaOne),
        OneAxis(zAxis.l + zAxisDirection.deltaOne)
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
                    xAxis.l <= minIndex && xAxisDirection == LineDirectionFor1Axis.Minus || // X is out of game field
                    xAxis.l >= maxIndex && xAxisDirection == LineDirectionFor1Axis.Plus || // X is out of game field
                    yAxis.l <= minIndex && yAxisDirection == LineDirectionFor1Axis.Minus || // X is out of game field
                    yAxis.l >= maxIndex && yAxisDirection == LineDirectionFor1Axis.Plus || // X is out of game field
                    zAxis.l <= minIndex && zAxisDirection == LineDirectionFor1Axis.Minus || // X is out of game field
                    zAxis.l >= maxIndex && zAxisDirection == LineDirectionFor1Axis.Plus -> // X is out of game field
                return Border
        }
        return Coordinates3D(
            OneAxis(xAxis.l + xAxisDirection.deltaOne),
            OneAxis(yAxis.l + yAxisDirection.deltaOne),
            OneAxis(zAxis.l + zAxisDirection.deltaOne)
        )
    }
}