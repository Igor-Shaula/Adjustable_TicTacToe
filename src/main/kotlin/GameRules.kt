class GameRules(
    private val maxLength: Int = 3,
    // potentially here we can add more criteria to detect if the game is won by any of players
) {
    fun isGameWon(lineLength: Int) = lineLength >= maxLength
}

interface winningCriteria {

    fun isGameActive(): Boolean

    fun isGameWon(winningLength: Int): Boolean // must be > 2

    fun isGameEndedWithoutWinning(): Boolean
}