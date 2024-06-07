package publicApi

import logic.GameEngine

interface AtttPlayer {

    fun getSymbol(): Char

    fun getMaxLineLength(): Int
}

interface AtttGame {

//    fun prepareGame(desiredFieldSize: Int, desiredMaxLineLength: Int): AtttPlayer

    fun mm(x: Int, y: Int): AtttPlayer

    fun makeMove(x: Int, y: Int): AtttPlayer

    fun getLeader(): AtttPlayer

    fun getWinner(): AtttPlayer

    fun isGameWon(): Boolean

    fun printCurrentFieldIn2d()

    fun finish()

    companion object {
        fun create(desiredFieldSize: Int, desiredMaxLineLength: Int): AtttGame =
            GameEngine(desiredFieldSize, desiredMaxLineLength)
    }
}
