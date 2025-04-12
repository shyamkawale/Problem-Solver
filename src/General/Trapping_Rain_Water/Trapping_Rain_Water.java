package General.Trapping_Rain_Water;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/trapping-rain-water/
Given n non-negative integers representing an elevation map where the width of each bar is 1, 
compute how much water it can trap after raining.

Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.

Input: height = [4,2,0,3,2,5]
Output: 9

Logic: water on a building = Math.min(leftMax, rightMax) - height of that buiding
Sol1: Bruteforce => for every element find leftmax & rightMax => TC: O(n*(n+n)) => O(n^2), SC: O(1)
Sol2: suffixMax, prefixMax => for every element find suffixMax, prefixMax => TC: O(n+n+n) => O(3n), SC: O(2n)
Sol3: one array that will be used as suffixMax and prefixMax => TC: O(3n), SC: O(n)
Sol4: Two-pointers

 */
public class Trapping_Rain_Water extends ProblemSolver {
    public static void main(String[] args) {
        new Trapping_Rain_Water().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] height = DataConvertor.toIntArray(args[0]);
        int res1 = trap_bruteforce(height);
        int res2 = trap_sufMax_preMax(height);
        int res3 = trap_oneArr(height);
        int res4 = trap_twoPointers(height);
        System.out.println(res1 + " " + res2 + " " + res3 + " "+res4);

    }

    // [0,1,0,2,1,0,1,3,2,1, 2, 1]
    //  0 1 2 3 4 5 6 7 8 9 10 11

    //TC: O(N*(N+N)) => O(N*2N) => O(N^2)
    //SC: O(1)
    public int trap_bruteforce(int[] height) {
        int len = height.length;
        int waterSum = 0;
        for(int i=0; i<len; i++){
            int rightMax = 0;
            int leftMax = 0;
            for(int j=i; j<len; j++){ // to get right biggest bar
                // rightMax
                rightMax = Math.max(rightMax, height[j]);
            }
            for(int j=i; j>=0; j--){ // to get left biggest bar
                //leftGreatest
                leftMax = Math.max(leftMax, height[j]);
            }
            int waterOnTop = Math.min(leftMax, rightMax) - height[i]; // water logged on current bar
            waterSum = waterSum + waterOnTop;
        }

        return waterSum;
    }

    //TC: O(N + N + N) => O(3N)
    //SC: O(N + N) => O(2N)
    public int trap_sufMax_preMax(int[] height) {
        int len = height.length;
        int waterSum = 0;

        int[] suffixMax = new int[len]; // to right Greatest of each element
        int rightMax = Integer.MIN_VALUE;
        for(int i=len-1; i>=0; i--){
            rightMax = Math.max(rightMax, height[i]);
            suffixMax[i] = rightMax;
        }

        int[] prefixMax = new int[len]; // to left Greatest of each element
        int leftMax = Integer.MIN_VALUE;
        for(int i=0; i<len; i++){
            leftMax = Math.max(leftMax, height[i]);
            prefixMax[i] = leftMax;
        }

        for(int i=0; i<len; i++){
            int waterOnTop = Math.min(prefixMax[i], suffixMax[i]) - height[i];
            waterSum = waterSum + waterOnTop;
        }

        return waterSum;
    }

    //TC: O(N + N + N) => O(3N)
    //SC: O(N)
    public int trap_oneArr(int[] height) {
        int len = height.length;
        int waterSum = 0;

        int[] suffixMax = new int[len]; // to store min(leftMax, rightMax)

        int rightMax = Integer.MIN_VALUE;
        for(int i=len-1; i>=0; i--){
            rightMax = Math.max(rightMax, height[i]);
            suffixMax[i] = rightMax;
        }

        int leftMax = Integer.MIN_VALUE;
        for(int i=0; i<len; i++){
            leftMax = Math.max(leftMax, height[i]);
            suffixMax[i] = Math.min(suffixMax[i], leftMax);
        }

        for(int i=0; i<len; i++){
            int waterOnTop = suffixMax[i] - height[i];
            waterSum = waterSum + waterOnTop;
        }

        return waterSum;
    }

    public int trap_twoPointers(int[] height){
        return 0;
    }
    
}
