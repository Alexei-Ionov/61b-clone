package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] matrix;
    private WeightedQuickUnionUF rootArr;
    private int topSiteIndex;
    private int botSiteIndex;
    private int numOpen;
    public Percolation(int N) {
        /// in my matrix, 0s represent blocked cells, 1s represent open, 2s represent open + full
        if (N <= 0 ) {
            throw new IllegalArgumentException();
        }
        // + 2 for top and bottom sites!
        rootArr = new WeightedQuickUnionUF((N*N) + 2);
        matrix = new boolean[N][N];
        topSiteIndex = N*N;
        botSiteIndex = N*N + 1;
        numOpen = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = false;
            }
        }
    }
    private int matrixToUnionIndex(int row, int col) {
        return (row * matrix.length) + col;
    }
    private int[] unionToMatrixIndex(int index) {
        int row = Math.floorDiv(index, matrix.length);
        int col = index % matrix.length;
        int[] res = new int[]{row, col};
        return res;

    }
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            matrix[row][col] = true;
            numOpen += 1;
            int rootIndex1 = matrixToUnionIndex(row, col);
            //top row
            if (row == 0) {
                rootArr.union(rootIndex1, topSiteIndex);

            }
            //bot row
            if (row == matrix.length - 1) {
                if (isFull(row - 1, col)) {
                    rootArr.union(rootIndex1, botSiteIndex);
                }g
            }
            if ((row + 1 < matrix.length) && (isOpen(row + 1, col))) {
                rootArr.union(rootIndex1, matrixToUnionIndex(row + 1,col));
            }
            if ((col - 1 >= 0) && (isOpen(row, col - 1))) {
                rootArr.union(rootIndex1, matrixToUnionIndex(row,col - 1));
            }
            if ((row - 1 >= 0) && (isOpen(row-1, col))) {
                rootArr.union(rootIndex1, matrixToUnionIndex(row-1,col));
            }
            if ((col + 1 < matrix.length) && (isOpen(row, col + 1))) {
                rootArr.union(rootIndex1, matrixToUnionIndex(row,col + 1));
            }
        }
    }
    public boolean isOpen(int row, int col) {
        return matrix[row][col];
    }
    public boolean isFull(int row, int col) {
        int index = matrixToUnionIndex(row, col);
        return rootArr.connected(index, topSiteIndex);
    }
    public int numberOfOpenSites() {
        return numOpen;
    }
    public boolean percolates() {
        return rootArr.connected(topSiteIndex, botSiteIndex);
    }
}