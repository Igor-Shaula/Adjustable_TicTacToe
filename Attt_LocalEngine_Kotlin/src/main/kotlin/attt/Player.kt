package attt

interface Player {

    fun getId(): Int

    fun getName(): String?

    fun getSymbol(): Char?

    fun getMaxLineLength(): Int
}