package Patterns.Sliding_Window.Dynamic_Window.dsw10_Binary_Subarrays_With_Sum; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/binary-subarrays-with-sum/description/
Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.
A subarray is a contiguous part of the array.

Example 1:
Input: nums = [1,0,1,0,1], goal = 2
Output: 4
Explanation: The 4 subarrays are bolded and underlined below:
[1,0,1]
[1,0,1,0]
  [0,1,0,1]
    [1,0,1]

Example 2:
Input: nums = [0,0,0,0,0], goal = 0
Output: 15

Concept: Sliding Window(Count All Valid Windows)
Return: Total Count of all Valid Windows

Approach2: Two Pass Sliding window
Approach3: PrefixSum
 */
public class Binary_Subarrays_With_Sum extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Binary_Subarrays_With_Sum().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res1 = numSubarraysWithSum_usingSlidingWin(nums, k); 
        int res3 = numSubarraysWithSum_usingPrefixSum(nums, k);
        System.out.println(res1 + " " + res3); 
    } 
 
    private int numSubarraysWithSum_usingSlidingWin(int[] nums, int k) {
        int start = 0;
        int end = 0;
        int niceSubArrCnt = 0;
        int sum = 0;

        while(end < nums.length){
            int endElem = nums[end];
            sum = sum + endElem;

            if(sum < k){ // InValid Window (sum < k)
                end++;
            }
            else if(sum == k){ // Valid Window (sum == k)
                int startIdx = start; // mimic start
                while(sum == k && startIdx <= end){ // find all Valid subarray (with inc start & end intact)
                    niceSubArrCnt++;
                    sum = sum - nums[startIdx];
                    startIdx++;
                }
                sum = k; // revert sum to what it was previously
                end++;
            }
            else if(sum > k){ // InValid Window (sum > k)
                while(sum > k){
                    sum = sum - nums[start];
                    start++;
                }

                // remove end to revaluate after(sum == k)
                sum = sum - nums[end];
            }
        }

        return niceSubArrCnt;
    }

    // Using Prefix Sum Approach
    // TC: O(n)
    // SC: O(n + n)
    public int numSubarraysWithSum_usingPrefixSum(int[] nums, int sum) {
        int n = nums.length;
        int[] P = new int[n+1];
        for(int i=0; i<n; i++){
            P[i+1] = P[i] + nums[i];
        }
        
        int subArrCnt = 0;
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        // P[start] = P[end] - sum
        for(int ps : P){
            // ps as endElem
            subArrCnt = subArrCnt + freqMap.getOrDefault(ps - sum, 0);

            // ps as startElem
            freqMap.put(ps, freqMap.getOrDefault(ps, 0)+1);
        }
        
        return subArrCnt;
    }
 
} 
