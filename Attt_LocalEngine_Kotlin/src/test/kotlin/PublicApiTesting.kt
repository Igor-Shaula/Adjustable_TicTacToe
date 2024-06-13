import constants.MAX_NUMBER_OF_PLAYERS
import players.PlayerProvider
import publicApi.AtttGame
import utilities.Log
import kotlin.random.Random
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
    fun havingDefault2DField_zAxisIsUpdatedForEveryMove_winnerIsDetectedCorrectlyAsIn2dGame() {
        val game = AtttGame.create()
        /*
            . X x
            . x o
            o x o
         */
        game.mm(1, 1, 1) // X
        game.mm(2, 1, 2) // O
        game.mm(2, 0, 3) // X
        game.mm(0, 2, 4) // O
        game.mm(1, 2, 5) // X
        game.mm(2, 2, 6) // O
        game.mm(1, 0, 7) // X - this one was problematic but in version 0.3.0 this bug was fixed
        val playerX = PlayerProvider.playersList[0]
        assertTrue(game.isGameWon(), "Game should have been won")
        assertEquals(playerX, game.getWinner())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_onePlayerGetsMultiplePossibleLines_winnerIsDetectedOnceTheConditionsAreMet() {
        val game = AtttGame.create()
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
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3x3Field_onePlayerGetsMultiplePossibleLines_winnerIsDetectedOnceTheConditionsAreMet() {
        val game = AtttGame.create(is3D = true)
        /*
            . . x
            . X o <- x x x - on Z axis
            o . o
         */
        game.mm(1, 1, 0) // X
        game.mm(2, 1, 0) // O
        game.mm(2, 0, 0) // X
        game.mm(0, 2, 0) // O
        game.mm(1, 1, 1) // X
        game.mm(2, 2, 0) // O
        game.mm(1, 1, 2) // X - this one was problematic but in version 0.3.0 this bug was fixed
        val playerX = PlayerProvider.playersList[0]
        assertTrue(game.isGameWon(), "Game should have been won")
        assertEquals(playerX, game.getWinner())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3Field_onePlayerMakesTheFirstLine_leadingPlayerIsDetectedCorrectly() {
        val game = AtttGame.create()
        game.mm(0, 0) // X
        game.mm(1, 0) // O
        game.mm(0, 1) // X -> now A has a line of 2 marks
        val playerX = PlayerProvider.playersList[0]
        assertEquals(playerX, game.getLeader())
        assertEquals(2, game.getLeader().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having3x3x3Field_onePlayerMakesTheFirstLine_leadingPlayerIsDetectedCorrectly() {
        val game = AtttGame.create(is3D = true)
        game.mm(0, 0, 0) // X
        game.mm(1, 0, 0) // O
        game.mm(0, 0, 1) // X -> now A has a line of 2 marks
        val playerX = PlayerProvider.playersList[0]
        assertEquals(playerX, game.getLeader())
        assertEquals(2, game.getLeader().getMaxLineLength())
        game.printCurrentFieldIn2d()
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
    fun having4x4x4Field_onePlayerMakesLongerLineThanAnother_thisPlayerBecomesTheLeadingOne() {
        val game = AtttGame.create(4, 4, true)
        /*
            a B a . <- b b b . on Z axis
            a . . .
            . . . .
            . . . .
         */
        game.mm(0, 0, 0) // X
        game.mm(1, 0, 0) // O
        game.mm(0, 1, 0) // X -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 0, 1) // O -> now B also has a line of 2 marks
        game.mm(2, 0, 0) // X -> now A still has a line of 2 marks
        game.mm(1, 0, 2) // O -> now B has a line of 3 marks and becomes a new leader
        val playerO = PlayerProvider.playersList[1]
        assertEquals(playerO.getId(), game.getLeader().getId())
        assertEquals(3, game.getLeader().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having4x4Field_3PlayersMakeCorrectMoves_activePlayerDefinitionForEachMoveIsCorrect() {
        val game = AtttGame.create(4, 4, false, 3)
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
        assertEquals(PlayerProvider.playersList[0], game.getLeader())
        assertEquals(3, game.getLeader().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }

    @Test
    fun having4x4x4Field_3PlayersMakeCorrectMoves_activePlayerDefinitionForEachMoveIsCorrect() {
        val game = AtttGame.create(4, 4, true, 3)
        /*
            A B C . <- A A A . on Z-axis
            . B C .
            . . . .
            . . . .
         */
        game.mm(0, 0, 0) // A
        game.mm(1, 0, 0) // B
        game.mm(2, 0, 0) // C
        game.mm(0, 0, 1) // A -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 1, 0) // B -> now B also has a line of 2 marks
        game.mm(2, 1, 0) // C -> now C also has a line of 2 marks
        game.mm(0, 0, 2) // A -> now A has a line of 3 marks and becomes a new leader
        assertEquals(PlayerProvider.playersList[0], game.getLeader())
        assertEquals(3, game.getLeader().getMaxLineLength())
        game.printCurrentFieldIn2d()
    }

    // kind of a load testing on a field that is big and yet still able to fit into console output
    @Test
    fun having100x100Field_2PlayersMakeRandomMoves_activePlayerDefinitionForEachMoveIsCorrect() {
        val game = AtttGame.create(100, 10, desiredPlayerNumber = MAX_NUMBER_OF_PLAYERS)
        Log.switch(false) // speeding up and preventing from huge amount of messages in the console
        var iterationsCount = 0
        (0..999_999).forEach { _ -> // including ,so it's precisely a million in fact
            if (!game.isGameWon()) {
                game.mm(Random.nextInt(100), Random.nextInt(100))
                iterationsCount++
            }
        }
        Log.switch(true) // restoring for possible other tests
        Log.pl("iterationsCount: $iterationsCount")
        Log.pl(
            "player ${game.getLeader().getName()} is leading with maxLineLength: ${game.getLeader().getMaxLineLength()}"
        )
        game.printCurrentFieldIn2d()
    }
}