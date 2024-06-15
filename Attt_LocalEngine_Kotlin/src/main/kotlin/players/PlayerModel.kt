package players

import publicApi.AtttPlayer

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
internal data class PlayerModel(
    private val id: Int, private var name: String? = null, private var symbol: Char? = null
) : AtttPlayer {

    init {
        if (name.isNullOrBlank()) name = "Player # $id"
        if (symbol == null || symbol?.isWhitespace() == true) {
            symbol = definePlayerSymbolById()
        }
    }

    private var maxLineLength = 0 // not using get & set here as this data is accessed from AtttPlayer interface

    override fun getId(): Int = id // this is the main criterion to distinguish one player from any other

    override fun getName(): String? = name // optional as the name is not required to play the game

    override fun getSymbol(): Char? = symbol // this works mostly for a 2d field

    override fun getMaxLineLength(): Int = maxLineLength

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
}
