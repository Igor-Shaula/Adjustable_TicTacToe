package logic

import elements.MAX_WINNING_LINE_LENGTH
import elements.MIN_WINNING_LINE_LENGTH
import publicApi.AtttPlayer

/**
 * a single point of check if anybody wins, also container for all limitations & settings of game mechanics.
 */
internal class GameRules(
    private var winningLength: Int,
    // potentially here we can later add more criteria to detect if the game is won by any of players
) {
    private val maxLines: MutableMap<AtttPlayer, Int> = mutableMapOf()

    private var theWinner: AtttPlayer = PlayerProvider.None

    init {
        // here we're doing possible corrections that may be needed to keep the game rules reasonable
        if (winningLength > MAX_WINNING_LINE_LENGTH) winningLength = MAX_WINNING_LINE_LENGTH
        else if (winningLength < MIN_WINNING_LINE_LENGTH) winningLength = MIN_WINNING_LINE_LENGTH
    }

    internal fun isGameWon(): Boolean = theWinner != PlayerProvider.None

    internal fun getWinner(): AtttPlayer = theWinner

    internal fun getLeadingPlayer(): AtttPlayer = detectLeadingPlayer() ?: PlayerProvider.None

    // here we need the player - not its line length, so do not use maxOfOrNull {...} as it returns Int? in this case
    private fun detectLeadingPlayer(): AtttPlayer? = maxLines.entries.maxByOrNull { k -> k.value }?.key

    internal fun updatePlayerScore(whichPlayer: AtttPlayer, newLineLength: Int) {
        if (isGameWon()) return // I decided to preserve the state of gameField when the winner is detected
        // the following lines work only when the winner has NOT been yet detected
        if (newLineLength > (maxLines[whichPlayer] ?: 0)) {
            maxLines[whichPlayer] = newLineLength
            if (newLineLength >= winningLength) theWinner = whichPlayer
        }
    }
}
