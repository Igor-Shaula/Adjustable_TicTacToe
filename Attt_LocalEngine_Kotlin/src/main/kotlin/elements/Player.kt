package elements

import publicApi.AtttPlayer

internal const val SYMBOL_FOR_ABSENT_MARK = '.'
internal const val SYMBOL_FOR_PLAYER_A = 'X'
internal const val SYMBOL_FOR_PLAYER_B = 'O'
// todo: later make the symbol configurable - because we can have more than 2 players in perspective

// for now, 2 players is enough, but in the future we will have more
enum class Player(private val symbol: Char) : AtttPlayer {

    // player None was added to avoid nullability in all places that require use of this class
    None(SYMBOL_FOR_ABSENT_MARK), A(SYMBOL_FOR_PLAYER_A), B(SYMBOL_FOR_PLAYER_B);

    private var maxLineLength = 0

    override fun getSymbol(): Char = this.symbol

    override fun getMaxLineLength(): Int = maxLineLength

    internal fun tryToSetMaxLineLength(newLineLength: Int) {
        if (newLineLength > maxLineLength) maxLineLength = newLineLength
    }
}
