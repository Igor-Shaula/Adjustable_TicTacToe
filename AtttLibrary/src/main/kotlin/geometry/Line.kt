package geometry

import geometry.abstractions.Coordinates
import geometry.abstractions.LineDirection

/**
 * a line is more than one adjacent dot/mark in one constant direction.
 * for now a line can only grow during detection work, it cannot become smaller.
 * any line consists of minimum two dots/marks.
 * two different lines with different order of dots/marks inside are equal.
 * processing/detecting of the line direction is not a job of the line.
 * all existing lines are meant to be proven/correct/checked to be straight & aligned in a direction.
 */
internal class Line(startingMark: Coordinates, adjacentMark: Coordinates, val direction: LineDirection) {

    /**
     * why a Set? -> because each coordinate is unique, and also we need unordered set
     * - to avoid keeping track of a starting point and its repositioning in case of adding a new mark before it.
     * also I decided to avoid using LineDirection concept here - for simplicity.
     * all control of line correctness is done at the level of line detection inside existing logic chains.
     */
    private val marks: MutableSet<Coordinates> = mutableSetOf()

    init {
        marks.add(startingMark)
        marks.add(adjacentMark)
    }

    internal fun add(newMark: Coordinates) {
        marks.add(newMark)
    }

    internal fun setOfMarks() = marks as Set<Coordinates>

    internal fun size() = marks.size

    // it is decided to treat two lines with similar marks but opposite direction as one (the same) line
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (other == null || other !is Line) false
        else marks == other.marks // as we use unordered set - it will be the same for opposite line as well
    }

    // if we override equals -> we have to override hashCode as well to prevent from breaking their contract
    override fun hashCode(): Int {
        return marks.hashCode() // as a line is a set of marks - let recognize it by the set of marks
    }

    override fun toString(): String {
        return "$marks / $direction"
    }
}
