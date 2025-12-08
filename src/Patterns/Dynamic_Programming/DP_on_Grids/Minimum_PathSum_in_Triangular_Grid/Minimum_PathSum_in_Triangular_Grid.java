package Patterns.Dynamic_Programming.DP_on_Grids.Minimum_PathSum_in_Triangular_Grid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Minimum_PathSum_in_Triangular_Grid extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_PathSum_in_Triangular_Grid().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] triangleGrid = DataConvertor.to2DIntArray(args[0]);
        List<List<Integer>> triangle = Arrays.stream(triangleGrid)
            .map((int[] row) -> IntStream.of(row).boxed().toList())
            .toList();
        int res = minimumTotal(triangle);
        System.out.println(res);
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[][] dp = new int[n][n];
        for(int i=0; i<n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        // we have started from (0, 0) as there is no fixed end cell.. but start cell is fixed...
        return helper(0, 0, triangle, dp);
    }

    public int helper(int r, int c, List<List<Integer>> triangle, int[][] dp) {
        int n = triangle.size();

        if(r == n-1) {
            return triangle.get(r).get(c);
        }

        if(dp[r][c] != Integer.MAX_VALUE) {
            return dp[r][c];
        }

        int downSum = triangle.get(r).get(c) + helper(r+1, c, triangle, dp);
        int rightSum = triangle.get(r).get(c) + helper(r+1, c+1, triangle, dp);

        dp[r][c] = Math.min(downSum, rightSum);
        return dp[r][c];
    }

}
