package logic

import elements.Player
import publicApi.AtttPlayer

internal const val SYMBOL_FOR_ABSENT_MARK = '.'

private const val idForPlayerA = 0
private const val idForPlayerB = 1
private const val idForPlayerNone = -1

private const val symbolForPlayerA = 'X'
private const val symbolForPlayerB = 'O'
private const val symbolForPlayerNone = 'n'

// for now - just the replacement of the former enums use
internal object PlayerProvider {
    /*
        private var idCounter = -1
        val nextAvailableId: Int
            get() = idCounter++
    */

    // this is a very temporary solution to just check the failing tests -> then the architecture will get better
    var A: AtttPlayer = Player(idForPlayerA, "PlayerA", symbolForPlayerA)
        private set
    var B: AtttPlayer = Player(idForPlayerB, "PlayerB", symbolForPlayerB)
        private set
    val None: AtttPlayer = Player(idForPlayerNone, "PlayerNone", symbolForPlayerNone) // one for all cases

    // creates new instances for all players for every new GameSession instance
    internal fun prepareNewPlayersInstances() {
        A = Player(idForPlayerA, "PlayerA", symbolForPlayerA)
        B = Player(idForPlayerB, "PlayerB", symbolForPlayerB)
    }
}