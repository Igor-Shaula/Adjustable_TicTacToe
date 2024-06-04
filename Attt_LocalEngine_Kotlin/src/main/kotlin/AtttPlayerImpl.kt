import publicApi.AtttPlayer

// for now, 2 players is enough, but in the future we can have more
@Suppress("unused")
enum class AtttPlayerImpl(private val symbol: Char) : AtttPlayer {

    None('·'), A('X'), B('O');

    override fun getSymbol(): Char = this.symbol
}
// idea: later make the symbol configurable - because again, we can have more than 2 players in perspective
