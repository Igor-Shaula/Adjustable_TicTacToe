import gameLogic.GameSession
import geometry.abstractions.Border
import geometry.abstractions.Coordinates
import geometry.conceptXY.CoordinatesXY
import geometry.conceptXY.LineDirectionForXY
import attt.Game
import utilities.Log
import kotlin.test.*

class AdjacentAreaDetectionTests {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

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
}

private fun checkTheNextSpotDetectionBlock(startSpot: CoordinatesXY) {
    Log.pl("\ncheckTheNextSpotDetectionBlock for given spot: $startSpot:")
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.XmY0)
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.XpY0)
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.X0Ym)
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.X0Yp)
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.XmYm)
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.XpYp)
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.XmYp)
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.XpYm)
    checkTheNextSpotDetectionForLineDirection(startSpot, LineDirectionForXY.None)
}

private fun checkTheNextSpotDetectionForLineDirection(startSpot: CoordinatesXY, direction: LineDirectionForXY) {
    // as gameField is a stateful object - we have to reset it every time before a new test
    val game = Game.create() as GameSession
    val nextSpot = startSpot.getTheNextSpaceFor(direction, game.gameField.sideLength)
    Log.pl("nextSpot on 3x3 field for $direction is $nextSpot")
    when {
        // lowest limit for X axis
        startSpot.x == 0 && (direction == LineDirectionForXY.XmYm || direction == LineDirectionForXY.XmY0 || direction == LineDirectionForXY.XmYp)
        -> assertEquals(Border, nextSpot)
        // lowest limit for Y axis
        startSpot.y == 0 && (direction == LineDirectionForXY.XmYm || direction == LineDirectionForXY.X0Ym || direction == LineDirectionForXY.XpYm)
        -> assertEquals(Border, nextSpot)
        // highest limit for X axis
        startSpot.x == 2 && (direction == LineDirectionForXY.XpYm || direction == LineDirectionForXY.XpY0 || direction == LineDirectionForXY.XpYp)
        -> assertEquals(Border, nextSpot)
        // highest limit for Y axis
        startSpot.y == 2 && (direction == LineDirectionForXY.XmYp || direction == LineDirectionForXY.X0Yp || direction == LineDirectionForXY.XpYp)
        -> assertEquals(Border, nextSpot)
        // right in the center -> adjacent spot exists in any direction
        startSpot.x == 1 && startSpot.y == 1
        -> {
            assertNotEquals(Border, nextSpot)
            assertTrue(nextSpot is Coordinates)
        }
    }
}
