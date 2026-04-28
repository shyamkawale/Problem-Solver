package Patterns.Prefix_Sum.ps5_Subarray_Sums_Divisible_by_K; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
Given an integer array nums and an integer k, 
return the number of non-empty subarrays that have a sum divisible by k.

A subarray is a contiguous part of an array.

Example 1:
Input: nums = [4,5,0,-2,-3,1], k = 5
Output: 7
Explanation: There are 7 subarrays with a sum divisible by k = 5:
[4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]

Example 2:
Input: nums = [5], k = 9
Output: 0
*/
public class Subarray_Sums_Divisible_by_K extends ProblemSolver { 
    public static void main(String[] args) { 
        new Subarray_Sums_Divisible_by_K().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res1 = subarraysDivByK_usingPrefixSumArray(nums, k); 
        int res2 = subarraysDivByK(nums, k);
        System.out.println(res1 + " " + res2); 
    }
 
    public int subarraysDivByK_usingPrefixSumArray(int[] nums, int k) {
        int len = nums.length;
        int[] P = new int[len+1];
        for(int i=0; i<len; i++){
            P[i+1] = P[i] + nums[i];
        }

        // P[end] - P[start] = sum, where sum = n*k => sum%k = 0
        // (P[end] - P[start])%k = sum%k = 0
        // p[end]%k = p[start]%k
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>(); // (ps%k) i.e rem -> freq
        int subCnt = 0;
        for(int i=0; i<len+1; i++){
            // rem as End
            int rem = (P[i]%k + k)%k;
            subCnt = subCnt + freqMap.getOrDefault(rem, 0);

            // rem as Start
            freqMap.put(rem, freqMap.getOrDefault(rem, 0)+1);
        }

        return subCnt;
    }

    public int subarraysDivByK(int[] nums, int k) {
        // P[end] - P[start] = sum, where sum = n*k => sum%k = 0
        // (P[end] - P[start])%k = sum%k = 0
        // p[end]%k = p[start]%k
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>(); // (ps%k) i.e rem -> freq
        int subCnt = 0;
        int ps = 0;
        freqMap.put(ps%k, 1);
        for(int n: nums){
            ps = ps + n;
            // rem as End
            int rem = (ps%k + k)%k;
            subCnt = subCnt + freqMap.getOrDefault(rem, 0);

            // rem as Start
            freqMap.put(rem, freqMap.getOrDefault(rem, 0)+1);
        }

        return subCnt;
    }
 
} 
