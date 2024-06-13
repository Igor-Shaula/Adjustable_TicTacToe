package geometry.conceptXY

import geometry.Coordinates
import gameLogic.GameField
import geometry.OneMoveProcessing
import players.PlayerProvider
import utilities.Log

internal class NearestAreaScanWithXY(private val gameField: GameField) : OneMoveProcessing {

    override fun getMaxLengthAchievedForThisMove(where: Coordinates): Int? {
        if (where !is CoordinatesXY) return null
        return detectAllExistingLineDirectionsFromThePlacedMark(where)
            .maxOfOrNull { lineDirection ->
                measureFullLengthForExistingLineFrom(where, lineDirection)
            }
    }

    override fun getCoordinatesFor(x: Int, y: Int, z: Int): Coordinates = CoordinatesXY(x, y)

    private fun detectAllExistingLineDirectionsFromThePlacedMark(fromWhere: CoordinatesXY): List<LineDirectionForXY> {
        val checkedMark = gameField.getCurrentMarkAt(fromWhere)
        if (checkedMark == null || checkedMark == PlayerProvider.None) {
            return emptyList() // preventing from doing detection calculations for initially wrong Player
        }
        val allDirections = mutableListOf<LineDirectionForXY>()
        LineDirectionForXY.entries.filter { it != LineDirectionForXY.None }.forEach { lineDirection ->
            val nextCoordinates = fromWhere.getNextInTheDirection(lineDirection)
            if (nextCoordinates.existsWithin(gameField.sideLength) &&
                gameField.containsTheSameMark(checkedMark, nextCoordinates)
            ) {
                allDirections.add(lineDirection)
                Log.pl("line exists in direction: $lineDirection")
            }
        }
        return allDirections // is empty if no lines ae found in all possible directions
    }

    private fun measureFullLengthForExistingLineFrom(
        start: CoordinatesXY, lineDirectionForXY: LineDirectionForXY
    ): Int {
        // here we already have a detected line of 2 minimum dots, now let's measure its full potential length.
        // we also have a proven placed dot of the same player in the detected line direction.
        // so, we only have to inspect next potential dot of the same direction -> let's prepare the coordinates:
        val checkedNearCoordinates = start.getTheNextSpaceFor(lineDirectionForXY, gameField.sideLength)
        var lineTotalLength = 0
        if (checkedNearCoordinates is CoordinatesXY) {
            lineTotalLength =
                measureLineFrom(checkedNearCoordinates, lineDirectionForXY, 2) +
                        measureLineFrom(start, lineDirectionForXY.opposite(), 0)
            Log.pl("makeNewMove: lineTotalLength = $lineTotalLength")
        } // else checkedNearCoordinates cannot be Border or anything else apart from Coordinates type
        return lineTotalLength
    }

    internal fun measureLineFrom(
        givenMark: CoordinatesXY, lineDirectionForXY: LineDirectionForXY, startingLength: Int
    ): Int {
        Log.pl("measureLineFrom: given startingLength: $startingLength")
        Log.pl("measureLineFrom: given start coordinates: $givenMark")
        // firstly let's measure in the given direction and then in the opposite, also recursively
        val nextMark = givenMark.getTheNextSpaceFor(lineDirectionForXY, gameField.sideLength)
        Log.pl("measureLineFrom: detected next coordinates: $nextMark")
        return if (nextMark is CoordinatesXY && gameField.belongToTheSameRealPlayer(givenMark, nextMark)) {
            measureLineFrom(nextMark, lineDirectionForXY, startingLength + 1)
        } else {
            Log.pl("measureLineFrom: ELSE -> exit: $startingLength")
            startingLength
        }
    }
}