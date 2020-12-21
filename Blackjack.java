/* creates blackjack object for the interface to use  */


public class Blackjack {

  private final int[] Deck;
  private final int max_possible_cards;
  private int[] DealerCards;
  private int[] PlayerCards;
  private int numPlayerCards;
  private int numDealerCards;

  public Blackjack() {
    Deck = new int[52];
    max_possible_cards = 12;
    DealerCards = new int[max_possible_cards];
    PlayerCards = new int[max_possible_cards];
    for(int i = 0; i < 52; i++) {
      Deck[i] = 1 + (i % 13);
    }
    for(int i = 0; i < max_possible_cards; i++) {
      DealerCards[i] = 0;
      PlayerCards[i] = 0; 
    }
    PlayerCards[0] = drawCard();
    DealerCards[0] = drawCard();
    PlayerCards[1] = drawCard();
    DealerCards[1] = drawCard();

    numPlayerCards = 2;
    numDealerCards = 2;
  }

  private int drawCard() {
    int outputCard = -1;
    int cardindex = (int) (Math.random() * 52);
    if (Deck[cardindex] == -1) drawCard();
    else {
      outputCard = Deck[cardindex];
      Deck[cardindex] = -1;
    }
    return outputCard;
  }

  public int get_num_player() {
    return numPlayerCards;
  }

  public int get_num_dealer() {
    return numPlayerCards;
  }

  public int[] get_player_cards() {
    int[] cards = new int[numPlayerCards];
    int index = 0;
    for (int i = 0; i < max_possible_cards; i++) {
      if(PlayerCards[i] != 0) cards[index++] = PlayerCards[i];
    }
    return cards;
  }

  public int[] get_dealer_cards() {
    int[] cards = new int[numDealerCards];
    int index = 0;
    for (int i = 0; i < max_possible_cards; i++) {
      if(DealerCards[i] != 0) cards[index++] = DealerCards[i];
    }
    return cards;
  }

  public void hit() {
    int index = -1;
    for(int i = 2; i < max_possible_cards; i++) {
      if(PlayerCards[i] == 0) {
        index = i;
        break;
      }
    }
    numPlayerCards++;
    PlayerCards[index] = drawCard();
  }

  private void dealerHit() {
    int index = -1;
    for(int i = 2; i < max_possible_cards; i++) {
      if(DealerCards[i] == 0) {
        index = i; 
        break;
      }
    }
    numDealerCards++;
    DealerCards[index] = drawCard();
  }

  // returns 1 if player wins -1 if dealer wins, 2 if dealer busts, 0 if push
  public int stand() {
    int pSum = playerSum();
    if (dealerSum() > 21) return 2;
    else if (pSum < dealerSum()) return -1;
    else {
      while (true) {
        if(dealerSum() < 17) dealerHit();
        else break;
      }
      if (dealerSum() < pSum) return 1;
      else if (dealerSum() > 21) return 2;
      else if (dealerSum() == pSum) return 0;
      else return -1;
    }
  }

  public int ace_adder(int ace_count, int sum) {
    if(ace_count == 0) return sum;
    else {
      if(sum > 10) sum++;
      else sum = sum + 11;
      return ace_adder(--ace_count, sum);
    }
  }

  public int dealerSum() {
    int sum = 0;
    int ace_count = 0;
    for (int i = 0; i < max_possible_cards; i++) {
      int card = DealerCards[i];
      if(card == 1) ace_count++;
      else if(card > 10) sum += 10;
      else sum += card;
    }
    if(ace_count == 0) return sum;
    else return ace_adder(ace_count, sum);
  }

  public int playerSum() {
    int sum = 0;
    int ace_count = 0;
    for (int i = 0; i < max_possible_cards; i++) {
      int card = PlayerCards[i];
      if(card == 1) ace_count++;
      else if (card > 10) sum += 10;
      else sum += card;
    }
    if(ace_count == 0) return sum;
    else return ace_adder(ace_count, sum);
  }

  public boolean blackjackCheck() {
    if(playerSum() == 21) return true;
    else return false;
  }

  private void iterate() {
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