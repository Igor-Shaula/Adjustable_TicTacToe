package logic

import elements.Coordinates
import elements.Coordinates2D
import elements.LineDirectionForTwoAxes
import utilities.Log

internal class NearestAreaScanWith2D(private val gameField: GameField) : OneMoveProcessing {

    override fun getMaxLengthAchievedForThisMove(where: Coordinates): Int? {
        if (where !is Coordinates2D) return null
        return detectAllExistingLineDirectionsFromThePlacedMark(where)
            .maxOfOrNull { twoAxisDirection ->
                measureFullLengthForExistingLineFrom(where, twoAxisDirection)
            }
    }

    private fun detectAllExistingLineDirectionsFromThePlacedMark(fromWhere: Coordinates2D): List<LineDirectionForTwoAxes> {
        val checkedMark = gameField.getCurrentMarkAt(fromWhere)
        if (checkedMark == null || checkedMark == PlayerProvider.None) {
            return emptyList() // preventing from doing detection calculations for initially wrong Player
        }
        val allDirections = mutableListOf<LineDirectionForTwoAxes>()
        LineDirectionForTwoAxes.getAllFromLoops().filter { !it.isNone() }.forEach { twoAxisDirection ->
            val nextCoordinates = fromWhere.getNextInTheDirection(
                twoAxisDirection.xAxisLD, twoAxisDirection.yAxisLD
            )
            if (nextCoordinates.existsWithin(gameField.sideLength) &&
                gameField.containsTheSameMark(checkedMark, nextCoordinates)
            ) {
                allDirections.add(twoAxisDirection)
                Log.pl("line exists in direction: $twoAxisDirection")
            }
        }
        return allDirections // is empty if no lines ae found in all possible directions
    }

    private fun measureFullLengthForExistingLineFrom(
        start: Coordinates2D, lineDirectionForTwoAxes: LineDirectionForTwoAxes
    ): Int {
        // here we already have a detected line of 2 minimum dots, now let's measure its full potential length.
        // we also have a proven placed dot of the same player in the detected line direction.
        // so, we only have to inspect next potential dot of the same direction -> let's prepare the coordinates:
        val checkedNearCoordinates = start.getTheNextSpaceFor(
            lineDirectionForTwoAxes.xAxisLD, lineDirectionForTwoAxes.yAxisLD, gameField.sideLength
        )
        var lineTotalLength = 0
        if (checkedNearCoordinates is Coordinates2D) {
            lineTotalLength =
                measureLineFrom(checkedNearCoordinates, lineDirectionForTwoAxes, 2) +
                        measureLineFrom(start, lineDirectionForTwoAxes.opposite(), 0)
            Log.pl("makeNewMove: lineTotalLength = $lineTotalLength")
        } // else checkedNearCoordinates cannot be Border or anything else apart from Coordinates type
        return lineTotalLength
    }

    private fun measureLineFrom(
        givenMark: Coordinates2D, lineDirectionForTwoAxes: LineDirectionForTwoAxes, startingLength: Int
    ): Int {
        Log.pl("measureLineFrom: given startingLength: $startingLength")
        Log.pl("measureLineFrom: given start coordinates: $givenMark")
        // firstly let's measure in the given direction and then in the opposite, also recursively
        val nextMark = givenMark.getTheNextSpaceFor(
            lineDirectionForTwoAxes.xAxisLD, lineDirectionForTwoAxes.yAxisLD, gameField.sideLength
        )
        Log.pl("measureLineFrom: detected next coordinates: $nextMark")
        return if (nextMark is Coordinates2D && gameField.belongToTheSameRealPlayer(givenMark, nextMark)) {
            measureLineFrom(nextMark, lineDirectionForTwoAxes, startingLength + 1)
        } else {
            Log.pl("measureLineFrom: ELSE -> exit: $startingLength")
            startingLength
        }
    }
}