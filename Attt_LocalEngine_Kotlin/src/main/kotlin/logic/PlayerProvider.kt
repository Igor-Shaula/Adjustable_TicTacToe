package logic

import elements.Player
import publicApi.AtttPlayer

internal const val SYMBOL_FOR_ABSENT_MARK = '.'

// for now - just the replacement of the former enums use
internal object PlayerProvider {
    /*
        private var idCounter = -1
        val nextAvailableId: Int
            get() = idCounter++
    */
    val idForPlayerA = 0
    val idForPlayerB = 1
    val idForPlayerNone = -1

    val symbolForPlayerA = 'X'
    val symbolForPlayerB = 'O'
    val symbolForPlayerNone = 'n'

    // this is a very temporary solution to just check the failing tests -> then the architecture will get better
    val A: AtttPlayer = Player(idForPlayerA, "PlayerA", symbolForPlayerA)
//        private set
    var B: AtttPlayer = Player(idForPlayerB, "PlayerB", symbolForPlayerB)
//        private set
    val None: AtttPlayer = Player(idForPlayerNone, "PlayerNone", symbolForPlayerNone) // one for all cases

    init {
        prepareNewPlayersInstances()
    }

    private fun prepareNewPlayersInstances() {
//        A = Player(idForPlayerA, "PlayerA", symbolForPlayerA)
//        B = Player(idForPlayerB, "PlayerB", symbolForPlayerB)
    }
}