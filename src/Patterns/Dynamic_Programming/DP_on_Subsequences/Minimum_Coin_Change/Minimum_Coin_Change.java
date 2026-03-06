package Patterns.Dynamic_Programming.DP_on_Subsequences.Minimum_Coin_Change;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
 * Minimum Coin Change (Coin Change I)
 * https://leetcode.com/problems/coin-change/
 * 
 * Given an array of coin denominations and a target amount,
 * return the minimum number of coins needed to make up that amount.
 * If it's not possible, return -1.
 * Each coin can be used UNLIMITED times (unbounded knapsack variant).
 * 
 * Example:
 * coins = [1, 2, 5], amount = 11
 * Output: 3 (5 + 5 + 1 = 11)
 * 
 * coins = [2], amount = 3
 * Output: -1 (impossible)
 */
public class Minimum_Coin_Change extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Coin_Change().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] coins = DataConvertor.toIntArray(args[0]);
        int amount = DataConvertor.toInt(args[1]);

        int res1 = coinChange_rec(coins, amount);
        int res2 = coinChange_memo(coins, amount);
        int res3 = coinChange_tabu(coins, amount);
        int res4 = coinChange_tabu_op(coins, amount);
        int res5 = coinChange_tabu_op2(coins, amount);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5);
    }

    private static int INF = (int)1e9;

    // Pure Recursion: Try pick/not-pick for each coin
    // On pick: stay at same index (unlimited coins) | On not-pick: move to n-1
    // TC: O(2^(n*amount)) - exponential
    // SC: O(n + amount) - recursion stack depth
    public int coinChange_rec(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int res = helper1(len-1, amount, coins);
        if(res == INF) return -1;
        return res;
    }

    private int helper1(int n, int amount, int[] coins) {
        if(amount == 0) {
            return 0;
        }

        if(n == 0) {
            if(amount%coins[0] != 0) return INF;
            else return amount/coins[0];
        }

        int notPick = helper1(n-1, amount, coins);
        int pick = INF;
        if(amount-coins[n] >= 0) {
            pick = 1 + helper1(n, amount-coins[n], coins);
        }

        return Math.min(notPick, pick);
    }

    // Memoization (Top-Down DP): Cache results for (index, remainingAmount) pairs
    // TC: O(n * amount) - each state computed once
    // SC: O(n * amount) for dp array + O(n + amount) recursion stack
    public int coinChange_memo(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int[][] dp = new int[len][amount+1];
        for(int i=0; i<len; i++) {
            Arrays.fill(dp[i], -1);
        }
        int res = helper2(len-1, amount, coins, dp);
        if(res == INF) return -1;
        return res;
    }

    private int helper2(int n, int amount, int[] coins, int[][] dp) {
        if(amount == 0) {
            return 0;
        }

        if(n == 0) {
            if(amount%coins[0] != 0) return INF;
            else return amount/coins[0];
        }

        if(dp[n][amount] != -1) {
            return dp[n][amount];
        }

        int notPick = helper2(n-1, amount, coins, dp);
        int pick = INF;
        if(amount-coins[n] >= 0) {
            pick = 1 + helper2(n, amount-coins[n], coins, dp);
        }

        dp[n][amount] = Math.min(notPick, pick);
        return dp[n][amount];
    }

    // Tabulation (Bottom-Up DP): Fill dp table iteratively
    // TC: O(n * amount) - iterate through all states
    // SC: O(n * amount) - 2D dp array
    private int coinChange_tabu(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int[][] dp = new int[len][amount+1];
        // not required.
        for(int n=0; n<len; n++) {
            dp[n][0] = 0;
        }

        for(int amt=0; amt<=amount; amt++) {
            dp[0][amt] = amt%coins[0] == 0 ? amt/coins[0] : INF;
        }

        for(int n=1; n<len; n++) {
            for(int amt=1; amt<=amount; amt++) {
                int notPick = dp[n-1][amt];
                int pick = INF;
                if(amt-coins[n] >= 0) {
                    pick = 1 + dp[n][amt-coins[n]];
                }

                dp[n][amt] = Math.min(notPick, pick);
            }
        }

        int ans = dp[len-1][amount];
        if(ans >= INF) return -1;
        return ans;
    }

    // Space Optimized (Two Arrays): Only keep previous row
    // TC: O(n * amount) - iterate through all states
    // SC: O(amount) - two 1D arrays
    private int coinChange_tabu_op(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int[] prev = new int[amount+1];

        // Base case: using only first coin
        for(int amt=0; amt<=amount; amt++) {
            prev[amt] = amt%coins[0] == 0 ? amt/coins[0] : INF;
        }

        for(int n=1; n<len; n++) {
            int[] curr = new int[amount+1];
            curr[0] = 0; // 0 coins needed for amount 0
            for(int amt=1; amt<=amount; amt++) {
                int notPick = prev[amt];
                int pick = INF;
                if(amt-coins[n] >= 0) {
                    pick = 1 + curr[amt-coins[n]]; // Same row (current coin can be picked again)
                }

                curr[amt] = Math.min(notPick, pick);
            }
            prev = curr;
        }

        int ans = prev[amount]; // Return prev (handles len=1 edge case)
        if(ans >= INF) return -1;
        return ans;
    }

    // Single Array Optimization: Traverse LEFT to RIGHT (unbounded - can pick same coin multiple times)
    // TC: O(n * amount) - iterate through all states
    // SC: O(amount) - single 1D array
    // Key insight: pick uses dp[amt-coins[n]] from SAME row, so going LEFT→RIGHT uses updated values
    private int coinChange_tabu_op2(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int[] dp = new int[amount+1];

        // Base case: using only first coin
        for(int amt=0; amt<=amount; amt++) {
            dp[amt] = amt%coins[0] == 0 ? amt/coins[0] : INF;
        }

        for(int n=1; n<len; n++) {
            // LEFT to RIGHT: uses updated dp[amt-coins[n]] for unlimited picks
            for(int amt=1; amt<=amount; amt++) {
                int notPick = dp[amt]; // From previous iteration (before update)
                int pick = INF;
                if(amt-coins[n] >= 0) {
                    pick = 1 + dp[amt-coins[n]]; // Uses current iteration's updated value!
                }

                dp[amt] = Math.min(notPick, pick);
            }
        }

        int ans = dp[amount];
        if(ans >= INF) return -1;
        return ans;
    }
}
