package players

import constants.*
import attt.Player
import utilities.Log

/**
 * unified place for handling active player for the upcoming move for any game session
 */
internal object PlayerProvider {

    // one instance for all cases
    internal val None: Player = PlayerModel(ID_FOR_PLAYER_NONE, PLAYER_NONE_NAME, SYMBOL_FOR_PLAYER_NONE)

    // this is a part of inner game logic - it should be used only internally, for now there's no need to show it to a client
    internal var activePlayer: Player = None
        private set

    internal var playersList: MutableList<PlayerModel> = mutableListOf()
        private set

    private var numberOfPlayersInGameSession: Int = -1 // real value cannot be less than 2 and more than 90 for now

    /**
     * resets the activePlayer and creates new instances for all players for every new GameSession instance
     */
    internal fun prepareNewPlayersInstances(desiredNumberOfPlayers: Int) {
        activePlayer = None
        Log.pl("prepareNewPlayersInstances: desiredNumberOfPlayers = $desiredNumberOfPlayers")
        numberOfPlayersInGameSession = when {
            desiredNumberOfPlayers > MAX_NUMBER_OF_PLAYERS -> MAX_NUMBER_OF_PLAYERS
            desiredNumberOfPlayers < MIN_NUMBER_OF_PLAYERS -> MIN_NUMBER_OF_PLAYERS
            else -> desiredNumberOfPlayers
        }
        Log.pl("prepareNewPlayersInstances: numberOfPlayersInGameSession = $numberOfPlayersInGameSession")
        playersList = ArrayList(numberOfPlayersInGameSession)
        if (numberOfPlayersInGameSession == MIN_NUMBER_OF_PLAYERS) { // default case for a classic Crosses & Noughts game
            playersList.add(0, PlayerModel(ID_FOR_PLAYER_X, PLAYER_X_NAME, SYMBOL_FOR_PLAYER_X)) // usually goes first
            playersList.add(1, PlayerModel(ID_FOR_PLAYER_O, PLAYER_O_NAME, SYMBOL_FOR_PLAYER_O)) // usually goes after X
        } else { // more than 2 players
            (0 until numberOfPlayersInGameSession).forEachIndexed { index, _ ->
                playersList.add(index, PlayerModel(index))
            }
        }
        Log.pl("prepareNewPlayersInstances: playersList = $playersList")
    }

    /**
     * sets the currently active player, for which a move will be made & returns the player for the next move
     */
    internal fun prepareNextPlayer(gameIsAlreadyWon: Boolean = false) {
        Log.pl("prepareNextPlayer: gameIsAlreadyWon = $gameIsAlreadyWon")
        activePlayer = if (gameIsAlreadyWon) {
            None // no real player is ready for the next move as there will be no any move in this game as it's finished
        } else if (activePlayer == playersList.last() || activePlayer == None) { // any possible edge case -> select the first
            playersList.first() // we need a ring here to make this carousel infinite
        } else {
            playersList[activePlayer.getId() + 1] // normal case in the middle of a game -> just pick the next one
        }
        Log.pl("activePlayer is set to be: $activePlayer")
    }
}