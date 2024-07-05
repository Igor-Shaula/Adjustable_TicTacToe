package geometry

import geometry.abstractions.LineDirection

/**
 * this is the simplest case - only for one axis - imagine a line where you can move only in two directions
 */
internal enum class LineDirectionFor1Axis(internal val deltaOne: Int) : LineDirection {

    Minus(-1), Plus(+1), None(0);

    override fun opposite() = when (this) {
        Minus -> Plus
        Plus -> Minus
        None -> None
    }
}
