package geometry.concept3D

import attt.Player
import gameLogic.GameField
import geometry.Line
import geometry.abstractions.Coordinates
import geometry.abstractions.LineDirection
import geometry.abstractions.OneMoveProcessing
import players.PlayerModel
import utilities.Log

internal class NearestAreaScanWith3D(private val gameField: GameField) : OneMoveProcessing {

    override fun getMaxLengthAchievedForThisMove(
        where: Coordinates,
        saveNewLine: (Player, Line) -> Unit,
        addNewMark: (Player, Coordinates) -> Unit
    ): Int? {
        if (where !is Coordinates3D) return null
        return detectAllExistingLineDirectionsFromThePlacedMark(where, saveNewLine)
            .maxOfOrNull { threeAxesDirection ->
                measureFullLengthForExistingLineFrom(where, threeAxesDirection, addNewMark)
            }
    }

    override fun getCoordinatesFor(x: Int, y: Int, z: Int): Coordinates = Coordinates3D(x, y, z)

    private fun detectAllExistingLineDirectionsFromThePlacedMark(
        fromWhere: Coordinates,
        saveNewLine: (Player, Line) -> Unit
    ): List<LineDirection> {
        val checkedMark = gameField.getCurrentMarkAt(fromWhere)
        if (checkedMark == null || checkedMark == PlayerModel.None) {
            return emptyList() // preventing from doing detection calculations for initially wrong Player
        }
        val allDirections = mutableListOf<LineDirection>()
        LineDirectionFor3Axes.getAllFromLoops().filter { !it.isNone() }.forEach { threeAxisDirection ->
            val nextCoordinates = fromWhere.getNextInTheDirection(threeAxisDirection)
            if (nextCoordinates.existsWithin(gameField.sideLength) &&
                gameField.containsTheSameMark(checkedMark, nextCoordinates)
            ) {
                allDirections.add(threeAxisDirection)
                Log.pl("line exists in direction: $threeAxisDirection")
                saveNewLine(checkedMark, Line(fromWhere, nextCoordinates))
            }
        }
        return allDirections // is empty if no lines ae found in all possible directions
    }

    private fun measureFullLengthForExistingLineFrom(
        start: Coordinates,
        lineDirectionFor3Axes: LineDirection,
        addNewMark: (Player, Coordinates) -> Unit
    ): Int {
        // here we already have a detected line of 2 minimum dots, now let's measure its full potential length.
        // we also have a proven placed dot of the same player in the detected line direction.
        // so, we only have to inspect next potential dot of the same direction -> let's prepare the coordinates:
        val checkedNearCoordinates = start.getTheNextSpaceFor(
            lineDirectionFor3Axes, gameField.sideLength
        )
        var lineTotalLength = 0
        if (checkedNearCoordinates is Coordinates3D) {
            lineTotalLength =
                measureLineFrom(checkedNearCoordinates, lineDirectionFor3Axes, 2, addNewMark) +
                        measureLineFrom(start, lineDirectionFor3Axes.opposite(), 0, addNewMark)
            Log.pl("makeNewMove: lineTotalLength = $lineTotalLength")
        } // else checkedNearCoordinates cannot be Border or anything else apart from Coordinates type
        return lineTotalLength
    }

    private fun measureLineFrom(
        givenMark: Coordinates,
        lineDirectionFor3Axes: LineDirection,
        startingLength: Int,
        addNewMark: (Player, Coordinates) -> Unit
    ): Int {
        Log.pl("measureLineFrom: given startingLength: $startingLength")
        Log.pl("measureLineFrom: given start coordinates: $givenMark")
        // firstly let's measure in the given direction and then in the opposite, also recursively
        val nextMark = givenMark.getTheNextSpaceFor(
            lineDirectionFor3Axes, gameField.sideLength
        )
        Log.pl("measureLineFrom: detected next coordinates: $nextMark")
        return if (nextMark is Coordinates3D && gameField.belongToTheSameRealPlayer(givenMark, nextMark)) {
            // here the line gets longer by one new mark, this is a side effect to the measurement
            gameField.getCurrentMarkAt(givenMark)?.let { addNewMark(it, nextMark) }
            measureLineFrom(nextMark, lineDirectionFor3Axes, startingLength + 1, addNewMark)
        } else {
            Log.pl("measureLineFrom: ELSE -> exit: $startingLength")
            startingLength
        }
    }
}