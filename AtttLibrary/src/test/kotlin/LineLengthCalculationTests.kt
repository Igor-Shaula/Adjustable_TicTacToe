import attt.Game
import gameLogic.GameSession
import geometry.conceptXY.CoordinatesXY
import geometry.conceptXY.LineDirectionForXY
import geometry.measureLineFrom
import players.PlayerProvider
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
        val game = Game.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(1, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond =
            measureLineFrom(game.gameField, firstMark, LineDirectionForXY.XpY0, 1) { _, _ -> }
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst =
            measureLineFrom(game.gameField, secondMark, LineDirectionForXY.XmY0, 1) { _, _ -> }
        assertEquals(2, lengthFromFirstToSecond)
        assertEquals(2, lengthFromSecondToFirst)
        Log.pl(game.getCurrentFieldAsAString())
    }

    @Test
    fun having3x3Field_2RemoteMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        val game = Game.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(2, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond =
            measureLineFrom(game.gameField, firstMark, LineDirectionForXY.XpY0, 1) { _, _ -> }
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst =
            measureLineFrom(game.gameField, secondMark, LineDirectionForXY.XmY0, 1) { _, _ -> }
        assertEquals(1, lengthFromFirstToSecond)
        assertEquals(1, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
        Log.pl(game.getCurrentFieldAsAString())
    }

    @Test
    fun having3x3Field_2RemoteMarksOfTheSamePlayerAreConnected_detectedLineLengthIsCorrect() {
        val game = Game.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(2, 0)
        val connectingMark = CoordinatesXY(1, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        game.makeMove(connectingMark, playerX)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond =
            measureLineFrom(game.gameField, firstMark, LineDirectionForXY.XpY0, 1) { _, _ -> }
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst =
            measureLineFrom(game.gameField, secondMark, LineDirectionForXY.XmY0, 1) { _, _ -> }
        assertEquals(3, lengthFromFirstToSecond)
        assertEquals(3, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
        Log.pl(game.getCurrentFieldAsAString())
    }

    @Test
    fun having3x3Field_2AdjacentMarksOfTheSamePlayerAreAddedWithOneMoreMark_detectedLineLengthIsCorrect() {
        val game = Game.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(1, 0)
        val oneMoreMark = CoordinatesXY(2, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        game.makeMove(oneMoreMark, playerX)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromEdgeToEdge =
            measureLineFrom(game.gameField, firstMark, LineDirectionForXY.XpY0, 1) { _, _ -> }
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromEdgeToEdgeOpposite =
            measureLineFrom(game.gameField, oneMoreMark, LineDirectionForXY.XmY0, 1) { _, _ -> }
        assertEquals(3, lengthFromEdgeToEdge)
        assertEquals(3, lengthFromEdgeToEdgeOpposite)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
        Log.pl(game.getCurrentFieldAsAString())
    }

    @Test
    fun having3x3Field_2AdjacentMarksAreSetByDifferentPlayers_noLineIsCreatedForAnyPlayer() {
        val game = Game.create() as GameSession
        val firstMark = CoordinatesXY(0, 0)
        val secondMark = CoordinatesXY(1, 0)
        val firstActivePlayer = PlayerProvider.activePlayer // should be player A
        game.makeMove(firstMark) // after this line active player is replaced with the next -> B
        Log.pl("measuring line from $firstMark for player: $firstActivePlayer in the forward direction:")
        val lengthForPlayerA =
            measureLineFrom(game.gameField, firstMark, LineDirectionForXY.XpY0, 1) { _, _ -> }
        val secondActivePlayer = PlayerProvider.activePlayer // should be player B
        game.makeMove(secondMark) // after this line active player is replaced with the next -> A
        Log.pl("measuring line from $secondMark for player: $secondActivePlayer in the forward direction:")
        val lengthForPlayerB =
            measureLineFrom(game.gameField, secondMark, LineDirectionForXY.XpY0, 1) { _, _ -> }
        assertEquals(1, lengthForPlayerA)
        assertEquals(1, lengthForPlayerB)
        Log.pl(game.getCurrentFieldAsAString())
    }
}