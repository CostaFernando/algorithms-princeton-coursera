import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double[] percolationThresholds;
  private final double CONFIDENCE_95 = 1.96;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0) {
      throw new IllegalArgumentException("n must be greater than 0.");
    }
    if (trials <= 0) {
      throw new IllegalArgumentException("trials must be greater than 0.");
    }

    percolationThresholds = new double[trials];
    for (int i = 0; i < trials; i++) {
      percolationThresholds[i] = 0.0;
    }

    for (int i = 0; i < trials; i++) {
      Percolation percolationExperiment = new Percolation(n);

      while (!percolationExperiment.percolates()) {
        int row = StdRandom.uniformInt(1, n + 1);
        int col = StdRandom.uniformInt(1, n + 1);

        percolationExperiment.open(row, col);
      }

      percolationThresholds[i] = (double) percolationExperiment.numberOfOpenSites() / (n * n);
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(percolationThresholds);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(percolationThresholds);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(percolationThresholds.length);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(percolationThresholds.length);
  }

  // test client (see below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);

    PercolationStats percolationStats = new PercolationStats(n, trials);

    System.out.println("mean = " + percolationStats.mean());
    System.out.println("stddev = " + percolationStats.stddev());
    System.out.println(
        "95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
  }
}
