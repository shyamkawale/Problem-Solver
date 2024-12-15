package Patterns.Sliding_Window.Dynamic_Window.dsw7_Minimum_Window_Substring;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/minimum-window-substring/
Given two strings s and t of lengths m and n respectively, return the minimum window 
substring of s such that every character in t (including duplicates) is included in the window. 
If there is no such substring, return the empty string "".

The testcases will be generated such that the answer is unique.
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
Input: s = "AaBb", t = "ab"
Output: "aBb"

// Concept: Sliding Window + Minimum window + hashmap
// Return Minimum Valid Window
 */
public class Minimum_Window_Substring extends ProblemSolver{
    public static void main(String[] args) {
        new Minimum_Window_Substring().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        String t = DataConvertor.toString(args[1]);

        String res = minWindow(s, t);
        System.out.println(res);
    }

    // TC: O(t + s) => O(2N)
    // SC: O(tMap + sMap) => O(2N)
    public String minWindow(String s, String t) {
        if(t.length() > s.length()) return "";

        Map<Character, Integer> tMap = new HashMap<Character, Integer>();
        for(char tCh: t.toCharArray()){
            tMap.put(tCh, tMap.getOrDefault(tCh, 0)+1);
        }

        Map<Character, Integer> sMap = new HashMap<Character, Integer>();
        int start = 0;
        int end = 0;
        String minWindow = "";
        int minWindowLen = s.length();
        int matchedCnt = 0;

        while(end < s.length()){
            char endCh = s.charAt(end);

            sMap.put(endCh, sMap.getOrDefault(endCh, 0)+1);
            if(tMap.containsKey(endCh) && sMap.get(endCh).equals(tMap.get(endCh))) matchedCnt++;

            if(matchedCnt < tMap.size()){ // InValid Window (when cond < k)
                end++;
            }
            else if(matchedCnt == tMap.size()){ // Valid Window (when cond == k) Minimum window so no scope for incrementing end(i.e cond > k)
                while(matchedCnt == tMap.size()){
                    char startCh = s.charAt(start);
                    // store answer
                    if(end-start+1 <= minWindowLen){
                        minWindowLen = end-start+1;
                        minWindow = s.substring(start, end+1);
                    }

                    //remove startCh from hashMap
                    sMap.put(startCh, sMap.get(startCh)-1);
                    if(sMap.get(startCh) <= 0){
                        sMap.remove(startCh);
                    }

                    // remove startCh count from matchedCnt
                    if(tMap.containsKey(startCh) && sMap.getOrDefault(startCh, 0) < tMap.get(startCh)){
                        matchedCnt--;
                    }

                    start++;
                }
                end++;
            }
        }
        return minWindow;
    }
}
