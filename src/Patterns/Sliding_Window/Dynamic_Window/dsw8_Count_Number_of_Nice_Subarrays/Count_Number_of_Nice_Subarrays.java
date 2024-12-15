package Patterns.Sliding_Window.Dynamic_Window.dsw8_Count_Number_of_Nice_Subarrays; 
 
import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/count-number-of-nice-subarrays
Given an array of integers nums and an integer k. A continuous subarray is called nice if there are k odd numbers on it.
Return the number of nice sub-arrays.

Example 1:
Input: nums = [1,1,2,1,1], k = 3
Output: 2
Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].
Example 2:
Input: nums = [2,4,6], k = 1
Output: 0
Explanation: There are no odd numbers in the array.
Example 3:
Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
Output: 16

Concept: Sliding Window(Count All Valid Windows)
Return: Total Count of all Valid Windows
 */
public class Count_Number_of_Nice_Subarrays extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Count_Number_of_Nice_Subarrays().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res = numberOfSubarrays(nums, k); 
        System.out.println(res); 
    } 
 
    //TC: 
    public int numberOfSubarrays(int[] nums, int k) {
        int start = 0;
        int end = 0;
        int niceSubArrCnt = 0;
        int oddCnt = 0;

        while(end < nums.length){
            int endElem = nums[end];
            if(endElem%2 != 0) oddCnt++;

            if(oddCnt < k){ // InValid Window(cond < k)
                end++;
            }
            else if(oddCnt == k){ // Valid Window bcz k odd integers
                int startIdx = start; // mimic start index
                while(oddCnt == k && startIdx <= end){ // find all valid substring (with inc start & end intact)
                    //store ans
                    niceSubArrCnt++;

                    // remove start
                    if(nums[startIdx]%2 != 0){
                        oddCnt--;
                    }
                    startIdx++;
                }
                // revert oddCnt to what it was previously
                oddCnt = k;
                end++;
            }
            else if(oddCnt > k){ //InValid Window(cond > k) less odd integers 
                while(oddCnt > k){
                    if(nums[start]%2 != 0){
                        oddCnt--;
                    }
                    start++;
                }

                // remove end to revaluate window
                if(endElem%2 != 0){
                    oddCnt--;
                }
            }
        }

        return niceSubArrCnt;
    }
 
} 
