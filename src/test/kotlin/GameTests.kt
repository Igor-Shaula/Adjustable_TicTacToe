import kotlin.test.Test
import kotlin.test.assertEquals

class GameTests {

    /**
     * here we check if an adjacent spot exists for every cell in 3x3 game for every possible direction
     */
    @Test
    fun having3x3Field_1MarkSet_adjacentMarkDetectionLogiIsCorrect() {
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
        println("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        println("measuring line from $firstMark in the opposite direction:")
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
        println("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        println("measuring line from $firstMark in the opposite direction:")
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
        println("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = GameEngine.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        println("measuring line from $firstMark in the opposite direction:")
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
        println("measuring line from $firstMark in the forward direction:")
        val lengthFromEdgeToEdge = GameEngine.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        println("measuring line from $firstMark in the opposite direction:")
        val lengthFromEdgeToEdgeOpposite = GameEngine.measureLineFrom(oneMoreMark, LineDirection.XmY0, 1)
        assertEquals(3, lengthFromEdgeToEdge)
        assertEquals(3, lengthFromEdgeToEdgeOpposite)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
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
        println("\ntest3x3Field: gameEngine ready with given field: ${gameField.print2dGameField()}")
        GameEngine.makeNewMove(Coordinates(0, 0), WhichPlayer.A)
        GameEngine.makeNewMove(Coordinates(1, 0), WhichPlayer.A)
//    GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A) // intentionally commented - it will be used a bit later
        GameEngine.makeNewMove(Coordinates(3, 0), WhichPlayer.A)
        GameEngine.makeNewMove(Coordinates(4, 0), WhichPlayer.A)
        GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A) // intentionally placed here to connect 2 segments
    }
}