package Patterns.Prefix_Sum.ps4_Maximum_Subarray;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/maximum-subarray

Given an integer array nums, find the subarray with the largest sum, and return its sum.

Example 1:
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: The subarray [4,-1,2,1] has the largest sum 6.

Example 2:
Input: nums = [1]
Output: 1
Explanation: The subarray [1] has the largest sum 1.

Example 3:
Input: nums = [5,4,-1,7,8]
Output: 23
Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.

Approach 1: Prefix Sum
Approach 2: Kadane Algorithm
*/
public class Maximum_Subarray extends ProblemSolver {

    public static void main(String[] args) {
        new Maximum_Subarray().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res1 = maxSubArray_prefixSum(nums);
        int res2 = maxSubArray_kadane(nums);
        System.out.println(res1 + " " + res2);
    }

    // Prefix Sum
    public int maxSubArray_prefixSum(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int len = nums.length;

        int minPs = Integer.MAX_VALUE;
        int ps = 0;
        for (int i = 0; i < len; i++) {
            minPs = Math.min(minPs, ps);

            ps = ps + nums[i];
            maxSum = Math.max(maxSum, ps - minPs);
        }

        return maxSum;
    }

    // Kadane Algorithm
    /*
    Imagine you are walking along a path picking up coins (positive numbers) and paying tolls (negative numbers). 
    You want to maximize the money in your pocket at some point.
     - Positive numbers are great => they add to your wealth.
     - Negative numbers reduce your wealth, but you might tolerate them if a big pot of gold is just ahead.
    The Golden Rule: If your pocket is empty or in debt (sum < 0), drop the baggage and start fresh. There is no point in carrying debt into the future.

    matlab => if you have prev < 0 and curr is +ve then drop prev and take curr
    because sum = a+b then if a is -ve then sum will always < b.. in this case we should drop a and just take b.
    */
    private int maxSubArray_kadane(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int len = nums.length;

        int sum = 0;
        for(int i=0; i<len; i++) {
            if(sum < 0) {
                sum = 0;
            }

            sum = sum + nums[i];
            maxSum = Math.max(sum, maxSum);
        }

        return maxSum;
    }
}
