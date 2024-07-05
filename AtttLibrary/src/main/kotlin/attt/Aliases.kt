package attt

/**
 * precisely describes every dot/mark/position on any game field in 2d.
 * Pair<Int, Int> represents coordinates - X & Y exactly in this order (the same as for makeMove() method).
 */
typealias XY = Pair<Int, Int>

/**
 * precisely describes every dot/mark/position on any game field in 3d.
 * Triple<Int, Int, Int> represents coordinates - X, Y, Z in this order (the same as for makeMove() method).
 */
typealias XYZ = Triple<Int, Int, Int>

/**
 * describes any line as a collection of its dots. List is chosen as the simplest form convenient for looping through
 */
typealias OneLine = List<XYZ>