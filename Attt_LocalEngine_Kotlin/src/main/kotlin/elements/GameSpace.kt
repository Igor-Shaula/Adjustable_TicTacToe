package elements

internal interface LineDirection {
    fun opposite(): LineDirection
}

sealed interface GameSpace

/**
 * a spot with any coordinate which does not exist on the reasonably sized field - is considered to be its border
 */
internal data object Border : GameSpace

data class OneAxis(val l: Int)