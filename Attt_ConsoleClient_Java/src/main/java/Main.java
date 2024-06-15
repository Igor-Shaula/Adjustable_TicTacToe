import publicApi.AtttGame;

public class Main {

    public static void main(String[] args) {
        // todo: fix the following error after this Main is launched:
        // Exception in thread "main" java.lang.NoClassDefFoundError: kotlin/jvm/internal/Intrinsics
        final AtttGame game = AtttGame.Companion.create(3, 3, false, 2);
        game.mm(0, 0, 0);
        game.mm(0, 1, 0);
        game.mm(1, 0, 0);
        game.mm(0, 1, 0);
        game.mm(2, 0, 0);
        game.mm(0, 2, 0);
        game.printCurrentFieldIn2d();
    }
}