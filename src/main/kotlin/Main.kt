// To execute Kotlin code, please define a top level function named main

fun main() {
    for (i in 1..5) println("Hello, World!")
}

data class Coordinates(val x: Int, val y: Int)

enum class Players { PlayerA, PlayerB }

data class PlayerDot(val player: Players, val isTheMarkPlaced: Boolean)

class TheLine(val length: Int, val lineDirection: LineDirection) {}

//enum class LineDirections { Straight, Diagonal }
//enum class LineType { Normal, Opposite }

enum class LineDirection(val changeInX: Int, val changeInY: Int) {
    Right(1, 0), // Left => Left(-1, 0)
    Down(0, 1),
    RightUp(1, -1),
    RightDown(1, 1),
    // + 4
}

class GamePlayer(private val gameField: GameField) {

    var maxLengthAchieved: Int = 0

    fun updateMaxLength(newLength: Int) {
        maxLengthAchieved = newLength
    }

    fun addNewCoordinate(x: Int, y: Int) {
        // modify the map here

/*        gameField.theMap.apply {
            if (this.isEmpty()) {
                return
            }
            // detect the line presence
            // val newLineIsCreated = this.detectIfANewLineIsCreated(x, y)

        }*/
    }
}

fun Map<Coordinates, PlayerDot>.detectIfANewLineIsCreated(x: Int, y: Int) {
    forEach {

    }
}
