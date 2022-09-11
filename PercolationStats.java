import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int size;
    private int tests;
    private double[] checks;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid grid size or trials");
        }
        size = n;
        tests = trials;
        checks = new double[trials];
    }

    public void runTests() {
        int tested = 1;
        while (tested <= tests) {
            int check = 0;
            Percolation perc = new Percolation(size);
            StdOut.println("Start");
            perc.printGrid();
            while (!perc.percolates()) {
                check++;
                int x = StdRandom.uniformInt(1, size + 1);
                int y = StdRandom.uniformInt(1, size + 1);
                perc.open(x, y);
                perc.printGrid();
            }

            checks[tested - 1] = ((double) perc.numberOfOpenSites() / (size * size));
            StdOut.println("TRIED " + check + " times");
            tested++;
        }
        StdOut.println("TESTED " + tested + " times");
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(checks);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(checks);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(tests));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(tests));
    }

    // test client (see below)
    public static void main(String[] args) {
        int tests = StdIn.readInt();
        int size = StdIn.readInt();
        PercolationStats stats = new PercolationStats(size, tests);
        stats.runTests();
        StdOut.println("mean \t\t\t\t= " + stats.mean());
        StdOut.println("stddev \t\t\t\t= " + stats.stddev());
        StdOut.println("95% confidence interval \t= [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}