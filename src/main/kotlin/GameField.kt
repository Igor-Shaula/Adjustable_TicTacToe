class GameField(
    val sideLength: Int = 3, // for a good old 3x3 game
    val dimensions: Int = 2, // simplest variant of a 2d game
    val numberOfPlayers: Int = 2, // this is obvious
) {
    val theMap: MutableMap<Coordinates, WhichPlayer> = mutableMapOf()

    init {
        // let's create the initial board or field for the game
        for (x in 0..<sideLength) {
            for (y in 0..<sideLength) {
                theMap[Coordinates(x, y)] = WhichPlayer.None
            }
        }
    }

    fun clear() {
        theMap.clear()
    }

    fun placeNewDot(where: Coordinates, what: WhichPlayer): Boolean = if (theMap[where] == WhichPlayer.None) {
        theMap[where] = what
        true
    } else {
        println("attempting to set a mark for player $what on the occupied coordinates: $where")
        // later we can also emit a custom exception here - to be caught on the UI side and ask for another point
        false
    }
}

// describes every place on the game field
data class Coordinates(val x: Int, val y: Int)
//data class Coordinates(val x: Int, val y: Int, val z: Int? = null) // later

enum class WhichPlayer { None, A, B }
