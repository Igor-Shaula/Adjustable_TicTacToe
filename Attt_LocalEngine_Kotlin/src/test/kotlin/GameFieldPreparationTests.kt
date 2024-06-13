import constants.MAX_GAME_FIELD_SIDE_SIZE
import constants.MIN_GAME_FIELD_SIDE_SIZE
import gameLogic.GameSession
import publicApi.AtttGame
import utilities.Log
import kotlin.test.*

class GameFieldPreparationTests {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    @Test
    fun gameIsNotStarted_classic3x3GameIsCreated_classic3x3GameFieldIsReady() {
        val game = AtttGame.create() as GameSession
        assertTrue(game.gameField.isReady())
        assertFalse(game.isGameWon())
    }

    @Test
    fun gameIsNotStarted_gameFieldOfAnyCorrectSizeIsCreated_gameFieldWithSpecifiedSizeIsReady() {
        val game = AtttGame.create(7, 5) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${game.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(game.gameField.isReady())
        assertEquals(7, game.gameField.sideLength)
    }

    @Test
    fun gameIsNotStarted_tooSmallGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength = 2 -> game would have no sense in this case, the same as with field size of 1
        val game2x2 = AtttGame.create(2, 2) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${game2x2.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(game2x2.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, game2x2.gameField.sideLength)
        // size = maxLength = 0
        val game0x0 = AtttGame.create(0, 0) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${game0x0.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(game0x0.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, game0x0.gameField.sideLength)
        // size = maxLength = -1
        val gameM1M1 = AtttGame.create(-1, -1) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameM1M1.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameM1M1.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameM1M1.gameField.sideLength)
        // size = maxLength = Int.MIN_VALUE
        val gameMxM = AtttGame.create(Int.MIN_VALUE, Int.MIN_VALUE) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMxM.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMxM.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMxM.gameField.sideLength)
    }

    @Test
    fun gameIsNotStarted_tooBigGameFieldIsCreated_maximal1000x1000GameFieldIsReady() {
        // size = maxLength = 1001 -> for now the limit is set to 1000 dots per side but in the future there could be more
        val game1k1 = AtttGame.create(
            MAX_GAME_FIELD_SIDE_SIZE + 1, MAX_GAME_FIELD_SIDE_SIZE + 1
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${game1k1.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(game1k1.gameField.isReady())
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, game1k1.gameField.sideLength)
        // size = maxLength = Int.MAX_VALUE
        val gameMM = AtttGame.create(Int.MAX_VALUE, Int.MAX_VALUE) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMM.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMM.gameField.isReady())
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, gameMM.gameField.sideLength)
    }

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_underMinIntGameFieldIsCreated_maximal1000x1000GameFieldIsReady() {
        // size = maxLength tries to be less than Int.MIN_VALUE -> there will be overflow of the Int
        val game = AtttGame.create(
            Int.MIN_VALUE - 1, Int.MIN_VALUE - 1
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${game.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(game.gameField.isReady())
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, game.gameField.sideLength)
    }

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_overMaxIntGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength tries to be more than Int.MAX_VALUE -> there will be overflow of the Int
        val game = AtttGame.create(
            Int.MAX_VALUE + 1, Int.MAX_VALUE + 1
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${game.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(game.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, game.gameField.sideLength)
    }

    // assertions of this test are made mostly to see interesting effects during different kinds of limits mixing
    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_MinAndMaxIntMixedGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength = Int.MIN_VALUE + Int.MIN_VALUE = 0 in fact - this is an interesting effect of the Int
        val gameMinPlusMin = AtttGame.create(
            Int.MIN_VALUE + Int.MIN_VALUE, Int.MIN_VALUE + Int.MIN_VALUE
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMinPlusMin.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMinPlusMin.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMinPlusMin.gameField.sideLength)
        println(Int.MIN_VALUE + Int.MIN_VALUE)

        // size = maxLength = Int.MIN_VALUE - Int.MIN_VALUE = 0 in fact - this is an interesting effect of the Int
        val gameMinMinusMin = AtttGame.create(
            Int.MIN_VALUE - Int.MIN_VALUE, Int.MIN_VALUE - Int.MIN_VALUE
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMinMinusMin.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMinMinusMin.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMinMinusMin.gameField.sideLength)
        println(Int.MIN_VALUE - Int.MIN_VALUE)

        // size = maxLength = Int.MAX_VALUE + Int.MAX_VALUE = -2 in fact - this is an interesting effect of the Int
        val gameMaxPlusMax = AtttGame.create(
            Int.MAX_VALUE + Int.MAX_VALUE, Int.MAX_VALUE + Int.MAX_VALUE
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMaxPlusMax.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMaxPlusMax.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMaxPlusMax.gameField.sideLength)
        println(Int.MAX_VALUE + Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE - Int.MAX_VALUE = 0 which is obvious
        val gameMaxMinusMax = AtttGame.create(
            Int.MAX_VALUE - Int.MAX_VALUE, Int.MAX_VALUE - Int.MAX_VALUE
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMaxMinusMax.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMaxMinusMax.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMaxMinusMax.gameField.sideLength)
        println(Int.MAX_VALUE - Int.MAX_VALUE)

        // size = maxLength = Int.MIN_VALUE - Int.MAX_VALUE = +1 in fact - this is an interesting effect of the Int
        val gameMinMinusMax = AtttGame.create(
            Int.MIN_VALUE - Int.MAX_VALUE, Int.MIN_VALUE - Int.MAX_VALUE
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMinMinusMax.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMinMinusMax.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMinMinusMax.gameField.sideLength)
        println(Int.MIN_VALUE - Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE - Int.MIN_VALUE = -1 which is obvious
        val gameMaxMinusMin = AtttGame.create(
            Int.MAX_VALUE - Int.MIN_VALUE, Int.MAX_VALUE - Int.MIN_VALUE
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMaxMinusMin.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMaxMinusMin.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMaxMinusMin.gameField.sideLength)
        println(Int.MAX_VALUE - Int.MIN_VALUE)

        // size = maxLength = Int.MIN_VALUE + Int.MAX_VALUE = -1 which is obvious
        val gameMinPlusMax = AtttGame.create(
            Int.MIN_VALUE + Int.MAX_VALUE, Int.MIN_VALUE + Int.MAX_VALUE
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMinPlusMax.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMinPlusMax.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMinPlusMax.gameField.sideLength)
        println(Int.MIN_VALUE + Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE + Int.MIN_VALUE = -1 which is obvious
        val gameMaxPlusMin = AtttGame.create(
            Int.MAX_VALUE + Int.MIN_VALUE, Int.MAX_VALUE + Int.MIN_VALUE
        ) as GameSession
        Log.pl("\ngameEngine is ready having this field: ${gameMaxPlusMin.gameField.prepareForPrinting3dIn2d()}")
        assertTrue(gameMaxPlusMin.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMaxPlusMin.gameField.sideLength)
        println(Int.MAX_VALUE + Int.MIN_VALUE)
    }
}