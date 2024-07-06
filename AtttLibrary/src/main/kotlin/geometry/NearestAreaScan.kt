package geometry

import attt.Player
import gameLogic.GameField
import geometry.abstractions.Coordinates
import geometry.abstractions.LineDirection
import players.PlayerModel
import utilities.Log

internal inline fun <reified C : Coordinates> getMaxLengthAchievedForThisMove(
    allDirections: List<LineDirection>,
    gameField: GameField,
    where: Coordinates,
    noinline saveNewLine: (Player, Line) -> Unit,
    noinline addNewMark: (Player, Coordinates) -> Unit
): Int? {
    if (where !is C) return null
    return detectAllExistingLineDirectionsFromThePlacedMark(gameField, where, allDirections, saveNewLine)
        .maxOfOrNull { lineDirection ->
            measureFullLengthForExistingLineFrom<C>(gameField, where, lineDirection, addNewMark)
        }
}

internal fun detectAllExistingLineDirectionsFromThePlacedMark(
    gameField: GameField,
    fromWhere: Coordinates,
    lineDirections: List<LineDirection>,
    saveNewLine: (Player, Line) -> Unit
): List<LineDirection> {
    val checkedMark = gameField.getCurrentMarkAt(fromWhere)
    if (checkedMark == null || checkedMark == PlayerModel.None) {
        return emptyList() // preventing from doing detection calculations for initially wrong Player
    }
    val allDirections = mutableListOf<LineDirection>()
    lineDirections.filter { !it.isNone() }.forEach { lineDirection ->
        val nextCoordinates = fromWhere.getNextInTheDirection(lineDirection)
        if (nextCoordinates.existsWithin(gameField.sideLength) &&
            gameField.containsTheSameMark(checkedMark, nextCoordinates)
        ) {
            allDirections.add(lineDirection)
            Log.pl("line exists in direction: $lineDirection")
            saveNewLine(checkedMark, Line(fromWhere, nextCoordinates))
        }
    }
    return allDirections // is empty if no lines ae found in all possible directions
}

internal inline fun <reified C : Coordinates> measureFullLengthForExistingLineFrom(
    gameField: GameField,
    start: Coordinates,
    lineDirection: LineDirection,
    noinline addNewMark: (Player, Coordinates) -> Unit
): Int {
    // here we already have a detected line of 2 minimum dots, now let's measure its full potential length.
    // we also have a proven placed dot of the same player in the detected line direction.
    // so, we only have to inspect next potential dot of the same direction -> let's prepare the coordinates:
    val checkedNearCoordinates = start.getTheNextSpaceFor(
        lineDirection, gameField.sideLength
    )
    var lineTotalLength = 0
    if (checkedNearCoordinates is C) {
        lineTotalLength =
            measureLineFrom(gameField, checkedNearCoordinates, lineDirection, 2, addNewMark) +
                    measureLineFrom(gameField, start, lineDirection.opposite(), 0, addNewMark)
        Log.pl("makeNewMove: lineTotalLength = $lineTotalLength")
    } // else checkedNearCoordinates cannot be Border or anything else apart from Coordinates type
    return lineTotalLength
}

internal fun measureLineFrom(
    gameField: GameField,
    givenMark: Coordinates,
    lineDirection: LineDirection,
    startingLength: Int,
    addNewMark: (Player, Coordinates) -> Unit
): Int {
    Log.pl("measureLineFrom: given startingLength: $startingLength")
    Log.pl("measureLineFrom: given start coordinates: $givenMark")
    // firstly let's measure in the given direction and then in the opposite, also recursively
    val nextMark = givenMark.getTheNextSpaceFor(
        lineDirection, gameField.sideLength
    )
    Log.pl("measureLineFrom: detected next coordinates: $nextMark")
    return if (nextMark is Coordinates && gameField.belongToTheSameRealPlayer(givenMark, nextMark)) {
        // here the line gets longer by one new mark, this is a side effect to the measurement
        gameField.getCurrentMarkAt(givenMark)?.let { addNewMark(it, nextMark) }
        measureLineFrom(gameField, nextMark, lineDirection, startingLength + 1, addNewMark)
    } else {
        Log.pl("measureLineFrom: ELSE -> exit: $startingLength")
        startingLength
    }
}