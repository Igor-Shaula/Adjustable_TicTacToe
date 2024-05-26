// To execute Kotlin code, please define a top level function named main

fun main() {
    testTheNextSpotCreationBlock()
}

fun testTheNextSpotCreationBlock() {
    val startSpot = Coordinates(1, 1)

    testTheNextSpotCreationFor(startSpot, LineDirection.XMinus)
    testTheNextSpotCreationFor(startSpot, LineDirection.XPlus)
    testTheNextSpotCreationFor(startSpot, LineDirection.YMinus)
    testTheNextSpotCreationFor(startSpot, LineDirection.YPlus)
    testTheNextSpotCreationFor(startSpot, LineDirection.XmYm)
    testTheNextSpotCreationFor(startSpot, LineDirection.XpYp)
    testTheNextSpotCreationFor(startSpot, LineDirection.XmYp)
    testTheNextSpotCreationFor(startSpot, LineDirection.XpYm)
    testTheNextSpotCreationFor(startSpot, LineDirection.None)
}

fun testTheNextSpotCreationFor(startSpot: Coordinates, direction: LineDirection) {
    val nextSpot = getTheNextSpotFor(startSpot, direction)
    println("nextSpot for $direction is $nextSpot")
}
