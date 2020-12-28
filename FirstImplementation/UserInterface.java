/* Userinterface for blackjack program */

import java.util.*;

public class UserInterface {

  private static int sum;
  private static int bet;

  /* Used to determine if a string is a number or not
   * Function isNumeric is based off of the example on
   * https://www.baeldung.com/java-check-string-number */
  private static boolean isNumeric(String input) {
    if (input == null)
      return false;
    try {
      Integer.parseInt(input);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  /* Used to give pauses between printing out the game when the dealer is hitting
   * Function wait is from 
   * https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java */
  public static void wait(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /* getVal is used to get the amount the user is gambling with */
  private static int getVal(Scanner sc) {
    String str = null;
    boolean check = false;
    while (!check) {
      str = sc.nextLine();
      if (!isNumeric(str))
        System.out.print("Please give a numeric value (i.e. 50)\n");
      else
        check = true;
    }
    return Integer.parseInt(str);
  }

  /* getBet is used to see if the user has given a valid bet, if not it prompts
   * the user to give a different amount */
  private static int getBet(Scanner sc) {
    int val = 0;
    boolean check = false;
    while (!check) {
      String str = null;
      boolean check2 = false;
      while (!check2) {
        str = sc.nextLine();
        if (str.equals("quit"))
          return -1;
        else if (!isNumeric(str))
          System.out.print("Please give a numeric value (i.e. 50) or \"quit\"\n");
        else
          check2 = true;
      }
      val = Integer.parseInt(str);
      if (val > sum)
        System.out.format("Uh oh, you only have %d in your account, try a lower bet!\n", sum);
      else if (val <= 0)
        System.out.format("Please choose a bet greater than 0. \n");
      else
        check = true;
    }
    return val;
  }

  /* parseCommand is used to check to see if the user has given a valid command for the game */
  private static String parseCommand(Scanner sc) {
    String str = null;
    boolean check = false;
    while (!check) {
      str = sc.nextLine();
      if (sum >= (2 * bet)) {
        if (!str.equals("hit") && !str.equals("stand") && !str.equals("double")) {
          System.out.print("Please give one of the following three commands: \"hit\", \"stand\", or \"double\"\n");
        } else
          check = true;
      } else {
        if (!str.equals("hit") && !str.equals("stand")) {
          System.out.print("Please give one of the following two commands: \"hit\" or \"stand\"\n");
        } else
          check = true;
      }
    }
    return str;
  }

  /* formatCards is used to format two strings that represent cards into a
   * string array of three lines that will be printed for the user to see the cards,
   * checks to see if either card is 10, in which case the card has to be formatted
   * differently */
  private static String[] formatCards(String c1, String c2) {
    String[] output = new String[3];

    if (c1 == "10" && c2 == "10") {
      output[0] = ("  ____\t ____ \n");
      output[1] = (" | " + c1 + " |\t| " + c2 + " | \n");
      output[2] = (" |____|\t|____| \n");
    } else if (c1 == "10") {
      output[0] = ("  ____\t ___ \n");
      output[1] = (" | " + c1 + " |\t| " + c2 + " | \n");
      output[2] = (" |____|\t|___| \n");
    } else if (c2 == "10") {
      output[0] = ("  ___\t ____ \n");
      output[1] = (" | " + c1 + " |\t| " + c2 + " | \n");
      output[2] = (" |___|\t|____| \n");
    } else {
      output[0] = ("  ___\t ___ \n");
      output[1] = (" | " + c1 + " |\t| " + c2 + " | \n");
      output[2] = (" |___|\t|___| \n");
    }
    return output;
  }

  /* format is used to take in an integer and return the string representation
   * of that card */
  private static String format(int card) {
    if (card == 1)
      return "A";
    if (card == 2)
      return "2";
    if (card == 3)
      return "3";
    if (card == 4)
      return "4";
    if (card == 5)
      return "5";
    if (card == 6)
      return "6";
    if (card == 7)
      return "7";
    if (card == 8)
      return "8";
    if (card == 9)
      return "9";
    if (card == 10)
      return "10";
    if (card == 11)
      return "J";
    if (card == 12)
      return "Q";
    else
      return "K";
  }

  /* hitCall uses the blackjack game and the formatted player cards to call hit
   * and then format the new card to be printed to the terminal */
  private static String[] hitCall(Blackjack game, String[] pcards) {
    game.hit();
    int[] cards = game.get_player_cards();
    // the last card in the array is the newest
    String c = format(cards[cards.length - 1]);
    if (c == "10") {
      String temp = pcards[0].substring(0, pcards[0].length() - 2);
      pcards[0] = temp + "\t ____ \n";

      temp = pcards[1].substring(0, pcards[1].length() - 2);
      pcards[1] = temp + "\t| " + c + " | \n";

      temp = pcards[2].substring(0, pcards[2].length() - 2);
      pcards[2] = temp + "\t|____| \n";
    } else {
      String temp = pcards[0].substring(0, pcards[0].length() - 2);
      pcards[0] = temp + "\t ___ \n";

      temp = pcards[1].substring(0, pcards[1].length() - 3);
      pcards[1] = temp + "|\t| " + c + " | \n";

      temp = pcards[2].substring(0, pcards[2].length() - 2);
      pcards[2] = temp + "\t|___| \n";
    }
    return pcards;
  }

  /* standCall is used when the user issuses a stand command, it calls stand
   * with the Blackjack game to update the cards then prints out the dealer
   * and players cards, waiting 2 seconds between each additional card the
   * dealer pulls, and finally prints the result of the play */
  private static void standCall(String[] pCF, String[] dCF, Blackjack game) {
    StringBuilder str = new StringBuilder();
    int winner = game.stand();
    int[] dealer_cards = game.get_dealer_cards();
    System.out.print("Dealer's Cards: \n");
    // show the upside down dealer card
    dCF = formatCards(format(dealer_cards[0]), format(dealer_cards[1]));
    for (int i = 0; i < 3; i++) {
      str.append(dCF[i]);
    }
    System.out.print(str.toString());
    System.out.print("\n\n");
    // show all the player cards including the hit
    System.out.print("Your Cards: \n");
    str = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      str.append(pCF[i]);
    }
    System.out.print(str.toString());
    System.out.print("\n\n");
    int i = 2;
    while (i < dealer_cards.length) {
      wait(2000);
      String c = format(dealer_cards[i]);
      if (c == "10") {
        String temp = dCF[0].substring(0, dCF[0].length() - 2);
        dCF[0] = temp + "\t ____ \n";

        temp = dCF[1].substring(0, dCF[1].length() - 2);
        dCF[1] = temp + "\t| " + c + " | \n";

        temp = dCF[2].substring(0, dCF[2].length() - 2);
        dCF[2] = temp + "\t|____| \n";
      } else {
        String temp = dCF[0].substring(0, dCF[0].length() - 2);
        dCF[0] = temp + "\t ___ \n";

        temp = dCF[1].substring(0, dCF[1].length() - 3);
        dCF[1] = temp + "|\t| " + c + " | \n";

        temp = dCF[2].substring(0, dCF[2].length() - 2);
        dCF[2] = temp + "\t|___| \n";
      }
      System.out.print("Dealer's Cards: \n");
      // show the upside down dealer card
      str = new StringBuilder();
      for (int j = 0; j < 3; j++) {
        str.append(dCF[j]);
      }
      System.out.print(str.toString());
      System.out.print("\n\n");
      // show all the player cards including the hit
      System.out.print("Your Cards: \n");
      str = new StringBuilder();
      for (int j = 0; j < 3; j++) {
        str.append(pCF[j]);
      }
      System.out.print(str.toString());
      System.out.print("\n\n");
      i++;
    }
    if (winner == 1) {
      sum += bet;
      System.out.format("You won! You new total is %d.\n\n", sum);
    } else if (winner == 2) {
      sum += bet;
      System.out.format("Dealer bust, you win! Your new total is %d.\n\n", sum);
    } else if (winner == 0) {
      System.out.format("Push! Your total is still %d.\n\n", sum);
    } else {
      sum -= bet;
      System.out.format("You lost, your new total is %d.\n\n", sum);
    }
  }

  /* The majority of the gameplay is in main, first prompts the user to give
   * how much they will gamble with, then uses a while loop that allows them
   * to play until they run out of money or quit, note that a user cannot quit
   * while in the middle of a game. */
  public static void main(String[] args) {
    String[] pCardsFormatted = new String[3];
    String[] dCardsFormatted = new String[3];
    Scanner sc = new Scanner(System.in);
    System.out.print("*******************************\n");
    System.out.print("     Welcome to BlackJack!\n");
    System.out.print("*******************************\n");
    System.out.print("How much would you like to gamble with?\n");
    sum = getVal(sc);
    System.out.print("Great! ");
    System.out.format("You have %d in your account!\n", sum);
    while (sum > 0) {
      System.out.print("How much would you like to bet? (Or type \"quit\" to cash out!)\n");
      bet = getBet(sc);
      if (bet == -1) {
        System.out.format("Thanks for playing! Your total is %d. \n", sum);
        break;
      }
      System.out.format("Great! You have bet %d for this game, let's play!\n", bet);
      String p1, p2, d1;
      Blackjack game = new Blackjack();
      int player_card_num = game.get_num_player();
      int dealer_card_num = game.get_num_dealer();
      int[] player_cards = new int[player_card_num];
      int[] dealer_cards = new int[dealer_card_num];
      player_cards = game.get_player_cards();
      dealer_cards = game.get_dealer_cards();

      p1 = format(player_cards[0]);
      d1 = format(dealer_cards[0]);
      p2 = format(player_cards[1]);

      System.out.print("Dealer's Cards: \n");
      dCardsFormatted = formatCards(d1, "?");
      StringBuilder str = new StringBuilder();
      for (int i = 0; i < 3; i++) {
        str.append(dCardsFormatted[i]);
      }
      System.out.print(str.toString());
      System.out.print("\n\n");
      System.out.print("Your Cards: \n");
      pCardsFormatted = formatCards(p1, p2);
      str = new StringBuilder();
      for (int i = 0; i < 3; i++) {
        str.append(pCardsFormatted[i]);
      }
      System.out.print(str.toString());
      System.out.print("\n\n");

      int doublecheck = 0;
      int doublecheck1 = 0;
      while (true) {
        if (game.blackjackCheck()) {
          sum += bet;
          System.out.format("Blackjack! You now have %d! \n\n", sum);
          break;
        } else {
          if (sum >= (2 * bet)) {
            if (doublecheck == 0) {
              System.out.print("Would you like to hit, stand, or double?\n");
              doublecheck++;
            } else {
              System.out.print("Would you like to hit or stand?\n");
              doublecheck1++;
            }
          } else
            System.out.print("Would you like to hit or stand?\n");
          String command = parseCommand(sc);
          // if the player hits
          if (command.equals("hit")) {
            pCardsFormatted = hitCall(game, pCardsFormatted);
            if (game.playerSum() > 21) {
              System.out.print("Dealer's Cards: \n");
              str = new StringBuilder();
              // show the upside down dealer card
              dCardsFormatted = formatCards(d1, format(dealer_cards[1]));
              for (int i = 0; i < 3; i++) {
                str.append(dCardsFormatted[i]);
              }
              System.out.print(str.toString());
              System.out.print("\n\n");
              // show all the player cards including the hit
              System.out.print("Your Cards: \n");
              str = new StringBuilder();
              for (int i = 0; i < 3; i++) {
                str.append(pCardsFormatted[i]);
              }
              System.out.print(str.toString());
              System.out.print("\n\n");
              sum = sum - bet;
              System.out.format("Bust! You now have %d! \n\n", sum);
              break;
            } else {
              System.out.print("Dealer's Cards: \n");
              str = new StringBuilder();
              for (int i = 0; i < 3; i++) {
                str.append(dCardsFormatted[i]);
              }
              System.out.print(str.toString());
              System.out.print("\n\n");
              System.out.print("Your Cards: \n");
              str = new StringBuilder();
              for (int i = 0; i < 3; i++) {
                str.append(pCardsFormatted[i]);
              }
              System.out.print(str.toString());
              System.out.print("\n\n");
            }
          }
          // if player stands
          else if (command.equals("stand")) {
            standCall(pCardsFormatted, dCardsFormatted, game);
            break;
          }
          // if player doubles
          else {
            if (doublecheck1 == 1) {
              // player hits once then we basically call stand.
              System.out.print("Sorry, you can't double here!\n");
            }
            // add blackjack check for player
            else {
              bet = bet * 2;
              // hit to get the one additional card
              pCardsFormatted = hitCall(game, pCardsFormatted);
              if (game.playerSum() > 21) {
                System.out.print("Dealer's Cards: \n");
                str = new StringBuilder();
                // show the upside down dealer card
                dCardsFormatted = formatCards(d1, format(dealer_cards[1]));
                for (int i = 0; i < 3; i++) {
                  str.append(dCardsFormatted[i]);
                }
                System.out.print(str.toString());
                System.out.print("\n\n");
                // show all the player cards including the hit
                System.out.print("Your Cards: \n");
                str = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                  str.append(pCardsFormatted[i]);
                }
                System.out.print(str.toString());
                System.out.print("\n\n");
                sum = sum - bet;
                System.out.format("Bust! You now have %d! \n\n", sum);
                break;
              } else {
                System.out.print("Dealer's Cards: \n");
                str = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                  str.append(dCardsFormatted[i]);
                }
                System.out.print(str.toString());
                System.out.print("\n\n");
                System.out.print("Your Cards: \n");
                str = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                  str.append(pCardsFormatted[i]);
                }
                System.out.print(str.toString());
                System.out.print("\n\n");
                if (game.blackjackCheck()) {
                  sum += bet;
                  System.out.format("Blackjack! You now have %d! \n\n", sum);
                  break;
                } else {
                  wait(2000);
                  // now dealer hits until game ends
                  standCall(pCardsFormatted, dCardsFormatted, game);
                  break;
                }
              }
            }
          }
        }
      }
    }
  }
}
