/**
 * describes all possible directions for the simplest line of 2 dots on a 2d field
 * m -> minus, p -> plus, 0 -> no change along this axis
 */
enum class LineDirection(val dx: Int, val dy: Int) {
    XmY0(-1, 0),
    XpY0(+1, 0),
    X0Ym(0, -1),
    X0Yp(0, +1),
    XmYm(-1, -1),
    XpYp(+1, +1),
    XmYp(-1, +1),
    XpYm(+1, -1),
    None(0, 0)
}