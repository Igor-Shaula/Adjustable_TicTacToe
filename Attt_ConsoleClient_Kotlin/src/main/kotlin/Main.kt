import publicApi.AtttGame

fun main() {
    play3x3gameWithNoWinner() // 2D
    println()
    play3x3gameWhichIsWon() // 2D
    println()
    play3x3x3gameWhichIsWon() // 3D
    println()
    play4x4gameWhichIsWon() // 2D
    println()
    play4x4x4gameWhichIsWon() // 3D
}

fun play3x3gameWithNoWinner() {
    println("=========================")
    println("game #1 is about to start")
    val game = AtttGame.create()
    game.mm(1, 1)
    game.mm(0, 0)
    game.mm(1, 2)
    game.mm(1, 0)
    game.mm(2, 0)
    game.mm(0, 2)
    game.mm(0, 1)
    game.mm(2, 1)
    game.mm(2, 2)
    game.printCurrentFieldIn2d()
    println("game.isGameFinished() = " + game.isGameFinished())
    println("game.isGameWon() = " + game.isGameWon())
    println("game.getWinner() = " + game.getWinner())
    println("game.getLeader() = " + game.getLeader())
}

fun play3x3gameWhichIsWon() {
    println("=========================")
    println("game #2 is about to start")
    val game = AtttGame.create()
    game.mm(1, 1) // X
    game.mm(2, 1) // O
    game.mm(2, 0) // X
    game.mm(0, 2) // O
    game.mm(1, 2) // X
    game.mm(2, 2) // O
    game.printCurrentFieldIn2d()
    println("game.isGameFinished() = " + game.isGameFinished())
    game.mm(1, 0) // X - this one was problematic
    game.printCurrentFieldIn2d()
    println("game.isGameFinished() = " + game.isGameFinished())
    println("game.isGameWon() = " + game.isGameWon())
    println("game.getWinner() = " + game.getWinner())
}

fun play3x3x3gameWhichIsWon() {
    println("=========================")
    println("game #3 is about to start")
    val game = AtttGame.create(is3D = true)
    game.mm(1, 1, 0) // X
    game.mm(2, 1, 0) // O
    game.mm(2, 0, 0) // X
    game.mm(0, 2, 0) // O
    game.mm(1, 1, 1) // X
    game.mm(2, 2, 0) // O
    game.mm(1, 1, 2) // X - this one was problematic but in version 0.3.0 this bug was fixed
    /*
        . . x
        . X o <- x x x - on Z axis
        o . o
    */
    game.printCurrentFieldIn2d()
    println("game.isGameFinished() = " + game.isGameFinished())
    println("game.isGameWon() = " + game.isGameWon())
    println("game.getWinner() = " + game.getWinner())
}

fun play4x4gameWhichIsWon() {
    println("=========================")
    println("game #4 is about to start")
    val game = AtttGame.create(4, 3)
    game.mm(0, 0) // X
    game.mm(1, 0) // O
    game.mm(0, 1) // X -> now A has a line of 2 marks and becomes a leader
    game.mm(1, 1) // O -> now B also has a line of 2 marks
    game.mm(2, 0) // X -> now A still has a line of 2 marks
    game.mm(1, 2) // O -> now B has a line of 3 marks and becomes a new leader
    /*
        X O X .
        X O . .
        . O . .
        . . . .
     */
    game.printCurrentFieldIn2d()
    println("game.isGameFinished() = " + game.isGameFinished())
    println("game.isGameWon() = " + game.isGameWon())
    println("game.getWinner() = " + game.getWinner())
}

fun play4x4x4gameWhichIsWon() {
    println("=========================")
    println("game #5 is about to start")
    val game = AtttGame.create(4, 3, true)
    game.mm(0, 0, 0) // X
    game.mm(1, 0, 0) // O
    game.mm(0, 1, 0) // X -> now A has a line of 2 marks and becomes a leader
    game.mm(1, 0, 1) // O -> now B also has a line of 2 marks
    game.mm(2, 0, 0) // X -> now A still has a line of 2 marks
    game.mm(1, 0, 2) // O -> now B has a line of 3 marks and becomes a new leader
    /*
        a B a . <- b b b . on Z axis
        a . . .
        . . . .
        . . . .
     */
    game.printCurrentFieldIn2d()
    println("game.isGameFinished() = " + game.isGameFinished())
    println("game.isGameWon() = " + game.isGameWon())
    println("game.getWinner() = " + game.getWinner())
}