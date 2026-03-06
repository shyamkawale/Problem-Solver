package Patterns.Dynamic_Programming.DP_on_Subsequences.Subset_Sum_Equals_To_Target;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://www.naukri.com/code360/problems/subset-sum-equal-to-k_1550954

You are given an array/list ‘ARR’ of ‘N’ positive integers and an integer ‘K’.
Your task is to check if there exists a subset in ‘ARR’ with a sum equal to ‘K’.

Note: Return true if there exists a subset with sum equal to ‘K’. Otherwise, return false.

For Example :
If ‘ARR’ is {1,2,3,4} and ‘K’ = 4, then there exists 2 subsets with sum = 4. 
These are {1,3} and {4}. Hence, return true.
*/
public class Subset_Sum_Equals_To_Target extends ProblemSolver {

    public static void main(String[] args) {
        new Subset_Sum_Equals_To_Target().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        boolean res1 = isSubsetSum_rec(nums, target);
        boolean res2 = isSubsetSum_memo(nums, target);
        boolean res3 = isSubsetSum_tabu(nums, target);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    public boolean isSubsetSum_rec(int[] nums, int target) {
        int n = nums.length;
        return helper1(n-1, target, nums);
    }

    private boolean helper1(int n, int target, int[] nums) {
        if(target == 0) {
            return true;
        }
        if(n == 0) {
            return target == nums[0];
        }

        boolean notTake = helper1(n-1, target, nums);

        boolean take = false;
        if(target - nums[n] >= 0) {
            take = helper1(n-1, target-nums[n], nums);
        }

        return notTake || take;
    }

    private boolean isSubsetSum_memo(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][target+1];

        for(int i=0; i<n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return helper2(n-1, target, nums, dp);
    }

    private boolean helper2(int n, int target, int[] nums, int[][] dp) {
        if(target == 0) {
            return true;
        }
        if(n == 0) {
            return target == nums[0];
        }

        if(dp[n][target] != -1) {
            return dp[n][target] == 0;
        }

        boolean notTake = helper2(n-1, target, nums, dp);

        boolean take = false;
        if(target - nums[n] >= 0) {
            take = helper2(n-1, target-nums[n], nums, dp);
        }

        dp[n][target] = (notTake || take) ? 0 : 1;

        return (notTake || take);
    }

    private boolean isSubsetSum_tabu(int[] nums, int target) {
        int n = nums.length;
        boolean[][] dp = new boolean[n][target+1];

        if(nums[0] >= 0) {
            dp[0][nums[0]] = true;
        }

        for(int i=0; i<n; i++) {
            dp[i][0] = true;
        }

        for(int i=1; i<n; i++) {
            for(int t=1; t<=target; t++) {
                boolean notTake = dp[i-1][t];
                boolean take = false;
                if(t - nums[i] >= 0) {
                    take = dp[i-1][t-nums[i]];
                }

                dp[i][t] = notTake || take;
            }
        }

        return dp[n-1][target];
    }
}
