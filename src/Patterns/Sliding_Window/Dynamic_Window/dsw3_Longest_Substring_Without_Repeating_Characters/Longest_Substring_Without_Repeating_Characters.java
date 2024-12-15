package Patterns.Sliding_Window.Dynamic_Window.dsw3_Longest_Substring_Without_Repeating_Characters; 
 
import java.util.HashSet;

import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/longest-substring-without-repeating-characters/
Given a string s, find the length of the longest substring without repeating characters.
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.

Concept: Sliding Window (2 cond + check first then add i.e cal in if-else statement) + HashSet(to know if repeated character)
Return: Longest Valid Window Length
 */
public class Longest_Substring_Without_Repeating_Characters extends ProblemSolver { 
    public static void main(String[] args) { 
        new Longest_Substring_Without_Repeating_Characters().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String str = DataConvertor.toString(args[0]);
 
        int res = lengthOfLongestSubstring(str);
        System.out.println(res); 
    } 
 
    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> set = new HashSet<Character>();
        int start = 0;
        int end = 0;
        int maxLen = 0;

        while(end < s.length()){
            Character endCh = s.charAt(end);
            if(!set.contains(endCh)){ // Valid Window (== case)
                set.add(endCh);
                maxLen = Math.max(maxLen, end-start+1);
                end++;
            }
            else if(set.contains(endCh)){ // Not Valid Window (> case)
                Character startCh = s.charAt(start);
                while(set.contains(endCh)){
                    set.remove(startCh);
                    start++;
                }
            }
        }
        return maxLen;
    }
 
} 
