package logic

import elements.MAX_WINNING_LINE_LENGTH
import elements.MIN_WINNING_LINE_LENGTH
import elements.Player
import publicApi.AtttPlayer

/**
 * AtttRules = Adjustable TicTacToe Rules
 * a single point of check if anybody wins, also container for all limitations & settings of game mechanics.
 */
internal class GameRules(
    private var winningLength: Int,
    // potentially here we can later add more criteria to detect if the game is won by any of players
) {
    private val players: MutableMap<AtttPlayer, Int> = mutableMapOf()

    init {
        // here we're doing possible corrections that may be needed to keep the game rules reasonable
        if (winningLength > MAX_WINNING_LINE_LENGTH) winningLength = MAX_WINNING_LINE_LENGTH
        else if (winningLength < MIN_WINNING_LINE_LENGTH) winningLength = MIN_WINNING_LINE_LENGTH
    }

    internal fun isWinningLength(lineLength: Int) = lineLength >= winningLength

    internal fun isGameWon(): Boolean = isWinningLength(getLeadingPlayer().getMaxLineLength())

    internal fun getLeadingPlayer(): AtttPlayer = detectLeadingPlayer() ?: Player.None

    // here we need the player - not its line length, so do not use maxOfOrNull {...} as it returns Int? in this case
    private fun detectLeadingPlayer(): AtttPlayer? = players.entries.maxByOrNull { k -> k.value }?.key

    internal fun updatePlayerScore(whichPlayer: AtttPlayer, newLineLength: Int) {
        val existingMaxLineLength = players[whichPlayer]
        if (newLineLength > (existingMaxLineLength ?: 0)) {
            players[whichPlayer] = newLineLength
        }
    }
}
