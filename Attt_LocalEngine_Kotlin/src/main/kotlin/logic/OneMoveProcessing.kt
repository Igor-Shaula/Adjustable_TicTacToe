package logic

import elements.Coordinates

interface OneMoveProcessing {

    fun getMaxLengthAchievedForThisMove(where: Coordinates): Int?
}