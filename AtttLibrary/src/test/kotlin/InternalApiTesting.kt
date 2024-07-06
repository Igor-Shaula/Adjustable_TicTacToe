import attt.Game
import gameLogic.GameSession
import players.PlayerProvider
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InternalApiTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    @Test
    fun havingOneMarkSetForOnePlayerOn3x3Field_TryToSetMarkForAnotherPlayerInTheSamePlace_previousMarkRemainsUnchanged() {
        val game = Game.create() as GameSession
        val theSameSpot = game.gameField.getCoordinatesFor(1, 1)
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]
        game.makeMove(theSameSpot, playerX)
        game.makeMove(theSameSpot, playerO)
        assertEquals(playerX, game.gameField.getCurrentMarkAt(theSameSpot))
        Log.pl(game.getCurrentFieldAsAString())
    }

    @Test
    fun having3x3Field_TryToSetMarkForThisPlayerOnWrongPosition_currentPlayerRemainsUnchanged() {
        val game = Game.create() as GameSession
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]
        game.makeMove(-1, -1) // attempt to set the mark on a wrong place
        assertEquals(playerX, PlayerProvider.activePlayer) // this player remains chosen for the next move
        game.makeMove(1, 1) // another attempt for the same player - this time successful
        assertEquals(playerO, PlayerProvider.activePlayer) // this time the next player is prepared for a move
        assertEquals(playerX, game.gameField.getCurrentMarkAt(game.gameField.getCoordinatesFor(1, 1)))
        Log.pl(game.getCurrentFieldAsAString())
    }
}
