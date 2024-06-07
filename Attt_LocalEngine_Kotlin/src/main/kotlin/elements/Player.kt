package elements

import publicApi.AtttPlayer

// todo: later make the symbol configurable - because we can have more than 2 players in perspective

// for now, 2 players is enough, but in the future we will have more
internal class Player(
    private val id: Int, private val name: String? = null, private val symbol: Char
) : AtttPlayer {
    // player None was added to avoid nullability in all places that require use of this class

    private var maxLineLength = 0 // not using get & set here as this data is accessed from AtttPlayer interface

    override fun getId(): Int = id

    override fun getName(): String? = name

    override fun getSymbol(): Char = symbol

    override fun getMaxLineLength(): Int = maxLineLength

    internal fun tryToSetMaxLineLength(newLineLength: Int) {
        if (newLineLength > maxLineLength) maxLineLength = newLineLength
    }
}
