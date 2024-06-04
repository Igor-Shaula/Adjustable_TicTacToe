import internalElements.GameEngine
import internalElements.GameField
import elements.Player
import elements.Coordinates
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse

class InternalApiTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    // this test was provided by Matt Tucker - https://github.com/tuck182 - many thanks for finding a serious bug!
    @Test
    fun test3x3FieldWithMultiplePossibleLines() {
        GameEngine.prepareGame(3, 3)

        // .Xx
        // .xo
        // oxo

        GameEngine.makeMove(Coordinates(1, 1), Player.A)
        GameEngine.makeMove(Coordinates(2, 1), Player.B)
        GameEngine.makeMove(Coordinates(2, 0), Player.A)
        GameEngine.makeMove(Coordinates(0, 2), Player.B)
        GameEngine.makeMove(Coordinates(1, 2), Player.A)
        GameEngine.makeMove(Coordinates(2, 2), Player.B)
        GameEngine.makeMove(Coordinates(1, 0), Player.A)

        assertFalse(GameEngine.isActive(), "Game should have been won")
        // Would be nice to be able to do this:
        // assertEquals(AtttPlayer.A, internalElements.AtttEngine.getWinner())
    }

    @Test
    fun having3x3Field_realSimulation2PlayersMovesMade_victoryConditionIsCorrect() {
        prepareClassic3x3GameField()
        GameEngine.makeMove(Coordinates(0, 0))
        GameEngine.makeMove(Coordinates(1, 0))
        GameEngine.makeMove(Coordinates(2, 0))
        GameEngine.makeMove(Coordinates(1, 1))
        GameEngine.makeMove(Coordinates(2, 1))
        GameEngine.makeMove(Coordinates(1, 2))
        // gameField & winning message for player B is printed in the console
        // todo: add assertion here
    }

    @Test
    fun having3x3Field_realSimulation2PlayersShortenedMovesMade_victoryConditionIsCorrect() {
        prepareClassic3x3GameField()
        GameEngine.makeMove(0, 0)
        GameEngine.makeMove(1, 0)
        GameEngine.makeMove(2, 0)
        GameEngine.makeMove(1, 1)
        GameEngine.makeMove(2, 1)
        GameEngine.makeMove(1, 2)
        // gameField & winning message for player B is printed in the console
        // todo: add assertion here
    }

    @Test
    fun test5x5Field() {
        val gameField = GameField(5)
        GameEngine.prepareGame(5, 5)
        Log.pl("\ntest3x3Field: gameEngine ready with given field: ${gameField.prepareForPrintingIn2d()}")
        GameEngine.makeMove(Coordinates(0, 0), Player.A)
        GameEngine.makeMove(Coordinates(1, 0), Player.A)
//    GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A) // intentionally commented - it will be used a bit later
        GameEngine.makeMove(Coordinates(3, 0), Player.A)
        GameEngine.makeMove(Coordinates(4, 0), Player.A)
        GameEngine.makeMove(Coordinates(2, 0), Player.A) // intentionally placed here to connect 2 segments
        // todo: add assertion here
    }
}