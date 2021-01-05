
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

  /* pop returns the next card on the deck */
  public int pop() {
    Node popped = head;
    int temp = popped.card;
    head = popped.next;
    return temp;
  }

  /* push takes an array of cards and puts them to the bottom of the deck*/
  public void push(int[] cards) {
    for (int i = 0; i < cards.length; i++) {
      if(cards[i] == 0) break;
      Node insert = new Node();
      insert.card = cards[i];
      insert.next = null;
      tail.next = insert;
      tail = insert;
    }
  }

  /* iterates through the entire queue and returns everything it holds as an array*/
  private void iter() {
    Node curr = new Node();
    curr = head;
    while (curr != null) {
      System.out.print(curr.card + " ");
      curr = curr.next;
    }
  }

  /* Main function is for testing to make sure the queue randomizes the cards*/
  public static void main(String[] args) {
    int[] test = {1,2,3,4,5,6,7,8,9,10};
    Queue testing = new Queue(test);
    int[] pulled = new int[3];
    pulled[0] = testing.pop();
    pulled[1] = testing.pop();
    pulled[0] = testing.pop();
    System.out.println("pulled " + pulled[0] + " " + pulled[1] + " " + pulled[2]);
    testing.push(pulled);
    testing.iter();
  }
}
