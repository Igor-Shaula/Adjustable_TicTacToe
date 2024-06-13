import gameLogic.GameSession
import geometry.conceptXY.CoordinatesXY
import geometry.conceptXY.LineDirectionForXY
import players.PlayerProvider
import publicApi.AtttGame
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InternalElementsTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    // region line direction & next spot detection

    @Test
    fun having2dField_anyLineDirectionChosen_detectingOppositeDirectionIsCorrect() {
        assertEquals(LineDirectionForXY.XpY0, LineDirectionForXY.XmY0.opposite())
        assertEquals(LineDirectionForXY.XmY0, LineDirectionForXY.XpY0.opposite())
        assertEquals(LineDirectionForXY.X0Yp, LineDirectionForXY.X0Ym.opposite())
        assertEquals(LineDirectionForXY.X0Ym, LineDirectionForXY.X0Yp.opposite())
        assertEquals(LineDirectionForXY.XpYp, LineDirectionForXY.XmYm.opposite())
        assertEquals(LineDirectionForXY.XmYm, LineDirectionForXY.XpYp.opposite())
        assertEquals(LineDirectionForXY.XpYm, LineDirectionForXY.XmYp.opposite())
        assertEquals(LineDirectionForXY.XmYp, LineDirectionForXY.XpYm.opposite())
        assertEquals(LineDirectionForXY.None, LineDirectionForXY.None.opposite())
    }

    /**
     * here we check if an adjacent spot exists for every cell in 3x3 game for every possible direction
     */
    @Test
    fun having3x3Field_1MarkSet_adjacentMarkDetectionLogicIsCorrect() {
        checkTheNextSpotDetectionBlock(CoordinatesXY(0, 0))
        checkTheNextSpotDetectionBlock(CoordinatesXY(0, 1))
        checkTheNextSpotDetectionBlock(CoordinatesXY(0, 2))
        checkTheNextSpotDetectionBlock(CoordinatesXY(1, 0))
        checkTheNextSpotDetectionBlock(CoordinatesXY(1, 1))
        checkTheNextSpotDetectionBlock(CoordinatesXY(1, 2))
        checkTheNextSpotDetectionBlock(CoordinatesXY(2, 0))
        checkTheNextSpotDetectionBlock(CoordinatesXY(2, 1))
        checkTheNextSpotDetectionBlock(CoordinatesXY(2, 2))
    }

    // endregion line direction & next spot detection

    @Test
    fun havingOneMarkSetForOnePlayerOn3x3Field_TryToSetMarkForAnotherPlayerInTheSamePlace_previousMarkRemainsUnchanged() {
        val game = AtttGame.create() as GameSession
        val theSameSpot = game.chosenAlgorithm.getCoordinatesFor(1, 1)
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]
        game.makeMove(theSameSpot, playerX)
        game.makeMove(theSameSpot, playerO)
        assertEquals(playerX, game.gameField.getCurrentMarkAt(theSameSpot))
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_TryToSetMarkForThisPlayerOnWrongPosition_currentPlayerRemainsUnchanged() {
        val game = AtttGame.create() as GameSession
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]
        game.makeMove(-1, -1) // attempt to set the mark on a wrong place
        assertEquals(playerX, PlayerProvider.activePlayer) // this player remains chosen for the next move
        game.makeMove(1, 1) // another attempt for the same player - this time successful
        assertEquals(playerO, PlayerProvider.activePlayer) // this time the next player is prepared for a move
        assertEquals(playerX, game.gameField.getCurrentMarkAt(game.chosenAlgorithm.getCoordinatesFor(1, 1)))
        game.printCurrentFieldIn2d()
    }
}
