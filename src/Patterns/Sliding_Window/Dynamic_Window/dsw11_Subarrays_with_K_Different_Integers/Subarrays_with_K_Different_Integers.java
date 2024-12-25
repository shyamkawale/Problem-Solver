package Patterns.Sliding_Window.Dynamic_Window.dsw11_Subarrays_with_K_Different_Integers; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 

import java.util.*;
 
/*
https://leetcode.com/problems/subarrays-with-k-different-integers/
Given an integer array nums and an integer k, return the number of subarrays where the number of different integers in that array is exactly k.

For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.
A subarray is a contiguous part of an array.

Example 1:
Input: nums = [1,2,1,2,3], k = 2
Output: 7
Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]

Example 2:
Input: nums = [1,2,1,3,4], k = 3
Output: 3
Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].

// Concept: Dynamic Sliding Window(Count all valid subarrays), optimal sol = Atmost Venn Diagram Technique
// Return: Total Count of all Valid Windows
// Trick(Most Optimal Solution): countOfSubarrays(==k) = countOfSubarrays(<=k) - countOfSubarrays(<=k-1)
// ** 🚀 AtMost Venn Diagram Technique 🚀 **            (atMost K)            - (atMost K-1)
*/
public class Subarrays_with_K_Different_Integers extends ProblemSolver { 
    public static void main(String[] args) { 
        new Subarrays_with_K_Different_Integers().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) {
        int[] arr = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);
 
        int res1 = subarraysWithKDistinct(arr, k);
        int res2 = subarraysWithKDistinct_OptimalSol(arr, k);
        System.out.println(res1 + " " + res2);
    } 
 
    // using usual dynamic sliding window + Cnt
    public int subarraysWithKDistinct(int[] arr, int k) {
        int len = arr.length;
        int start = 0;
        int end = 0;

        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();
        int validCnt = 0;

        while(end < len){
            int endElem = arr[end];
            freqMap.put(endElem, freqMap.getOrDefault(endElem, 0)+1);

            if(freqMap.size() < k){ // InValid Window
                end++;
            }
            else if(freqMap.size() == k){ // Valid Window
                int startIdx = start;
                while(freqMap.size() == k && startIdx <= end){
                    validCnt++;
                    int startElem = arr[startIdx];
                    freqMap.put(startElem, freqMap.get(startElem)-1);
                    if(freqMap.get(startElem) <= 0) freqMap.remove(startElem);
                    startIdx++;
                }

                for(int i=start; i<startIdx; i++){
                    int startElem = arr[i];
                    freqMap.put(startElem, freqMap.getOrDefault(startElem, 0)+1);
                }
                end++;
            }
            else if(freqMap.size() > k){ // InValid Window
                while(freqMap.size() > k){
                    int startElem = arr[start];
                    freqMap.put(startElem, freqMap.get(startElem)-1);
                    if(freqMap.get(startElem) <= 0) freqMap.remove(startElem);
                    start++;
                }

                //now window size <= k (i.e it may become valid window == case) / so revaluate window
                freqMap.put(endElem, freqMap.get(endElem)-1);
                if(freqMap.get(endElem) <= 0) freqMap.remove(endElem);
            }
        }

        return validCnt;
    }

    // AtMost Venn Diagram technique 
    public int subarraysWithKDistinct_OptimalSol(int[] nums, int k) {
        return slidingWindowAtMost(nums, k) - slidingWindowAtMost(nums, k-1);
    }

    private int slidingWindowAtMost(int[] nums, int distinctK){
        if(distinctK == 0) return 0;
        int len = nums.length;
        int start = 0;
        int end = 0;
        int winCnt = 0;
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        while(end < len){
            int endElem = nums[end];
            freqMap.put(endElem, freqMap.getOrDefault(endElem, 0)+1);

            if(freqMap.size() <= distinctK){ // Valid Window
                winCnt = winCnt + (end - start + 1);
                end++;
            }
            else if(freqMap.size() > distinctK){ // InValid Window
                while(freqMap.size() > distinctK){
                    int startElem = nums[start];
                    freqMap.put(startElem, freqMap.get(startElem)-1);
                    if(freqMap.get(startElem) <= 0) freqMap.remove(startElem);
                    start++;
                }

                // revaluate new valid window
                freqMap.put(endElem, freqMap.getOrDefault(endElem, 0)-1);
                if(freqMap.get(endElem) <= 0) freqMap.remove(endElem);
            }
        }
        return winCnt;
    }
 
} 
