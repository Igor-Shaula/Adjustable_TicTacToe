import geometry.conceptXY.CoordinatesXY
import geometry.conceptXY.LineDirectionForXY
import constants.MAX_GAME_FIELD_SIDE_SIZE
import constants.MIN_GAME_FIELD_SIDE_SIZE
import gameLogic.GameSession
import geometry.conceptXY.NearestAreaScanWithXY
import players.PlayerProvider
import publicApi.AtttGame
import utilities.Log
import kotlin.test.*

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

    // region line length measurements

    @Test
    fun having3x3Field_2AdjacentMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        val game = AtttGame.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(1, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val algorithmForXY = NearestAreaScanWithXY(game.gameField)
        val lengthFromFirstToSecond = algorithmForXY.measureLineFrom(firstMark, LineDirectionForXY.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = algorithmForXY.measureLineFrom(secondMark, LineDirectionForXY.XmY0, 1)
        assertEquals(2, lengthFromFirstToSecond)
        assertEquals(2, lengthFromSecondToFirst)
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_2RemoteMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        val game = AtttGame.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(2, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        val algorithmForXY = NearestAreaScanWithXY(game.gameField)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = algorithmForXY.measureLineFrom(firstMark, LineDirectionForXY.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = algorithmForXY.measureLineFrom(secondMark, LineDirectionForXY.XmY0, 1)
        assertEquals(1, lengthFromFirstToSecond)
        assertEquals(1, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_2RemoteMarksOfTheSamePlayerAreConnected_detectedLineLengthIsCorrect() {
        val game = AtttGame.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(2, 0)
        val connectingMark = CoordinatesXY(1, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        game.makeMove(connectingMark, playerX)
        val algorithmForXY = NearestAreaScanWithXY(game.gameField)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = algorithmForXY.measureLineFrom(firstMark, LineDirectionForXY.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = algorithmForXY.measureLineFrom(secondMark, LineDirectionForXY.XmY0, 1)
        assertEquals(3, lengthFromFirstToSecond)
        assertEquals(3, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_2AdjacentMarksOfTheSamePlayerAreAddedWithOneMoreMark_detectedLineLengthIsCorrect() {
        val game = AtttGame.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(1, 0)
        val oneMoreMark = CoordinatesXY(2, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        game.makeMove(oneMoreMark, playerX)
        val algorithmForXY = NearestAreaScanWithXY(game.gameField)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromEdgeToEdge = algorithmForXY.measureLineFrom(firstMark, LineDirectionForXY.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromEdgeToEdgeOpposite = algorithmForXY.measureLineFrom(oneMoreMark, LineDirectionForXY.XmY0, 1)
        assertEquals(3, lengthFromEdgeToEdge)
        assertEquals(3, lengthFromEdgeToEdgeOpposite)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_2AdjacentMarksAreSetByDifferentPlayers_noLineIsCreatedForAnyPlayer() {
        val game = AtttGame.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(1, 0)
        val firstActivePlayer = PlayerProvider.activePlayer // should be player A
        val algorithmForXY = NearestAreaScanWithXY(game.gameField)
        game.makeMove(firstMark) // after this line active player is replaced with the next -> B
        Log.pl("measuring line from $firstMark for player: $firstActivePlayer in the forward direction:")
        val lengthForPlayerA = algorithmForXY.measureLineFrom(firstMark, LineDirectionForXY.XpY0, 1)
        val secondActivePlayer = PlayerProvider.activePlayer // should be player B
        game.makeMove(secondMark) // after this line active player is replaced with the next -> A
        Log.pl("measuring line from $secondMark for player: $secondActivePlayer in the forward direction:")
        val lengthForPlayerB = algorithmForXY.measureLineFrom(secondMark, LineDirectionForXY.XpY0, 1)
        assertEquals(1, lengthForPlayerA)
        assertEquals(1, lengthForPlayerB)
        game.printCurrentFieldIn2d()
    }

    // endregion line length measurements

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

    @Test
    fun having3x3Field_onlyOnePlayerMarksAreSet_victoryConditionIsCorrect() {
        val game = AtttGame.create() as GameSession
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(0, 0), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(1, 0), playerX)
        game.makeMove(game.chosenAlgorithm.getCoordinatesFor(2, 0), playerX)
        // gameField & winning message for player A is printed in the console
        assertEquals(playerX, game.getWinner())
        assertEquals(3, game.getWinner().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having2LinesOfOnePlayerOn5x5Field_thisPlayerMarkIsSetInBetween_victoryConditionIsCorrect() {
        val game = AtttGame.create(5, 5) as GameSession
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
        assertEquals(5, game.getWinner().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }
}
