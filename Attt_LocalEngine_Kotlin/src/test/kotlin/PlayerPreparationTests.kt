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

    @Test
    fun having62players_6playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10 + 26 + 26
        (playersBefore until playersBefore + 6).forEach { i ->
            assertEquals(('!' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }

    @Test
    fun having68players_6playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10 + 26 + 26 + 6
        (playersBefore until playersBefore + 6).forEach { i ->
            assertEquals(('(' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }

    @Test
    fun having74players_1playerIsAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10 + 26 + 26 + 6 + 6
        (playersBefore until playersBefore + 1).forEach { i ->
            assertEquals(('/' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }

    @Test
    fun having75players_7playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10 + 26 + 26 + 6 + 6 + 1
        (playersBefore until playersBefore + 7).forEach { i ->
            assertEquals((':' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }

    @Test
    fun having82players_4playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10 + 26 + 26 + 6 + 6 + 1 + 7
        (playersBefore until playersBefore + 4).forEach { i ->
            assertEquals(('[' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }

    @Test
    fun having86players_4playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10 + 26 + 26 + 6 + 6 + 1 + 7 + 4
        (playersBefore until playersBefore + 4).forEach { i ->
            assertEquals(('{' + i - playersBefore), PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }

    @Test
    fun having90players_10playersAreAdded_playersSymbolsAreDefinedCorrectly() {
        val playersBefore = 10 + 26 + 26 + 6 + 6 + 1 + 7 + 4 + 4
        (playersBefore until playersBefore + 10).forEach { i ->
            assertEquals('_', PlayerModel(i).getSymbol())
            Log.pl("Player's index: $i, symbol: ${PlayerModel(i).getSymbol().toString()}")
        }
    }
}