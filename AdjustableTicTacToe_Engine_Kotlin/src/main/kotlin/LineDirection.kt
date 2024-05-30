/**
 * describes all possible directions for the simplest line of 2 dots on a 2d field
 * m -> minus, p -> plus, 0 -> no change along this axis
 */
internal enum class LineDirection(val dx: Int, val dy: Int) {
    XmY0(-1, 0),
    XpY0(+1, 0),
    X0Ym(0, -1),
    X0Yp(0, +1),
    XmYm(-1, -1),
    XpYp(+1, +1),
    XmYp(-1, +1),
    XpYm(+1, -1),
    None(0, 0);

    // setting opposite direction is very easy: plus -> minus, minus -> plus, 0 -> 0
    fun opposite(): LineDirection = when (this) {
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
