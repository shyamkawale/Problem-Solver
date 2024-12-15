package Patterns.Sliding_Window.Dynamic_Window.dsw9_Binary_Subarrays_With_Sum; 
 
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
 */
public class Binary_Subarrays_With_Sum extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Binary_Subarrays_With_Sum().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res = numSubarraysWithSum(nums, k); 
        System.out.println(res); 
    } 
 
    private int numSubarraysWithSum(int[] nums, int k) {
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
 
} 
