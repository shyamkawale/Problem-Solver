package Patterns.Stack.Normal_Stack.ns8_Remove_k_Adjacent_Duplicates_from_a_String;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/

You are given a string s and an integer k, a k duplicate removal consists of choosing k adjacent and equal letters from s and removing them, 
causing the left and the right side of the deleted substring to concatenate together.
We repeatedly make k duplicate removals on s until we no longer can.
Return the final string after all such duplicate removals have been made. It is guaranteed that the answer is unique.

Example 1:
Input: s = "abcd", k = 2
Output: "abcd"
Explanation: There's nothing to delete.

Example 2:
Input: s = "deeedbbcccbdaa", k = 3
Output: "aa"
Explanation: 
First delete "eee" and "ccc", get "ddbbbdaa"
Then delete "bbb", get "dddaa"
Finally delete "ddd", get "aa"

Example 3:
Input: s = "pbbcggttciiippooaais", k = 2
Output: "ps"
 */
public class Remove_k_Adjacent_Duplicates_from_a_String extends ProblemSolver {
    public static void main(String[] args) {
        new Remove_k_Adjacent_Duplicates_from_a_String().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        int k = DataConvertor.toInt(args[1]);

        String res1 = removeDuplicates1(s, k);
        System.out.println(res1);
    }

    // Using StringBuilder as a Stack - TLE!! ⏳
    // Time Complexity: O(n*k) in worst case, where n is the length of the string
    // Space Complexity: O(n) for the StringBuilder
    public String removeDuplicates1(String s, int k) {
       StringBuilder stack = new StringBuilder();
 
        for(Character ch: s.toCharArray()) {
            stack.append(ch);

            if(stack.length() < k) {
                continue;
            }

            boolean kDuplicate = true;
            int len = stack.length();
            for(int i=0; i<k; i++) {
                if(stack.charAt(len-i-1) != ch) {
                    kDuplicate = false;
                    break;
                }
            }

            if(!kDuplicate) {
                continue;
            }

            for(int j=0; j<k; j++) {
                stack.deleteCharAt(stack.length()-1);
            }
        }
        return stack.toString();
    }

}
