package geometry

import constants.MAX_WINNING_LINE_LENGTH
import geometry.abstractions.Coordinates

/**
 * a line is more than one adjacent dot/mark in one constant direction.
 * for now a line can only grow during detection work, it cannot become smaller.
 * any line consists of minimum two dots/marks.
 * two different lines with different order of dots/marks inside are equal.
 * processing/detecting of the line direction is not a job of the line.
 * all existing lines are meant to be proven/correct/checked to be straight & aligned in a direction.
 */
internal data class Line(val startingMark: Coordinates, val adjacentMark: Coordinates) {

    // decided to initially allocate maximum of memory to avoid possible re-allocations
    val marks: ArrayDeque<Coordinates> = ArrayDeque(initialCapacity = MAX_WINNING_LINE_LENGTH)

    init {
        marks.addFirst(startingMark)
        marks.addLast(adjacentMark)
    }

    internal fun addToHead(newMark: Coordinates) {
        marks.addFirst(newMark)
    }

    internal fun addToTail(newMark: Coordinates) {
        marks.addLast(newMark)
    }

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
}