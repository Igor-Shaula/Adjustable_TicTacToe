import elements.Border
import elements.Coordinates
import geometry.CoordinatesXY
import geometry.LineDirectionForXY
import logic.GameSession
import publicApi.AtttGame
import utilities.Log
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal fun checkTheNextSpotDetectionBlock(startSpot: CoordinatesXY) {
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

internal fun checkTheNextSpotDetectionForLineDirection(startSpot: CoordinatesXY, direction: LineDirectionForXY) {
    // as gameField is a stateful object - we have to reset it every time before a new test
    val game = AtttGame.create() as GameSession
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
