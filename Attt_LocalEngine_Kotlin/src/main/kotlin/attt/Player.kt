package attt

interface Player {

    /**
     * this is the main criterion to distinguish one player from any other
     */
    val id: Int

    /**
     * optional - as the name is not really required to play the game & differ the players
     */
    val name: String?

    /**
     * optional - is set automatically, symbol is used mostly for printing the game field in 2d
     */
    val symbol: Char?

    /**
     * the longest achieved line for this player, winning condition check is based on it
     */
    val maxLineLength: Int
}