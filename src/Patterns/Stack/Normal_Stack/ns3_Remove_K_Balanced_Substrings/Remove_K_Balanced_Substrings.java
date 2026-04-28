package Patterns.Stack.Normal_Stack.ns3_Remove_K_Balanced_Substrings;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/remove-k-balanced-substrings/description/

You are given a string s consisting of '(' and ')', and an integer k.

A string is k-balanced if it is exactly k consecutive '(' followed by k consecutive ')', 
i.e., '(' * k + ')' * k.

For example, if k = 3, k-balanced is "((()))".

You must repeatedly remove all non-overlapping k-balanced substrings from s, 
and then join the remaining parts. Continue this process until no k-balanced substring exists.

Return the final string after all possible removals.
*/
public class Remove_K_Balanced_Substrings extends ProblemSolver {

    public static void main(String[] args) {
        new Remove_K_Balanced_Substrings().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        int k = DataConvertor.toInt(args[1]);

        String res = removeSubstring(s, k);
        System.out.println(res);
    }

    public String removeSubstring(String s, int k) {
        Deque<Parenthesis> stack = new ArrayDeque<>();

        for(Character ch: s.toCharArray()) {
            int currScore = stack.isEmpty() ? 0 : stack.peek().score;

            if(ch == '(') {
                if(currScore < k) {
                    currScore++;
                }
                else if(currScore == k) {
                    currScore = k;
                }
                else if(currScore > k) {
                    currScore = 1;
                }
            }
            else {
                if(currScore < k) {
                    currScore = 0;
                }
                else {
                    currScore++;
                }
            }

            stack.push(new Parenthesis(ch, currScore));

            if(currScore == 2*k) {
                for(int i=0; i<2*k; i++) {
                    stack.pop();
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        while(!stack.isEmpty()) {
            sb.insert(0, stack.pop().ch);
        }

        return sb.toString();
    }

    public class Parenthesis {
        Character ch;
        int score;

        public Parenthesis (Character ch, int score) {
            this.ch = ch;
            this.score = score;
        }
    }

}
