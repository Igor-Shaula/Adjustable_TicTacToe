import elements.Player
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
        assertEquals(Player.A, game.getWinner())
    }
}