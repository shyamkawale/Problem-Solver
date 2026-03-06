package Patterns.Dynamic_Programming.DP_1D.House_Robber_II;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/house-robber-ii/

You are a professional robber planning to rob houses along a street. 
Each house has a certain amount of money stashed. 
All houses at this place are arranged in a circle. 
That means the first house is the neighbor of the last one. 
Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police 
if two adjacent houses were broken into on the same night.

Given an integer array nums representing the amount of money of each house, 
return the maximum amount of money you can rob tonight without alerting the police.


Example 1:
Input: nums = [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.

Example 2:
Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.

Example 3:
Input: nums = [1,2,3]
Output: 3
*/
public class House_Robber_II extends ProblemSolver {

    public static void main(String[] args) {
        new House_Robber_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res = rob(nums);
        System.out.println(res);
    }

    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0];

        int[] nums1 = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            nums1[i] = nums[i];
        }

        int[] nums2 = new int[n - 1];
        for (int i = 1; i < n; i++) {
            nums2[i - 1] = nums[i];
        }

        return Math.max(houseRob_tabuop(nums1), houseRob_tabuop(nums2));
    }

    // house Robber 1 solution (Maximum Sub of NonAdjacent Elements)
    public int houseRob_tabuop(int[] nums) {
        int n = nums.length;

        int prev2 = nums[0];
        if (n == 1)
            return prev2;
        int prev1 = Math.max(prev2, nums[1]);
        if (n == 2)
            return prev1;
        int curr = 0;

        for (int i = 2; i < n; i++) {
            int pick = nums[i] + prev2;
            int notPick = 0 + prev1;
            curr = Math.max(pick, notPick);

            prev2 = prev1;
            prev1 = curr;
        }

        return curr;
    }
}
