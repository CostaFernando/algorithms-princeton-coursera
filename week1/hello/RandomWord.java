import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void main(String[] args){
    String champion = "";
    int k = 1;
    int i = 1;

    while(!StdIn.isEmpty()){
      String word = StdIn.readString();
      
      if(i > k){
        double probability = 1.0/i;
        if(StdRandom.bernoulli(probability)){
          champion = word;
        }
      }else{
        champion = word;
      }
      i++;
    }

    StdOut.println(champion);
  }
}