import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse

// simulation of different cases which can emerge when playing as a consumer of this API
// preferably tests for AtttGame interface & other publicly accessible code of the library

class PublicApiTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    @Test
    fun test3x3FieldWithMultiplePossibleLinesNew() {
        val game = prepareGameInstanceForClassic3x3GameField()

        // .Xx
        // .xo
        // oxo

        game.mm(1, 1) // X
        game.mm(2, 1) // O
        game.mm(2, 0) // X
        game.mm(0, 2) // O
        game.mm(1, 2) // X
        game.mm(2, 2) // O
        game.mm(1, 0) // X - this one was problematic

        assertFalse(game.isActive(), "Game should have been won")
        // Would be nice to be able to do this:
        // assertEquals(AtttPlayer.A, AtttEngine.getWinner())
    }
}