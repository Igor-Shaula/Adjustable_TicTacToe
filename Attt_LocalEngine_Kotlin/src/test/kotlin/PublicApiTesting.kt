import logic.PlayerProvider
import publicApi.AtttGame
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// simulation of different cases which can emerge when playing as a consumer of this API
// preferably tests for AtttGame interface & other publicly accessible code of the library

class PublicApiTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    @Test
    fun having3x3Field_onePlayerGetsMultiplePossibleLines_winnerIsDetectedOnceTheConditionsAreMet() {
        val game = AtttGame.create(3, 3)
        /*
            . X x
            . x o
            o x o
         */
        game.mm(1, 1) // X
        game.mm(2, 1) // O
        game.mm(2, 0) // X
        game.mm(0, 2) // O
        game.mm(1, 2) // X
        game.mm(2, 2) // O
        game.mm(1, 0) // X - this one was problematic but in version 0.3.0 this bug was fixed
        val playerX = PlayerProvider.playersList[0]
        assertTrue(game.isGameWon(), "Game should have been won")
        assertEquals(playerX, game.getWinner())
    }

    @Test
    fun having3x3Field_onePlayerMakesTheFirstLine_leadingPlayerIsDetectedCorrectly() {
        val game = AtttGame.create(3, 3)
        game.mm(0, 0) // X
        game.mm(1, 0) // O
        game.mm(0, 1) // X -> now A has a line of 2 marks
        val playerX = PlayerProvider.playersList[0]
        assertEquals(playerX, game.getLeader())
        assertEquals(2, game.getLeader().getMaxLineLength())
    }

    @Test
    fun having4x4Field_onePlayerMakesLongerLineThanAnother_thisPlayerBecomesTheLeadingOne() {
        val game = AtttGame.create(4, 4)
        /*
            A B A .
            A B . .
            . B . .
            . . . .
         */
        game.mm(0, 0) // X
        game.mm(1, 0) // O
        game.mm(0, 1) // X -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 1) // O -> now B also has a line of 2 marks
        game.mm(2, 0) // X -> now A still has a line of 2 marks
        game.mm(1, 2) // O -> now B has a line of 3 marks and becomes a new leader
        val playerO = PlayerProvider.playersList[1]
        assertEquals(playerO.getId(), game.getLeader().getId())
        assertEquals(3, game.getLeader().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having4x4Field_3PlayersMakeCorrectMoves_activePlayerDefinitionForEachMoveIsCorrect() {
        val game = AtttGame.create(4, 4, 3)
        /*
            A B C .
            A B C .
            A . . .
            . . . .
         */
        game.mm(0, 0) // A
        game.mm(1, 0) // B
        game.mm(2, 0) // C
        game.mm(0, 1) // A -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 1) // B -> now B also has a line of 2 marks
        game.mm(2, 1) // C -> now C also has a line of 2 marks
        game.mm(0, 2) // A -> now A has a line of 3 marks and becomes a new leader
        game.printCurrentFieldIn2d()
        assertEquals(PlayerProvider.playersList[0], game.getLeader())
        assertEquals(3, game.getLeader().getMaxLineLength())
    }

}