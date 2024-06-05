import elements.*
import logic.GameEngine
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InternalElementsTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    // region GameEngine preparation

    @Test
    fun gameIsNotStarted_classic3x3GameIsCreated_classic3x3GameFieldIsReady() {
        prepareClassic3x3GameField()
        assertTrue(GameEngine.gameField.isReady())
    }

    @Test
    fun gameIsNotStarted_gameFieldOfAnyCorrectSizeIsCreated_gameFieldWithSpecifiedSizeIsReady() {
        GameEngine.prepareGame(5, 5)
        Log.pl("\ngameEngine is ready having this field: ${GameEngine.gameField.prepareForPrintingIn2d()}")
        assertTrue(GameEngine.gameField.isReady())
        assertEquals(5, GameEngine.gameField.sideLength)
    }

    @Test
    fun gameIsNotStarted_tooSmallGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength = 2 -> game would have no sense in this case, the same as with field size of 1
        GameEngine.prepareGame(2, 2)
        Log.pl("\ngameEngine is ready having this field: ${GameEngine.gameField.prepareForPrintingIn2d()}")
        assertTrue(GameEngine.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, GameEngine.gameField.sideLength)
        // size = maxLength = 0
        GameEngine.prepareGame(0, 0)
        Log.pl("\ngameEngine is ready having this field: ${GameEngine.gameField.prepareForPrintingIn2d()}")
        assertTrue(GameEngine.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, GameEngine.gameField.sideLength)
        // size = maxLength = -1
        GameEngine.prepareGame(-1, -1)
        Log.pl("\ngameEngine is ready having this field: ${GameEngine.gameField.prepareForPrintingIn2d()}")
        assertTrue(GameEngine.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, GameEngine.gameField.sideLength)
        // size = maxLength = Int.MIN_VALUE
        GameEngine.prepareGame(Int.MIN_VALUE, Int.MIN_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${GameEngine.gameField.prepareForPrintingIn2d()}")
        assertTrue(GameEngine.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, GameEngine.gameField.sideLength)
    }

    @Test
    fun gameIsNotStarted_tooBigGameFieldIsCreated_maximal1000x1000GameFieldIsReady() {
        // size = maxLength = 1001 -> for now the limit is set to 1000 dots per side but in the future there could be more
        GameEngine.prepareGame(1001, 1001)
        Log.pl("\ngameEngine is ready having this field: ${GameEngine.gameField.prepareForPrintingIn2d()}")
        assertTrue(GameEngine.gameField.isReady())
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, GameEngine.gameField.sideLength)
        // size = maxLength = Int.MAX_VALUE
        GameEngine.prepareGame(Int.MAX_VALUE, Int.MAX_VALUE)
        Log.pl("\ngameEngine is ready having this field: ${GameEngine.gameField.prepareForPrintingIn2d()}")
        assertTrue(GameEngine.gameField.isReady())
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, GameEngine.gameField.sideLength)
    }

    // endregion GameEngine preparation

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
        GameEngine.makeMove(firstMark, Player.A)
        GameEngine.makeMove(secondMark, Player.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.gameField.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = GameEngine.gameField.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(2, lengthFromFirstToSecond)
        assertEquals(2, lengthFromSecondToFirst)
    }

    @Test
    fun having3x3Field_2RemoteMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(2, 0)
        GameEngine.makeMove(firstMark, Player.A)
        GameEngine.makeMove(secondMark, Player.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.gameField.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = GameEngine.gameField.measureLineFrom(secondMark, LineDirection.XmY0, 1)
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
        GameEngine.makeMove(firstMark, Player.A)
        GameEngine.makeMove(secondMark, Player.A)
        GameEngine.makeMove(connectingMark, Player.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.gameField.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = GameEngine.gameField.measureLineFrom(secondMark, LineDirection.XmY0, 1)
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
        GameEngine.makeMove(firstMark, Player.A)
        GameEngine.makeMove(secondMark, Player.A)
        GameEngine.makeMove(oneMoreMark, Player.A)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromEdgeToEdge = GameEngine.gameField.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromEdgeToEdgeOpposite = GameEngine.gameField.measureLineFrom(oneMoreMark, LineDirection.XmY0, 1)
        assertEquals(3, lengthFromEdgeToEdge)
        assertEquals(3, lengthFromEdgeToEdgeOpposite)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2AdjacentMarksAreSetBySequentialPlayers_noLineIsCreatedForAnyPlayer() {
        prepareClassic3x3GameField()
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        val firstActivePlayer = GameEngine.activePlayer // should be player A
        GameEngine.makeMove(firstMark) // after this line active player is replaced with the next -> B
        Log.pl("measuring line from $firstMark for player: $firstActivePlayer in the forward direction:")
        val lengthForPlayerA = GameEngine.gameField.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        val secondActivePlayer = GameEngine.activePlayer // should be player B
        GameEngine.makeMove(secondMark) // after this line active player is replaced with the next -> A
        Log.pl("measuring line from $secondMark for player: $secondActivePlayer in the forward direction:")
        val lengthForPlayerB = GameEngine.gameField.measureLineFrom(secondMark, LineDirection.XpY0, 1)
        assertEquals(1, lengthForPlayerA)
        assertEquals(1, lengthForPlayerB)
        GameEngine.makeMove(Coordinates(2, 0))
        GameEngine.makeMove(Coordinates(1, 1))
        GameEngine.makeMove(Coordinates(2, 1))
        GameEngine.makeMove(Coordinates(1, 2))
        Log.pl(GameEngine.gameField.prepareForPrintingIn2d())
    }

    @Test
    fun havingOneMarkSetForOnePlayerOn3x3Field_TryToSetMarkForAnotherPlayerInTheSamePlace_previousMarkRemainsUnchanged() {
        prepareClassic3x3GameField()
        val someSpot = Coordinates(1, 1)
        GameEngine.makeMove(someSpot, Player.A)
        GameEngine.makeMove(someSpot, Player.B)
        Log.pl("\ngame field with only one player's mark: ${GameEngine.gameField.prepareForPrintingIn2d()}")
        assertEquals(Player.A, GameEngine.gameField.getCurrentMarkAt(1, 1))
    }

    @Test
    fun having3x3Field_onlyOnePlayerMarksAreSet_victoryConditionIsCorrect() {
        prepareClassic3x3GameField()
        GameEngine.makeMove(Coordinates(0, 0), Player.A)
        GameEngine.makeMove(Coordinates(1, 0), Player.A)
        GameEngine.makeMove(Coordinates(2, 0), Player.A)
        // gameField & winning message for player A is printed in the console
        // todo: add assertion here
    }
}
