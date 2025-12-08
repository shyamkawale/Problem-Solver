package Patterns.Stack.Normal_Stack.Find_the_Most_Competitive_Subsequence;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/find-the-most-competitive-subsequence
Given an integer array nums and a positive integer k, return the most competitive subsequence of nums of size k.

An array's subsequence is a resulting sequence obtained by erasing some (possibly zero) elements from the array.

We define that a subsequence a is more competitive than a subsequence b (of the same length) if in the first position where a and b differ,
subsequence a has a number less than the corresponding number in b. 
For example, [1,3,4] is more competitive than [1,3,5] because the first position they differ is at the final number, and 4 is less than 5.

Other description: Find Smallest Subsequence of size k

Example 1:
Input: nums = [3,5,2,6], k = 2
Output: [2,6]
Explanation: Among the set of every possible subsequence: {[3,5], [3,2], [3,6], [5,2], [5,6], [2,6]}, [2,6] is the most competitive.

Example 2:
Input: nums = [2,4,3,3,5,4,9,6], k = 4
Output: [2,3,3,4]
 */
public class Find_the_Most_Competitive_Subsequence extends ProblemSolver {

    public static void main(String[] args) {
        new Find_the_Most_Competitive_Subsequence().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int[] res = mostCompetitive(nums, k);
        System.out.println(Arrays.toString(res));
    }

    public int[] mostCompetitive(int[] nums, int k) {
        // we have to find smallest subsequence of size k
        int len = nums.length;
        int[] res = new int[k];
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i=0; i<len; i++) {
            int n = nums[i];
            int maxRemovalPossible = len - i;
            while(!stack.isEmpty() && stack.peek() > n && k-stack.size() < maxRemovalPossible) {
                stack.pop();
            }

            if(stack.size() < k) {
                stack.push(n);
            }
        }

        for(int j=k-1; j>=0; j--) {
            res[j] = stack.pop();
        }

        return res;
    }

}
