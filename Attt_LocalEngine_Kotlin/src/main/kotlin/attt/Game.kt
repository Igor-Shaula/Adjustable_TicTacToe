package attt

import constants.MIN_GAME_FIELD_SIDE_SIZE
import constants.MIN_NUMBER_OF_PLAYERS
import constants.MIN_WINNING_LINE_LENGTH
import gameLogic.GameSession

interface Game {

    /**
     * the only action for a selected/next player during the active game, this one is the most precise form
     */
    fun makeMove(x: Int, y: Int, z: Int = 0): Player

    /**
     * the same as makeMove() with 3 arguments, but this one is much more convenient for use in Java code
     */
    fun makeMove(x: Int, y: Int): Player = makeMove(x, y, 0)

    /**
     * the same as makeMove() - this reduction is made for convenience as this method is the most frequently used
     */
    fun m(x: Int, y: Int, z: Int = 0): Player = makeMove(x, y, z)

    /**
     * the same as m() with 3 arguments, but this one is much more convenient for use in Java code
     */
    fun m(x: Int, y: Int): Player = makeMove(x, y, 0)

    fun getLeader(): Player

    fun getWinner(): Player

    fun isGameWon(): Boolean

    fun isGameFinished(): Boolean

    fun getCurrentFieldIn2dAsAString(): String

    fun printCurrentFieldIn2d()

    fun getExistingLinesForGivenPlayerAsAString(player: Player): String

    fun printExistingLinesFor(player: Player)

    fun getExistingLinesForLeadingPlayerAsAString(): String

    fun printExistingLinesForLeadingPlayer()

    fun getExistingLinesForTheWinnerAsAString(): String

    fun printExistingLinesForTheWinner()

    fun getTheWinningLineAsAString(): String

    fun printTheWinningLine()

    companion object {
        /**
         * create AtttGame instance & provide the UI with a new game field, adjustability happens here - in the parameters
         */
        fun create(
            desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE,
            desiredMaxLineLength: Int = MIN_WINNING_LINE_LENGTH,
            is3D: Boolean = false,
            desiredPlayerNumber: Int = MIN_NUMBER_OF_PLAYERS
        ): Game = GameSession(desiredFieldSize, desiredMaxLineLength, is3D, desiredPlayerNumber)

        /**
         * the shortest ever way to get the game ready
         */
        fun create(): Game =
            GameSession(MIN_GAME_FIELD_SIDE_SIZE, MIN_WINNING_LINE_LENGTH, false, MIN_NUMBER_OF_PLAYERS)

        /**
         * convenient for getting the game field of bigger size
         */
        fun create(desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE): Game =
            GameSession(desiredFieldSize, MIN_WINNING_LINE_LENGTH, false, MIN_NUMBER_OF_PLAYERS)

        /**
         * more precise adjustment of winning condition for configurable game field
         */
        fun create(
            desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE, desiredMaxLineLength: Int = MIN_WINNING_LINE_LENGTH
        ): Game = GameSession(desiredFieldSize, desiredMaxLineLength, false, MIN_NUMBER_OF_PLAYERS)

        /**
         * the shortest ever way to get the game ready in 3D
         */
        fun create(is3D: Boolean = false): Game =
            GameSession(MIN_GAME_FIELD_SIDE_SIZE, MIN_WINNING_LINE_LENGTH, is3D, MIN_NUMBER_OF_PLAYERS)

        /**
         * convenient for getting the game field of bigger size in 3D
         */
        fun create(desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE, is3D: Boolean = false): Game =
            GameSession(desiredFieldSize, MIN_WINNING_LINE_LENGTH, is3D, MIN_NUMBER_OF_PLAYERS)

        /**
         * more precise adjustment of winning condition for configurable 3D game field
         */
        fun create(
            desiredFieldSize: Int = MIN_GAME_FIELD_SIDE_SIZE,
            desiredMaxLineLength: Int = MIN_WINNING_LINE_LENGTH,
            is3D: Boolean = false
        ): Game = GameSession(desiredFieldSize, desiredMaxLineLength, is3D, MIN_NUMBER_OF_PLAYERS)
    }
}
