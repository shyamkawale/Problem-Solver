package Patterns.Binary_Search.BinarySearch_On_Answer.bsa1_Square_Root_In_Logn; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/sqrtx/
Given a non-negative integer x, return the square root of x rounded down to the nearest integer. The returned integer should be non-negative as well.
Example 1:
Input: x = 4
Output: 2
Explanation: The square root of 4 is 2, so we return 2.

Example 2:
Input: x = 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since we round it down to the nearest integer, 2 is returned.

Concept: Binary Search on Answer
 */
public class Square_Root_In_Logn extends ProblemSolver { 
    public static void main(String[] args) { 
        new Square_Root_In_Logn().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int num = DataConvertor.toInt(args[0]); 
 
        int res = mySqrt(num); 
        System.out.println(res); 
    } 
 
    // TC: O(log(num))
    public int mySqrt(int num) {
        int left = 0; // min answer possible
        int right = num; // max answer possible
        int ans = 0;

        // 0, 1,  2, 3, 4, 5, 6, 7, 8
        // T< T< T<= F> F> F> F> F> F>
        while(left <= right){
            int mid = left + (int)((right-left)/2);
            if(isSquareLess(num, mid)){
                ans = mid;
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }

        return ans;
    }

    public boolean isSquareLess(int num, int k){
        if(k == 0) return true;
        if(k <= num/k) return true;
        return false; 
    }
 
} 
