import constants.MAX_NUMBER_OF_PLAYERS
import players.PlayerModel
import utilities.Log
import kotlin.test.BeforeTest
import kotlin.test.Test

class PlayerPreparationTests {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    @Test
    fun playerIsCreated_playerSymbolIsDefinedCorrectly() {
        (0..MAX_NUMBER_OF_PLAYERS).forEach { index ->
            Log.pl("symbol for player with id $index is: ${PlayerModel(index).getSymbol()}")
        }
    }
}