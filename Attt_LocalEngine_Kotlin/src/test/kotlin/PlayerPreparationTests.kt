import players.PlayerModel
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerPreparationTests {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    //    @Test
//    fun playerIsCreated_playerSymbolIsDefinedCorrectly() {
//        (0..MAX_NUMBER_OF_PLAYERS).forEach { index ->
//            Log.pl("symbol for player with id $index is: ${PlayerModel(index).getSymbol()}")
//        }
//    }

    @Test
    fun having0players_10playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 0
        (playersBefore until playersBefore + 10).forEach { i ->
            assertEquals(('0' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }

    @Test
    fun having10players_26playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10
        (playersBefore until playersBefore + 26).forEach { i ->
            assertEquals(('A' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }

    @Test
    fun having36players_26playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10 + 26
        (playersBefore until playersBefore + 26).forEach { i ->
            assertEquals(('a' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }
}