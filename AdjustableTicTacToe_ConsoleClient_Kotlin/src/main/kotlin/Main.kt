fun main() {
    val engine = AtttEngine
    val field = AtttField(3, 2, 2)
    val rules = AtttRules(3)
    engine.prepare(field, rules)
    engine.makeNewMove(AtttPlace(0, 0), AtttPlayer.A)
    engine.makeNewMove(AtttPlace(1, 0), AtttPlayer.A)
    engine.makeNewMove(AtttPlace(2, 0), AtttPlayer.A)
    println("engine.gameField = " + engine.getCurrentField())
    println("engine.isRunning() = " + engine.isRunning())
}