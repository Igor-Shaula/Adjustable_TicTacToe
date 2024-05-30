fun main() {
    val engine = AtttEngine
    val field = AtttField(3, 2, 2)
    val rules = AtttRules(3)
    engine.prepare(field, rules)

    engine.makeNewMove(AtttPlace(1, 1))
    engine.makeNewMove(AtttPlace(0, 0))
    engine.makeNewMove(AtttPlace(1, 2))
    engine.makeNewMove(AtttPlace(1, 0))
    engine.makeNewMove(AtttPlace(2, 0))
    engine.makeNewMove(AtttPlace(0, 2))
    engine.makeNewMove(AtttPlace(0, 1))
    engine.makeNewMove(AtttPlace(2, 1))

    engine.printCurrentFieldIn2d()

    println("engine.isRunning() = " + engine.isRunning())
}