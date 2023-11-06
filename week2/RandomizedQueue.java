import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] queue;
  private int size;

  // construct an empty randomized queue
  public RandomizedQueue() {
    queue = (Item[]) new Object[1];
    size = 0;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return size;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null)
      throw new IllegalArgumentException();

    if (size == queue.length) {
      resize(queue.length * 2);
    }
    queue[size++] = item;
  }

  private void resize(int newSize) {
    Item[] oldQueue = queue;
    queue = (Item[]) new Object[newSize];
    int loopIterations;

    if (oldQueue.length > newSize) {
      loopIterations = newSize;
    } else {
      loopIterations = oldQueue.length;
    }

    for (int i = 0; i < loopIterations; i++) {
      queue[i] = oldQueue[i];
    }
  }

  // remove and return a random item
  public Item dequeue() {
    if (size == 0)
      throw new NoSuchElementException();

    if (size == queue.length / 4) {
      resize(queue.length / 2);
    }

    int randomIndex = StdRandom.uniformInt(size);
    Item item = queue[randomIndex];
    queue[randomIndex] = queue[size - 1];
    queue[size - 1] = null;

    size--;

    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (size == 0)
      throw new NoSuchElementException();

    int randomIndex = StdRandom.uniformInt(size);

    return queue[randomIndex];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private int sizeCopy;
    private Item[] queueCopy;

    public ListIterator() {
      queueCopy = (Item[]) new Object[size];
      for (int i = 0; i < size; i++) {
        queueCopy[i] = queue[i];
      }

      sizeCopy = size;
    }

    public boolean hasNext() {
      return sizeCopy > 0;
    }

    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();

      int randomIndex = StdRandom.uniformInt(sizeCopy);
      Item item = queueCopy[randomIndex];
      queueCopy[randomIndex] = queueCopy[sizeCopy - 1];
      queueCopy[sizeCopy - 1] = null;

      sizeCopy--;

      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<Integer> queue = new RandomizedQueue<>();

    queue.enqueue(1);
    queue.enqueue(2);
    System.out.println("sample(): " + queue.sample());
    System.out.println("isEmpty(): " + queue.isEmpty());
    System.out.println("queue.size(): " + queue.size());
    for (int item : queue) {
      System.out.println(item);
    }

    queue.dequeue();
    System.out.println("sample(): " + queue.sample());
    System.out.println("isEmpty(): " + queue.isEmpty());
    System.out.println("queue.size(): " + queue.size());
    for (int item : queue) {
      System.out.println(item);
    }

    queue.dequeue();
    System.out.println("isEmpty(): " + queue.isEmpty());
    System.out.println("queue.size(): " + queue.size());
    for (int item : queue) {
      System.out.println(item);
    }
  }

}