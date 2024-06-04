package internalElements

internal sealed interface GameSpace

// describes every place on the game field
internal data class Coordinates(val x: Int, val y: Int) : GameSpace
//data class Coordinates(val x: Int, val y: Int, val z: Int? = null) // later

internal data object Border : GameSpace
