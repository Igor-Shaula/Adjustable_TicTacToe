import publicApi.AtttGame;

@SuppressWarnings({"ExplicitToImplicitClassMigration", "StringTemplateMigration"})
public class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        play3x3gameWithNoWinner(); // 2D
        System.out.println();
        play3x3gameWhichIsWon(); // 2D
        System.out.println();
        play3x3x3gameWhichIsWon(); // 3D
        System.out.println();
        play4x4gameWhichIsWon(); // bigger field & 2D
        System.out.println();
        play4x4x4gameWhichIsWon(); // bigger field & 3D
        System.out.println();
        play4x4gameWith3PlayersWhichIsWon(); // bigger field & 2D & 3 players
        System.out.println();
        play4x4x4gameWith3PlayersWhichIsWon(); // bigger field & 3D & 3 players
    }

    static void play3x3gameWithNoWinner() {
        System.out.println("=========================");
        System.out.println("game #1 is about to start");
        var game = AtttGame.Companion.create(3, 3, false, 2);
        game.mm(1, 1, 0);
        game.mm(0, 0, 0);
        game.mm(1, 2, 0);
        game.mm(1, 0, 0);
        game.mm(2, 0, 0);
        game.mm(0, 2, 0);
        game.mm(0, 1, 0);
        game.mm(2, 1, 0);
        game.mm(2, 2, 0);
        game.printCurrentFieldIn2d();
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
        System.out.println("game.getLeader() = " + game.getLeader());
    }

    static void play3x3gameWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #2 is about to start");
        var game = AtttGame.Companion.create(3, 3, false, 2);
        game.mm(1, 1, 0); // X
        game.mm(2, 1, 0); // O
        game.mm(2, 0, 0); // X
        game.mm(0, 2, 0); // O
        game.mm(1, 2, 0); // X
        game.mm(2, 2, 0); // O
        game.printCurrentFieldIn2d();
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        game.mm(1, 0, 0); // X - this one was problematic
        game.printCurrentFieldIn2d();
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play3x3x3gameWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #3 is about to start");
        var game = AtttGame.Companion.create(3, 3, true, 2);
        game.mm(1, 1, 0); // X
        game.mm(2, 1, 0); // O
        game.mm(2, 0, 0); // X
        game.mm(0, 2, 0); // O
        game.mm(1, 1, 1); // X
        game.mm(2, 2, 0); // O
        game.mm(1, 1, 2); // X - this one was problematic but in version 0.3.0 this bug was fixed
    /*
        . . x
        . X o <- x x x - on Z axis
        o . o
    */
        game.printCurrentFieldIn2d();
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play4x4gameWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #4 is about to start");
        var game = AtttGame.Companion.create(4, 3, false, 2);
        game.mm(0, 0, 0); // X
        game.mm(1, 0, 0); // O
        game.mm(0, 1, 0); // X -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 1, 0); // O -> now B also has a line of 2 marks
        game.mm(2, 0, 0); // X -> now A still has a line of 2 marks
        game.mm(1, 2, 0); // O -> now B has a line of 3 marks and becomes a new leader
    /*
        X O X .
        X O . .
        . O . .
        . . . .
     */
        game.printCurrentFieldIn2d();
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play4x4x4gameWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #5 is about to start");
        var game = AtttGame.Companion.create(4, 3, true, 2);
        game.mm(0, 0, 0); // X
        game.mm(1, 0, 0); // O
        game.mm(0, 1, 0); // X -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 0, 1); // O -> now B also has a line of 2 marks
        game.mm(2, 0, 0); // X -> now A still has a line of 2 marks
        game.mm(1, 0, 2); // O -> now B has a line of 3 marks and becomes a new leader
    /*
        a B a . <- b b b . on Z axis
        a . . .
        . . . .
        . . . .
     */
        game.printCurrentFieldIn2d();
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play4x4gameWith3PlayersWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #6 is about to start");
        var game = AtttGame.Companion.create(4, 3, false, 3);
        game.mm(0, 0, 0); // A
        game.mm(1, 0, 0); // B
        game.mm(2, 0, 0); // C
        game.mm(0, 1, 0); // A -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 1, 0); // B -> now B also has a line of 2 marks
        game.mm(2, 1, 0); // C -> now C also has a line of 2 marks
        game.mm(0, 2, 0); // A -> now A has a line of 3 marks and becomes a new leader
    /*
        A B C .
        A B C .
        A . . .
        . . . .
     */
        game.printCurrentFieldIn2d();
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play4x4x4gameWith3PlayersWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #7 is about to start");
        var game = AtttGame.Companion.create(4, 3, true, 3);
        game.mm(0, 0, 0); // A
        game.mm(1, 0, 0); // B
        game.mm(2, 0, 0); // C
        game.mm(0, 0, 1); // A -> now A has a line of 2 marks and becomes a leader
        game.mm(1, 1, 0); // B -> now B also has a line of 2 marks
        game.mm(2, 1, 0); // C -> now C also has a line of 2 marks
        game.mm(0, 0, 2); // A -> now A has a line of 3 marks and becomes a new leader
    /*
        A B C . <- A A A . on Z-axis
        . B C .
        . . . .
        . . . .
     */
        game.printCurrentFieldIn2d();
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }
}
