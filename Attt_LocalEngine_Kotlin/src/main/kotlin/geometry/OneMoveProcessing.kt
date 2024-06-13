package geometry

import elements.Coordinates

interface OneMoveProcessing {

    fun getMaxLengthAchievedForThisMove(where: Coordinates): Int?

    fun getCoordinatesFor(x: Int, y: Int, z: Int = 0): Coordinates
}