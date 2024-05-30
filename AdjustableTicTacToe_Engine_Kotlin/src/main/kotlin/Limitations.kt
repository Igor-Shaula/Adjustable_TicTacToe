@file:Suppress("unused")

// all numbers & limitations are set here

const val MIN_GAME_FIELD_SIDE_SIZE = 3 // the game has no sense if less
const val MAX_GAME_FIELD_SIDE_SIZE = 1000 // because that's already a lot
const val MIN_WINNING_LINE_LENGTH = 3 // the game has no sense if less
const val MAX_WINNING_LINE_LENGTH = 30 // even this would be a very complex case
const val MIN_GAME_FIELD_DIMENSIONS = 2 // classic plain game which is very easy to draw
const val MAX_GAME_FIELD_DIMENSIONS = 3 // what if we want a 3d? this case can be more complex, but why not? :)
const val MIN_NUMBER_OF_PLAYERS = 2 // the game has no sense if less
const val MAX_NUMBER_OF_PLAYERS = 100 // let's imagine that we this engine once works for an online multiplayer game :)