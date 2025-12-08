package Patterns.Sliding_Window.Fixed_Window.fsw1_Maximum_Sum_of_Distinct_Subarrays_of_Size_K; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/description/
Find the maximum subarray sum of all the subarrays of nums that meet the following conditions:
1) The length of the subarray is k,
2) All the elements of the subarray are distinct.
*/
public class Maximum_Sum_of_Distinct_Subarrays_of_Size_K extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Maximum_Sum_of_Distinct_Subarrays_of_Size_K().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        long res = maximumSubarraySum(nums, k); 
        System.out.println(res); 
    } 
 
    // TC: O(n)
    // SC: O(n)
    public long maximumSubarraySum(int[] nums, int k) {
        // map used to know if window contains distinct elements(window size == map size)
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(k);
        int start = 0;
        int end = 0;

        long sum = 0; // long becoz of overflow issue
        long maxSum = 0;

        while(end < nums.length){
            int endElem = nums[end];

            sum = sum + endElem;
            map.put(endElem, map.getOrDefault(endElem, 0)+1);

            if(end-start+1 < k){ // window size < k
                end++;
            }
            else if(end-start+1 == k){ // window is set
                //store ans
                if(map.size() == k){ // to know if window contains all distinct
                    maxSum = Math.max(sum, maxSum);
                }
                
                // remove start element from map and sum
                int startElem = nums[start];
                sum = sum - startElem;
                map.computeIfPresent(startElem, (key, value) -> value == 1 ? null : value-1);

                start++;
                end++;
            }
        }
        return maxSum;
    }
 
} 
