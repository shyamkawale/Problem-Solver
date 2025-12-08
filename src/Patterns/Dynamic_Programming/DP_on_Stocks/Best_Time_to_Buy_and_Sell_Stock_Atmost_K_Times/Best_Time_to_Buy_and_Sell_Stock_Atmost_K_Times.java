package Patterns.Dynamic_Programming.DP_on_Stocks.Best_Time_to_Buy_and_Sell_Stock_Atmost_K_Times;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Best_Time_to_Buy_and_Sell_Stock_Atmost_K_Times extends ProblemSolver {

    public static void main(String[] args) {
        new Best_Time_to_Buy_and_Sell_Stock_Atmost_K_Times().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int k = DataConvertor.toInt(args[0]);
        int[] prices = DataConvertor.toIntArray(args[1]);

        int res1 = maxProfit_rec(k, prices);
        int res2 = maxProfit_memo(k, prices);
        int res3 = maxProfit_tabu(k, prices);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    public int maxProfit_rec(int k, int[] prices) {
        return helper1(0, 1, k, prices);
    }

    private int helper1(int n, int canBuy, int k, int[] prices) {
        if(k <= 0) {
            return 0;
        }
        if(n >= prices.length) {
            return 0;
        }

        int profit = 0;

        if(canBuy == 1) {
            int noTran = helper1(n+1, 1, k, prices);
            int buy = -prices[n] + helper1(n+1, 0, k, prices);
            profit = Math.max(noTran, buy);
        }
        else {
            int noTran = helper1(n+1, 0, k, prices);
            int sell = prices[n] + helper1(n+1, 1, k-1, prices);
            profit = Math.max(noTran, sell);
        }

        return profit;
    }

    public int maxProfit_memo(int k, int[] prices) {
        int len = prices.length;
        int[][][] dp = new int[len][2][k];
        for(int i=0; i<len; i++) {
            for(int j=0; j<2; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return helper2(0, 1, k, prices, dp);
    }

    private int helper2(int n, int canBuy, int k, int[] prices, int[][][] dp) {
        if(k <= 0) {
            return 0;
        }
        if(n >= prices.length) {
            return 0;
        }

        if(dp[n][canBuy][k-1] != -1) {
            return dp[n][canBuy][k-1];
        }

        int profit = 0;

        if(canBuy == 1) {
            int noTran = helper2(n+1, 1, k, prices, dp);
            int buy = -prices[n] + helper2(n+1, 0, k, prices, dp);
            profit = Math.max(noTran, buy);
        }
        else {
            int noTran = helper2(n+1, 0, k, prices, dp);
            int sell = prices[n] + helper2(n+1, 1, k-1, prices, dp);
            profit = Math.max(noTran, sell);
        }

        dp[n][canBuy][k-1] = profit;
        return dp[n][canBuy][k-1];
    }

    private int maxProfit_tabu(int k, int[] prices) {
        int len = prices.length;
        int[][][] dp = new int[len+1][2][k+1];

        for(int n=len-1; n>=0; n--) {
            for(int canBuy=0; canBuy<=1; canBuy++) {
                for(int k1 = 1; k1<=k; k1++) {
                    int profit = 0;

                    if(canBuy == 1) {
                        int noTran = dp[n+1][1][k1];
                        int buy = -prices[n] + dp[n+1][0][k1];
                        profit = Math.max(noTran, buy);
                    }
                    else {
                        int noTran = dp[n+1][0][k1];
                        int sell = prices[n] + dp[n+1][1][k1-1];
                        profit = Math.max(noTran, sell);
                    }

                    dp[n][canBuy][k1] = profit;
                }
            }
        }
        
        return dp[0][1][k];
    }

}
