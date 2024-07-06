package geometry.abstractions

/**
 * any 2 and more dots in any space can be represented as a line.
 * this interface & its children describe this alignment in all possible ways of coordinates/space presentation.
 */
internal interface LineDirection {

    fun opposite(): LineDirection

    fun isNone(): Boolean
}