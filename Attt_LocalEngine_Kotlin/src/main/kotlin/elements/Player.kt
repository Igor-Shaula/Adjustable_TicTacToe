package elements

import publicApi.AtttPlayer

internal class Player(
    private val id: Int, private val name: String? = null, private val symbol: Char? = null
) : AtttPlayer {

    private var maxLineLength = 0 // not using get & set here as this data is accessed from AtttPlayer interface

    override fun getId(): Int = id // this is the main criterion to distinguish one player from any other

    override fun getName(): String? = name // optional as the name is not required to play the game

    override fun getSymbol(): Char? = symbol // this works mostly for a 2d field

    override fun getMaxLineLength(): Int = maxLineLength

    internal fun tryToSetMaxLineLength(newLineLength: Int) {
        if (newLineLength > maxLineLength) maxLineLength = newLineLength
    }
}
