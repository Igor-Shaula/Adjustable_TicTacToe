public class Main {
    public static void main(String[] args) {
//        AtttEngine engine = AtttEngine.INSTANCE;
//        AtttField field = new AtttField(3, 2, 2);
//        AtttRules rules = new AtttRules(3);
//        engine.prepare(field, rules);
//        engine.makeNewMove(new AtttPlace(0, 0), AtttPlayer.A);
//        engine.makeNewMove(new AtttPlace(1, 0), AtttPlayer.A);
//        engine.makeNewMove(new AtttPlace(2, 0), AtttPlayer.A);
//        System.out.println("engine.gameField = " + engine.getGameField$Adjustable_TicTacToe_Engine());
//        System.out.println("engine.isRunning() = " + engine.isRunning());

        final AtttGame game = AtttGame.Companion.create();
        final AtttField field = new AtttField(3, 2, 2); // default parameters don't work!!!
        final AtttRules rules = new AtttRules(3);
        game.prepare(field, rules);
        game.mm(0,0);
        game.mm(0,1);
        game.mm(1,0);
        game.mm(0,1);
        game.mm(2,0);
        game.mm(0,2);
        game.printCurrentFieldIn2d();

        // todo: fix the following error after this Main is launched:
        // Caused by: java.lang.ClassNotFoundException: kotlin.enums.EnumEntriesKt
    }
}