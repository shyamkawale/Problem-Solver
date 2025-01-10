package Patterns.Stack.Monotonic_Stack.ms10_Sum_of_All_Subarrays_Ranges; 
 
import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/sum-of-subarray-ranges/
The range of a subarray of array nums is the difference between the largest and smallest element in the subarray.
Return the sum of all subarray ranges of nums.
A subarray is a contiguous non-empty sequence of elements within an array.
Input: nums = [1,2,3]
Output: 4
Explanation: The 6 subarrays of nums are the following:
[1], range = largest - smallest = 1 - 1 = 0 
[2], range = 2 - 2 = 0
[3], range = 3 - 3 = 0
[1,2], range = 2 - 1 = 1
[2,3], range = 3 - 2 = 1
[1,2,3], range = 3 - 1 = 2
So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.

Concept: sum$(max - min) of all subarray => sum$(max) - sum$(min)
sum$(max) = sum of all subarray maximums => for element find all subarray where it can be maximum element => use PGE & NGEE 💡💡
sum$(min) = sum of all subarray minimums => for element find all subarray where it can be minimum element => use PSE & NSEE 💡💡

Bruteforce approach: find all subarray and find its max, min
Approach 2: find sum of all maximums(PGE, NGEE) and find sum of all minimums(PSE, NSEE)
 */
public class Sum_of_All_Subarrays_Ranges extends ProblemSolver { 
    public static void main(String[] args) { 
        new Sum_of_All_Subarrays_Ranges().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        long res1 = subArrayRanges(arr); 
        long res2 = subArrayRanges_Optimized(arr);
        System.out.println(res1 + " " + res2); 
    } 

    public long subArrayRanges(int[] nums) {
        int len = nums.length;

        long sum = 0;
        for(int i=0; i<len; i++){
            int min = nums[i];
            int max = nums[i];
            for(int j=i; j<len; j++){
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);

                int diff = max - min;
                sum = sum + diff;
            }
        }

        return sum;
    }
 
    public long subArrayRanges_Optimized(int[] arr) {
        long sumOfAllSubArrMaxs = sumOfAllSubArrMaxs(arr);
        long sumOfAllSubArrMins = sumOfAllSubArrMins(arr);

        return sumOfAllSubArrMaxs - sumOfAllSubArrMins;
    }

    private long sumOfAllSubArrMaxs(int[] arr){
        int len = arr.length;
        Deque<Integer> stack = new ArrayDeque<Integer>();
        int[] pgeIdx = new int[len];
        int[] ngeeIdx = new int[len];

        for(int i=0; i<len; i++){
            int currElem = arr[i];
            if(stack.isEmpty()){
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] <= currElem){
                    int topElem = stack.pop();
                    pgeIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
                    ngeeIdx[topElem] = i;
                }
                stack.push(i);
            }
        }

        while(!stack.isEmpty()){
            int topElem = stack.pop();
            pgeIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
            ngeeIdx[topElem] = len;
        }

        long sum = 0;
        for(int i=0; i<len; i++){
            int leftMaxSubArrCnt = i-pgeIdx[i];
            int rightMaxSubArrCnt = ngeeIdx[i] - i;
            long totalMaxSubArrCnt = (long)(leftMaxSubArrCnt*rightMaxSubArrCnt);

            sum = (long)(sum + totalMaxSubArrCnt*arr[i]);
        }
        return sum;
    }

    private long sumOfAllSubArrMins(int[] arr){
        int len = arr.length;
        Deque<Integer> stack = new ArrayDeque<Integer>();
        int[] pseIdx = new int[len];
        int[] nseeIdx = new int[len];

        for(int i=0; i<len; i++){
            int currElem = arr[i];
            if(stack.isEmpty()){
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] >= currElem){
                    int topElem = stack.pop();
                    pseIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
                    nseeIdx[topElem] = i;
                }
                stack.push(i);
            }
        }

        while(!stack.isEmpty()){
            int topElem = stack.pop();
            pseIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
            nseeIdx[topElem] = len;
        }

        long sum = 0;
        for(int i=0; i<len; i++){
            int leftMinSubArrCnt = i-pseIdx[i];
            int rightMinSubArrCnt = nseeIdx[i] - i;
            long totalMinSubArrCnt = (long)(leftMinSubArrCnt*rightMinSubArrCnt);

            sum = (long)(sum + totalMinSubArrCnt*arr[i]);
        }
        return sum;
    }
 
} 
