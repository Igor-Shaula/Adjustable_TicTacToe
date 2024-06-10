package elements

internal sealed interface GameSpace

// describes every place on the game field
internal data class Coordinates(val x: Int, val y: Int) : GameSpace {

    fun getNextInTheDirection(lineDirection: LineDirection) =
        Coordinates(x + lineDirection.dx, y + lineDirection.dy)
}

//data class Coordinates(val x: Int, val y: Int, val z: Int? = null) // later

internal data object Border : GameSpace
