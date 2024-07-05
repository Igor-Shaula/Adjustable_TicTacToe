package players

import attt.Player
import constants.SYMBOL_FOR_FULL_BLOCK
import constants.SYMBOL_FOR_PLAYER_O
import constants.SYMBOL_FOR_PLAYER_X

private const val symbolsBeforeDigits = 0
private const val symbolsBeforeCapitalLetters = 10
private const val symbolsBeforeSmallLetters = 10 + 26
private const val symbolsBefore1stSymbolsBlock = 10 + 26 + 26
private const val symbolsBefore2ndSymbolsBlock = 10 + 26 + 26 + 6
private const val symbolsBeforeSlashSymbol = 10 + 26 + 26 + 6 + 6
private const val symbolsBefore3rdSymbolsBlock = 10 + 26 + 26 + 6 + 6 + 1
private const val symbolsBefore4thSymbolsBlock = 10 + 26 + 26 + 6 + 6 + 1 + 7
private const val symbolsBefore5thSymbolsBlock = 10 + 26 + 26 + 6 + 6 + 1 + 7 + 4

/**
 * the replacement of the former enums use, completely describes all player's data
 */
internal data class PlayerModel(override val id: Int) : Player {

    override var name: String? = null
        private set

    override var symbol: Char? = null
        private set

    override var maxLineLength = 0
        private set

    init {
        if (name.isNullOrBlank()) name = "Player # $id"
        if (symbol == null || symbol?.isWhitespace() == true) {
            symbol = definePlayerSymbolById()
        }
    }

    internal fun tryToSetMaxLineLength(newLineLength: Int) {
        if (newLineLength > maxLineLength) maxLineLength = newLineLength
    }

    private fun definePlayerSymbolById(): Char {
        // let's do it with ASCII table - all following numbers are decimal for simplicity
        val asciiCode = if (id < symbolsBeforeDigits + 10) {
            id - symbolsBeforeDigits + '0'.code // 0...9
        } else if (id < symbolsBeforeCapitalLetters + 26) { // id: 10...35 -> capital letters
            id - symbolsBeforeCapitalLetters + 'A'.code // A...Z
        } else if (id < symbolsBeforeSmallLetters + 26) { // id: 36...61 -> small letters
            id - symbolsBeforeSmallLetters + 'a'.code // a...z
        } else if (id < symbolsBefore1stSymbolsBlock + 6) { // id: 62...67 -> signs from ! to & including both
            id - symbolsBefore1stSymbolsBlock + '!'.code // ! " # $ % &
        } else if (id < symbolsBefore2ndSymbolsBlock + 6) { // id: 68...73 -> signs from ( to - including both
            id - symbolsBefore2ndSymbolsBlock + '('.code // ( ) * + , -
        } else if (id < symbolsBeforeSlashSymbol + 1) { // id = 74 -> sign /
            '/'.code
        } else if (id < symbolsBefore3rdSymbolsBlock + 7) { // id: 75...81 -> signs from : to @
            id - symbolsBefore3rdSymbolsBlock + ':'.code // : ; < = > ? @
        } else if (id < symbolsBefore4thSymbolsBlock + 4) { // id: 82...85 -> signs from [ to ^
            id - symbolsBefore4thSymbolsBlock + '['.code // [ \ ] ^
        } else if (id < symbolsBefore5thSymbolsBlock + 4) { // id: 86...89 -> signs from { to ~
            id - symbolsBefore5thSymbolsBlock + '{'.code // { | } ~
        } else { // id: 90+ -> but this case should not be accessible because
            '_'.code // _
        }
        return asciiCode.toChar()
    }

    /**
    here I create new instances of players X & O because PlayerModel is stateful
    and contains maxLineLength which needs to be reset for every game, that would complicate the flow,
    as maxLineLength is not the only thing which can keep player data from a previous game session.
     */
    companion object {

        internal fun createPlayerX() =
            PlayerModel(ID_FOR_PLAYER_X).apply { name = PLAYER_X_NAME; symbol = SYMBOL_FOR_PLAYER_X }

        internal fun createPlayerO() =
            PlayerModel(ID_FOR_PLAYER_O).apply { name = PLAYER_O_NAME; symbol = SYMBOL_FOR_PLAYER_O }

        internal fun markWinner(player: Player) =
            PlayerModel(player.id).apply { name = player.name; symbol = SYMBOL_FOR_FULL_BLOCK }

        // one instance is enough for all the time of all possible games
        internal val None: Player = PlayerModel(ID_FOR_PLAYER_NONE)
    }
}
