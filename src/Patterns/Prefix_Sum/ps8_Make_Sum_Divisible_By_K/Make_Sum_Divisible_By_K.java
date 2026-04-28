package Patterns.Prefix_Sum.ps8_Make_Sum_Divisible_By_K;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/make-sum-divisible-by-p/

Given an array of positive integers nums, 
remove the smallest subarray (possibly empty) such that the sum of the remaining elements is divisible by p. 
It is not allowed to remove the whole array.

Return the length of the smallest subarray that you need to remove, or -1 if it's impossible.

A subarray is defined as a contiguous block of elements in the array.

Example 1:
Input: nums = [3,1,4,2], p = 6
Output: 1
Explanation: The sum of the elements in nums is 10, which is not divisible by 6. 
We can remove the subarray [4], and the sum of the remaining elements is 6, which is divisible by 6.

Example 2:
Input: nums = [6,3,5,2], p = 9
Output: 2
Explanation: We cannot remove a single element to get a sum divisible by 9. 
The best way is to remove the subarray [5,2], leaving us with [6,3] with sum 9.

Example 3:
Input: nums = [1,2,3], p = 3
Output: 0
Explanation: Here the sum is 6. which is already divisible by 3. Thus we do not need to remove anything.
*/
public class Make_Sum_Divisible_By_K extends ProblemSolver {

    public static void main(String[] args) {
        new Make_Sum_Divisible_By_K().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int res = minSubarray(nums, k);
        System.out.println(res);
    }

    public int minSubarray(int[] nums, int k) {
        int len = nums.length;

        long totSum = 0L;
        for (int n : nums) {
            totSum = totSum + n;
        }

        if (totSum % k == 0) {
            return 0;
        }

        long ps = 0L;
        Map<Long, Integer> idxMap = new HashMap<>();
        idxMap.put(ps % k, -1);
        int minSubLen = len;

        for (int i = 0; i < len; i++) {
            ps = ps + nums[i];

            if (idxMap.containsKey((ps % k - totSum % k + k) % k)) {
                minSubLen = Math.min(minSubLen, i - idxMap.get((ps % k - totSum % k + k) % k));
            }

            idxMap.put(ps % k, i);
        }

        return minSubLen == len ? -1 : minSubLen;
    }

}
