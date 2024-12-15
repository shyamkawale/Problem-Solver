package Patterns.Sliding_Window.Dynamic_Window.dsw5_Longest_Repeating_Character_Replacement; 
 
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/longest-repeating-character-replacement
You are given a string s and an integer k. You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most k times.
Return the length of the longest substring containing the same letter you can get after performing the above operations.

Example 1:
Input: s = "ABAB", k = 2
Output: 4
Explanation: Replace the two 'A's with two 'B's or vice versa.
Example 2:
Input: s = "AABACDBBA", k = 1
Output: 4

Concept: Sliding Window + freqArr + (we can replace character whose freq < maxFreq element)
Return: Longest Valid Window Length
 */
public class Longest_Repeating_Character_Replacement extends ProblemSolver { 
    public static void main(String[] args) { 
        new Longest_Repeating_Character_Replacement().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String s = DataConvertor.toString(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res1 = characterReplacement_Using_Map(s, k); 
        int res2 = characterReplacement_Using_freqArr(s, k);

        System.out.println(res1 + " " + res2); 
    } 

    //TC: O(n + n) => O(n)
    //SC: O(26)
    public int characterReplacement_Using_freqArr(String s, int k) {
        int start = 0;
        int end = 0;
        int[] freqMap = new int[26];
        int maxFreq = 0;
        int maxLen = 0;
        while(end < s.length()){
            Character endCh = s.charAt(end);

            freqMap[endCh - 'A']++;
            maxFreq = Math.max(maxFreq, freqMap[endCh - 'A']); //IMP: to cal max freq of elements currently in window
            int remFreq = (end - start + 1) - maxFreq;

            if(remFreq <= k){ // Valid Window
                maxLen = Math.max(maxLen, end-start+1);
                end++;
            }
            else if(remFreq > k){ // Invalid Window
                while(remFreq > k){
                    Character startCh = s.charAt(start);
                    freqMap[startCh - 'A']--;

                    start++;
                    remFreq = (end - start + 1) - maxFreq;
                }

                freqMap[endCh - 'A']--; // remove endCh to revaluate window with endCh
            }
        }

        return maxLen;
    }
 
    public int characterReplacement_Using_Map(String s, int k) {
        int start = 0;
        int end = 0;
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        int maxFreq = 0;
        int maxLen = 0;
        while(end < s.length()){
            Character endCh = s.charAt(end);
            freqMap.put(endCh, freqMap.getOrDefault(endCh, 0)+1);
            maxFreq = (int)Collections.max(freqMap.values());
            int remFreq = (end - start + 1) - maxFreq;

            if(remFreq <= k){
                maxLen = Math.max(maxLen, end-start+1);
                end++;
            }
            else if(remFreq > k){
                while(remFreq > k){
                    Character startCh = s.charAt(start);
                    freqMap.replace(startCh, freqMap.get(startCh)-1);
                    if(freqMap.get(startCh) == 0) freqMap.remove(startCh);

                    maxFreq = (int)Collections.max(freqMap.values());
                    start++;
                    remFreq = (end - start + 1) - maxFreq;
                }
                freqMap.replace(endCh, freqMap.get(endCh)-1);
            }
        }

        return maxLen;
    }
 
} 
