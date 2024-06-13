package geometry.conceptXY

import geometry.abstractions.LineDirection

/**
 * describes all possible directions for the simplest line of 2 dots on a 2d field
 * m -> minus, p -> plus, 0 -> no change along this axis
 */
internal enum class LineDirectionForXY(val dx: Int, val dy: Int) : LineDirection {
    XmY0(-1, 0),    // horizontal to the left
    XpY0(+1, 0),    // horizontal to the right
    X0Ym(0, -1),    // vertical up
    X0Yp(0, +1),    // vertical down
    XmYm(-1, -1),   // diagonal to left & up
    XpYp(+1, +1),   // diagonal to right & down
    XmYp(-1, +1),   // diagonal to left & down
    XpYm(+1, -1),   // diagonal to right & up
    None(0, 0);     // no direction

    // setting opposite direction is very easy: plus -> minus, minus -> plus, 0 -> 0
    override fun opposite(): LineDirectionForXY = when (this) {
        XmY0 -> XpY0
        XpY0 -> XmY0
        X0Ym -> X0Yp
        X0Yp -> X0Ym
        XmYm -> XpYp
        XpYp -> XmYm
        XmYp -> XpYm
        XpYm -> XmYp
        else -> None
    }
}
