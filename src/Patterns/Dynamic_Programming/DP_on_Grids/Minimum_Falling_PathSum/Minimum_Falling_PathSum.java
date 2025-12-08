package Patterns.Dynamic_Programming.DP_on_Grids.Minimum_Falling_PathSum;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Minimum_Falling_PathSum extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Falling_PathSum().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] matrix = DataConvertor.to2DIntArray(args[0]);

        int res = minFallingPathSum(matrix);
        System.out.println(res);
    }

    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        long minSum = Integer.MAX_VALUE;

        for(int c=0; c<n; c++) {
            long[][] dp = new long[n][n];
            for(int i=0; i<n; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE);
            }
            minSum = Math.min(minSum, helper(n-1, c, matrix, dp));
        }

        return (int) minSum;
    }

    public long helper(int r, int c, int[][] matrix, long[][] dp) {
        int n = matrix.length;
        if(!isValidIdx(c, n)) {
            return Integer.MAX_VALUE;
        }

        if(r == 0) {
            return matrix[r][c];
        }

        if(dp[r][c] != Integer.MAX_VALUE) {
            return dp[r][c];
        }

        long leftDiagSum = matrix[r][c] + helper(r-1, c-1, matrix, dp);
        long rightDiagSum = matrix[r][c] + helper(r-1, c+1, matrix, dp);
        long upSum = matrix[r][c] + helper(r-1, c, matrix, dp);

        dp[r][c] = Math.min(Math.min(leftDiagSum, rightDiagSum), upSum);
        return dp[r][c];
    }

    public boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }
}
