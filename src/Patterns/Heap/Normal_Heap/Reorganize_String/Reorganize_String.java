package Patterns.Heap.Normal_Heap.Reorganize_String; 
 
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 

/*
https://leetcode.com/problems/reorganize-string/
Given a string s, rearrange the characters of s so that any two adjacent characters are not the same.
Return any possible rearrangement of s or return "" if not possible.

Input: s = "aab"
Output: "aba"
Input: s = "aaab"
Output: ""

sol: Heap using Map.Entry

 */
public class Reorganize_String extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Reorganize_String().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String s = DataConvertor.toString(args[0]); 
 
        String res = reorganizeString(s); 
        System.out.println(res); 
    } 
 
    // TC: O(n + ulogu + nlogu) u=> unique elements
    // SC: O(u + u)
    public String reorganizeString(String s) {
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        for(Character ch: s.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0)+1);
        }

        Queue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<Map.Entry<Character, Integer>>((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        for(Map.Entry<Character, Integer> entry: freqMap.entrySet()){
            maxHeap.offer(entry);
        }

        StringBuilder sb = new StringBuilder("");
        Map.Entry<Character, Integer> prevEntry = null;

        while(!maxHeap.isEmpty()){
            Map.Entry<Character, Integer> currEntry = maxHeap.poll();

            sb.append(currEntry.getKey());
            currEntry.setValue(currEntry.getValue()-1);

            if(prevEntry != null && prevEntry.getValue() > 0){
                maxHeap.offer(prevEntry);
            }

            prevEntry = currEntry;
        }

        if(sb.length() != s.length()){
            return "";
        }

        return sb.toString();
    } 
} 
