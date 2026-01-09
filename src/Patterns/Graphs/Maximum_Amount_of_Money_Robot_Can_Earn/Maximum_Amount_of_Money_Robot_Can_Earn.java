package Patterns.Graphs.Maximum_Amount_of_Money_Robot_Can_Earn;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Maximum_Amount_of_Money_Robot_Can_Earn extends ProblemSolver {

    public static void main(String[] args) {
        new Maximum_Amount_of_Money_Robot_Can_Earn().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] coins = DataConvertor.to2DIntArray(args[0]);

        int res1 = maximumAmount_memo(coins);
        int res2 = maximumAmount_tabu(coins);
        System.out.println(res1 + " " + res2);
    }

    public int maximumAmount_memo(int[][] coins) {
        int k = 2;

        int row = coins.length;
        int col = coins[0].length;

        int[][][] dp = new int[row][col][k+1];
        for (int r=0; r<row; r++) {
            for (int c=0; c<col; c++) {
                Arrays.fill(dp[r][c], (int) -1e9);
            }
        }

        return helper(0, 0, k, coins, dp);
    }

    private int helper(int r, int c, int k, int[][] coins, int[][][] dp) {
        int row = coins.length;
        int col = coins[0].length;

        if (r == row-1 && c == col-1) {
            if (coins[r][c] < 0 && k > 0) return 0;
            return coins[r][c];
        }

        if(r >= row || c >= col) {
            return (int) -1e9;
        }

        if(dp[r][c][k] != (int) -1e9) {
            return dp[r][c][k];
        }

        // Option A: We simply PAY the cost (or gain coin) at current cell
        // We keep 'k' abilities for the future
        int moveRightWithoutDecrementingK = helper(r+1, c, k, coins, dp);
        int moveDownWithoutDecrementingK = helper(r, c+1, k, coins, dp);
        int maxAnsWithoutDecrementingK = coins[r][c] + Math.max(moveRightWithoutDecrementingK, moveDownWithoutDecrementingK);


        // Option B: We NEUTRALIZE current cell (Only if negative and we have k)
        int maxAnsDecrementingK = (int) -1e9;
        if(coins[r][c] < 0 && k > 0) {
            int moveRightDecrementingK = helper(r+1, c, k-1, coins, dp);
            int moveDownDecrementingK = helper(r, c+1, k-1, coins, dp);
            maxAnsDecrementingK = 0 + Math.max(moveRightDecrementingK, moveDownDecrementingK);
        }

        dp[r][c][k] = Math.max(maxAnsWithoutDecrementingK, maxAnsDecrementingK);
        return dp[r][c][k];
    }

    private int maximumAmount_tabu(int[][] coins) {
        int k = 2;

        int row = coins.length;
        int col = coins[0].length;

        int[][][] dp = new int[row+1][col+1][k+1];

        for(int k1=0; k1<=k; k1++) {
            if(coins[row-1][col-1] < 0 && k1 > 0) dp[row-1][col-1][k1] = 0;
            else dp[row-1][col-1][k1] = coins[row-1][col-1];

            for(int c=0; c<=col; c++) {
                dp[row][c][k1] = (int) -1e9;
            }

            for(int r=0; r<=row; r++) {
                dp[r][col][k1] = (int) -1e9;
            }
        }

        for(int r=row-1; r>=0; r--) {
            for(int c=col-1; c>=0; c--) {
                for(int k1=1; k1>=k; k1++) {
                    int moveRightWithoutDecrementingK = dp[r+1][c][k1];
                    int moveDownWithoutDecrementingK = dp[r][c+1][k1];
                    int maxAnsWithoutDecrementingK = coins[r][c] + Math.max(moveRightWithoutDecrementingK, moveDownWithoutDecrementingK);


                    // Option B: We NEUTRALIZE current cell (Only if negative and we have k)
                    int maxAnsDecrementingK = (int) -1e9;
                    if(coins[r][c] < 0 && k1 > 0) {
                        int moveRightDecrementingK = dp[r+1][c][k1-1];
                        int moveDownDecrementingK = dp[r][c+1][k1-1];
                        maxAnsDecrementingK = 0 + Math.max(moveRightDecrementingK, moveDownDecrementingK);
                    }

                    dp[r][c][k1] = Math.max(maxAnsWithoutDecrementingK, maxAnsDecrementingK);
                }
            }
        }

        int ans = (int) -1e9;
        for(int k1=0; k1<=k; k1++) {
            ans = Math.max(ans, dp[0][0][k1]);
        }

        return ans;
    }
}
