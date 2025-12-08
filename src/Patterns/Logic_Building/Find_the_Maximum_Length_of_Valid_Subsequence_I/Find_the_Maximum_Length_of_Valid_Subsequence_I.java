package Patterns.Logic_Building.Find_the_Maximum_Length_of_Valid_Subsequence_I;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/find-the-maximum-length-of-valid-subsequence-i/

You are given an integer array nums.
A subsequence sub of nums with length x is called valid if it satisfies:

(sub[0] + sub[1]) % 2 == (sub[1] + sub[2]) % 2 == ... == (sub[x - 2] + sub[x - 1]) % 2.
Return the length of the longest valid subsequence of nums.

A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.
 */
public class Find_the_Maximum_Length_of_Valid_Subsequence_I extends ProblemSolver {

    public static void main(String[] args) {
        new Find_the_Maximum_Length_of_Valid_Subsequence_I().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res = maximumLength(nums);
        System.out.println(res);
    }

    /*
     * There are a total of four possible parity patterns for the subsequence:
     * 1. All elements are even.
     * 2. All elements are odd.
     * 3. Elements at odd indices are odd, and elements at even indices are even.
     * 4. Elements at odd indices are even, and elements at even indices are odd.
     */
    public int maximumLength(int[] nums) {
        int res = 0;
        int[][] patterns = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };

        for (int[] pattern : patterns) {
            int cnt = 0;
            for (int n : nums) {
                if (n % 2 == pattern[cnt % 2]) {
                    cnt++;
                }
            }

            res = Math.max(res, cnt);
        }

        return res;
    }
}
