import kotlin.test.*

class GameTests {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    @Test
    fun gameNotStarted_defaultGameCreated_3x3GameFieldExists() {
        prepareClassic3x3GameField()
        assertNotNull(GameEngine.getCurrentField())
        assertTrue(GameEngine.isRunning())
        assertTrue(GameEngine.getCurrentField().isNotEmpty()) // actually it's the same as GameEngine.isRunning()
    }

    @Test
    fun having2dField_anyLineDirectionChosen_detectingOppositeDirectionIsCorrect() {
        assertEquals(LineDirection.XpY0, LineDirection.XmY0.opposite())
        assertEquals(LineDirection.XmY0, LineDirection.XpY0.opposite())
        assertEquals(LineDirection.X0Yp, LineDirection.X0Ym.opposite())
        assertEquals(LineDirection.X0Ym, LineDirection.X0Yp.opposite())
        assertEquals(LineDirection.XpYp, LineDirection.XmYm.opposite())
        assertEquals(LineDirection.XmYm, LineDirection.XpYp.opposite())
        assertEquals(LineDirection.XpYm, LineDirection.XmYp.opposite())
        assertEquals(LineDirection.XmYp, LineDirection.XpYm.opposite())
        assertEquals(LineDirection.None, LineDirection.None.opposite())
    }

    /**
     * here we check if an adjacent spot exists for every cell in 3x3 game for every possible direction
     */
    @Test
    fun having3x3Field_1MarkSet_adjacentMarkDetectionLogicIsCorrect() {
        checkTheNextSpotDetectionBlock(Coordinates(0, 0))
        checkTheNextSpotDetectionBlock(Coordinates(0, 1))
        checkTheNextSpotDetectionBlock(Coordinates(0, 2))
        checkTheNextSpotDetectionBlock(Coordinates(1, 0))
        checkTheNextSpotDetectionBlock(Coordinates(1, 1))
        checkTheNextSpotDetectionBlock(Coordinates(1, 2))
        checkTheNextSpotDetectionBlock(Coordinates(2, 0))
        checkTheNextSpotDetectionBlock(Coordinates(2, 1))
        checkTheNextSpotDetectionBlock(Coordinates(2, 2))
    }

    @Test
    fun having3x3Field_2AdjacentMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        GameEngine.makeNewMove(firstMark, WhichPlayer.A)
        GameEngine.makeNewMove(secondMark, WhichPlayer.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = GameEngine.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(2, lengthFromFirstToSecond)
        assertEquals(2, lengthFromSecondToFirst)
    }

    @Test
    fun having3x3Field_2RemoteMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(2, 0)
        GameEngine.makeNewMove(firstMark, WhichPlayer.A)
        GameEngine.makeNewMove(secondMark, WhichPlayer.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = GameEngine.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(1, lengthFromFirstToSecond)
        assertEquals(1, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2RemoteMarksOfTheSamePlayerAreConnected_detectedLineLengthIsCorrect() {
        prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(2, 0)
        val connectingMark = Coordinates(1, 0)
        GameEngine.makeNewMove(firstMark, WhichPlayer.A)
        GameEngine.makeNewMove(secondMark, WhichPlayer.A)
        GameEngine.makeNewMove(connectingMark, WhichPlayer.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = GameEngine.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(3, lengthFromFirstToSecond)
        assertEquals(3, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2AdjacentMarksOfTheSamePlayerAreAddedWithOneMoreMark_detectedLineLengthIsCorrect() {
        prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        val oneMoreMark = Coordinates(2, 0)
        GameEngine.makeNewMove(firstMark, WhichPlayer.A)
        GameEngine.makeNewMove(secondMark, WhichPlayer.A)
        GameEngine.makeNewMove(oneMoreMark, WhichPlayer.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromEdgeToEdge = GameEngine.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromEdgeToEdgeOpposite = GameEngine.measureLineFrom(oneMoreMark, LineDirection.XmY0, 1)
        assertEquals(3, lengthFromEdgeToEdge)
        assertEquals(3, lengthFromEdgeToEdgeOpposite)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun havingOneMarkSetForOnePlayerOn3x3Field_TryToSetMarkForAnotherPlayerInTheSamePlace_previousMarkRemainsUnchanged() {
        prepareClassic3x3GameField()
        val someSpot = Coordinates(1, 1)
        GameEngine.makeNewMove(someSpot, WhichPlayer.A)
        GameEngine.makeNewMove(someSpot, WhichPlayer.B)
        Log.pl("\ngame field with only one player's mark: ${GameEngine.gameField.print2dGameField()}")
        assertEquals(WhichPlayer.A, GameEngine.getCurrentField()[someSpot])
    }

    @Test
    fun test3x3Field() {
        prepareClassic3x3GameField()
        GameEngine.makeNewMove(Coordinates(0, 0), WhichPlayer.A)
        GameEngine.makeNewMove(Coordinates(1, 0), WhichPlayer.A)
        GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A)
    }

    @Test
    fun test5x5Field() {
        val gameField = GameField(5)
        val gameRules = GameRules(5)
        GameEngine.prepare(gameField, gameRules)
        Log.pl("\ntest3x3Field: gameEngine ready with given field: ${gameField.print2dGameField()}")
        GameEngine.makeNewMove(Coordinates(0, 0), WhichPlayer.A)
        GameEngine.makeNewMove(Coordinates(1, 0), WhichPlayer.A)
//    GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A) // intentionally commented - it will be used a bit later
        GameEngine.makeNewMove(Coordinates(3, 0), WhichPlayer.A)
        GameEngine.makeNewMove(Coordinates(4, 0), WhichPlayer.A)
        GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A) // intentionally placed here to connect 2 segments
    }
}