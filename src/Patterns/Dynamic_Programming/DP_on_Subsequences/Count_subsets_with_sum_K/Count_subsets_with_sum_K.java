package Patterns.Dynamic_Programming.DP_on_Subsequences.Count_subsets_with_sum_K;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Count_subsets_with_sum_K extends ProblemSolver {

    public static void main(String[] args) {
        new Count_subsets_with_sum_K().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        int res1 = cntSubsets_rec(nums, target);
        int res2 = cntSubsets_memo(nums, target);
        int res3 = cntSubsets_tabu(nums, target);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    private int cntSubsets_rec(int[] nums, int target) {
        int len = nums.length;
        return helper1(len-1, target, nums);
    }

    private int helper1(int n, int target, int[] nums) {
        if(target == 0) {
            return 1;
        }

        if(n==0) {
            return nums[0] == target ? 1 : 0;
        }

        int notPick = helper1(n-1, target, nums);
        int pick = 0;
        if(target-nums[n] >= 0) {
            pick = helper1(n-1, target-nums[n], nums);
        }

        return notPick + pick;
    }

    private int cntSubsets_memo(int[] nums, int target) {
        int len = nums.length;
        int[][] dp = new int[len][target+1];
        for(int i=0; i<len; i++) {
            Arrays.fill(dp[i], -1);
        }
        return helper2(len-1, target, nums, dp);
    }

    private int helper2(int n, int target, int[] nums, int[][] dp) {
        if(target == 0) {
            return 1;
        }

        if(n==0) {
            return nums[0] == target ? 1 : 0;
        }

        if(dp[n][target] != -1) {
            return dp[n][target];
        }

        int notPick = helper2(n-1, target, nums, dp);
        int pick = 0;
        if(target-nums[n] >= 0) {
            pick = helper2(n-1, target-nums[n], nums, dp);
        }

        if(dp[n][target] == -1) {
            dp[n][target] = 0;
        }
        dp[n][target] = dp[n][target] + notPick + pick;
        return dp[n][target];
    }

    private int cntSubsets_tabu(int[] nums, int target) {
        int len = nums.length;
        int[][] dp = new int[len][target+1];
        for(int i=0; i<len; i++) {
            dp[i][0] = 1;
        }

        dp[0][nums[0]] = 1;

        for(int n=1; n<len; n++) {
            for(int t=1; t<=target; t++) {
                int notPick = dp[n-1][t];
                int pick = 0;
                if(t-nums[n] >= 0) {
                    pick = dp[n-1][t-nums[n]];
                }

                dp[n][t] = notPick + pick;
            }
        }

        return dp[len-1][target];
    }
}
