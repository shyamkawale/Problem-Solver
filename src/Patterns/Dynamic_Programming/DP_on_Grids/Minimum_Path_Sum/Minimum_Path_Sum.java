package Patterns.Dynamic_Programming.DP_on_Grids.Minimum_Path_Sum;

import java.util.Arrays;
import java.util.PriorityQueue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/minimum-path-sum/

Given a m x n grid filled with non-negative numbers, 
find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.

Input: grid = [[1,2,3],[4,5,6]]
Output: 12
*/
public class Minimum_Path_Sum extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Path_Sum().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] grid = DataConvertor.to2DIntArray(args[0]);

        int res1 = minPathSum_Dijktras(grid);
        int res2 = minPathSum_rec(grid);
        int res3 = minPathSum_memo(grid);
        int res4 = minPathSum_tabu(grid);
        int res5 = minPathSum_tabuop(grid);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5);
    }

    public int minPathSum_Dijktras(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        int[][] dist = new int[row][col];
        for(int i=0; i<dist.length; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        // r, c, wt
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        queue.offer(new int[]{0, 0, grid[0][0]});
        dist[0][0] = grid[0][0];

        int[][] dir = {{0, 1}, {1, 0}};

        while(!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int r = polledNode[0];
            int c = polledNode[1];
            int wt = polledNode[2];

            if(r == row-1 && c == col-1) {
                return wt;
            }

            if(dist[r][c] != wt) {
                continue;
            }

            for(int i=0; i<dir.length; i++) {
                int nr = r + dir[i][0];
                int nc = c + dir[i][1];

                if(isValid(nr, row) && isValid(nc, col)) {
                    int nwt = grid[nr][nc];

                    if(dist[nr][nc] > wt + nwt) {
                        dist[nr][nc] = wt + nwt;
                        queue.offer(new int[]{nr, nc, dist[nr][nc]});
                    }
                }
            }
        }

        return grid[row-1][col-1];
    }

    public boolean isValid(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

    private int minPathSum_rec(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        return helper1(row-1, col-1, grid);
    }

    private int helper1(int r, int c, int[][] grid) {
        if(r == 0 && c == 0) {
            return grid[0][0];
        }

        if(r < 0 || c < 0) {
            return 0;
        }

        int upSum = Integer.MAX_VALUE;
        if(r-1 >= 0) {
            upSum = grid[r][c] + helper1(r-1, c, grid);
        }

        int leftSum = Integer.MAX_VALUE;
        if(c-1 >= 0) {
            leftSum = grid[r][c] + helper1(r, c-1, grid);
        }

        return Math.min(upSum, leftSum);
    }

    private int minPathSum_memo(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        long[][] dp = new long[row][col];
        for(int i=0; i<dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        return (int)helper2(row-1, col-1, grid, dp);
    }

    private long helper2(int r, int c, int[][] grid, long[][] dp) {
        if(r == 0 && c == 0) {
            return grid[0][0];
        }

        if(r < 0 || c < 0) {
            return Integer.MAX_VALUE;
        }

        if(dp[r][c] != -1) {
            return dp[r][c];
        }

        long upSum = grid[r][c] + helper2(r-1, c, grid, dp);
        long leftSum = grid[r][c] + helper2(r, c-1, grid, dp);

        dp[r][c] = Math.min(upSum, leftSum);
        return dp[r][c];
    }

    private int minPathSum_tabu(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        int[][] dp = new int[row][col];
        dp[0][0] = grid[0][0];

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(r == 0 && c == 0) continue;

                int upSum = Integer.MAX_VALUE;
                if(r-1 >= 0) {
                    upSum = grid[r][c] + dp[r-1][c];
                }

                int leftSum = Integer.MAX_VALUE;
                if(c-1 >= 0) {
                    leftSum = grid[r][c] + dp[r][c-1];
                }

                dp[r][c] = Math.min(upSum, leftSum);
            }
        }

        return dp[row-1][col-1];
    }

    private int minPathSum_tabuop(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        int[] prevRow = new int[col];

        for(int r=0; r<row; r++) {
            int[] currRow = new int[col];
            for(int c=0; c<col; c++) {
                if(r == 0 && c == 0) {
                    currRow[c] = grid[0][0];
                    continue;
                }

                int upSum = Integer.MAX_VALUE;
                if(r-1 >= 0) {
                    upSum = grid[r][c] + prevRow[c];
                }

                int leftSum = Integer.MAX_VALUE;
                if(c-1 >= 0) {
                    leftSum = grid[r][c] + currRow[c-1];
                }

                currRow[c] = Math.min(upSum, leftSum);
            }

            prevRow = currRow;
        }

        return prevRow[col-1];
    }
}
