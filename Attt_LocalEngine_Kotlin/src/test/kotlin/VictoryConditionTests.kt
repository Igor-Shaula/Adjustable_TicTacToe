import attt.Game
import constants.MIN_WINNING_LINE_LENGTH
import gameLogic.GameSession
import players.PlayerModel
import players.PlayerProvider
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class VictoryConditionTests {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    @Test
    fun having3x3Field_onlyOnePlayerMarksAreSet_victoryConditionIsCorrect() {
        val game = Game.create() as GameSession
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(0, 0), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(1, 0), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(2, 0), playerX)
        // gameField & winning message for player A is printed in the console
        assertEquals(playerX, game.getWinner())
        assertEquals(MIN_WINNING_LINE_LENGTH, game.getWinner().maxLineLength)
        assertEquals(PlayerModel.None, PlayerProvider.activePlayer)
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having2LinesOfOnePlayerOn5x5Field_thisPlayerMarkIsSetInBetween_victoryConditionIsCorrect() {
        val game = Game.create(5, 5) as GameSession
        val playerX = PlayerProvider.playersList[0]
        Log.pl("\ntest5x5Field: gameEngine ready with given field: ${game.gameField.prepareForPrinting3dIn2d()}")
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(0, 0), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(1, 0), playerX)
        // GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A) // intentionally commented - it will be used a bit later
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(3, 0), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(4, 0), playerX)
        // intentionally placed here to connect 2 segments
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(2, 0), playerX)
        assertEquals(playerX, game.getWinner())
        assertEquals(5, game.getWinner().maxLineLength)
        assertEquals(PlayerModel.None, PlayerProvider.activePlayer)
        game.printCurrentFieldIn2d()
    }

    // this test was provided by Matt Tucker - https://github.com/tuck182 - many thanks for finding a serious bug!
    @Test
    fun test3x3FieldWithMultiplePossibleLines() { // test name is left as it was in the pull-request
        val game = Game.create() as GameSession
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
        assertEquals(MIN_WINNING_LINE_LENGTH, game.getWinner().maxLineLength)
        assertEquals(PlayerModel.None, PlayerProvider.activePlayer)
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_realSimulation2PlayersMovesMade_victoryConditionIsCorrect() {
        val game = Game.create() as GameSession
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
        val playerO = PlayerProvider.playersList[1]
        assertEquals(playerO, game.getWinner())
        assertEquals(MIN_WINNING_LINE_LENGTH, game.getWinner().maxLineLength)
        assertEquals(PlayerModel.None, PlayerProvider.activePlayer)
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_realSimulation2PlayersShortenedMovesMade_victoryConditionIsCorrect() {
        val game = Game.create()
        val playerO = PlayerProvider.playersList[1]
        game.makeMove(0, 0) // X
        game.makeMove(1, 0) // O
        game.makeMove(2, 0) // X
        game.makeMove(1, 1) // O
        game.makeMove(2, 1) // X
        game.makeMove(1, 2) // O
        // gameField & winning message for player B is printed in the console
        assertEquals(playerO, game.getWinner())
        assertEquals(MIN_WINNING_LINE_LENGTH, game.getWinner().maxLineLength)
        assertEquals(PlayerModel.None, PlayerProvider.activePlayer)
        game.printCurrentFieldIn2d()
    }
}