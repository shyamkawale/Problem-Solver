package Patterns.Dynamic_Programming.DP_on_Subsequences.Unbounded_Knapsack;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
 * Unbounded Knapsack Problem
 * https://www.geeksforgeeks.org/problems/knapsack-with-duplicate-items4201/1
 * 
 * Given N items where each item has a weight and a value, and a knapsack with capacity W.
 * Select items to maximize total value without exceeding knapsack capacity.
 * Each item can be picked UNLIMITED times (unlike 0/1 knapsack).
 * 
 * Example:
 * weights = [2, 4, 6], values = [5, 11, 13], capacity = 10
 * Pick item with weight 4 twice + weight 2 once → total weight = 10, total value = 11 + 11 + 5 = 27
 * 
 * Key difference from 0/1 Knapsack:
 * - 0/1: After picking item at index i, move to i-1 (can't pick i again)
 * - Unbounded: After picking item at index i, stay at i (can pick again)
 */
public class Unbounded_Knapsack extends ProblemSolver {

    public static void main(String[] args) {
        new Unbounded_Knapsack().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[] weight = DataConvertor.toIntArray(args[1]);
        int[] value = DataConvertor.toIntArray(args[2]);
        int bagWeight = DataConvertor.toInt(args[3]);

        int res1 = knapsackUnbounded_rec(n, weight, value, bagWeight);
        int res2 = knapsackUnbounded_memo(n, weight, value, bagWeight);
        int res3 = knapsackUnbounded_tabu(n, weight, value, bagWeight);
        int res4 = knapsackUnbounded_tabu_op(n, weight, value, bagWeight);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    // Pure Recursion: Try pick/not-pick for each item
    // On pick: stay at same index (can pick again) | On not-pick: move to i-1
    // TC: O(2^(n*W)) - exponential (much worse than 0/1 as we can stay at same index)
    // SC: O(n + W) - recursion stack depth
    private int knapsackUnbounded_rec(int n, int[] weight, int[] value, int bagWeight) {
        return helper1(n-1, weight, value, bagWeight);
    }

    private int helper1(int n, int[] weight, int[] value, int bagWeight) {
        if(bagWeight == 0) {
            return 0;
        }
        if(n==0) {
            return bagWeight%weight[n] == 0 ? value[n] * (bagWeight/weight[n]) : 0;
        }

        int notPick = helper1(n-1, weight, value, bagWeight);
        int pick = 0;
        if(bagWeight-weight[n] >= 0) {
            pick = value[n] + helper1(n, weight, value, bagWeight-weight[n]);
        }

        return Math.max(notPick, pick);
    }

    // Memoization (Top-Down DP): Cache results for (index, remainingCapacity) pairs
    // TC: O(n * W) - each state computed once
    // SC: O(n * W) for dp array + O(n + W) recursion stack
    private int knapsackUnbounded_memo(int n, int[] weight, int[] value, int bagWeight) {
        int[][] dp = new int[n][bagWeight+1];
        for(int i=0; i<n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return helper2(n-1, weight, value, bagWeight, dp);
    }

    private int helper2(int n, int[] weight, int[] value, int bagWeight, int[][] dp) {
        if(bagWeight == 0) {
            return 0;
        }

        if(n == 0) {
            return bagWeight%weight[n] == 0 ? value[n] * (bagWeight/weight[n]) : 0;
        }

        if(dp[n][bagWeight] != -1) {
            return dp[n][bagWeight];
        }

        int notPick = helper2(n-1, weight, value, bagWeight, dp);
        int pick = 0;
        if(bagWeight-weight[n] >= 0) {
            pick = value[n] + helper2(n, weight, value, bagWeight-weight[n], dp);
        }

        dp[n][bagWeight] = Math.max(notPick, pick);
        return dp[n][bagWeight];
    }

    // Tabulation (Bottom-Up DP): Fill dp table iteratively
    // TC: O(n * W) - iterate through all states
    // SC: O(n * W) - 2D dp array
    private int knapsackUnbounded_tabu(int n, int[] weight, int[] value, int bagWeight) {
        int[][] dp = new int[n][bagWeight+1];

        for(int i=0; i<n; i++) {
            dp[i][0] = 0;
        }
        for(int wt=0; wt<=bagWeight; wt++) {
            dp[0][wt] = wt%weight[0] == 0 ? value[0] * (wt/weight[0]) : 0;
        }

        for(int i=1; i<n; i++) {
            for(int wt=0; wt<=bagWeight; wt++) {
                int notPick = dp[i-1][wt];
                int pick = 0;
                if(wt-weight[i] >= 0) {
                    pick = value[i] + dp[i][wt-weight[i]];
                }

                dp[i][wt] = Math.max(notPick, pick);
            }
        }

        return dp[n-1][bagWeight];
    }

    // Single Array Optimization: Traverse LEFT to RIGHT (opposite of 0/1 knapsack!)
    // TC: O(n * W) - iterate through all states
    // SC: O(W) - single 1D array
    // Key insight:
    //   dp[i][wt] depends on dp[i-1][wt] (not-pick) and dp[i][wt-weight[i]] (pick - SAME row, left side)
    //   Going LEFT → RIGHT ensures we use current row's updated values for pick (allowing unlimited picks)
    //   This is OPPOSITE of 0/1 knapsack which goes RIGHT → LEFT!
    private int knapsackUnbounded_tabu_op(int n, int[] weight, int[] value, int bagWeight) {
        int[] dp = new int[bagWeight + 1];

        // Base case: first item - fill as many as possible
        for (int wt = 0; wt <= bagWeight; wt++) {
            dp[wt] = wt % weight[0] == 0 ? value[0] * (wt / weight[0]) : 0;
        }

        for (int i = 1; i < n; i++) {
            // LEFT to RIGHT: allows using updated dp[wt-weight[i]] (current row) for unlimited picks
            for (int wt = 0; wt <= bagWeight; wt++) {
                int notPick = dp[wt]; // from previous row (before update)
                int pick = 0;
                if (wt - weight[i] >= 0) {
                    pick = value[i] + dp[wt - weight[i]]; // uses current row's updated value!
                }
                dp[wt] = Math.max(notPick, pick);
            }
        }

        return dp[bagWeight];
    }
}
