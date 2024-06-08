package elements

import publicApi.AtttPlayer

internal data class Player(
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
        val asciiCode = if (id < 10) {
            id + '0'.code // 0...9
        } else if (id < 10 + 26) { // id: 10...35 -> capital letters
            id + 'A'.code // A...Z
        } else if (id < 10 + 26 + 26) { // id: 36...61 -> small letters
            id + 'a'.code // a...z
        } else if (id < 10 + 26 + 26 + 6) { // id: 62...67 -> signs from ! to & including both
            id + '!'.code // ! " # $ % &
        } else if (id < 10 + 26 + 26 + 6 + 6) { // id: 68...73 -> signs from ( to - including both
            id + '('.code // ( ) * + , -
        } else if (id < 10 + 26 + 26 + 6 + 6 + 1) { // id = 74 -> sign /
            id + '/'.code
        } else if (id < 10 + 26 + 26 + 6 + 6 + 1 + 7) { // id: 75...81 -> signs from : to @
            id + ':'.code // : ; < = > ? @
        } else if (id < 10 + 26 + 26 + 6 + 6 + 1 + 7 + 4) { // id: 82...85 -> signs from [ to ^
            id + '['.code // [ \ ] ^
        } else if (id < 10 + 26 + 26 + 6 + 6 + 1 + 7 + 4 + 4) { // id: 86...89 -> signs from { to ~
            id + '{'.code // { | } ~
        } else { // id: 90+ -> but this case should not be accessible because
            '_'.code // _
        }
        return asciiCode.toChar()
    }
}
