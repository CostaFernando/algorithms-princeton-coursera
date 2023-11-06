import edu.princeton.cs.algs4.StdIn;

public class Permutation {
  public static void main(String[] args) {
    RandomizedQueue<String> queue = new RandomizedQueue<>();
    int k = Integer.parseInt(args[0]);

    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      queue.enqueue(s);
    }

    for (String item : queue) {
      if (k > 0) {
        System.out.println(item);
        k--;
      } else {
        break;
      }
    }

  }
}