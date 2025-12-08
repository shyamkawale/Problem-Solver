package Patterns.Prefix_Sum.Subarray_Sum_Equals_K; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/subarray-sum-equals-k/
Given an integer array nums and an integer k, return the number of non-empty subarrays that have a sum divisible by k.
Input: nums = [4,5,0,-2,-3,1], k = 5
Output: 7
Explanation: There are 7 subarrays with a sum divisible by k = 5:
[4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]

Input: nums = [5], k = 9
Output: 0
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
