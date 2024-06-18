package publicApi

import constants.MIN_GAME_FIELD_SIDE_SIZE
import constants.MIN_NUMBER_OF_PLAYERS
import constants.MIN_WINNING_LINE_LENGTH
import gameLogic.GameSession

interface AtttGame {

    fun makeMove(x: Int, y: Int, z: Int = 0): AtttPlayer

    /**
     * the same as makeMove() with 3 arguments, but this one is much more convenient for use in Java code
     */
    fun makeMove(x: Int, y: Int): AtttPlayer

    fun mm(x: Int, y: Int, z: Int = 0): AtttPlayer

    /**
     * the same as mm() with 3 arguments, but this one is much more convenient for use in Java code
     */
    fun mm(x: Int, y: Int): AtttPlayer

    fun getLeader(): AtttPlayer

    fun getWinner(): AtttPlayer

    fun isGameWon(): Boolean

    fun isGameFinished(): Boolean

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

        /**
         * the shortest ever way to get the game ready
         */
        fun create(): AtttGame =
            GameSession(MIN_GAME_FIELD_SIDE_SIZE, MIN_WINNING_LINE_LENGTH, false, MIN_NUMBER_OF_PLAYERS)

        /**
         * convenient for getting the game field of bigger size
         */
        fun create(desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE): AtttGame =
            GameSession(desiredFieldSize, MIN_WINNING_LINE_LENGTH, false, MIN_NUMBER_OF_PLAYERS)

        /**
         * more precise adjustment of winning condition for configurable game field
         */
        fun create(
            desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE, desiredMaxLineLength: Int = MIN_WINNING_LINE_LENGTH
        ): AtttGame = GameSession(desiredFieldSize, desiredMaxLineLength, false, MIN_NUMBER_OF_PLAYERS)

        /**
         * the shortest ever way to get the game ready in 3D
         */
        fun create(is3D: Boolean = false): AtttGame =
            GameSession(MIN_GAME_FIELD_SIDE_SIZE, MIN_WINNING_LINE_LENGTH, is3D, MIN_NUMBER_OF_PLAYERS)

        /**
         * convenient for getting the game field of bigger size in 3D
         */
        fun create(desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE, is3D: Boolean = false): AtttGame =
            GameSession(desiredFieldSize, MIN_WINNING_LINE_LENGTH, is3D, MIN_NUMBER_OF_PLAYERS)

        /**
         * more precise adjustment of winning condition for configurable 3D game field
         */
        fun create(
            desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE,
            desiredMaxLineLength: Int = MIN_WINNING_LINE_LENGTH,
            is3D: Boolean = false
        ): AtttGame = GameSession(desiredFieldSize, desiredMaxLineLength, is3D, MIN_NUMBER_OF_PLAYERS)
    }
}
