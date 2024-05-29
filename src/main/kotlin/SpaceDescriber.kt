internal sealed interface GameSpace

// describes every place on the game field
data class Coordinates(val x: Int, val y: Int) : GameSpace
//data class Coordinates(val x: Int, val y: Int, val z: Int? = null) // later

internal data object Border : GameSpace

// for now, 2 players is enough, but in the future we can have more
@Suppress("unused")
enum class WhichPlayer(val symbol: Char) { None('·'), A('X'), B('O') }
// idea: later make the symbol configurable - because again, we can have more than 2 players in perspective