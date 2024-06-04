// region WHERE

internal sealed interface GameSpace

// describes every place on the game field
internal data class Coordinates(val x: Int, val y: Int) : GameSpace
//data class Coordinates(val x: Int, val y: Int, val z: Int? = null) // later

internal data object Border : GameSpace

// endregion WHERE
// region WHAT

// idea: later make the symbol configurable - because again, we can have more than 2 players in perspective

// endregion WHAT