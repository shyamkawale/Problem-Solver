package Patterns.Prefix_Sum.Contiguous_Array;

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

        int res = findMaxLength(nums);
        System.out.println(res);
    }

    // TC: O(n), SC: O(n+n)
    public int findMaxLength(int[] nums) {
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
}
