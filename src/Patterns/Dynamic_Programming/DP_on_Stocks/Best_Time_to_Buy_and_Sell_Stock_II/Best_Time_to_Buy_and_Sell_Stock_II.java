package Patterns.Dynamic_Programming.DP_on_Stocks.Best_Time_to_Buy_and_Sell_Stock_II;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Best_Time_to_Buy_and_Sell_Stock_II extends ProblemSolver {
    public static void main(String[] args) {
        new Best_Time_to_Buy_and_Sell_Stock_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] prices = DataConvertor.toIntArray(args[0]);

        int res1 = maxProfit_rec(prices);
        int res2 = maxProfit_memo(prices);
        int res3 = maxProfit_tabu(prices);
        int res4 = maxProfit_tabuop(prices);
        int res5 = maxProfit_greedy(prices);
        System.out.println(res1 + " " + res2 + " " +res3 + " " + res4 + " " + res5);
    }

    public int maxProfit_rec(int[] prices) {
        int n = prices.length;
        return helper1(0, 1, prices);
    }

    public int helper1(int n, int canBuy, int[] prices) {
        if(n >= prices.length) {
            return 0;
        }

        int profit = 0;

        if(canBuy == 1) {
            // no-transaction
            int noTran = 0 + helper1(n+1, 1, prices);
            // buy
            int buy = -prices[n] + helper1(n+1, 0, prices);

            profit = Math.max(noTran, buy);
        } 
        else {
            // no-transaction
            int noTran = 0 + helper1(n+1, 0, prices);
            // sell
            int sell = prices[n] + helper1(n+1, 1, prices);

            profit = Math.max(noTran, sell);
        }

        return profit;
    }

    public int maxProfit_memo(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for(int i=0; i<n; i++) {
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
            // no-transaction
            int noTran = 0 + helper2(n+1, 1, prices, dp);
            // buy
            int buy = -prices[n] + helper2(n+1, 0, prices, dp);

            profit = Math.max(noTran, buy);
        } 
        else {
            // no-transaction
            int noTran = 0 + helper2(n+1, 0, prices, dp);
            // sell
            int sell = prices[n] + helper2(n+1, 1, prices, dp);

            profit = Math.max(noTran, sell);
        }

        dp[n][canBuy] = profit;
        return dp[n][canBuy];
    }

    private int maxProfit_tabu(int[] prices) {
        int len = prices.length;
        int[][] dp = new int[len+1][2];

        dp[len][0] = 0;
        dp[len][1] = 0;

        for(int n=len-1; n>=0; n--) {
            for(int canBuy=0; canBuy<=1; canBuy++) {
                int profit = 0;

                if(canBuy == 1) {
                    // no-transaction
                    int noTran = 0 + dp[n+1][1];
                    // buy
                    int buy = -prices[n] + dp[n+1][0];

                    profit = Math.max(noTran, buy);
                } 
                else {
                    // no-transaction
                    int noTran = 0 + dp[n+1][0];
                    // sell
                    int sell = prices[n] + dp[n+1][1];

                    profit = Math.max(noTran, sell);
                }

                dp[n][canBuy] = profit;
            }
        }

        return dp[0][1];
    }

    private int maxProfit_tabuop(int[] prices) {
        int len = prices.length;
        int[] next = new int[2];

        next[0] = 0;
        next[1] = 0;

        for(int n=len-1; n>=0; n--) {
            int[] curr = new int[2];
            for(int canBuy=0; canBuy<=1; canBuy++) {
                int profit = 0;

                if(canBuy == 1) {
                    // no-transaction
                    int noTran = 0 + next[1];
                    // buy
                    int buy = -prices[n] + next[0];

                    profit = Math.max(noTran, buy);
                } 
                else {
                    // no-transaction
                    int noTran = 0 + next[0];
                    // sell
                    int sell = prices[n] + next[1];

                    profit = Math.max(noTran, sell);
                }

                curr[canBuy] = profit;
            }

            next = curr;
        }

        return next[1];
    }

    private int maxProfit_greedy(int[] prices) {
        int profit = 0;
        for (int i=1; i<prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                profit = profit + (prices[i] - prices[i - 1]);
            }
        }
        return profit;
    }
}
