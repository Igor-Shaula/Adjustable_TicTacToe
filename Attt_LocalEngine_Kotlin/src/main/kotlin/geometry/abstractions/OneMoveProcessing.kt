package geometry.abstractions

import attt.Player
import geometry.Line

/**
 * abstraction for any action to be done right after every move is made - it analyses the new state of the game field
 */
internal interface OneMoveProcessing {

    fun getMaxLengthAchievedForThisMove(
        where: Coordinates,
        saveNewLine: (Player, Line) -> Unit,
        addNewMark: (Player, Coordinates) -> Unit
    ): Int?

    fun getCoordinatesFor(x: Int, y: Int, z: Int = 0): Coordinates
}