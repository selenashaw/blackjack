/* creates blackjack object for the interface to use  */

public class Blackjack {

  private final int[] Deck;
  private final int max_possible_cards;
  private Queue queueDeck;
  private int[] DealerCards;
  private int[] PlayerCards;
  private int numPlayerCards;
  private int numDealerCards;
  private int cardsDrawn = 0;

  /* The constructor initializes all the global variables and draws the first
   * two cards for the player and for the dealer */
  public Blackjack() {
    Deck = new int[52];
   
    max_possible_cards = 12;
    DealerCards = new int[max_possible_cards];
    PlayerCards = new int[max_possible_cards];
    for (int i = 0; i < 52; i++) {
      Deck[i] = 1 + (i % 13);
    }
    queueDeck = new Queue(Deck);
  }

  // deals the player and dealers first set of cards
  public void dealCards() {
    for (int i = 0; i < max_possible_cards; i++) {
      DealerCards[i] = 0;
      PlayerCards[i] = 0;
    }

    PlayerCards[0] = drawCard();
    DealerCards[0] = drawCard();
    PlayerCards[1] = drawCard();
    DealerCards[1] = drawCard();

    cardsDrawn += 4;
    numPlayerCards = 2;
    numDealerCards = 2;
  }

  // used to check if the number of cards drawn is greater than half.
  public int numCardsDrawn() {
    return cardsDrawn;
  }

  // shuffles the deck
  public Queue shuffle() {
    Queue newQueue = new Queue(Deck);
    cardsDrawn = 0;
    return newQueue;
  }

  /* drawCard is used to access the deck array and randomly chooses an index,
   * if the card at that index hasn't been used it returns that card and sets
   * that location to -1, if it has been used it runs again */
  private int drawCard() {
    int outputCard = queueDeck.pop();
    return outputCard;
  }

  /* get_num_player returns the number of cards the player has */
  public int get_num_player() {
    return numPlayerCards;
  }

  /* get_num_dealer returns the number of cards the dealer has */
  public int get_num_dealer() {
    return numPlayerCards;
  }

  /* returns an array of the cards the player is holding */
  public int[] get_player_cards() {
    int[] cards = new int[numPlayerCards];
    int index = 0;
    for (int i = 0; i < max_possible_cards; i++) {
      if (PlayerCards[i] != 0)
        cards[index++] = PlayerCards[i];
    }
    return cards;
  }

  /* returns an array of the cards the dealer is holding*/
  public int[] get_dealer_cards() {
    int[] cards = new int[numDealerCards];
    int index = 0;
    for (int i = 0; i < max_possible_cards; i++) {
      if (DealerCards[i] != 0)
        cards[index++] = DealerCards[i];
    }
    return cards;
  }

  /* hit is a call the player can make, it draws a card and adds it to the 
   * player's hand */
  public void hit() {
    int index = -1;
    for (int i = 2; i < max_possible_cards; i++) {
      if (PlayerCards[i] == 0) {
        index = i;
        break;
      }
    }
    numPlayerCards++;
    cardsDrawn++;
    PlayerCards[index] = drawCard();
  }

  /* dealerHit draws a card and adds it to the dealer's hand */
  private void dealerHit() {
    int index = -1;
    for (int i = 2; i < max_possible_cards; i++) {
      if (DealerCards[i] == 0) {
        index = i;
        break;
      }
    }
    numDealerCards++;
    cardsDrawn++;
    DealerCards[index] = drawCard();
  }

  /* stand checks the players sum, then hits for the dealer until the dealer's 
   * sum is greater or equal to 17. The function returns 1 if the player wins,
   * -1 if the dealer wins, 2 if the dealer busts, and 0 if there is a push */
  public int stand() {
    int pSum = playerSum();
    if (dealerSum() > 21)
      return 2;
    else if (pSum < dealerSum())
      return -1;
    else {
      while (true) {
        if (dealerSum() < 17)
          dealerHit();
        else
          break;
      }
      if (dealerSum() < pSum)
        return 1;
      else if (dealerSum() > 21)
        return 2;
      else if (dealerSum() == pSum)
        return 0;
      else
        return -1;
    }
  }

  /* ace_adder is used by the summing functions, takes in the current sum and the
   * total number of aces, if the sum is greater than 10 then an ace is counted
   * as 1, otherwise the ace is added to the sum as 11 */
  public int ace_adder(int ace_count, int sum) {
    if (ace_count == 0)
      return sum;
    else {
      if (sum > 10)
        sum++;
      else
        sum = sum + 11;
      return ace_adder(--ace_count, sum);
    }
  }

  /* dealerSum calculates the sum of the dealer's hand */
  public int dealerSum() {
    int sum = 0;
    int ace_count = 0;
    for (int i = 0; i < max_possible_cards; i++) {
      int card = DealerCards[i];
      if (card == 1)
        ace_count++;
      else if (card > 10)
        sum += 10;
      else
        sum += card;
    }
    if (ace_count == 0)
      return sum;
    else
      return ace_adder(ace_count, sum);
  }

  /* playerSum calculates the sum of the player's hand */
  public int playerSum() {
    int sum = 0;
    int ace_count = 0;
    for (int i = 0; i < max_possible_cards; i++) {
      int card = PlayerCards[i];
      if (card == 1)
        ace_count++;
      else if (card > 10)
        sum += 10;
      else
        sum += card;
    }
    if (ace_count == 0)
      return sum;
    else
      return ace_adder(ace_count, sum);
  }

  /* blackjackCheck checks to see if the playerSum is equal to 21 */
  public boolean blackjackCheck() {
    if (playerSum() == 21)
      return true;
    else
      return false;
  }

  /* pushes the player and dealers hand to the back of the queue*/
  public void returnToDealer() {
    queueDeck.push(DealerCards);
    queueDeck.push(PlayerCards);
  }

  /* iterate is used for testing to see what the deck is set to */
  private void iterate() {
    int check = 0;
    for (int i : Deck) {
      System.out.println(i);
      check++;
    }
    System.out.format("Num cards: %d", check);
  }

  /* The main function is used for testing Blackjack */
  public static void main(String[] args) {
    Blackjack game = new Blackjack();
    // iterate runs over all the cards and prints their integer values
    int card = game.drawCard();
    System.out.println(card);
    game.iterate();
  }
}
