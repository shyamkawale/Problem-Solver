package Patterns.Prefix_Sum.ps6_Longest_Subarray_with_Sum_Divisible_by_K; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 


/*
https://www.geeksforgeeks.org/problems/longest-subarray-with-sum-divisible-by-k1259/1

Given an array arr[] and a positive integer k, 
find the length of the longest subarray with the sum of the elements divisible by k.
Note: If there is no subarray with sum divisible by k, then return 0.

Examples :
Input: arr[] = [2, 7, 6, 1, 4, 5], k = 3
Output: 4
Explanation: The subarray [7, 6, 1, 4] has sum = 18, which is divisible by 3.

Input: arr[] = [-2, 2, -5, 12, -11, -1, 7], k = 3
Output: 5
Explanation: The subarray [2, -5, 12, -11, -1] has sum = -3, which is divisible by 3.
 */
public class Longest_Subarray_with_Sum_Divisible_by_K extends ProblemSolver { 
    public static void main(String[] args) { 
        new Longest_Subarray_with_Sum_Divisible_by_K().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res1 = longestSubarraysDivByK_usingPrefixSumArray(nums, k); 
        int res2 = longestSubarraysDivByK(nums, k); 
        System.out.println(res1 + " " + res2); 
    } 
 
    public int longestSubarraysDivByK_usingPrefixSumArray(int[] nums, int k) {
        int len = nums.length;
        int[] P = new int[len+1];
        for(int i=0; i<len; i++){
            P[i+1] = P[i] + nums[i];
        }

        // P[end] - P[start] = sum, where sum = n*k => sum%k = 0
        // (P[end] - P[start])%k = sum%k = 0
        // p[end]%k = p[start]%k
        Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>(); // (ps%k) i.e rem -> idx
        int maxWin = 0;
        for(int i=0; i<len+1; i++){
            // rem as End
            int rem = (P[i]%k + k)%k;
            if(idxMap.containsKey(rem)){
                maxWin = Math.max(maxWin, i-idxMap.get(rem));
            }

            // rem as Start
            idxMap.putIfAbsent(rem, i);
        }

        return maxWin;
    }

    public int longestSubarraysDivByK(int[] nums, int k) {
        int len = nums.length;
        // P[end] - P[start] = sum, where sum = n*k => sum%k = 0
        // (P[end] - P[start])%k = sum%k = 0
        // p[end]%k = p[start]%k
        Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>(); // (ps%k) i.e rem -> idx
        int maxWin = 0;
        int ps = 0;
        idxMap.put(ps%k, -1);
        for(int i=0; i<len; i++){
            ps = ps + nums[i];
            // rem as End
            int rem = (ps%k + k)%k;
            if(idxMap.containsKey(rem)){
                maxWin = Math.max(maxWin, i-idxMap.get(rem));
            }

            // rem as Start
            idxMap.putIfAbsent(rem, i);
        }

        return maxWin;
    }
 
} 
