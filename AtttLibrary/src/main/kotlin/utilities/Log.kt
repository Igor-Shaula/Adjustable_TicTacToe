package utilities

/**
 * simplest log wrapper for direct logging from main classes.
 * for now the main purpose is to enable silence in logs & switch it on/off if needed
 */
internal object Log {

    private var isLogActive = false

    // pl - for short from PrintLine
    fun pl(what: String) {
        if (isLogActive) println(what)
    }

    fun switch(isOn: Boolean) {
        isLogActive = isOn
    }
}
