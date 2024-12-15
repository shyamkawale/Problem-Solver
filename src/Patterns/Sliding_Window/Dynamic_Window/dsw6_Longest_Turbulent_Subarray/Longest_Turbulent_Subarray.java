package Patterns.Sliding_Window.Dynamic_Window.dsw6_Longest_Turbulent_Subarray; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 

/*
https://leetcode.com/problems/longest-turbulent-subarray/
Given an integer array arr, return the length of a maximum size turbulent subarray of arr.

A subarray is turbulent if the comparison sign flips between each adjacent pair of elements in the subarray.

More formally, a subarray [arr[i], arr[i + 1], ..., arr[j]] of arr is said to be turbulent if and only if:

For i <= k < j:
arr[k] > arr[k + 1] when k is odd, and
arr[k] < arr[k + 1] when k is even.
Or, for i <= k < j:
arr[k] > arr[k + 1] when k is even, and
arr[k] < arr[k + 1] when k is odd.

Input: arr = [9,4,2,10,7,8,8,1,9]
Output: 5
Explanation: arr[1] > arr[2] < arr[3] > arr[4] < arr[5]
Concept: Sliding Window + variation(if valid window found then next valid window will not be part of prev valid window)
Return: Longest Valid Window Length
 */
public class Longest_Turbulent_Subarray extends ProblemSolver { 
    public static void main(String[] args) { 
        new Longest_Turbulent_Subarray().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int res = maxTurbulenceSize(arr); 
        System.out.println(res); 
    } 

    //TC: O(n)
    //SC: O(1)
    //[9,4,2,10,7,8,8,1,9]
    // 9>4>2<10>7<8=8>1<9
    //[9,4] [4,2,10,7,8] [8,8] [8,1,9]
    public int maxTurbulenceSize(int[] arr) {
        int start = 0;
        int end = 1;
        int cmpSign = 0; // comparison between end-1 and end;

        int maxSubArrLen = 1;

        while(end < arr.length){
            //returns 1 for >
            //returns 0 for =
            //returns -1 for <
            int curCmpSign = Integer.compare(arr[end-1], arr[end]);

            if(curCmpSign == 0){ // if end-1 & end are same then leave that window.
                start = end;
                end++;
            }
            else if(curCmpSign != cmpSign){ // valid window
                maxSubArrLen = Math.max(maxSubArrLen, end-start+1);
                cmpSign = curCmpSign;
                end++;
            }
            else if(curCmpSign == cmpSign){ // invalid window
                start = end-1;
                end++;
            }

        }
        return maxSubArrLen;
    }
 
} 
