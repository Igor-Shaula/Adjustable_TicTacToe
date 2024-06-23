package geometry

import geometry.abstractions.Coordinates

/**
 * a line is more than one adjacent dot/mark in one constant direction.
 * for now a line can only grow during detection work, it cannot become smaller.
 * any line consists of minimum two dots/marks.
 * two different lines with different order of dots/marks inside are equal.
 * processing/detecting of the line direction is not a job of the line.
 * all existing lines are meant to be proven/correct/checked to be straight & aligned in a direction.
 */
internal class Line(startingMark: Coordinates, adjacentMark: Coordinates) {

    /**
     * why a Set? -> because each coordinate is unique, and also we need unordered set
     * - to avoid keeping track of a starting point and its repositioning in case of adding a new mark before it.
     * also I decided to avoid using LineDirection concept here - for simplicity.
     * all control of line correctness is done at the level of line detection inside existing logic chains.
     */
    val marks: MutableSet<Coordinates> = mutableSetOf()

    init {
        marks.add(startingMark)
        marks.add(adjacentMark)
    }

    internal fun add(newMark: Coordinates) {
        marks.add(newMark)
    }

    /*
        // it is decided to treat two lines with similar marks but opposite direction as one (the same) line
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return if (other == null || other !is Line) false
            else this.startingMark == other.adjacentMark && this.adjacentMark == other.startingMark
        }

        // if we override equals -> we have to override hashCode as well to prevent from breaking their contract
        override fun hashCode(): Int {
            return (startingMark.z + adjacentMark.z) * 1_000_000 + // 1 & 1 -> 2_000_000
                    (startingMark.y + adjacentMark.y) * 1_000 + // 1 & 1 -> 2_002_000
                    (startingMark.x + adjacentMark.x) // 1 & 1 -> 2_002_002
            // this is only beginning implementation - later check upper limits & maybe include lineDirection here
        }
    */
}

internal fun Set<Line?>.getMaxLength(): Int =
    this.maxByOrNull { line: Line? -> line?.marks?.size ?: 0 }?.marks?.size ?: 0
