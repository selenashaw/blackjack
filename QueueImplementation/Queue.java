
// Queue implementation to be used in Blackjack.java
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Queue {

  // nodes for the queue
  class Node {
    int card;
    Node next;
  }

  private Node head = null;
  private Node tail = null;

  /* builds the queue by taking all the cards in the deck, shuffling them,
   * then adds each to the queue */
  public Queue(int[] Deck) {
    shuffleArray(Deck);
    Node node = new Node();
    for (int i = 0; i < Deck.length; i++) {
      node = new Node();
      node.card = Deck[i];
      node.next = null;
      if (head == null) {
        head = node;
        tail = node;
      }
      else {
      tail.next = node;
      tail = node;
    }
    }
  }

  /*
   * Implementation of Fisher-Yates Shuffle from
   * https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
   */
  private static void shuffleArray(int[] ar) {
    Random rnd = ThreadLocalRandom.current();
    for (int i = ar.length - 1; i > 0; i--) {
      int index = rnd.nextInt(i + 1);
      int a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
  }

  /* pop returns the next card on the deck, and puts that card on the bottom*/
  public int pop() {
    Node popped = head;
    int temp = popped.card;
    head = popped.next;
    popped.next = null;
    tail.next = popped;
    tail = popped;
    return temp;
  }

  /* Main function is for testing to make sure the queue randomizes the cards*/
  public static void main(String[] args) {
    int[] test = {1,2,3,4,5,6,7,8,9,10};
    Queue testing = new Queue(test);
    for (int i = 0; i < test.length; i++) {
      System.out.println(testing.pop());
    }
  }
}
