# Adjustable Game Engine

This is not a game - it's a logic engine that has to be ready to use by any UI.

Main concepts:
- (default) This game field is traditionally a square, so here all its dimensions will be equal.
- (required) The game field must be not 3x3 but ADJUSTABLE (let's imagine that, for example, up to 1000x1000).
- (required) Criteria for winning length must also be adjustable (if the field is 1000x1000 than maxLength should be in range 3..1000).
- (additional) There can be more than 2 dimensions of the game field, so 3d and even more should work the same as a 2d flat field.
- (additional) Why do we have only two players? there can be more - let's imagine up to 100 players for a 1000x1000 field.

For now, this code will try to align with only the required concepts, but let's keep in mind that things can get more interesting in the future :)
