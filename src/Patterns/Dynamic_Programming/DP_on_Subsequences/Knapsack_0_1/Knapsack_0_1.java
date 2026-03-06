package Patterns.Dynamic_Programming.DP_on_Subsequences.Knapsack_0_1;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
 * 0/1 Knapsack Problem
 * https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1
 * 
 * Given N items where each item has a weight and a value, and a knapsack with capacity W.
 * Select items to maximize total value without exceeding knapsack capacity.
 * Each item can be picked at most once (0/1 choice).
 * 
 * Example:
 * weights = [1, 2, 3], values = [10, 15, 40], capacity = 6
 * Pick items with weights [1, 2, 3] → total weight = 6, total value = 65
 */
public class Knapsack_0_1 extends ProblemSolver {

    public static void main(String[] args) {
        new Knapsack_0_1().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[] weight = DataConvertor.toIntArray(args[1]);
        int[] value = DataConvertor.toIntArray(args[2]);
        int bagWeight = DataConvertor.toInt(args[3]);

        int res1 = knapsack01_rec(n, weight, value, bagWeight);
        int res2 = knapsack01_memo(n, weight, value, bagWeight);
        int res3 = knapsack01_tabu(n, weight, value, bagWeight);
        int res4 = knapsack01_tabu_op1(n, weight, value, bagWeight);
        int res5 = knapsack01_tabu_op2(n, weight, value, bagWeight);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5);
    }

    // Pure Recursion: Try pick/not-pick for each item from last to first
    // TC: O(2^n) - exponential, explores all subsets
    // SC: O(n) - recursion stack depth
    private int knapsack01_rec(int n, int[] weight, int[] value, int bagWeight) {
        return helper1(n-1, weight, value, bagWeight);
    }

    private int helper1(int n, int[] weight, int[] value, int bagWeight) {
        if(bagWeight == 0) {
            return 0;
        }

        if(n == 0) {
            return bagWeight >= weight[0] ? value[0] : 0;
        }

        int notPick = helper1(n-1, weight, value, bagWeight);
        int pick = 0;
        if(bagWeight-weight[n] >= 0) {
            pick = value[n] + helper1(n-1, weight, value, bagWeight-weight[n]);
        }

        return Math.max(notPick, pick);
    }

    // Memoization (Top-Down DP): Cache results for (index, remainingCapacity) pairs
    // TC: O(n * W) - each state computed once
    // SC: O(n * W) for dp array + O(n) recursion stack
    private int knapsack01_memo(int n, int[] weight, int[] value, int bagWeight) {
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
            return bagWeight >= weight[0] ? value[0] : 0;
        }

        if(dp[n][bagWeight] != -1) {
            return dp[n][bagWeight];
        }

        int notPick = helper2(n-1, weight, value, bagWeight, dp);
        int pick = 0;
        if(bagWeight-weight[n] >= 0) {
            pick = value[n] + helper2(n-1, weight, value, bagWeight-weight[n], dp);
        }

        dp[n][bagWeight] = Math.max(notPick, pick);
        return dp[n][bagWeight];
    }

    // Tabulation (Bottom-Up DP): Fill dp table iteratively
    // TC: O(n * W) - iterate through all states
    // SC: O(n * W) - 2D dp array
    private int knapsack01_tabu(int n, int[] weight, int[] value, int bagWeight) {
        int[][] dp = new int[n][bagWeight+1];

        // not needed
        // for all elements when remaining bagWt becomes 0;
        for(int i=0; i<n; i++) {
            dp[i][0] = weight[i] == 0 ? value[i] : 0;
        }

        // for the first element initialize for all weights
        for(int wt=0; wt<=bagWeight; wt++) {
            dp[0][wt] = weight[0] <= wt ? value[0] : 0;
        }

        for(int i=1; i<n; i++) {
            for(int wt=1; wt<=bagWeight; wt++) {
                int notPick = dp[i-1][wt];
                int pick = 0;
                if(wt-weight[i] >= 0) {
                    pick = value[i] + dp[i-1][wt-weight[i]];
                }

                dp[i][wt] = Math.max(notPick, pick);
            }
        }

        return dp[n-1][bagWeight];
    }

    // Space Optimized (Two Arrays): Only keep previous row since dp[i] depends only on dp[i-1]
    // TC: O(n * W) - iterate through all states
    // SC: O(W) - two 1D arrays
    private int knapsack01_tabu_op1(int n, int[] weight, int[] value, int bagWeight) {
        int[] prev = new int[bagWeight+1];

        // for the first element initialize for all weights
        for(int wt=0; wt<=bagWeight; wt++) {
            prev[wt] = weight[0] <= wt ? value[0] : 0;
        }

        for(int i=1; i<n; i++) {
            int[] curr = new int[bagWeight+1]; // Create new array each iteration to avoid aliasing
            for(int wt=0; wt<=bagWeight; wt++) {
                int notPick = prev[wt];
                int pick = 0;
                if(wt-weight[i] >= 0) {
                    pick = value[i] + prev[wt-weight[i]];
                }

                curr[wt] = Math.max(notPick, pick);
            }
            prev = curr;
        }

        return prev[bagWeight]; // Return prev (handles n=1 edge case)
    }

    // Single Array Optimization: Traverse RIGHT to LEFT to avoid using updated values
    // TC: O(n * W) - iterate through all states
    // SC: O(W) - single 1D array
    // Key insight: dp[i][wt] depends on dp[i-1][wt] and dp[i-1][wt - weight[i]] (both from prev row, left side)
    // By going right to left, we ensure we read prev row values before overwriting them
    private int knapsack01_tabu_op2(int n, int[] weight, int[] value, int bagWeight) {
        int[] curr = new int[bagWeight+1];

        // for the first element initialize for all weights
        for(int wt=0; wt<=bagWeight; wt++) {
            curr[wt] = weight[0] <= wt ? value[0] : 0;
        }

        for(int i=1; i<n; i++) {
            // this is very important(only works when we move wt from RIGHT -> LEFT... 
            // becoz dp[i][wt] depends on dp[i-1][wt] and dp[i-1][less than wt].. 
            // hence we will start computing dp[i][wt] where wt starting from wt and moving to left)
            for(int wt=bagWeight; wt>=0; wt--) {
                int notPick = curr[wt];
                int pick = 0;
                if(wt-weight[i] >= 0) {
                    pick = value[i] + curr[wt-weight[i]];
                }

                curr[wt] = Math.max(notPick, pick);
            }
        }

        return curr[bagWeight];
    }
}
