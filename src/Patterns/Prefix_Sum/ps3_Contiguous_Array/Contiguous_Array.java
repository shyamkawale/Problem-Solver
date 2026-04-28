package Patterns.Prefix_Sum.ps3_Contiguous_Array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/contiguous-array/
Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.

Input: nums = [0,1]
Output: 2
Explanation: [0, 1] is the longest contiguous subarray with an equal number of 0 and 1.

Input: nums = [0,1,0]
Output: 2
Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
 */
public class Contiguous_Array extends ProblemSolver {

    public static void main(String[] args) {
        new Contiguous_Array().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res1 = findMaxLength1(nums);
        int res2 = findMaxLength2(Arrays.copyOf(nums, nums.length));
        System.out.println(res1 + " " + res2);
    }

    // In a subarray, sum of subarray = no. of 1s
    // sum = no. of 1s
    // according to the question, subarray should have equal no. of 1s and 0s
    // sum*2 = subarray length
    // (P[end] - P[start])*2 = end - start
    // 2*P[end] - end = 2*P[start] - start
    // End-Element = Start-Element
    // TC: O(n), SC: O(n+n)
    public int findMaxLength1(int[] nums) {
        Map<Integer, Integer> idxMap = new HashMap<>();
        int ps = 0;
        idxMap.put(2*0 - (-1), -1);
        int maxWin = 0;
        for(int i=0; i<nums.length; i++){
            ps = ps + nums[i];

            if(idxMap.containsKey(2*ps - i)){
                maxWin = Math.max(maxWin, i-idxMap.get(2*ps-i));
            }

            idxMap.putIfAbsent(2*ps-i, i);
        }

        return maxWin;
    }

    // replace all 0s to -1
    // sum of subarray = 0
    // P[end] - P[start] = 0
    // P[start] = P[end]
    private int findMaxLength2(int[] nums) {
        for(int i=0; i<nums.length; i++) {
            if(nums[i] == 0) {
                nums[i] = -1;
            }
        }

        Map<Integer, Integer> idxMap = new HashMap<>();
        int ps = 0;
        idxMap.put(0, -1);
        int maxWin = 0;
        for(int i=0; i<nums.length; i++){
            ps = ps + nums[i];

            if(idxMap.containsKey(ps)){
                maxWin = Math.max(maxWin, i-idxMap.get(ps));
            }

            idxMap.putIfAbsent(ps, i);
        }

        return maxWin;
    }
}
