package Patterns.Dynamic_Programming.DP_on_Subsequences.Does_Partition_Equal_Subset_Sum;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/partition-equal-subset-sum/description

Given an integer array nums, 
return true if you can partition the array into two subsets
such that the sum of the elements in both subsets is equal or false otherwise.

Example 1:
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].

Example 2:
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.

TRICK: target = sum/2;
*/
public class Does_Partition_Equal_Subset_Sum extends ProblemSolver {

    public static void main(String[] args) {
        new Does_Partition_Equal_Subset_Sum().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        boolean res1 = canPartition_rec(nums);
        boolean res2 = canPartition_memo(nums);
        System.out.println(res1 + " " + res2);
    }

    public boolean canPartition_rec(int[] nums) {
        // sum = sum of all elements
        // 2 partition is possible if sum of subsequence = sum/2;

        int n = nums.length;
        int sum = 0;
        for(int num: nums) {
            sum = sum + num;
        }
        if(sum%2 != 0) {
            return false;
        }

        int target = sum/2;
        return helper1(n-1, target, nums);
    }

    public boolean helper1(int n, int target, int[] nums) {
        if(n == 0) {
            return target == nums[0];
        }

        if(target == 0) {
            return true;
        }

        boolean notPick = helper1(n-1, target, nums);
        boolean pick = false;
        if(target - nums[n] >= 0) {
            pick = helper1(n-1, target-nums[n], nums);
        }

        return notPick || pick;
    }

    public boolean canPartition_memo(int[] nums) {
        // sum = sum of all elements
        // 2 partition is possible if sum of subsequence = sum/2;

        int n = nums.length;
        int sum = 0;
        for(int num: nums) {
            sum = sum + num;
        }
        if(sum%2 != 0) {
            return false;
        }

        int target = sum/2;
        int[][] dp = new int[n][target+1];
        for(int i=0; i<n; i++) {
            Arrays.fill(dp[i], -1);
        }
        return helper2(n-1, target, nums, dp);
    }

    public boolean helper2(int n, int target, int[] nums, int[][] dp) {
        if(n == 0) {
            return target == nums[0];
        }

        if(target == 0) {
            return true;
        }

        if(dp[n][target] != -1) {
            return dp[n][target] == 0;
        }

        boolean notPick = helper2(n-1, target, nums, dp);
        boolean pick = false;
        if(target - nums[n] >= 0) {
            pick = helper2(n-1, target-nums[n], nums, dp);
        }

        dp[n][target] = (notPick || pick) ? 0 : 1;
        return notPick || pick;
    }

}
