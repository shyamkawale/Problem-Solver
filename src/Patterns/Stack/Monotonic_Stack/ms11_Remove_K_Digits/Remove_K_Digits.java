package Patterns.Stack.Monotonic_Stack.ms11_Remove_K_Digits; 
 
import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/remove-k-digits
Given string num representing a non-negative integer num, and an integer k, 
return the smallest possible integer after removing k digits from num.

// Concept: Monotonous Deque
// LOGIC: to keep smaller elements at left of stack and bigger elements a right(near top)
// so remove bigger from deque..
 */
public class Remove_K_Digits extends ProblemSolver { 
    public static void main(String[] args) { 
        new Remove_K_Digits().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String num = DataConvertor.toString(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        String res = removeKdigits(num, k); 
        System.out.println(res); 
    } 
 
    // TC: O(n+k + n) => O(2n+k) => O(2n) => O(n)
    // SC: O(n)
    public String removeKdigits(String num, int k) {
        if(num.length() == k) return "0";

        Deque<Character> deque = new ArrayDeque<Character>();
        int removeCnt = 0;

        for(Character ch: num.toCharArray()){
            int currVal = Integer.parseInt(ch.toString());

            // AIM: to keep smaller elements at left of stack and bigger elements a right(near top)
            // so remove bigger from stack..
            if(deque.isEmpty()){
                deque.offerLast(ch);
            }
            else{
                // remove bigger element from stack k times only. => so that stack will keep on adding smaller ones => becoz we can achieve smallest element when left digits are smallest.
                while(removeCnt < k && !deque.isEmpty() && Integer.parseInt(deque.peekLast().toString()) > currVal){
                    deque.pollLast();
                    removeCnt++;
                }
    
                deque.offerLast(ch);
            }
        }

        // remove from top (as starting of stack we have smaller elements)
        while(removeCnt < k && !deque.isEmpty()){
            deque.pollLast();
            removeCnt++;
        }

        // remove start 0s
        while(!deque.isEmpty() && deque.peekFirst() == '0'){
            deque.pollFirst();
        }

        // append elements in sb
        StringBuilder sb = new StringBuilder();
        while(!deque.isEmpty()){
            sb.append(deque.pollFirst());
        }

        return sb.isEmpty() ? "0" : sb.toString();
    }
 
} 
