package Patterns.Prefix_Sum.Longest_Subarray_with_Sum_K;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://www.geeksforgeeks.org/problems/longest-sub-array-with-sum-k0809/1

Given an array arr[] containing integers and an integer k,
your task is to find the length of the longest subarray where the sum of its elements is equal to the given value k.

Input: arr[] = [10, 5, 2, 7, 1, -10], k = 15
Output: 6
Explanation: Subarrays with sum = 15 are [5, 2, 7, 1], [10, 5] and [10, 5, 2, 7, 1, -10]. The length of the longest subarray with a sum of 15 is 6.

Input: arr[] = [-5, 8, -14, 2, 4, 12], k = -5
Output: 5
Explanation: Only subarray with sum = -5 is [-5, 8, -14, 2, 4] of length 5.

Input: arr[] = [10, -10, 20, 30], k = 5
Output: 0
 */
public class Longest_Subarray_with_Sum_K extends ProblemSolver {
    public static void main(String[] args) {
        new Longest_Subarray_with_Sum_K().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int sum = DataConvertor.toInt(args[1]);

        int res1 = lenOfLongestSubarr_usingPrefixSumArray(nums, sum);
        int res2 = lenOfLongestSubarr(nums, sum);
        System.out.println(res1 + " " + res2);
    }

    // TC: O(n) SC: O(n + n)
    public int lenOfLongestSubarr_usingPrefixSumArray(int[] nums, int sum) {
        int len = nums.length;
        int[] P = new int[len + 1];
        for (int i = 0; i < len; i++) {
            P[i + 1] = P[i] + nums[i];
        }

        int maxWin = 0;
        Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
        // P[start] = P[end] - sum
        for (int i = 0; i < len + 1; i++) {
            int ps = P[i];
            // ps as endElem
            if (idxMap.containsKey(ps - sum)) {
                maxWin = Math.max(maxWin, i - idxMap.get(ps - sum));
            }

            // ps as startElem
            idxMap.putIfAbsent(ps, i);
        }

        return maxWin;
    }

    // TC: O(n) SC: O(n)
    public int lenOfLongestSubarr(int[] nums, int sum) {
        int len = nums.length;
        int maxWin = 0;
        Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();

        int ps = 0;
        idxMap.put(ps, -1);
        // P[start] = P[end] - sum
        for (int i = 0; i < len; i++) {
            ps = ps + nums[i];
            // ps as endElem
            if (idxMap.containsKey(ps - sum)) {
                maxWin = Math.max(maxWin, i - idxMap.get(ps - sum));
            }

            // ps as startElem
            idxMap.putIfAbsent(ps, i);
        }

        return maxWin;
    }

}
