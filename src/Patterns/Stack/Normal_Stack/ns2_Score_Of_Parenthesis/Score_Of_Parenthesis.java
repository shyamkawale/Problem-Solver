package Patterns.Stack.Normal_Stack.ns2_Score_Of_Parenthesis;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/score-of-parentheses/
Given a balanced parentheses string s, return the score of the string.
The score of a balanced parentheses string is based on the following rule:
1. "()" has score 1.
2. AB has score A + B, where A and B are balanced parentheses strings.
3. (A) has score 2 * A, where A is a balanced parentheses string.

Example 1:
Input: s = "()"
Output: 1

Example 2:
Input: s = "(())"
Output: 2

Example 3:
Input: s = "()()"
Output: 2

 */
public class Score_Of_Parenthesis extends ProblemSolver {

    public static void main(String[] args) {
        new Score_Of_Parenthesis().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);

        int res1 = scoreOfParentheses(s);
        int res2 = scoreOfParentheses_Optimized(s);
        System.out.println(res1 + " " + res2);
    }

    // Approach 1:
    public int scoreOfParentheses(String s) {
        int score = 0;
        Deque<Paranthesis> stack = new ArrayDeque<>();
        for(Character ch: s.toCharArray()) {
            if(ch == '(') {
                stack.push(new Paranthesis());
            }
            else {
                Paranthesis popped = stack.pop();
                if(stack.isEmpty()) {
                    score = score + popped.score;
                }
                else{
                    if(!stack.peek().visited) {
                        stack.peek().setScore(2*popped.score);
                    }
                    else{
                        stack.peek().addScore(2*popped.score);
                    }
                }
            }
        }

        return score;
    }

    class Paranthesis {
        Character ch;
        int score;
        boolean visited;

        public Paranthesis () {
            this.ch = '(';
            this.score = 1;
            visited = false;
        }

        public void setScore(int score) {
            this.score = score;
            this.visited = true;
        }

        public void addScore(int score) {
            this.score = this.score + score;
            this.visited = true;
        }
    }

    // Optimized version using a single stack
    // This version uses a stack to keep track of the current score at each level of parentheses
    public int scoreOfParentheses_Optimized(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);  // Initial score

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                stack.push(0);  // New frame
            } else {
                int inner = stack.pop();  // Score inside current ()
                int outer = stack.pop();  // Score before this ()
                stack.push(outer + Math.max(2 * inner, 1));  // Apply rule
            }
        }

        return stack.pop();
    }

}
