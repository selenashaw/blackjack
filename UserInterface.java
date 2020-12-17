/* Userinterface for blackjack program */
import java.util.*;

public class UserInterface {

  private static int sum;
  private static int bet;

  /* Function isNumeric is based off of the example on
  https://www.baeldung.com/java-check-string-number */
  private static boolean isNumeric(String input) {
    if(input == null) return false;
    try {
      Double.parseDouble(input); 
    } catch (NumberFormatException nfe) { 
      return false;
    }
    return true;
  }

  private static int getVal(Scanner sc) {
    String str = null;
    boolean check = false;
    while (!check) {
      str = sc.nextLine();
      if(!isNumeric(str)) System.out.print("Please give a numeric value (i.e. 50)\n");
      else check = true;
    }
    return Integer.parseInt(str);
  }

  private static int getBet(Scanner sc) {
    int val = 0;
    boolean check = false;
    while (!check) {
      String str = null;
      boolean check2 = false;
      while (!check2) {
        str = sc.nextLine();
        if (str.equals("quit")) return -1;
        else if(!isNumeric(str)) System.out.print("Please give a numeric value (i.e. 50) or \"quit\"\n");
        else check2 = true;
      }
      val = Integer.parseInt(str);
      if(val > sum) System.out.format("Uh oh, you only have %d in your account, try a lower bet!\n", sum);
      else if(val <= 0) System.out.format("Please choose a bet greater than 0. \n");
      else check = true;
    }
    return val;
  }

  private static String parseCommand(Scanner sc) {
    String str = null;
    boolean check = false;
    while (!check) {
      str = sc.nextLine();
      if(sum >= (2*bet)) {
        if(!str.equals("hit") && !str.equals("stand") && !str.equals("double")) {
          System.out.print("Please give one of the following three commands: \"hit\", \"stand\", or \"double\"\n");
        }
        else check = true;
      }
      else{
        if(!str.equals("hit") && !str.equals("stand")) {
          System.out.print("Please give one of the following two commands: \"hit\" or \"stand\"\n");
        }
        else check = true;
      }
    }
    return str;
  }

  private static String formatCards (String c1, String c2) {
    StringBuilder output = new StringBuilder();

    if (c1 == "10" && c2 == "10") {
      output.append("  ____        ____ \n");
      output.append(" | " + c1 + " |      | " + c2 + " | \n");
      output.append(" |____|      |____| \n");
    }
    else if(c1 == "10") {
      output.append("  ____        ___ \n");
      output.append(" | " + c1 + " |      | " + c2 + " | \n");
      output.append(" |____|      |___| \n");
    }
    else if (c2 == "10"){
      output.append("  ___        ____ \n");
      output.append(" | " + c1 + " |      | " + c2 + " | \n");
      output.append(" |___|      |____| \n");
    }
    else {
    output.append("  ___        ___ \n");
    output.append(" | " + c1 + " |      | " + c2 + " | \n");
    output.append(" |___|      |___| \n");
    }
    return output.toString();
  }

  private static String format(int card) {
    if (card == 1) return "A";
    if (card == 2) return "2";
    if (card == 3) return "3";
    if (card == 4) return "4";
    if (card == 5) return "5";
    if (card == 6) return "6";
    if (card == 7) return "7";
    if (card == 8) return "8";
    if (card == 9) return "9";
    if (card == 10) return "10";
    if (card == 11) return "J";
    if (card == 12) return "Q";
    else return "K";
  }

  public static void main(String[] args) {
    //call blackjack to get first four cards, first is player, second is dealer down
    // third is player, fourth is face up dealer
    Scanner sc = new Scanner(System.in);
    System.out.print("*******************************\n");
    System.out.print("     Welcome to BlackJack!\n");
    System.out.print("*******************************\n");
    System.out.print("How much would you like to gamble with?\n");
    sum = getVal(sc);
    System.out.print("Great! ");
    while(sum > 0){
      System.out.format("You have %d in your account!\n", sum);
      System.out.print("How much would you like to bet? (Or type \"quit\" to cash out!)\n");
      bet = getBet(sc);
      if(bet == -1) {
        System.out.format("Thanks for playing! Your total is %d \n", sum);
        break;
      }
      System.out.format("Great! You have bet %d for this game, let's play!\n", bet);
      String p1, p2, d2;
      Blackjack game = new Blackjack();
      int[] starters = new int[3];
      starters = game.getStarters();
      p1 = format(starters[0]);
      d2 = format(starters[2]);
      p2 = format(starters[1]);
      System.out.print("Dealer's Cards: \n");
      System.out.print(formatCards(d2, "?"));
      System.out.print("\n\n");
      System.out.print("Your Cards: \n");
      System.out.print(formatCards(p1, p2));
      // some while thingy here
      if(game.blackjackCheck()) { 
        sum += bet;
        System.out.format("Blackjack! You now have %d", sum);
      }
      else {
       if(sum >= (2*bet)) System.out.print ("Would you like to hit, stand, or double?\n");
        else System.out.print ("Would you like to hit or stand?\n");
        String command = parseCommand(sc);
        // add hit in interface to handle if the player busts!
        if(command.equals("hit")) game.hit();
        // in stand and double check for pushes and dealer busts
        // also make a stand in interface that prints all of the cards and the result
        //else if(command.equals("stand")) game.stand();
        // also make a double in interface that prints all of the carsd and then result, as well as doubling the number in bet
        //else game.double();
      }
    }
  }
}
