package Patterns.Sliding_Window.Dynamic_Window.dsw4_Max_Consecutive_Ones_III; 
 
import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/max-consecutive-ones-iii
Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.

Example 1:
Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
Output: 6
Explanation: [1,1,1,0,0,1,1,1,1,1,1]
Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
Example 2:
Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
Output: 10
Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.

Concept: SLiding Window
Return: Longest Valid Window Length
Trick: max substring with 0's <= k will be the answer
 */
public class Max_Consecutive_Ones_III extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Max_Consecutive_Ones_III().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res = longestOnes(nums, k); 
        System.out.println(res); 
    } 
 
    // TC: O(n + n) => O(n)
    // SC: O(1)
    private int longestOnes(int[] nums, int k) {
        int start = 0;
        int end = 0;
        int cnt = 0;
        int maxLen = 0;

        while(end < nums.length){
            if(nums[end] == 0) cnt++;
            
            if(cnt <= k){ // Valid Window (cond <= k)
                maxLen = Math.max(maxLen, end-start+1);
                end++;
            }
            else if(cnt > k){ // InValid Window (cond > k)
                while(cnt > k){  //remove start till cond <= k to make it Valid Window
                    if(nums[start] == 0){
                        cnt--;
                    }
                    start++;
                }
                
                if(nums[end] == 0) cnt--; // to remove end's contribution from cnt to revaluate window
            }
        }
        return maxLen;
    }
 
} 
