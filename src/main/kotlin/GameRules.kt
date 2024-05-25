object GameRules {

    fun create() {

    }

    fun clear() {
        TODO("Not yet implemented")
    }
}

interface winningCriteria {

    fun isGameActive(): Boolean

    fun isGameWon(winningLength: Int): Boolean // must be > 2

    fun isGameEndedWithoutWinning(): Boolean
}