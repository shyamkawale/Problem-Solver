package Patterns.Dynamic_Programming.DP_on_Subsequences.Knapsack_0_1;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

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

    private int knapsack01_tabu_op1(int n, int[] weight, int[] value, int bagWeight) {
        int[] prev = new int[bagWeight+1];
        int[] curr = new int[bagWeight+1];

        // for the first element initialize for all weights
        for(int wt=0; wt<=bagWeight; wt++) {
            prev[wt] = weight[0] <= wt ? value[0] : 0;
        }

        for(int i=1; i<n; i++) {
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

        return curr[bagWeight];
    }

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
