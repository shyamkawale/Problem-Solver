package Patterns.Dynamic_Programming.DP_on_Grids.Two_Ninja_Chocolate_Pickup;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Two_Ninja_Chocolate_Pickup extends ProblemSolver {

    public static void main(String[] args) {
        new Two_Ninja_Chocolate_Pickup().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] grid = DataConvertor.to2DIntArray(args[0]);

        int res1 = chocolatePickup_rec(grid);
        int res2 = chocolatePickup_memo(grid);
        System.out.println(res1 + " " + res2);
    }

    private int chocolatePickup_rec(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        return helper1(0, 0, col-1, grid);
    }

    private int helper1(int r, int c1, int c2, int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        if(!isValidIdx(r, row) || !isValidIdx(c1, col) || !isValidIdx(c2, col)) {
            return Integer.MIN_VALUE;
        }

        if(r == row-1) {
            if(c1 == c2) {
                return grid[r][c1];
            }
            else {
                return grid[r][c1] + grid[r][c2];
            }
        }

        int maxChocolate = Integer.MIN_VALUE;
        int[][] dir = {{-1, -1}, {-1, 0}, {-1, 1}};

        for(int[] dir1: dir) {
            int nc1 = c1 + dir1[1];
            for(int[] dir2: dir) {
                int nc2 = c2 + dir2[1];
                int ans = Integer.MIN_VALUE;

                if(c1 == c2) {
                    ans = grid[r][c1] + helper1(r+1, nc1, nc2, grid);
                }
                else {
                    ans = grid[r][c1] + grid[r][c2] + helper1(r+1, nc1, nc2, grid);
                }

                maxChocolate = Math.max(maxChocolate, ans);
            }
        }

        return maxChocolate;
    }

    private boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

    private int chocolatePickup_memo(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        // r, c1, c2
        int[][][] dp = new int[row][col][col];
        for(int r=0; r<row; r++) {
            for(int c1=0; c1<col; c1++) {
                Arrays.fill(dp[r][c1], -1);
            }
        }
        return helper2(0, 0, col-1, grid, dp);
    }

    private int helper2(int r, int c1, int c2, int[][] grid, int[][][] dp) {
        int row = grid.length;
        int col = grid[0].length;

        if(!isValidIdx(r, row) || !isValidIdx(c1, col) || !isValidIdx(c2, col)) {
            return Integer.MIN_VALUE;
        }

        if(r == row-1) {
            if(c1 == c2) {
                return grid[r][c1];
            }
            else {
                return grid[r][c1] + grid[r][c2];
            }
        }

        if(dp[r][c1][c2] != -1) {
            return dp[r][c1][c2];
        }

        // max chocolates Alice and Bob can eat in the row.
        int maxChocolate = Integer.MIN_VALUE;
        int[][] dir = {{-1, -1}, {-1, 0}, {-1, 1}};

        for(int[] dir1: dir) {
            int nc1 = c1 + dir1[1];
            for(int[] dir2: dir) {
                int nc2 = c2 + dir2[1];
                int ans = Integer.MIN_VALUE;

                if(c1 == c2) {
                    ans = grid[r][c1] + helper2(r+1, nc1, nc2, grid, dp);
                }
                else {
                    ans = grid[r][c1] + grid[r][c2] + helper2(r+1, nc1, nc2, grid, dp);
                }

                maxChocolate = Math.max(maxChocolate, ans);
            }
        }

        dp[r][c1][c2] = maxChocolate;
        return dp[r][c1][c2];
    }

}
