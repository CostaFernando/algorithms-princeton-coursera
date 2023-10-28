import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int[][] grid;
  private WeightedQuickUnionUF weightedQuickUnionUF;
  int rowsInGrid;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    rowsInGrid = n;

    grid = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = 0;
      }
    }

    weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    grid[row - 1][col - 1] = 1;

    connectNodeToAdjacentNodes(row, col);
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    return grid[row - 1][col - 1] == 1;
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    return grid[row - 1][col - 1] == 0;
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    int openSites = 0;

    for (int row = 1; row <= grid[0].length; row++) {
      for (int col = 1; col <= grid[0].length; col++) {
        if (isOpen(row, col)) {
          openSites++;
        }
      }
    }

    return openSites;
  }

  // does the system percolate?
  public boolean percolates() {
    int topRootNode = weightedQuickUnionUF.find(0);
    int bottomRootNode = weightedQuickUnionUF.find(rowsInGrid * rowsInGrid + 1);

    return topRootNode == bottomRootNode;
  }

  private void connectNodeToAdjacentNodes(int row, int col) {
    int currentNodeIndex = getNodeIndex(row, col);
    if (row - 1 > 0) {
      if (grid[(row - 1) - 1][(col - 1)] == 1) {
        int topNodeIndex = getNodeIndex(row - 1, col);
        weightedQuickUnionUF.union(currentNodeIndex, topNodeIndex);
      }
    } else {
      int topNodeIndex = 0;
      weightedQuickUnionUF.union(currentNodeIndex, topNodeIndex);
    }

    if (row + 1 <= rowsInGrid) {
      if (grid[(row - 1) + 1][(col - 1)] == 1) {
        int bottomNodeIndex = getNodeIndex(row + 1, col);
        weightedQuickUnionUF.union(currentNodeIndex, bottomNodeIndex);
      }
    } else {
      int bottomNodeIndex = rowsInGrid * rowsInGrid + 1;
      weightedQuickUnionUF.union(currentNodeIndex, bottomNodeIndex);
    }

    if (col - 1 > 0) {
      if (grid[(row - 1)][(col - 1) - 1] == 1) {
        int leftNodeIndex = getNodeIndex(row, col - 1);
        weightedQuickUnionUF.union(currentNodeIndex, leftNodeIndex);
      }
    }

    if (col + 1 <= rowsInGrid) {
      if (grid[(row - 1)][(col - 1) + 1] == 1) {
        int rightNodeIndex = getNodeIndex(row, col + 1);
        weightedQuickUnionUF.union(currentNodeIndex, rightNodeIndex);
      }
    }
  }

  private int getNodeIndex(int row, int col) {
    return (row - 1) * rowsInGrid + col;
  }

  // test client (optional)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    Percolation percolationExperiment = new Percolation(n);

    while (!percolationExperiment.percolates()) {
      int row = StdRandom.uniformInt(1, n + 1);
      int col = StdRandom.uniformInt(1, n + 1);

      percolationExperiment.open(row, col);
    }

    System.out.println("Percolation threshold: " + (double) percolationExperiment.numberOfOpenSites() / (n * n));
  }
}