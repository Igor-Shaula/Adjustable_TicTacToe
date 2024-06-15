import publicApi.AtttGame

fun main() {
    play3x3gameWithNoWinner() // 2D
    println()
    play3x3gameWhichIsWon() // 2D
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

    val game2 = AtttGame.create()
    game2.mm(1, 1) // X
    game2.mm(2, 1) // O
    game2.mm(2, 0) // X
    game2.mm(0, 2) // O
    game2.mm(1, 2) // X
    game2.mm(2, 2) // O
    game2.printCurrentFieldIn2d()
    println("game2.isGameFinished() = " + game2.isGameFinished())
    game2.mm(1, 0) // X - this one was problematic
    game2.printCurrentFieldIn2d()
    println("game2.isGameFinished() = " + game2.isGameFinished())
    println("game2.isGameWon() = " + game2.isGameWon())
    println("game2.getWinner() = " + game2.getWinner())
}