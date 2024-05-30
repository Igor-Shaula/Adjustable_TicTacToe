interface AtttGame {

    fun prepare(newGameField: AtttField, newGameRules: AtttRules): AtttPlayer

    fun mm(x: Int, y: Int): AtttPlayer

    fun makeMove(x: Int, y: Int): AtttPlayer

    fun isActive(): Boolean

    fun printCurrentFieldIn2d()

    fun finish()

    companion object {
        fun create(): AtttGame = AtttEngine
    }
}