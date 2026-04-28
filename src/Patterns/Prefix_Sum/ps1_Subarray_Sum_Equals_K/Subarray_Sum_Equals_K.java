package Patterns.Prefix_Sum.ps1_Subarray_Sum_Equals_K; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/subarray-sum-equals-k/
Given an array of integers nums and an integer k, 
return the total number of subarrays whose sum equals to k.

A subarray is a contiguous non-empty sequence of elements within an array.

Constraints:
1 <= nums.length <= 2 * 10^4
-1000 <= nums[i] <= 1000
-10^7 <= k <= 10^7

Example 1:
Input: nums = [1,1,1], k = 2
Output: 2

Example 2:
Input: nums = [1,2,3], k = 3
Output: 2
 */
public class Subarray_Sum_Equals_K extends ProblemSolver { 
    public static void main(String[] args) { 
        new Subarray_Sum_Equals_K().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]);
        int sum = DataConvertor.toInt(args[1]); 
 
        int res1 = subarraySum_usingPrefixSumArray(nums, sum);
        int res2 = subarraySum_usingPrefixSum(nums, sum); 
        System.out.println(res1 + " " + res2); 
    } 
 
    public int subarraySum_usingPrefixSumArray(int[] nums, int sum) {
        int len = nums.length;
        int[] P = new int[len+1];
        for(int i=0; i<len; i++){
            P[i+1] = P[i] + nums[i];
        }
        
        int subArrCnt = 0;
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        // P[end] - sum = P[start]
        for(int ps : P){
            // ps as endElem
            subArrCnt = subArrCnt + freqMap.getOrDefault(ps - sum, 0);

            // ps as startElem
            freqMap.put(ps, freqMap.getOrDefault(ps, 0)+1);
        }
        
        return subArrCnt;
    } 

    public int subarraySum_usingPrefixSum(int[] nums, int sum) {
        int subArrCnt = 0;
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        // P[end] - P[start] = sum
        // P[start] = P[end] - sum (for end do we have start satisfying)
        int ps = 0;
        freqMap.put(ps, 1);
        for(int n: nums){
            ps = ps + n;
            // ps as endElem
            subArrCnt = subArrCnt + freqMap.getOrDefault(ps-sum, 0);

            // ps as startElem
            freqMap.put(ps, freqMap.getOrDefault(ps, 0)+1);
        }
        
        return subArrCnt;
    } 
 
} 
