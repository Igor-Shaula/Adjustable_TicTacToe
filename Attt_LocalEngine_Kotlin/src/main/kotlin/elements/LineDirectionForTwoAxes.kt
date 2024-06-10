package elements

internal data class LineDirectionForTwoAxes(
    internal val xAxisLD: LineDirectionForOneAxis, internal val yAxisLD: LineDirectionForOneAxis
) {
    internal fun opposite() = LineDirectionForTwoAxes(xAxisLD.opposite(), yAxisLD.opposite())

    fun isNone() = xAxisLD == LineDirectionForOneAxis.None && yAxisLD == LineDirectionForOneAxis.None

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
        fun getAllFromLoops(): List<LineDirectionForTwoAxes> {
            val resultList = mutableListOf<LineDirectionForTwoAxes>()
            LineDirectionForOneAxis.entries.forEach { xAxisDirection ->
                LineDirectionForOneAxis.entries.forEach { yAxisDirection ->
                    resultList.add(LineDirectionForTwoAxes(xAxisDirection, yAxisDirection))
                }
            }
            return resultList
        }
    }
}