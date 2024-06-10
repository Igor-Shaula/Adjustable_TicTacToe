import elements.Coordinates
import elements.LineDirection
import elements.MAX_GAME_FIELD_SIDE_SIZE
import elements.MIN_GAME_FIELD_SIDE_SIZE
import logic.GameSession
import logic.PlayerProvider
import utilities.Log
import kotlin.test.*

class InternalElementsTesting {

    @BeforeTest
    fun switchLoggingOn() {
        Log.switch(true)
    }

    // region GameEngine preparation

    @Test
    fun gameIsNotStarted_classic3x3GameIsCreated_classic3x3GameFieldIsReady() {
        val game = GameSession(3, 3, 2)
        assertTrue(game.gameField.isReady())
        assertFalse(game.isGameWon())
    }

    @Test
    fun gameIsNotStarted_gameFieldOfAnyCorrectSizeIsCreated_gameFieldWithSpecifiedSizeIsReady() {
        val game = GameSession(7, 5, 2)
        Log.pl("\ngameEngine is ready having this field: ${game.gameField.prepareForPrintingIn2d()}")
        assertTrue(game.gameField.isReady())
        assertEquals(7, game.gameField.sideLength)
    }

    @Test
    fun gameIsNotStarted_tooSmallGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength = 2 -> game would have no sense in this case, the same as with field size of 1
        val game2x2 = GameSession(2, 2, 2)
        Log.pl("\ngameEngine is ready having this field: ${game2x2.gameField.prepareForPrintingIn2d()}")
        assertTrue(game2x2.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, game2x2.gameField.sideLength)
        // size = maxLength = 0
        val game0x0 = GameSession(0, 0, 2)
        Log.pl("\ngameEngine is ready having this field: ${game0x0.gameField.prepareForPrintingIn2d()}")
        assertTrue(game0x0.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, game0x0.gameField.sideLength)
        // size = maxLength = -1
        val gameM1M1 = GameSession(-1, -1, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameM1M1.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameM1M1.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameM1M1.gameField.sideLength)
        // size = maxLength = Int.MIN_VALUE
        val gameMxM = GameSession(Int.MIN_VALUE, Int.MIN_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMxM.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMxM.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMxM.gameField.sideLength)
    }

    @Test
    fun gameIsNotStarted_tooBigGameFieldIsCreated_maximal1000x1000GameFieldIsReady() {
        // size = maxLength = 1001 -> for now the limit is set to 1000 dots per side but in the future there could be more
        val game1k1 = GameSession(1001, 1001, 2)
        Log.pl("\ngameEngine is ready having this field: ${game1k1.gameField.prepareForPrintingIn2d()}")
        assertTrue(game1k1.gameField.isReady())
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, game1k1.gameField.sideLength)
        // size = maxLength = Int.MAX_VALUE
        val gameMM = GameSession(Int.MAX_VALUE, Int.MAX_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMM.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMM.gameField.isReady())
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, gameMM.gameField.sideLength)
    }

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_underMinIntGameFieldIsCreated_maximal1000x1000GameFieldIsReady() {
        // size = maxLength tries to be less than Int.MIN_VALUE -> there will be overflow of the Int
        val game = GameSession(Int.MIN_VALUE - 1, Int.MIN_VALUE - 1, 2)
        Log.pl("\ngameEngine is ready having this field: ${game.gameField.prepareForPrintingIn2d()}")
        assertTrue(game.gameField.isReady())
        assertEquals(MAX_GAME_FIELD_SIDE_SIZE, game.gameField.sideLength)
    }

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_overMaxIntGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength tries to be more than Int.MAX_VALUE -> there will be overflow of the Int
        val game = GameSession(Int.MAX_VALUE + 1, Int.MAX_VALUE + 1, 2)
        Log.pl("\ngameEngine is ready having this field: ${game.gameField.prepareForPrintingIn2d()}")
        assertTrue(game.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, game.gameField.sideLength)
    }

    // assertions of this test are made mostly to see interesting effects during different kinds of limits mixing
    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun gameIsNotStarted_MinAndMaxIntMixedGameFieldIsCreated_minimal3x3GameFieldIsReady() {
        // size = maxLength = Int.MIN_VALUE + Int.MIN_VALUE = 0 in fact - this is an interesting effect of the Int
        val gameMinPlusMin = GameSession(Int.MIN_VALUE + Int.MIN_VALUE, Int.MIN_VALUE + Int.MIN_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMinPlusMin.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMinPlusMin.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMinPlusMin.gameField.sideLength)
        println(Int.MIN_VALUE + Int.MIN_VALUE)

        // size = maxLength = Int.MIN_VALUE - Int.MIN_VALUE = 0 in fact - this is an interesting effect of the Int
        val gameMinMinusMin = GameSession(Int.MIN_VALUE - Int.MIN_VALUE, Int.MIN_VALUE - Int.MIN_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMinMinusMin.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMinMinusMin.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMinMinusMin.gameField.sideLength)
        println(Int.MIN_VALUE - Int.MIN_VALUE)

        // size = maxLength = Int.MAX_VALUE + Int.MAX_VALUE = -2 in fact - this is an interesting effect of the Int
        val gameMaxPlusMax = GameSession(Int.MAX_VALUE + Int.MAX_VALUE, Int.MAX_VALUE + Int.MAX_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMaxPlusMax.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMaxPlusMax.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMaxPlusMax.gameField.sideLength)
        println(Int.MAX_VALUE + Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE - Int.MAX_VALUE = 0 which is obvious
        val gameMaxMinusMax = GameSession(Int.MAX_VALUE - Int.MAX_VALUE, Int.MAX_VALUE - Int.MAX_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMaxMinusMax.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMaxMinusMax.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMaxMinusMax.gameField.sideLength)
        println(Int.MAX_VALUE - Int.MAX_VALUE)

        // size = maxLength = Int.MIN_VALUE - Int.MAX_VALUE = +1 in fact - this is an interesting effect of the Int
        val gameMinMinusMax = GameSession(Int.MIN_VALUE - Int.MAX_VALUE, Int.MIN_VALUE - Int.MAX_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMinMinusMax.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMinMinusMax.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMinMinusMax.gameField.sideLength)
        println(Int.MIN_VALUE - Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE - Int.MIN_VALUE = -1 which is obvious
        val gameMaxMinusMin = GameSession(Int.MAX_VALUE - Int.MIN_VALUE, Int.MAX_VALUE - Int.MIN_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMaxMinusMin.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMaxMinusMin.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMaxMinusMin.gameField.sideLength)
        println(Int.MAX_VALUE - Int.MIN_VALUE)

        // size = maxLength = Int.MIN_VALUE + Int.MAX_VALUE = -1 which is obvious
        val gameMinPlusMax = GameSession(Int.MIN_VALUE + Int.MAX_VALUE, Int.MIN_VALUE + Int.MAX_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMinPlusMax.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMinPlusMax.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMinPlusMax.gameField.sideLength)
        println(Int.MIN_VALUE + Int.MAX_VALUE)

        // size = maxLength = Int.MAX_VALUE + Int.MIN_VALUE = -1 which is obvious
        val gameMaxPlusMin = GameSession(Int.MAX_VALUE + Int.MIN_VALUE, Int.MAX_VALUE + Int.MIN_VALUE, 2)
        Log.pl("\ngameEngine is ready having this field: ${gameMaxPlusMin.gameField.prepareForPrintingIn2d()}")
        assertTrue(gameMaxPlusMin.gameField.isReady())
        assertEquals(MIN_GAME_FIELD_SIDE_SIZE, gameMaxPlusMin.gameField.sideLength)
        println(Int.MAX_VALUE + Int.MIN_VALUE)
    }

    // endregion GameEngine preparation

    // region line direction & next spot detection

    @Test
    fun having2dField_anyLineDirectionChosen_detectingOppositeDirectionIsCorrect() {
        assertEquals(LineDirection.XpY0, LineDirection.XmY0.opposite())
        assertEquals(LineDirection.XmY0, LineDirection.XpY0.opposite())
        assertEquals(LineDirection.X0Yp, LineDirection.X0Ym.opposite())
        assertEquals(LineDirection.X0Ym, LineDirection.X0Yp.opposite())
        assertEquals(LineDirection.XpYp, LineDirection.XmYm.opposite())
        assertEquals(LineDirection.XmYm, LineDirection.XpYp.opposite())
        assertEquals(LineDirection.XpYm, LineDirection.XmYp.opposite())
        assertEquals(LineDirection.XmYp, LineDirection.XpYm.opposite())
        assertEquals(LineDirection.None, LineDirection.None.opposite())
    }

    /**
     * here we check if an adjacent spot exists for every cell in 3x3 game for every possible direction
     */
    @Test
    fun having3x3Field_1MarkSet_adjacentMarkDetectionLogicIsCorrect() {
        checkTheNextSpotDetectionBlock(Coordinates(0, 0))
        checkTheNextSpotDetectionBlock(Coordinates(0, 1))
        checkTheNextSpotDetectionBlock(Coordinates(0, 2))
        checkTheNextSpotDetectionBlock(Coordinates(1, 0))
        checkTheNextSpotDetectionBlock(Coordinates(1, 1))
        checkTheNextSpotDetectionBlock(Coordinates(1, 2))
        checkTheNextSpotDetectionBlock(Coordinates(2, 0))
        checkTheNextSpotDetectionBlock(Coordinates(2, 1))
        checkTheNextSpotDetectionBlock(Coordinates(2, 2))
    }

    // endregion line direction & next spot detection

    // region line length measurements

    @Test
    fun having3x3Field_2AdjacentMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        val game = GameSession(3, 3, 2)
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        game.printCurrentFieldIn2d()
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = game.chosenAlgorithm.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = game.chosenAlgorithm.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(2, lengthFromFirstToSecond)
        assertEquals(2, lengthFromSecondToFirst)
    }

    @Test
    fun having3x3Field_2RemoteMarksAreSetByTheSamePlayer_detectedLineLengthIsCorrect() {
        val game = GameSession(3, 3, 2)
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(2, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = game.chosenAlgorithm.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = game.chosenAlgorithm.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(1, lengthFromFirstToSecond)
        assertEquals(1, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2RemoteMarksOfTheSamePlayerAreConnected_detectedLineLengthIsCorrect() {
        val game = GameSession(3, 3, 2)
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(2, 0)
        val connectingMark = Coordinates(1, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        game.makeMove(connectingMark, playerX)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromFirstToSecond = game.chosenAlgorithm.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromSecondToFirst = game.chosenAlgorithm.measureLineFrom(secondMark, LineDirection.XmY0, 1)
        assertEquals(3, lengthFromFirstToSecond)
        assertEquals(3, lengthFromSecondToFirst)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2AdjacentMarksOfTheSamePlayerAreAddedWithOneMoreMark_detectedLineLengthIsCorrect() {
        val game = GameSession(3, 3, 2)
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        val oneMoreMark = Coordinates(2, 0)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(firstMark, playerX)
        game.makeMove(secondMark, playerX)
        game.makeMove(oneMoreMark, playerX)
        Log.pl("measuring line from $firstMark in the forward direction:")
        val lengthFromEdgeToEdge = game.chosenAlgorithm.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        Log.pl("measuring line from $firstMark in the opposite direction:")
        val lengthFromEdgeToEdgeOpposite = game.chosenAlgorithm.measureLineFrom(oneMoreMark, LineDirection.XmY0, 1)
        assertEquals(3, lengthFromEdgeToEdge)
        assertEquals(3, lengthFromEdgeToEdgeOpposite)
        // 1 here is the given length of one dot on the field - if the mark exists - its min line length is 1, not less
    }

    @Test
    fun having3x3Field_2AdjacentMarksAreSetByDifferentPlayers_noLineIsCreatedForAnyPlayer() {
        val game = GameSession(3, 3, 2)
        val firstMark = Coordinates(0, 0)
        val secondMark = Coordinates(1, 0)
        val firstActivePlayer = PlayerProvider.activePlayer // should be player A
        game.makeMove(firstMark) // after this line active player is replaced with the next -> B
        Log.pl("measuring line from $firstMark for player: $firstActivePlayer in the forward direction:")
        val lengthForPlayerA = game.chosenAlgorithm.measureLineFrom(firstMark, LineDirection.XpY0, 1)
        val secondActivePlayer = PlayerProvider.activePlayer // should be player B
        game.makeMove(secondMark) // after this line active player is replaced with the next -> A
        Log.pl("measuring line from $secondMark for player: $secondActivePlayer in the forward direction:")
        val lengthForPlayerB = game.chosenAlgorithm.measureLineFrom(secondMark, LineDirection.XpY0, 1)
        assertEquals(1, lengthForPlayerA)
        assertEquals(1, lengthForPlayerB)
        Log.pl(game.gameField.prepareForPrintingIn2d())
    }

    // endregion line length measurements

    @Test
    fun havingOneMarkSetForOnePlayerOn3x3Field_TryToSetMarkForAnotherPlayerInTheSamePlace_previousMarkRemainsUnchanged() {
        val game = GameSession(3, 3, 2)
        val theSameSpot = Coordinates(1, 1)
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]
        game.makeMove(theSameSpot, playerX)
        game.makeMove(theSameSpot, playerO)
        Log.pl("\ngame field with only one player's mark: ${game.gameField.prepareForPrintingIn2d()}")
        assertEquals(playerX, game.gameField.getCurrentMarkAt(theSameSpot))
    }

    @Test
    fun having3x3Field_TryToSetMarkForThisPlayerOnWrongPosition_currentPlayerRemainsUnchanged() {
        val game = GameSession(3, 3, 2)
        val playerX = PlayerProvider.playersList[0]
        val playerO = PlayerProvider.playersList[1]
        game.makeMove(-1, -1) // attempt to set the mark on a wrong place
        assertEquals(playerX, PlayerProvider.activePlayer) // this player remains chosen for the next move
        game.makeMove(1, 1) // another attempt for the same player - this time successful
        assertEquals(playerO, PlayerProvider.activePlayer) // this time the next player is prepared for a move
        Log.pl("\ngame field with only one player's mark: ${game.gameField.prepareForPrintingIn2d()}")
        assertEquals(playerX, game.gameField.getCurrentMarkAt(Coordinates(1, 1)))
    }

    @Test
    fun having3x3Field_onlyOnePlayerMarksAreSet_victoryConditionIsCorrect() {
        val game = GameSession(3, 3, 2)
        val playerX = PlayerProvider.playersList[0]
        game.makeMove(Coordinates(0, 0), playerX)
        game.makeMove(Coordinates(1, 0), playerX)
        game.makeMove(Coordinates(2, 0), playerX)
        // gameField & winning message for player A is printed in the console
        assertEquals(playerX, game.getWinner())
        assertEquals(3, game.getWinner().getMaxLineLength())
    }

    @Test
    fun having2LinesOfOnePlayerOn5x5Field_thisPlayerMarkIsSetInBetween_victoryConditionIsCorrect() {
        val game = GameSession(5, 5, 2)
        val playerX = PlayerProvider.playersList[0]
        Log.pl("\ntest5x5Field: gameEngine ready with given field: ${game.gameField.prepareForPrintingIn2d()}")
        game.makeMove(Coordinates(0, 0), playerX)
        game.makeMove(Coordinates(1, 0), playerX)
        // GameEngine.makeNewMove(Coordinates(2, 0), WhichPlayer.A) // intentionally commented - it will be used a bit later
        game.makeMove(Coordinates(3, 0), playerX)
        game.makeMove(Coordinates(4, 0), playerX)
        game.makeMove(Coordinates(2, 0), playerX) // intentionally placed here to connect 2 segments
        assertEquals(playerX, game.getWinner())
        assertEquals(5, game.getWinner().getMaxLineLength())
    }
}
