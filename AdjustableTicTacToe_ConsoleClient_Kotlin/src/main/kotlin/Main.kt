fun main() {
    val engine = AtttEngine
    val field = AtttField(3, 2, 2)
    val rules = AtttRules(3)
    engine.prepare(field, rules)

    engine.mm(1, 1)
    engine.mm(0, 0)
    engine.mm(1, 2)
    engine.mm(1, 0)
    engine.mm(2, 0)
    engine.mm(0, 2)
    engine.mm(0, 1)
    engine.mm(2, 1)
    engine.mm(2, 2)

    engine.printCurrentFieldIn2d()

    println("engine.isActive() = " + engine.isActive())

    engine.finish()
    println("engine.isActive() = " + engine.isActive())
}