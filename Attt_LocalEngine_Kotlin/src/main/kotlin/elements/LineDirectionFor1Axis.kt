package elements

// only for one axis - imagine a line where you can move only in two directions
internal enum class LineDirectionFor1Axis(internal val deltaOne: Int) {

    Minus(-1), Plus(+1), None(0);

    internal fun opposite() = when (this) {
        Minus -> Plus
        Plus -> Minus
        None -> None
    }
}
