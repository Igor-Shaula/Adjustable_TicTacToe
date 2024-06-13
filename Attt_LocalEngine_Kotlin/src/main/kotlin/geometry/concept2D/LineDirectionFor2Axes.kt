package geometry.concept2D

import elements.LineDirection
import geometry.LineDirectionFor1Axis

internal data class LineDirectionFor2Axes(
    internal val xAxisLD: LineDirectionFor1Axis, internal val yAxisLD: LineDirectionFor1Axis,
) : LineDirection {
    override fun opposite() = LineDirectionFor2Axes(xAxisLD.opposite(), yAxisLD.opposite())

    fun isNone() = xAxisLD == LineDirectionFor1Axis.None && yAxisLD == LineDirectionFor1Axis.None

    companion object {
        /*
                fun getAll(): List<TwoAxisDirection> = listOf(
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.Minus, LineDirectionForOneAxis.Minus),
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.Plus, LineDirectionForOneAxis.Minus),
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.None, LineDirectionForOneAxis.Minus),
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.Minus, LineDirectionForOneAxis.Plus),
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.Plus, LineDirectionForOneAxis.Plus),
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.None, LineDirectionForOneAxis.Plus),
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.Minus, LineDirectionForOneAxis.None),
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.Plus, LineDirectionForOneAxis.None),
                    LineDirectionForTwoAxes(LineDirectionForOneAxis.None, LineDirectionForOneAxis.None)
                )
        */
        fun getAllFromLoops(): List<LineDirectionFor2Axes> {
            val resultList = mutableListOf<LineDirectionFor2Axes>()
            LineDirectionFor1Axis.entries.forEach { xAxisDirection ->
                LineDirectionFor1Axis.entries.forEach { yAxisDirection ->
                    resultList.add(LineDirectionFor2Axes(xAxisDirection, yAxisDirection))
                }
            }
            return resultList
        }
    }
}