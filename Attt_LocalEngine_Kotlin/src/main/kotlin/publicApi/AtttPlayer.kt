package publicApi

interface AtttPlayer {

    fun getId(): Int

    fun getName(): String?

    fun getSymbol(): Char?

    fun getMaxLineLength(): Int
}