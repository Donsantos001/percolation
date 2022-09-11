import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int size;
    private int open;
    private WeightedQuickUnionUF wqu;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid row or column");
        }

        // additonal two for virtual top and bottom
        size = n;
        wqu = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        open = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; i < n; i++) {
                grid[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalArgumentException {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Invalid row or column");
        }

        if (grid[col - 1][row - 1]) {
            return;
        }

        // keeps track of the number of opens sites
        open++;
        grid[col - 1][row - 1] = true;

        // connects to virtual top
        if (row == 1) {
            wqu.union(0, position(row, col));
        }
        // connects to virtual bottom
        if (row == size) {
            wqu.union(size * size + 1, position(row, col));
        }

        // checks the left to connect
        if (col - 1 > 0 && isOpen(row, col - 1)) {
            wqu.union(position(row, col - 1), position(row, col));
        }
        // checks the right to connect
        if (col + 1 <= size && isOpen(row, col + 1)) {
            wqu.union(position(row, col + 1), position(row, col));
        }
        // checks the top to connect
        if (row - 1 > 0 && isOpen(row - 1, col)) {
            wqu.union(position(row - 1, col), position(row, col));
        }
        // checks the bottom to connect
        if (row + 1 <= size && isOpen(row + 1, col)) {
            wqu.union(position(row + 1, col), position(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws IllegalArgumentException {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Invalid row or column");
        }
        return grid[col - 1][row - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws IllegalArgumentException {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Invalid row or column");
        }

        return wqu.find(position(row, col)) == wqu.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqu.find(0) == wqu.find(size * size + 1);
    }

    // returns the linear position in the union array
    private int position(int row, int col) {
        return col + (row - 1) * size;
    }

    public void printGrid() throws IllegalArgumentException {
        System.out.println("----------------------------------");
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                String sign = isOpen(x + 1, y + 1) ? "X" : "-";
                System.out.print(" " + sign + " ");

            }
            System.out.println();
        }
        System.out.println("----------------------------------");
    }

}