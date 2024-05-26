internal sealed interface GameSpace

// describes every place on the game field
data class Coordinates(val x: Int, val y: Int) : GameSpace
//data class Coordinates(val x: Int, val y: Int, val z: Int? = null) // later

data object Border : GameSpace

@Suppress("unused")
enum class WhichPlayer { None, A, B } // for now, 2 players is enough, but in the future we can have more
