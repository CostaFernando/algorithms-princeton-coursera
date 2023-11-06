import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private Node first;
  private Node last;
  private int size = 0;

  private class Node {
    Item item;
    Node next;
    Node prev;
  }

  // construct an empty deque
  public Deque() {

  }

  // is the deque empty?
  public boolean isEmpty() {
    return first == null;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (isEmpty()) {
      first = new Node();
      last = first;
      first.item = item;
    } else {
      Node oldFirst = first;
      first = new Node();
      first.item = item;
      first.next = oldFirst;
      first.prev = null;
      oldFirst.prev = first;
    }

    size++;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (isEmpty()) {
      first = new Node();
      last = first;
      first.item = item;
    } else {
      Node oldLast = last;
      last = new Node();
      last.item = item;
      last.next = null;
      last.prev = oldLast;
      oldLast.next = last;
    }

    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Node oldFirst = first;
    Item oldFirstItem = first.item;

    if (size() == 1) {
      first = null;
      last = null;
    } else {
      first = oldFirst.next;
      first.prev = null;
    }

    size--;

    return oldFirstItem;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Node oldLast = last;
    Item oldLastItem = last.item;

    if (size() == 1) {
      first = null;
      last = null;
    } else {
      last = oldLast.prev;
      last.next = null;
    }

    size--;

    return oldLastItem;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      Item item = current.item;
      current = current.next;

      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    Deque<Integer> dequeOfNumbers = new Deque<>();

    dequeOfNumbers.addFirst(1);
    dequeOfNumbers.addFirst(2);

    System.out.println("Size: " + dequeOfNumbers.size());
    for (int number : dequeOfNumbers) {
      System.out.println(number);
    }

    dequeOfNumbers.removeFirst();
    System.out.println("Size: " + dequeOfNumbers.size());
    for (int number : dequeOfNumbers) {
      System.out.println(number);
    }

    dequeOfNumbers.removeLast();
    System.out.println("Size: " + dequeOfNumbers.size());
    for (int number : dequeOfNumbers) {
      System.out.println(number);
    }
    System.out.println("isEmpty(): " + dequeOfNumbers.isEmpty());

    dequeOfNumbers.addLast(4);
    dequeOfNumbers.addLast(5);
    System.out.println("Size: " + dequeOfNumbers.size());
    for (int number : dequeOfNumbers) {
      System.out.println(number);
    }
    System.out.println("isEmpty(): " + dequeOfNumbers.isEmpty());
  }
}