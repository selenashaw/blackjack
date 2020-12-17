/* creates blackjack object for the interface to use  */


public class Blackjack {

  private final int[] Deck;
  private int[] DealerCards;
  private int[] PlayerCards;

  public Blackjack() {
    Deck = new int[52];
    DealerCards = new int[14];
    PlayerCards = new int[14];
    for(int i = 0; i < 52; i++) {
      Deck[i] = 1 + (i % 13);
    }
    for(int i = 0; i < 14; i++) {
      DealerCards[i] = 0;
      PlayerCards[i] = 0; 
    }
    PlayerCards[0] = drawCard();
    DealerCards[0] = drawCard();
    PlayerCards[1] = drawCard();
    DealerCards[1] = drawCard();
  }

  public int drawCard() {
    int outputCard = -1;
    int cardindex = (int) (Math.random() * 52);
    if (Deck[cardindex] == -1) drawCard();
    else {
      outputCard = Deck[cardindex];
      Deck[cardindex] = -1;
    }
    return outputCard;
  }

  // returns array of size 3, first card is the first for the player, second card
  // is second for the player, third is the first for the dealer.
  public int[] getStarters() {
    int[] cards = new int[3];
    cards[0] = PlayerCards[0];
    cards[1] = PlayerCards[1];
    cards[2] = DealerCards[1];
    return cards;
  }

  public void hit() {
    int index = -1;
    for(int i = 2; i < 14; i++) {
      if(PlayerCards[i] == 0) {
        index = i;
        break;
      }
    }
    PlayerCards[index] = drawCard();
  }

  public void stand() {
    int pSum = playerSum();
    // here we want dealer to beat the player.
    // dealer stops hitting when they reach hard 17

  }

  public int dealerSum() {
    int sum = 0;
    for (int i = 0; i < 14; i++) {
      int card = DealerCards[i];
      if(card > 10) sum += 10;
      else sum += card;
    }
    return sum;
  }

  //TODO: handle ace for player and dealer
  public int playerSum() {
    int sum = 0;
    for (int i = 0; i < 14; i++) {
      int card = PlayerCards[i];
      if (card > 10) sum += 10;
      else sum += card;
    }
    return sum;
  }

  public boolean blackjackCheck() {
    if(playerSum() == 21) return true;
    else return false;
  }

  public void iterate() {
    int check = 0;
    for(int i: Deck) {
      System.out.println(i);
      check++;
    }
    System.out.format("Num cards: %d", check);
  }

  public static void main(String[] args) {
    Blackjack game = new Blackjack();
    // iterate runs over all the cards and prints their integer values
    int card = game.drawCard();
    System.out.println(card);
    game.iterate();
  }
}