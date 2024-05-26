// this class is the main contacting point for any game UI
// as any UI is meant to use only one game logic - let it be a singleton
object GameEngine {

    // let's not consume RAM with game objects until the game is not yet started - that's why these are nullable
    private var gameField: GameField? = null

    private var gameRules: GameRules? = null

    // -------
    // region PUBLIC API

    // create & provide the UI with a new game field
    // adjustability starts here - in the parameters
    fun prepare(
        newGameField: GameField = GameField, newGameRules: GameRules = GameRules
    ) {
        clear() // for all possible resources that could be used previously
        gameField = newGameField
        gameRules = newGameRules
    }

    // stop right now, count the achieved score for all players and show the result
    fun finish() {
        // todo: count and show the score here - a bit later
        clear()
    }

    fun save() { // todo: here we might specify a filename
        // later
    }

    fun restore() { // todo: specify what exactly to restore, a file for example
        // later
    }

    fun isRunning() = gameField != null && gameRules != null

    // endregion PUBLIC API
    // --------
    // region ALL PRIVATE

    // immediately clear if anything is running at the moment
    private fun clear() {
        gameField?.clear()
        gameField = null
        gameRules = null
    }

    // endregion ALL PRIVATE
}
