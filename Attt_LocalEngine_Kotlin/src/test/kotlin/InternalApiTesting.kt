import logic.GameSession
import logic.PlayerProvider
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

    // this test was provided by Matt Tucker - https://github.com/tuck182 - many thanks for finding a serious bug!
    @Test
    fun test3x3FieldWithMultiplePossibleLines() { // test name is left as it was in the pull-request
        val game = AtttGame.create() as GameSession
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]

        // .Xx
        // .xo
        // oxo

        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(1, 1), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(2, 1), playerO)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(2, 0), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(0, 2), playerO)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(1, 2), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(2, 2), playerO)
        game.makeMove(
            game.chosenAlgorithm.getCoordinatesFor(1, 0),
            playerX
        ) // here A wins and anyway the next possible player is B

        assertTrue(game.isGameWon(), "Game should have been won")
        // Would be nice to be able to do this:
        // assertEquals(AtttPlayer.A, AtttEngine.getWinner())
        // -> and yes, this is done:
        assertEquals(playerX, game.getWinner())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_realSimulation2PlayersMovesMade_victoryConditionIsCorrect() {
        val game = AtttGame.create() as GameSession
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(0, 0)) // X
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(1, 0)) // O
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(2, 0)) // X
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(1, 1)) // O
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(2, 1)) // X
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(1, 2)) // O - > O wins here
        /*
            X O X
            . O X
            . O .
         */
        // gameField & winning message for player B is printed in the console
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]
        assertEquals(playerO, game.getWinner())
        assertEquals(playerX, PlayerProvider.activePlayer) // game is ready for the next potential move in any case
        assertEquals(3, game.getWinner().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_realSimulation2PlayersShortenedMovesMade_victoryConditionIsCorrect() {
        val game = AtttGame.create()
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]
        game.makeMove(0, 0) // X
        game.makeMove(1, 0) // O
        game.makeMove(2, 0) // X
        game.makeMove(1, 1) // O
        game.makeMove(2, 1) // X
        game.makeMove(1, 2) // O
        // gameField & winning message for player B is printed in the console
        assertEquals(playerO, game.getWinner())
        assertEquals(playerX, PlayerProvider.activePlayer) // game is ready for the next potential move in any case
        assertEquals(3, game.getWinner().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }
}