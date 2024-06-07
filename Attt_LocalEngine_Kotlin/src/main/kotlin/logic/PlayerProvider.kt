package logic

import elements.Player
import publicApi.AtttPlayer

internal const val SYMBOL_FOR_ABSENT_MARK = '·'

private const val idForPlayerX = 42 // of course, you know why exactly 42 :)
private const val idForPlayerO = 0
private const val idForPlayerNone = -1

private const val symbolForPlayerX = 'X'
private const val symbolForPlayerO = 'O'
private const val symbolForPlayerNone = 'n'

// for now - just the replacement of the former enums use
internal object PlayerProvider {

    // this is a very temporary solution to just check the failing tests -> then the architecture will get better
    var X: AtttPlayer = createNewPlayerX()
        private set
    var O: AtttPlayer = createNewPlayerO()
        private set
    val None: AtttPlayer = Player(idForPlayerNone, "PlayerNone", symbolForPlayerNone) // one for all cases

    // this is a part of inner game logic - it should be used only internally, for now there's no need to show it to a client
    internal var activePlayer: AtttPlayer = None
        private set

    // creates new instances for all players for every new GameSession instance
    internal fun prepareNewPlayersInstances() {
        X = createNewPlayerX()
        O = createNewPlayerO()
    }

    /**
     * sets the currently active player, for which a move will be made & returns the player for the next move
     */
    internal fun presetNextPlayer(): AtttPlayer {
        activePlayer = if (activePlayer == X) O else X // A is set after None & null case as well
        return activePlayer
    }

    private fun createNewPlayerX() = Player(idForPlayerX, "PlayerX", symbolForPlayerX)
    private fun createNewPlayerO() = Player(idForPlayerO, "PlayerO", symbolForPlayerO)
}