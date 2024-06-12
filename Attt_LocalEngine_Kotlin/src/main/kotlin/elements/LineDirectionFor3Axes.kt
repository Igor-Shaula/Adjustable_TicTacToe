package elements

internal data class LineDirectionFor3Axes(
    internal val xAxisLD: LineDirectionFor1Axis,
    internal val yAxisLD: LineDirectionFor1Axis,
    internal val zAxisLD: LineDirectionFor1Axis,
) : LineDirection {
    override fun opposite() = LineDirectionFor3Axes(xAxisLD.opposite(), yAxisLD.opposite(), zAxisLD.opposite())

    fun isNone() = xAxisLD == LineDirectionFor1Axis.None && yAxisLD == LineDirectionFor1Axis.None
            && zAxisLD == LineDirectionFor1Axis.None

    companion object {
        fun getAllFromLoops(): List<LineDirectionFor3Axes> {
            val resultList = mutableListOf<LineDirectionFor3Axes>()
            LineDirectionFor1Axis.entries.forEach { xAxisDirection ->
                LineDirectionFor1Axis.entries.forEach { yAxisDirection ->
                    LineDirectionFor1Axis.entries.forEach { zAxisDirection ->
                        resultList.add(LineDirectionFor3Axes(xAxisDirection, yAxisDirection, zAxisDirection))
                    }
                }
            }
            return resultList
        }
    }
}