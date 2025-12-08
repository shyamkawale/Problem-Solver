package Patterns.Dynamic_Programming.DP_on_Stocks.Best_Time_to_Buy_and_Sell_Stock_with_Cooldown;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Best_Time_to_Buy_and_Sell_Stock_with_Cooldown extends ProblemSolver {

    public static void main(String[] args) {
        new Best_Time_to_Buy_and_Sell_Stock_with_Cooldown().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] prices = DataConvertor.toIntArray(args[0]);

        int res1 = maxProfit_rec(prices);
        int res2 = maxProfit_memo(prices);
        int res3 = maxProfit_tabu(prices);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    public int maxProfit_rec(int[] prices) {
        return helper1(0, 1, prices);
    }

    public int helper1(int n, int canBuy, int[] prices) {
        if(n >= prices.length) {
            return 0;
        }

        int profit = 0;
        if(canBuy == 1) {
            int noTran = helper1(n+1, 1, prices);
            int buy = -prices[n] + helper1(n+1, 0, prices);
            profit = Math.max(noTran, buy);
        }
        else {
            int noTran = helper1(n+1, 0, prices);
            int sell = prices[n] + helper1(n+2, 1, prices);
            profit = Math.max(noTran, sell);
        }

        return profit;
    }

    public int maxProfit_memo(int[] prices) {
        int len = prices.length;

        int[][] dp = new int[len][2];
        for(int i=0; i<len; i++) {
            Arrays.fill(dp[i], -1);
        }
        return helper2(0, 1, prices, dp);
    }

    public int helper2(int n, int canBuy, int[] prices, int[][] dp) {
        if(n >= prices.length) {
            return 0;
        }

        if(dp[n][canBuy] != -1) {
            return dp[n][canBuy];
        }

        int profit = 0;
        if(canBuy == 1) {
            int noTran = helper2(n+1, 1, prices, dp);
            int buy = -prices[n] + helper2(n+1, 0, prices, dp);
            profit = Math.max(noTran, buy);
        }
        else {
            int noTran = helper2(n+1, 0, prices, dp);
            int sell = prices[n] + helper2(n+2, 1, prices, dp);
            profit = Math.max(noTran, sell);
        }

        dp[n][canBuy] = profit;
        return dp[n][canBuy];
    }

    private int maxProfit_tabu(int[] prices) {
        int len = prices.length;

        int[][] dp = new int[len+2][2];

        for(int n=len-1; n>=0; n--) {
            for(int canBuy=0; canBuy<=1; canBuy++) {
                int profit = 0;
                if(canBuy == 1) {
                    int noTran = dp[n+1][1];
                    int buy = -prices[n] + dp[n+1][0];
                    profit = Math.max(noTran, buy);
                }
                else {
                    int noTran = dp[n+1][0];
                    int sell = prices[n] + dp[n+2][1];
                    profit = Math.max(noTran, sell);
                }

                dp[n][canBuy] = profit;
            }
        }
        
        return dp[0][1];
    }
}
