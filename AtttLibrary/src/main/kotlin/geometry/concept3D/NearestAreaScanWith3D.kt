package geometry.concept3D

import attt.Player
import gameLogic.GameField
import geometry.Line
import geometry.abstractions.Coordinates
import geometry.abstractions.OneMoveProcessing
import geometry.getMaxLengthAchievedForThisMove

internal class NearestAreaScanWith3D(private val gameField: GameField) : OneMoveProcessing {

    override fun getCoordinatesFor(x: Int, y: Int, z: Int): Coordinates = Coordinates3D(x, y, z)

    override fun getMaxLengthAchievedForThisMove(
        where: Coordinates,
        saveNewLine: (Player, Line) -> Unit,
        addNewMark: (Player, Coordinates) -> Unit
    ): Int? = getMaxLengthAchievedForThisMove<Coordinates3D>(
        LineDirectionFor3Axes.getAllFromLoops(), gameField, where, saveNewLine, addNewMark
    )
}