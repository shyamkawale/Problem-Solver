package Patterns.Stack.Normal_Stack.ns3_Smallest_Subsequence_of_Distinct_Characters;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/
or
https://leetcode.com/problems/remove-duplicate-letters/

Given a string s, return the lexicographically smallest subsequence of s that contains all the distinct characters of s exactly once.

Example 1:
Input: s = "bcabc"
Output: "abc"

Example 2:
Input: s = "cbacdcbc"
Output: "acdb"
 */
public class Smallest_Subsequence_of_Distinct_Characters extends ProblemSolver {

    public static void main(String[] args) {
        new Smallest_Subsequence_of_Distinct_Characters().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);

        String res = smallestSubsequence(s);
        System.out.println(res);
    }

    // used seen array for O(1) lookup
    public String smallestSubsequence(String s) {
        int[] lastIdx = new int[26];
        boolean[] seen = new boolean[26];
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            lastIdx[s.charAt(i) - 'a'] = i;
        }

        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (seen[ch - 'a']) {
                continue;
            }

            while (!stack.isEmpty() && stack.peek() >= ch && lastIdx[stack.peek() - 'a'] > i) {
                seen[stack.pop() - 'a'] = false;
            }

            stack.push(ch);
            seen[ch - 'a'] = true;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pollLast());
        }

        return sb.toString();
    }

}
