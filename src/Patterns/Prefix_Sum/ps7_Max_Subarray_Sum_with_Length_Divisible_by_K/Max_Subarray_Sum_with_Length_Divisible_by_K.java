package Patterns.Prefix_Sum.ps7_Max_Subarray_Sum_with_Length_Divisible_by_K; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/maximum-subarray-sum-with-length-divisible-by-k/

You are given an array of integers nums and an integer k.

Return the maximum sum of a subarray of nums, 
such that the size of the subarray is divisible by k.

Example 1:
Input: nums = [1,2], k = 1
Output: 3
Explanation:
The subarray [1, 2] with sum 3 has length equal to 2 which is divisible by 1.

Example 2:
Input: nums = [-1,-2,-3,-4,-5], k = 4
Output: -10
Explanation:
The maximum sum subarray is [-1, -2, -3, -4] which has length equal to 4 which is divisible by 4.
 */
public class Max_Subarray_Sum_with_Length_Divisible_by_K extends ProblemSolver { 
    public static void main(String[] args) { 
        new Max_Subarray_Sum_with_Length_Divisible_by_K().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        long res1 = maxSubarraySum_usingPrefixSumArray(nums, k); 
        long res2 = maxSubarraySum(nums, k);
        System.out.println(res1 + " " + res2);
    } 
 
    public long maxSubarraySum_usingPrefixSumArray(int[] nums, int k) {
        int len = nums.length;
        long[] P = new long[len+1];
        for(int i=0; i<len; i++){
            P[i+1] = P[i] + nums[i];
        }

        // P[end] - P[start] = maxSum , (end-start)%k = 0
        // P[start] = p[end] - maxSum , end%k = start%k

        Map<Integer, Long> remSumMap = new HashMap<Integer, Long>(); // rem -> sum
        long maxSum = Long.MIN_VALUE;
        
        for(int i=0; i<len+1; i++){
            long ps = P[i];
            int rem = i%k;

            // rem acts as end
            if(remSumMap.containsKey(rem)){
                long winSum = ps - remSumMap.get(rem);
                maxSum = Math.max(maxSum, winSum);
            }

            // rem acts as start
            remSumMap.put(rem, Math.min(ps, remSumMap.getOrDefault(rem, Long.MAX_VALUE)));
        }

        return maxSum;
    }

    public long maxSubarraySum(int[] nums, int k) {
        int len = nums.length;
        // P[end] - P[start] = maxSum , (end-start)%k = 0
        // for maxSum, P[start] should be minimum , end%k = start%k
        Map<Integer, Long> remSumMap = new HashMap<Integer, Long>(); // rem -> sum
        long maxSum = Long.MIN_VALUE;
        long ps = 0;
        remSumMap.put((-1%k + k)%k, 0L);

        for(int i=0; i<len; i++){
            ps = ps + nums[i];
            int rem = i%k;

            // rem acts as end
            if(remSumMap.containsKey(rem)){
                long winSum = ps - remSumMap.get(rem);
                maxSum = Math.max(maxSum, winSum);
            }

            // rem acts as start
            Long minStartSum = Math.min(ps, remSumMap.getOrDefault(rem, Long.MAX_VALUE));
            remSumMap.put(rem, minStartSum);
        }

        return maxSum;
    }

    // https://www.youtube.com/watch?v=7_PxgiUD9RE
    // not understood
    public long maxSubarraySum_optimised(int[] nums, int k) {
        int n = nums.length;
        long prefixSum = 0;
        long[] minPrefixSum = new long[k];

        for (int i = 0; i < k; i++) {
            minPrefixSum[i] = Long.MAX_VALUE;
        }
        minPrefixSum[k-1] = 0;

        long maxSum = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            prefixSum += nums[i];
            int remainder = i % k;

            if (i >= k) {
                maxSum = Math.max(maxSum, prefixSum - minPrefixSum[remainder]);
            }else if(i == k-1){
                maxSum = Math.max(maxSum, prefixSum);
            }

            minPrefixSum[remainder] = Math.min(minPrefixSum[remainder], prefixSum);
        }

        return maxSum;
    }
 
} 
