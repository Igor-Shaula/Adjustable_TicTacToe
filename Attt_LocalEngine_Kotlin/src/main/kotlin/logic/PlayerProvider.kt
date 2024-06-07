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

    // creates new instances for all players for every new GameSession instance
    internal fun prepareNewPlayersInstances() {
        X = createNewPlayerX()
        O = createNewPlayerO()
    }

    private fun createNewPlayerX() = Player(idForPlayerX, "PlayerX", symbolForPlayerX)
    private fun createNewPlayerO() = Player(idForPlayerO, "PlayerO", symbolForPlayerO)
}