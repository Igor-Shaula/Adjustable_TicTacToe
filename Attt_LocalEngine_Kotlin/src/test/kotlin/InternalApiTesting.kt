import elements.Coordinates
import elements.Player
import logic.GameEngine
import publicApi.AtttGame
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InternalApiTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    @Test
    fun havingUnpreparedField_aPlayerTriesToMakeMove_noMoveIsMadeOnUnpreparedField() {
        val game = AtttGame.create()
        (game as GameEngine).makeMove(Coordinates(0, 0))
        assertEquals(Player.None, game.getLeader())
        assertEquals(0, game.getLeader().getMaxLineLength())
        // so it's impossible to play game BEFORE prepare() is invoked
    }

    // this test was provided by Matt Tucker - https://github.com/tuck182 - many thanks for finding a serious bug!
    @Test
    fun test3x3FieldWithMultiplePossibleLines() { // test name is left as it was in the pull-request
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
        GameEngine.makeMove(Coordinates(1, 0), Player.A) // here A wins and anyway the next possible player is B

//        assertFalse(GameEngine.isActive(), "Game should have been won") -> no more relevant as the API was changed
        assertTrue(GameEngine.isGameWon(), "Game should have been won")
        // Would be nice to be able to do this:
        // assertEquals(AtttPlayer.A, AtttEngine.getWinner())
        // -> and yes, this is done:
        assertEquals(Player.A, GameEngine.getWinner())
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
        assertEquals(Player.B, GameEngine.getWinner())
        assertEquals(Player.A, GameEngine.activePlayer) // game is ready for the next potential move in any case
        assertEquals(3, GameEngine.getWinner().getMaxLineLength())
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
        assertEquals(Player.B, GameEngine.getWinner())
        assertEquals(Player.A, GameEngine.activePlayer) // game is ready for the next potential move in any case
        assertEquals(3, GameEngine.getWinner().getMaxLineLength())
    }
}