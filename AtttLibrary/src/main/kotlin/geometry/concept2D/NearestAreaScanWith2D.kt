package geometry.concept2D

import attt.Player
import gameLogic.GameField
import geometry.Line
import geometry.abstractions.Coordinates
import geometry.abstractions.OneMoveProcessing
import geometry.getMaxLengthAchievedForThisMove

internal class NearestAreaScanWith2D(private val gameField: GameField) : OneMoveProcessing {

    override fun getCoordinatesFor(x: Int, y: Int, z: Int): Coordinates = Coordinates2D(x, y)

    override fun getMaxLengthAchievedForThisMove(
        where: Coordinates,
        saveNewLine: (Player, Line) -> Unit,
        addNewMark: (Player, Coordinates) -> Unit
    ): Int? = getMaxLengthAchievedForThisMove<Coordinates2D>(
        LineDirectionFor2Axes.getAllFromLoops(), gameField, where, saveNewLine, addNewMark
    )
}