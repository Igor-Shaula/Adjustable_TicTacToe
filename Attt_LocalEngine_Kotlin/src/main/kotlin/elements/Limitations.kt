@file:Suppress("unused")

package elements

// all numbers & limitations are set here

// default size for AtttEngine creation
internal const val MIN_GAME_FIELD_SIDE_SIZE = 3 // the game has no sense if less
internal const val MAX_GAME_FIELD_SIDE_SIZE = 1000 // because that's already a lot

// default size for AtttEngine creation
internal const val MIN_WINNING_LINE_LENGTH = 3 // the game has no sense if less
internal const val MAX_WINNING_LINE_LENGTH = 30 // even this would be a very complex case

// default size for AtttField creation
internal const val MIN_NUMBER_OF_PLAYERS = 2 // the game has no sense if less
internal const val MAX_NUMBER_OF_PLAYERS = 90 // because ASCII visible basic symbols excluding ' ` . and _
