package Patterns.Prefix_Sum.ps10_Find_Maximum_Balanced_XOR_Subarray_Length;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/find-maximum-balanced-xor-subarray-length/

Given an integer array nums, 
return the length of the longest subarray that has a bitwise XOR of zero and contains an equal number of even and odd numbers. 
If no such subarray exists, return 0.

Example 1:
Input: nums = [3,1,3,2,0]
Output: 4
Explanation:
The subarray [1, 3, 2, 0] has bitwise XOR 1 XOR 3 XOR 2 XOR 0 = 0 and contains 2 even and 2 odd numbers.

Example 2:
Input: nums = [3,2,8,5,4,14,9,15]
Output: 8
Explanation:
The whole array has bitwise XOR 0 and contains 4 even and 4 odd numbers.

Example 3:
Input: nums = [0]
Output: 0
Explanation:
No non-empty subarray satisfies both conditions.
*/
public class Find_Maximum_Balanced_XOR_Subarray_Length extends ProblemSolver {

    public static void main(String[] args) {
        new Find_Maximum_Balanced_XOR_Subarray_Length().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res = maxBalancedSubarray(nums);
        System.out.println(res);
    }

    public int maxBalancedSubarray(int[] nums) {
        int len = nums.length;
        int px = 0;
        int peo = 0;

        Map<Long, Integer> map = new HashMap<>();
        map.put(encode(0, 0), -1);
        int maxWin = 0;

        for(int i=0; i<len; i++) {
            px = px ^ nums[i];
            peo += (nums[i] % 2 == 0) ? 1 : -1;

            // combine px + peo into a single long key
            Long key = encode(px, peo);

            if(map.containsKey(key)) {
                int prev = map.get(key);
                maxWin = Math.max(maxWin, i-prev);
            }
            else {
                map.put(key, i);
            }
        }

        return maxWin;
    }

    private long encode(int a, int b) {
        return ((long)a << 32) | (b & ((1L << 32)-1));
    }

}
