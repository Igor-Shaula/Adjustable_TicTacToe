package elements

internal const val SYMBOL_FOR_ABSENT_MARK = '·'
internal const val SYMBOL_FOR_DIVIDER = ' '
internal const val SYMBOL_FOR_NEW_LINE = '\n' // it can be a Char - no need to use it as the String like "\n"

internal const val SYMBOL_FOR_FULL_BLOCK = '\u2588' // all space for one character is painted - for better visibility: █

internal const val SYMBOL_FOR_PLAYER_X = 'X'
internal const val SYMBOL_FOR_PLAYER_O = 'O'
internal const val SYMBOL_FOR_PLAYER_NONE = '_' // just for case but this should not be ever shown on the game field

internal const val PLAYER_NONE_NAME = "PlayerNone"
internal const val PLAYER_X_NAME = "PlayerX"
internal const val PLAYER_O_NAME = "PlayerO"