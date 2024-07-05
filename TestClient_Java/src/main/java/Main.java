import attt.Game;

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
        var game = Game.Companion.create();
        game.m(1, 1);
        game.m(0, 0);
        game.m(1, 2);
        game.m(1, 0);
        game.m(2, 0);
        game.m(0, 2);
        game.m(0, 1);
        game.m(2, 1);
        game.m(2, 2);
        System.out.println(game.getCurrentFieldAsAString());
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
        System.out.println("game.getLeader() = " + game.getLeader());
    }

    static void play3x3gameWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #2 is about to start");
        var game = Game.Companion.create();
        game.m(1, 1); // X
        game.m(2, 1); // O
        game.m(2, 0); // X
        game.m(0, 2); // O
        game.m(1, 2); // X
        game.m(2, 2); // O
        System.out.println(game.getCurrentFieldAsAString());
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        game.m(1, 0); // X - this one was problematic
        System.out.println(game.getCurrentFieldAsAString());
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play3x3x3gameWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #3 is about to start");
        var game = Game.Companion.create(true);
        game.m(1, 1, 0); // X
        game.m(2, 1, 0); // O
        game.m(2, 0, 0); // X
        game.m(0, 2, 0); // O
        game.m(1, 1, 1); // X
        game.m(2, 2, 0); // O
        game.m(1, 1, 2); // X - this one was problematic but in version 0.3.0 this bug was fixed
    /*
        . . x
        . X o <- x x x - on Z axis
        o . o
     */
        System.out.println(game.getCurrentFieldAsAString());
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play4x4gameWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #4 is about to start");
        var game = Game.Companion.create(4);
        game.m(0, 0); // X
        game.m(1, 0); // O
        game.m(0, 1); // X -> now A has a line of 2 marks and becomes a leader
        game.m(1, 1); // O -> now B also has a line of 2 marks
        game.m(2, 0); // X -> now A still has a line of 2 marks
        game.m(1, 2); // O -> now B has a line of 3 marks and becomes a new leader
    /*
        X O X .
        X O . .
        . O . .
        . . . .
     */
        System.out.println(game.getCurrentFieldAsAString());
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play4x4x4gameWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #5 is about to start");
        var game = Game.Companion.create(true, 4);
        game.m(0, 0, 0); // X
        game.m(1, 0, 0); // O
        game.m(0, 1, 0); // X -> now A has a line of 2 marks and becomes a leader
        game.m(1, 0, 1); // O -> now B also has a line of 2 marks
        game.m(2, 0, 0); // X -> now A still has a line of 2 marks
        game.m(1, 0, 2); // O -> now B has a line of 3 marks and becomes a new leader
    /*
        a B a . <- b b b . on Z axis
        a . . .
        . . . .
        . . . .
     */
        System.out.println(game.getCurrentFieldAsAString());
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play4x4gameWith3PlayersWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #6 is about to start");
        var game = Game.Companion.create(false, 4, 3, 3);
        game.m(0, 0); // A
        game.m(1, 0); // B
        game.m(2, 0); // C
        game.m(0, 1); // A -> now A has a line of 2 marks and becomes a leader
        game.m(1, 1); // B -> now B also has a line of 2 marks
        game.m(2, 1); // C -> now C also has a line of 2 marks
        game.m(0, 2); // A -> now A has a line of 3 marks and becomes a new leader
    /*
        A B C .
        A B C .
        A . . .
        . . . .
     */
        System.out.println(game.getCurrentFieldAsAString());
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }

    static void play4x4x4gameWith3PlayersWhichIsWon() {
        System.out.println("=========================");
        System.out.println("game #7 is about to start");
        var game = Game.Companion.create(true, 4, 3, 3);
        game.m(0, 0, 0); // A
        game.m(1, 0, 0); // B
        game.m(2, 0, 0); // C
        game.m(0, 0, 1); // A -> now A has a line of 2 marks and becomes a leader
        game.m(1, 1, 0); // B -> now B also has a line of 2 marks
        game.m(2, 1, 0); // C -> now C also has a line of 2 marks
        game.m(0, 0, 2); // A -> now A has a line of 3 marks and becomes a new leader
    /*
        A B C . <- A A A . on Z-axis
        . B C .
        . . . .
        . . . .
     */
        System.out.println(game.getCurrentFieldAsAString());
        System.out.println("game.isGameFinished() = " + game.isGameFinished());
        System.out.println("game.isGameWon() = " + game.isGameWon());
        System.out.println("game.getWinner() = " + game.getWinner());
    }
}
