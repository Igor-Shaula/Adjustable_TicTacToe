import logic.PlayerProvider
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
        val game = prepareGameInstanceForClassic3x3GameField()
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

        assertTrue(game.isGameWon(), "Game should have been won")
        assertEquals(PlayerProvider.X, game.getWinner())
    }

    @Test
    fun having3x3Field_onePlayerMakesTheFirstLine_leadingPlayerIsDetectedCorrectly() {
        val game = prepareGameInstanceForClassic3x3GameField()
        game.mm(0, 0) // A
        game.mm(1, 0) // B
        game.mm(0, 1) // A -> now A has a line of 2 marks
        assertEquals(PlayerProvider.X, game.getLeader())
        assertEquals(2, game.getLeader().getMaxLineLength())
    }

    @Test
    fun having5x5Field_onePlayerMakesLongerLineThanAnother_thisPlayerBecomesTheLeadingOne() {
        /*
            A B A .
            A B . .
            . B . .
            . . . .
         */
        val game = prepareGameInstanceForClassic3x3GameField()
        game.mm(0, 0) // A
        game.mm(1, 0) // B
        game.mm(0, 1) // A -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 1) // B -> now B also has a line of 2 marks
        game.mm(2, 0) // A -> now A still has a line of 2 marks
        game.mm(1, 2) // A -> now B has a line of 3 marks and becomes a new leader
        assertEquals(PlayerProvider.O, game.getLeader())
        assertEquals(3, game.getLeader().getMaxLineLength())
    }
}