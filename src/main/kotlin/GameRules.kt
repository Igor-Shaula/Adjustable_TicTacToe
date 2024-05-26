class GameRules(
    private val maxLength: Int = 3,
    // potentially here we can add more criteria to detect if the game is won by any of players
) {
    fun isGameWon(lineLength: Int) = lineLength >= maxLength
}
