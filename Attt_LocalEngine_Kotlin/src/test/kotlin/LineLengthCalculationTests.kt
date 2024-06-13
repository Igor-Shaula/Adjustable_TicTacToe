import gameLogic.GameSession
import geometry.conceptXY.CoordinatesXY
import geometry.conceptXY.LineDirectionForXY
import geometry.conceptXY.NearestAreaScanWithXY
import players.PlayerProvider
import publicApi.AtttGame
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LineLengthCalculationTests {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

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
}