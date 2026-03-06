package Patterns.Dynamic_Programming.DP_on_Grids.Unique_Paths;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/unique-paths/

There is a robot on an m x n grid. 
The robot is initially located at the top-left corner (i.e., grid[0][0]). 
The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). 
The robot can only move either down or right at any point in time.

Given the two integers m and n, 
return the number of possible unique paths that the robot can take to reach the bottom-right corner.
*/
public class Unique_Paths extends ProblemSolver {

    public static void main(String[] args) {
        new Unique_Paths().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int row = DataConvertor.toInt(args[0]);
        int col = DataConvertor.toInt(args[1]);

        int res1 = uniquePaths_BFS(row, col);
        int res2 = uniquePaths_rec1(row, col);
        int res3 = uniquePaths_rec2(row, col);
        int res4 = uniquePaths_memo(row, col);
        int res5 = uniquePaths_tabu(row, col);
        int res6 = uniquePaths_tabuop(row, col);
        int res7 = uniquePaths_combinatorics(row, col);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " +res5 + " " + res6 + " " + res7);
    }

    // BFS: Count paths by propagating counts from source to destination
    // TC: O(m * n) - each cell visited once
    // SC: O(m * n) - count array + queue
    public int uniquePaths_BFS(int row, int col) {
        int[][] dir = {{0, 1}, {1, 0}};
        int[][] count = new int[row][col];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        count[0][0] = 1;

        while(!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int r = polledNode[0];
            int c = polledNode[1];

            for(int i=0; i<dir.length; i++) {
                int nr = r + dir[i][0];
                int nc = c + dir[i][1];

                if(isValid(nr, row) && isValid(nc, col)) {
                    if(count[nr][nc] == 0) {
                        queue.offer(new int[]{nr, nc});
                    }
                    count[nr][nc] = count[nr][nc] + count[r][c];
                }
            }
        }
        return count[row-1][col-1];
    }

    public boolean isValid(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

    // DFS Recursion: Explore all paths using global counter
    // TC: O(2^(m+n)) - exponential, explores all possible paths
    // SC: O(m + n) - recursion stack depth
    int count = 0;
    private int uniquePaths_rec1(int row, int col) {
        dfs(0, 0, row, col);
        return count;
    }

    private void dfs(int r, int c, int row, int col) {
        int[][] dir = {{0, 1}, {1, 0}};

        if(r == row-1 && c == col-1) {
            count++;
            return;
        }

        for(int i=0; i<dir.length; i++) {
            int nr = r + dir[i][0];
            int nc = c + dir[i][1];

            if(isValid(nr, row) && isValid(nc, col)) {
                dfs(nr, nc, row, col);
            }
        }
    }

    // Pure Recursion: Count paths by going backward from destination to source
    // TC: O(2^(m+n)) - exponential, overlapping subproblems
    // SC: O(m + n) - recursion stack depth
    private int uniquePaths_rec2(int row, int col) {
        return helper1(row-1, col-1);
    }

    private int helper1(int r, int c) {
        if(r == 0 && c == 0) {
            return 1;
        }

        if(r < 0 || c < 0) {
            return 0;
        }

        int leftCnt = helper1(r, c-1);
        int upCnt = helper1(r-1, c);

        return leftCnt + upCnt;
    }

    // Memoization (Top-Down DP): Cache computed subproblems
    // TC: O(m * n) - each cell computed once
    // SC: O(m * n) - dp array + O(m + n) recursion stack
    private int uniquePaths_memo(int row, int col) {
        int[][] dp = new int[row][col];
        for(int i=0; i<dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        return helper2(row-1, col-1, dp);
    }

    private int helper2(int r, int c, int[][] dp) {
        if(r == 0 && c == 0) {
            return 1;
        }

        if(r < 0 || c < 0) {
            return 0;
        }

        if(dp[r][c] != -1) {
            return dp[r][c];
        }

        int leftCnt = helper2(r, c-1, dp);
        int upCnt = helper2(r-1, c, dp);

        dp[r][c] = leftCnt + upCnt;
        return dp[r][c];
    }

    // Tabulation (Bottom-Up DP): Iteratively fill dp table
    // TC: O(m * n) - iterate through all cells
    // SC: O(m * n) - dp array
    private int uniquePaths_tabu(int row, int col) {
        int[][] dp = new int[row][col];
        dp[0][0] = 1;

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(r == 0 && c == 0) continue;

                int leftCnt = 0;
                if(r >= 0 && c-1 >= 0) {
                    leftCnt = dp[r][c-1];
                }

                int upCnt = 0;
                if(r-1 >= 0 && c >= 0) {
                    upCnt = dp[r-1][c];
                }

                dp[r][c] = leftCnt + upCnt;
            }
        }

        return dp[row-1][col-1];
    }

    // Space Optimized Tabulation: Only keep previous row
    // TC: O(m * n) - iterate through all cells
    // SC: O(n) - only two 1D arrays needed
    private int uniquePaths_tabuop(int row, int col) {
        int[] prevRow = new int[col];

        for(int r=0; r<row; r++) {
            int[] currRow = new int[col];
            for(int c=0; c<col; c++) {
                if(r == 0 && c == 0) {
                    currRow[c] = 1;
                    continue;
                }

                int leftCnt = 0;
                if(r >= 0 && c-1 >= 0) {
                    leftCnt = currRow[c-1];
                }

                int upCnt = 0;
                if(r-1 >= 0 && c >= 0) {
                    upCnt = prevRow[c];
                }

                currRow[c] = leftCnt + upCnt;
            }

            prevRow = currRow;
        }

        return prevRow[col-1];
    }

    // Combinatorics: Total paths = C(m+n-2, m-1) = (m+n-2)! / ((m-1)! * (n-1)!)
    // TC: O(min(m, n)) - single loop for combination calculation
    // SC: O(1) - constant space
    private int uniquePaths_combinatorics(int row, int col) {
        int rightSteps = col-1;
        int downSteps = row-1;

        int n = rightSteps + downSteps;
        int r = rightSteps; // or downSteps - anything will give right answer.
        double res = 1;

        for(int i=r; i>0; i--) {
            res = res * (n-r+i)/i;
        }

        return (int)res;
    }
}
