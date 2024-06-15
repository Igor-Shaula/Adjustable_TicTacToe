import publicApi.AtttGame

fun main() {
    println("=======================")
    println("game1 is about to start")

    val game1 = AtttGame.create()
    game1.mm(1, 1)
    game1.mm(0, 0)
    game1.mm(1, 2)
    game1.mm(1, 0)
    game1.mm(2, 0)
    game1.mm(0, 2)
    game1.mm(0, 1)
    game1.mm(2, 1)
    game1.mm(2, 2)
    game1.printCurrentFieldIn2d()
    println("game1.isGameFinished() = " + game1.isGameFinished())
    println("game1.isGameWon() = " + game1.isGameWon())
    println("game1.getWinner() = " + game1.getWinner())
    println("game1.getLeader() = " + game1.getLeader())

    println("\n=======================")
    println("\ngame2 is about to start")

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