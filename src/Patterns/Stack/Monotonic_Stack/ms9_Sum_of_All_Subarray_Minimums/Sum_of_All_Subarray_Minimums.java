package Patterns.Stack.Monotonic_Stack.ms9_Sum_of_All_Subarray_Minimums; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/sum-of-subarray-minimums
Given an array of integers arr, find the sum of min(b), where b ranges over every (contiguous) subarray of arr. 
Since the answer may be large, return the answer modulo 109 + 7.

Input: arr = [3,1,2,4]
Output: 17
Explanation: 
Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4]. 
Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.
Sum is 17.

Input: arr = [11,81,94,43,3]
Output: 444

Concept: for element find all subarray where it can be minimum element => PSE + NSEE + mod

Bruteforce approach: find all subarray and cal mins
Approach1: Two Pass solution
Approach2: One Pass solution

Question: Why sliding window is not possible => possible but of no use bcoz for this problem there is no condition for valid substring(i.e all substrings are valid)
 */
public class Sum_of_All_Subarray_Minimums extends ProblemSolver { 
    public static void main(String[] args) { 
        new Sum_of_All_Subarray_Minimums().readInput(); 
    }
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int res1 = sumSubarrayMins(arr); 
        int res2 = sumSubarrayMins_TwoPass(arr);
        int res3 = sumSubarrayMins_OnePass(arr);
        System.out.println(res1 + " " + res2 + " " + res3); 
    }
 
    // Bruteforce solution
    // TC: O(n^2)
    // SC: O(1)
    public int sumSubarrayMins(int[] arr) {
        int len = arr.length;
        int mod = 1000000007;

        int sum = 0;
        for(int i=0; i<len; i++){
            int minElem = Integer.MAX_VALUE;
            for(int j=i; j<len; j++){
                minElem = Math.min(minElem, arr[j]);
                sum = (sum + minElem)%mod;
            }
        }
        
        return sum;
    }

    /*
    CONCEPT TRICK: one time it is >= and other time it is > to avoid considering duplicate subarrays 💡
    CONCEPT TRICK: use of mod(use long) 💡
    CONCEPT TRICK: formula for calculating no of substring 💡💡
     */

    // Two Pass Solution
    // TC: O(n+n + n+n + n) => O(5n) => O(n)
    // SC: O(n+n + n+n) => pseIdx/nseIdx + 2 stack
    public int sumSubarrayMins_TwoPass(int[] arr) {
        int len = arr.length;

        int[] pseIdx = findPSE(arr);
        int[] nseeIdx = findNSEE(arr);
        System.out.println(Arrays.toString(pseIdx) + " . " + Arrays.toString(nseeIdx));

        long mod = 1000000007;
        long sum = 0;

        for(int i=0; i<len; i++){
            int currElem = arr[i];

            int leftSubArrLen = i-pseIdx[i];
            int rightSubArrLen = nseeIdx[i]-i;

            long totalSubArrPossible = (leftSubArrLen * rightSubArrLen)%mod;

            sum = (long)(sum + (totalSubArrPossible * currElem)%mod)%mod;
        }
        
        return (int)(sum%mod);
    }

    private int[] findPSE(int[] arr){
        int len = arr.length;
        int[] pseIdx = new int[len];
        Deque<Integer> stack = new ArrayDeque<Integer>();
        
        for(int i=0; i<len; i++){
            int currElem = arr[i];

            if(stack.isEmpty()){ // initial condition
                pseIdx[i] = -1;
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] >= currElem){
                    stack.pop();
                }

                pseIdx[i] = stack.isEmpty() ? -1 : stack.peek(); // now Stack contains all smaller building (top => curr ke pass wala)

                stack.push(i);
            }
        }

        return pseIdx;
    }

    private int[] findNSEE(int[] arr){
        int len = arr.length;
        int[] nseeIdx = new int[len];
        Deque<Integer> stack = new ArrayDeque<Integer>();

        // finding next nearest min
        for(int i=len-1; i>=0; i--){
            int currElem = arr[i];

            if(stack.isEmpty()){ // initial condition
                nseeIdx[i] = len;
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] > currElem){
                    stack.pop();
                }

                nseeIdx[i] = stack.isEmpty() ? len : stack.peek(); // now Stack contains all smaller building (top => curr ke pass wala)

                stack.push(i);
            }
        }

        return nseeIdx;
    }

    // One Pass solution
    // TC: O(n+n + n) => O(3n) => O(n)
    // SC: O(n+n + n) => pseIdx/nseIdx + 1 stack
    public int sumSubarrayMins_OnePass(int[] arr) {
        int len = arr.length;

        Deque<Integer> stack = new ArrayDeque<Integer>();
        int[] pseIdx = new int[len];
        int[] nseeIdx = new int[len];

        for(int i=len-1; i>=0; i--){
            int currElem = arr[i];

            if(stack.isEmpty()){
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] > currElem){
                    int topElem = stack.pop();
                    nseeIdx[topElem] =  stack.isEmpty() ? len : stack.peek();
                    pseIdx[topElem] = i;
                }

                stack.push(i);
            }
        }

        while(!stack.isEmpty()){
            int topElem = stack.pop();
            nseeIdx[topElem] =  stack.isEmpty() ? len : stack.peek();
            pseIdx[topElem] = -1;
        }

        System.out.println(Arrays.toString(pseIdx) + " + " + Arrays.toString(nseeIdx));

        long mod = 1000000007;
        long sum = 0;

        for(int i=0; i<len; i++){
            int currElem = arr[i];

            int leftSubArrLen = i-pseIdx[i];
            int rightSubArrLen = nseeIdx[i]-i;

            long totalSubArrPossible = (leftSubArrLen * rightSubArrLen)%mod;

            sum = (long)(sum + (totalSubArrPossible * currElem)%mod)%mod;
        }
        
        return (int)(sum%mod);
    }

    // one pass psee nse
    public int sumSubarrayMins_OptimizedPSEE_NSE(int[] arr) {
        int len = arr.length;

        Deque<Integer> stack = new ArrayDeque<Integer>();
        int[] pseeIdx = new int[len];
        int[] nseIdx = new int[len];

        for(int i=0; i<len; i++){
            int currElem = arr[i];

            if(stack.isEmpty()){
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] > currElem){
                    int topElem = stack.pop();
                    pseeIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
                    nseIdx[topElem] =  i;
                }

                stack.push(i);
            }
        }

        while(!stack.isEmpty()){
            int topElem = stack.pop();
            pseeIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
            nseIdx[topElem] =  len;
        }

        System.out.println(Arrays.toString(pseeIdx) + " + " + Arrays.toString(nseIdx));

        long mod = 1000000007;
        long sum = 0;

        for(int i=0; i<len; i++){
            int currElem = arr[i];

            int leftSubArrLen = i-pseeIdx[i];
            int rightSubArrLen = nseIdx[i]-i;

            long totalSubArrPossible = (leftSubArrLen * rightSubArrLen)%mod;

            sum = (long)(sum + (totalSubArrPossible * currElem)%mod)%mod;
        }
        
        return (int)(sum%mod);
    }
 
} 
