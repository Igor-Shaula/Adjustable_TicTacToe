package gameLogic

import attt.Player
import constants.MAX_WINNING_LINE_LENGTH
import constants.MIN_WINNING_LINE_LENGTH
import geometry.Line
import geometry.abstractions.Coordinates
import players.PlayerModel
import utilities.Log

/**
 * a single point of check if anybody wins, also container for all limitations & settings of game mechanics.
 */
internal class GameProgress(
    private var winningLength: Int
    // potentially here we can later add more criteria to detect if the game is won by any of players
) {
    // quickly accessible container for every player's achievements - needed for getting a winner & a leader
    private val maxLines: MutableMap<Player, Int> = mutableMapOf()

    // contains all detected lines for every player - should not be looped/read during every move
    private val allPlayersLines: MutableMap<Player, MutableSet<Line?>> = mutableMapOf()

    private var theWinner: Player = PlayerModel.None

    init {
        // here we're doing possible corrections that may be needed to keep the game rules reasonable
        if (winningLength > MAX_WINNING_LINE_LENGTH) winningLength = MAX_WINNING_LINE_LENGTH
        else if (winningLength < MIN_WINNING_LINE_LENGTH) winningLength = MIN_WINNING_LINE_LENGTH
    }

    internal fun isGameWon(): Boolean = theWinner != PlayerModel.None

    internal fun getWinner(): Player = theWinner

    internal fun getWinningLine(): Line? {
        allPlayersLines[theWinner]?.forEach { line: Line? ->
            line?.let {
                if (it.size() >= winningLength) return line
            } ?: return null
        }
        return null
    }

    internal fun getLeadingPlayer(): Player = detectLeadingPlayer() ?: PlayerModel.None

    // here we need the player - not its line length, so do not use maxOfOrNull {...} as it returns Int? in this case
    private fun detectLeadingPlayer(): Player? = maxLines.entries.maxByOrNull { length -> length.value }?.key

    internal fun updatePlayerScore(whichPlayer: Player, newLineLength: Int) {
        if (isGameWon()) {
            Log.pl("player ${theWinner.id} wins with detectedLineLength: ${theWinner.maxLineLength}")
            return // I decided to preserve the state of gameField when the winner is detected
        }
        // the following lines work only when the winner has NOT been yet detected
        if (newLineLength > (maxLines[whichPlayer] ?: 0)) {
            maxLines[whichPlayer] = newLineLength
            if (newLineLength >= winningLength) theWinner = whichPlayer
        }
    }

    internal fun saveNewLine(player: Player, newLine: Line) {
        // here we do not know currently existing number of players, also initially a set for every player does not exist
        val setOfThisPlayerLines = allPlayersLines[player] ?: mutableSetOf()
        // before adding newly detected line into the set - let's remove all existing lines which are inside this new one
        setOfThisPlayerLines.removeIf { line ->
            (newLine.direction == line?.direction || newLine.direction == line?.direction?.opposite())
                    && (newLine.contains(line.first()) || newLine.contains(line.last()))
        }
        setOfThisPlayerLines.add(newLine)
        allPlayersLines[player] = setOfThisPlayerLines
    }

    internal fun addToRecentLine(player: Player, coordinates: Coordinates) {
        // as LinkedHashSet is the default implementation of Set in Kotlin - we have the order of insertions preserved
        allPlayersLines[player]?.last()?.add(coordinates)
    }

    internal fun getLinesFor(player: Player): MutableSet<Line?>? = allPlayersLines[player]

    private fun detectLeadingPlayerFromSet(): Player? =
        allPlayersLines.entries.maxByOrNull { setOfLines -> setOfLines.value.getMaxLength() }?.key
}

internal fun Set<Line?>.getMaxLength(): Int =
    this.maxByOrNull { line: Line? -> line?.size() ?: 0 }?.size() ?: 0
