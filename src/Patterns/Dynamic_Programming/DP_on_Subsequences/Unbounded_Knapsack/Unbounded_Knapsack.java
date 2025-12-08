package Patterns.Dynamic_Programming.DP_on_Subsequences.Unbounded_Knapsack;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

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
        System.out.println(res1 + " " + res2 + " " + res3);
    }

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
}
