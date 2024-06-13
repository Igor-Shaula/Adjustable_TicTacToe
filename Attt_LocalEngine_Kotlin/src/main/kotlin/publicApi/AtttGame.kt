package publicApi

import constants.MIN_GAME_FIELD_SIDE_SIZE
import constants.MIN_NUMBER_OF_PLAYERS
import constants.MIN_WINNING_LINE_LENGTH
import logic.GameSession

interface AtttGame {

    fun mm(x: Int, y: Int, z: Int = 0): AtttPlayer

    fun makeMove(x: Int, y: Int, z: Int = 0): AtttPlayer

    fun getLeader(): AtttPlayer

    fun getWinner(): AtttPlayer

    fun isGameWon(): Boolean

    fun printCurrentFieldIn2d()

    companion object {
        /**
         * create AtttGame instance & provide the UI with a new game field, adjustability happens here - in the parameters
         */
        fun create(
            desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE,
            desiredMaxLineLength: Int = MIN_WINNING_LINE_LENGTH,
            is3D: Boolean = false,
            desiredPlayerNumber: Int = MIN_NUMBER_OF_PLAYERS
        ): AtttGame = GameSession(desiredFieldSize, desiredMaxLineLength, is3D, desiredPlayerNumber)
    }
}
