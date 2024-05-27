import GameEngine.getTheNextSafeSpotFor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class GameTests {

    /**
     * here we check if an adjacent spot exists for every cell in 3x3 game for every possible direction
     */
    @Test
    fun oneSpotSetOn3x3Field_adjacentSpotDetectionLogic_isCorrect() {
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

    private fun checkTheNextSpotDetectionBlock(startSpot: Coordinates) {
        println("\ncheckTheNextSpotDetectionBlock for given spot: $startSpot:")
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.XmY0)
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.XpY0)
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.X0Ym)
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.X0Yp)
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.XmYm)
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.XpYp)
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.XmYp)
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.XpYm)
        checkTheNextSpotDetectionForLineDirection(startSpot, LineDirection.None)
    }

    private fun checkTheNextSpotDetectionForLineDirection(startSpot: Coordinates, direction: LineDirection) {
        val nextSpot = getTheNextSafeSpotFor(startSpot, direction)
        println("nextSpot on 3x3 field for $direction is $nextSpot")
        when {
            // lowest limit for X axis
            startSpot.x == 0 && (direction == LineDirection.XmYm || direction == LineDirection.XmY0 || direction == LineDirection.XmYp)
            -> assertEquals(Border, nextSpot)
            // lowest limit for Y axis
            startSpot.y == 0 && (direction == LineDirection.XmYm || direction == LineDirection.X0Ym || direction == LineDirection.XpYm)
            -> assertEquals(Border, nextSpot)
            // highest limit for X axis
            startSpot.x == 2 && (direction == LineDirection.XpYm || direction == LineDirection.XpY0 || direction == LineDirection.XpYp)
            -> assertEquals(Border, nextSpot)
            // highest limit for Y axis
            startSpot.y == 2 && (direction == LineDirection.XmYp || direction == LineDirection.X0Yp || direction == LineDirection.XpYp)
            -> assertEquals(Border, nextSpot)
            // right in the center -> adjacent spot exists in any direction
            startSpot.x == 1 && startSpot.y == 1
            -> {
                assertNotEquals(Border, nextSpot)
                assertTrue(nextSpot is Coordinates)
            }
        }
    }

    @Test
    fun test3x3Field() {
        val gameField = GameField(3)
        val gameRules = GameRules(3)
        GameEngine.prepare(gameField, gameRules)
        println("\ntest3x3Field: gameEngine ready with given field: ${gameField.print2dGameField()}")
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