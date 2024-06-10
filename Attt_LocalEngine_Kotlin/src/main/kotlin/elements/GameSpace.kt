package elements

internal sealed interface GameSpace

/**
 * a spot with any coordinate which does not exist on the reasonably sized field - is considered to be its border
 */
internal data object Border : GameSpace

open class Coordinates(open val x: Int, open val y: Int) : GameSpace

data class OneAxis(val l: Int)