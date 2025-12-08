package Patterns.Dynamic_Programming.DP_on_Grids.Unique_Paths_with_Obstacles;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Unique_Paths_with_Obstacles extends ProblemSolver {

    public static void main(String[] args) {
        new Unique_Paths_with_Obstacles().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] obstacleGrid = DataConvertor.to2DIntArray(args[0]);

        int res = uniquePathsWithObstacles(obstacleGrid);
        System.out.println(res);
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        if(obstacleGrid[0][0] == 1 || obstacleGrid[row-1][col-1] == 1) {
            return 0;
        }

        int[][] dp = new int[row][col];
        dp[0][0] = 1;

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(r == 0 && c == 0) continue;
                if(obstacleGrid[r][c] == 1) continue;

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
}
