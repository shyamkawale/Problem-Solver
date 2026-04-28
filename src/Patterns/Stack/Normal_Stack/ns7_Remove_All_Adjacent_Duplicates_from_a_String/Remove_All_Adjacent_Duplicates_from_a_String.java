package Patterns.Stack.Normal_Stack.ns7_Remove_All_Adjacent_Duplicates_from_a_String;

import java.util.Stack;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/

You are given a string s consisting of lowercase English letters. A duplicate removal consists of choosing two adjacent and equal letters and removing them.
We repeatedly make duplicate removals on s until we no longer can.
Return the final string after all such duplicate removals have been made. It can be proven that the answer is unique.

Example 1:
Input: s = "abbaca"
Output: "ca"
Explanation: 
For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.  
The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".

Example 2:
Input: s = "azxxzy"
Output: "ay"
 */
public class Remove_All_Adjacent_Duplicates_from_a_String extends ProblemSolver {

    public static void main(String[] args) {
        new Remove_All_Adjacent_Duplicates_from_a_String().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);

        String res1 = removeDuplicates_stack(s);
        String res2 = removeDuplicates_stringbuilder(s);
        System.out.println(res1 + " " + res2);
    }

    public String removeDuplicates_stack(String s) {
        String res = "";
        Stack<Character> stack = new Stack<>();
        for(Character ch : s.toCharArray()){

            if(!stack.isEmpty() && stack.peek() == ch) {
                    stack.pop();
                }
            else {
                stack.push(ch);
            }
        }

        while(!stack.isEmpty()){
            res = stack.pop() + res;
        }
        return res;
    }

    // using StringBuilder as a Stack
    // This is more efficient than using Stack<Character> as it avoids the overhead of stack operations
    public String removeDuplicates_stringbuilder(String s) {
        StringBuilder sb = new StringBuilder();
        for(Character ch : s.toCharArray()){
            int len = sb.length();

            if(len > 0 && sb.charAt(len-1) == ch) {
                sb.deleteCharAt(len-1);
            }
            else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}
