import GameEngine.getTheNextSafeSpotFor
import kotlin.test.Test

class GameTests {

    // todo: ASSERTIONS will be added very soon. at first I wanted to see the console output of the GameEngine

    @Test
    fun oneSpotSetOn3x3Field_adjacentSpotDetectionLogic_isCorrect() {
        testTheNextSpotCreationBlock(Coordinates(0, 0))
        testTheNextSpotCreationBlock(Coordinates(0, 1))
        testTheNextSpotCreationBlock(Coordinates(0, 2))
        testTheNextSpotCreationBlock(Coordinates(1, 0))
        testTheNextSpotCreationBlock(Coordinates(1, 1))
        testTheNextSpotCreationBlock(Coordinates(1, 2))
        testTheNextSpotCreationBlock(Coordinates(2, 0))
        testTheNextSpotCreationBlock(Coordinates(2, 1))
        testTheNextSpotCreationBlock(Coordinates(2, 2))
    }

    private fun testTheNextSpotCreationBlock(startSpot: Coordinates) {
        println("\ntestTheNextSpotCreationBlock for given spot: $startSpot:")
        testTheNextSpotCreationFor(startSpot, LineDirection.XmY0)
        testTheNextSpotCreationFor(startSpot, LineDirection.XpY0)
        testTheNextSpotCreationFor(startSpot, LineDirection.X0Ym)
        testTheNextSpotCreationFor(startSpot, LineDirection.X0Yp)
        testTheNextSpotCreationFor(startSpot, LineDirection.XmYm)
        testTheNextSpotCreationFor(startSpot, LineDirection.XpYp)
        testTheNextSpotCreationFor(startSpot, LineDirection.XmYp)
        testTheNextSpotCreationFor(startSpot, LineDirection.XpYm)
        testTheNextSpotCreationFor(startSpot, LineDirection.None)
    }

    private fun testTheNextSpotCreationFor(startSpot: Coordinates, direction: LineDirection) {
        val nextSpot = getTheNextSafeSpotFor(startSpot, direction)
        println("nextSpot for $direction is $nextSpot")
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