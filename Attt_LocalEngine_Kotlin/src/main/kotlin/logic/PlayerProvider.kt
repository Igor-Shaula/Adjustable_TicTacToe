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
    /*
        private var idCounter = -1
        val nextAvailableId: Int
            get() = idCounter++
    */

    // this is a very temporary solution to just check the failing tests -> then the architecture will get better
    var X: AtttPlayer = Player(idForPlayerX, "PlayerX", symbolForPlayerX)
        private set
    var O: AtttPlayer = Player(idForPlayerO, "PlayerO", symbolForPlayerO)
        private set
    val None: AtttPlayer = Player(idForPlayerNone, "PlayerNone", symbolForPlayerNone) // one for all cases

    // creates new instances for all players for every new GameSession instance
    internal fun prepareNewPlayersInstances() {
        X = Player(idForPlayerX, "PlayerX", symbolForPlayerX)
        O = Player(idForPlayerO, "PlayerO", symbolForPlayerO)
    }
}