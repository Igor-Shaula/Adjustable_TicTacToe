class GameField(
    val sideLength: Int = 3, // for a good old 3x3 game
    val dimensions: Int = 2, // simplest variant of a 2d game
    val numberOfPlayers: Int = 2, // this is obvious
) {
    val theMap: MutableMap<Coordinates, WhichPlayer> = mutableMapOf()

    private var theMap = mutableMapOf<Coordinates, PlayerDot>()

    fun create(
        sideLength: Int = 3, // for a good old 3x3 game
        dimensions: Int = 2, // simplest variant of a 2d game
        numberOfPlayers: Int = 2, // this is obvious
    ): GameField {
        TODO("Not yet implemented")
    }

    fun clear() {
        TODO("Not yet implemented")
    }
}

// describes every place on the game field
data class Coordinates(val x: Int, val y: Int)
//data class Coordinates(val x: Int, val y: Int, val z: Int? = null) // later

enum class WhichPlayer { None, A, B }
