Readme for Blackjack

This is my queue implementation of the Blackjack program.

It is run in the shell by compiling Queue.java, Blackjack.java, and UserInterface.java, 
Run with UserInterface, with command line prompts to play the game.

This version uses a queue to shuffle and hold the cards, as cards are drawn they are
sent to the back of the queue, when the dealer has made it halfway through the 
queue the deck is shuffled.

Note that the card that is dealt remains in the deck, it is placed in the back
so the user will never see that the card remains - after a hand is done the cards
held by the player and dealer are tossed out (since the queue already has
those cards stored in the back).
