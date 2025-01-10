package Patterns.Stack.Monotonic_Stack.ms8_Largest_Rectangle_in_Histogram; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 

/*
https://leetcode.com/problems/largest-rectangle-in-histogram/
Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, 
return the area of the largest rectangle in the histogram.

Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.

// concept: PSE + NSE 💪
// logic: find pse and nse idx.. find subarray len = nseIdx - pseIdx - 1, rectangle area for that subarray = len*element
 */
public class Largest_Rectangle_in_Histogram extends ProblemSolver {
    public static void main(String[] args) { 
        new Largest_Rectangle_in_Histogram().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] heights = DataConvertor.toIntArray(args[0]); 
 
        int res1 = largestRectangleArea_TwoPass(heights); 
        int res2 = largestRectangleArea_OnePass(heights);
        System.out.println(res1 + " " + res2); 
    } 

    // Two Pass solution
    // TC: O(n+n + n+n + n) => O(5n) => O(n)
    // SC: O(n+n + n+n) => pseIdx/nseIdx + 2 stack
    public int largestRectangleArea_TwoPass(int[] heights) {
        int len = heights.length;

        int[] pseIdx = findPSE(heights);
        int[] nseIdx = findNSE(heights);

        System.out.println("PSE:  "+Arrays.toString(pseIdx) + " \t\t\t ." + "NSE:  " + Arrays.toString(nseIdx));

        int maxArea = 0;
        for(int i=0; i<len; i++){
            // int prevSubArrLen = i-pseIdx[i];
            // int nextSubArrLen = nseIdx[i]-i;
            // int totalSubArrLen = prevSubArrLen + nextSubArrLen - 1;

            // calculate length from nse and pse directly
            int totalSubArrLen = nseIdx[i] - pseIdx[i] - 1;

            int area = totalSubArrLen * heights[i];
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
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

    private int[] findNSE(int[] arr){
        int len = arr.length;
        int[] nseIdx = new int[len];
        Deque<Integer> stack = new ArrayDeque<Integer>();

        // finding next nearest min
        for(int i=len-1; i>=0; i--){
            int currElem = arr[i];

            if(stack.isEmpty()){ // initial condition
                nseIdx[i] = len;
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] >= currElem){
                    stack.pop();
                }

                nseIdx[i] = stack.isEmpty() ? len : stack.peek(); // now Stack contains all smaller building (top => curr ke pass wala)

                stack.push(i);
            }
        }

        return nseIdx;
    }


    // One Pass solution
    // TC: O(n+n n) => O(3n) => O(n)
    // SC: O(n+n + n) => pseIdx/nseIdx + 1 stack
    public int largestRectangleArea_OnePass(int[] heights) {
        int len = heights.length;

        Deque<Integer> stack = new ArrayDeque<Integer>();
        int[] pseIdx = new int[len];
        int[] nseeIdx = new int[len];

        for(int i=0; i<len; i++){
            int currElem = heights[i];

            if(stack.isEmpty()){
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && heights[stack.peek()] >= currElem){
                    int topElem = stack.pop();
                    pseIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
                    nseeIdx[topElem] =  i;
                }

                stack.push(i);
            }
        }

        while(!stack.isEmpty()){
            int topElem = stack.pop();
            pseIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
            nseeIdx[topElem] =  len;
        }

        System.out.println("PSE:  "+Arrays.toString(pseIdx) + " \t\t\t +" + "NSEE: " + Arrays.toString(nseeIdx));

        int maxArea = 0;
        for(int i=0; i<len; i++){
            int totalSubArrLen = nseeIdx[i] - pseIdx[i] - 1;
            int area = totalSubArrLen * heights[i];
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
} 
