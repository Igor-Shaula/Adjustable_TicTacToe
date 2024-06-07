package publicApi

import logic.GameSession

interface AtttPlayer {

    fun getSymbol(): Char

    fun getMaxLineLength(): Int
}

interface AtttGame {

    fun mm(x: Int, y: Int): AtttPlayer

    fun makeMove(x: Int, y: Int): AtttPlayer

    fun getLeader(): AtttPlayer

    fun getWinner(): AtttPlayer

    fun isGameWon(): Boolean

    fun printCurrentFieldIn2d()

    fun finish()

    companion object {
        /**
         * create AtttGame instance & provide the UI with a new game field, adjustability happens here - in the parameters
         */
        fun create(desiredFieldSize: Int, desiredMaxLineLength: Int): AtttGame =
            GameSession(desiredFieldSize, desiredMaxLineLength)
    }
}
