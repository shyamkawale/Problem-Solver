package Patterns.Heap.Normal_Heap.Sort_Characters_By_Frequency; 
 
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/sort-characters-by-frequency/
Given a string s, sort it in decreasing order based on the frequency of the characters. 
The frequency of a character is the number of times it appears in the string.
Return the sorted string. If there are multiple answers, return any of them.

Input: s = "tree"
Output: "eert"
Input: s = "cccaaa"
Output: "aaaccc"
Input: s = "Aabb"
Output: "bbAa"

sol1: Heap with Map.Entry
 */
public class Sort_Characters_By_Frequency extends ProblemSolver { 
    public static void main(String[] args) { 
        new Sort_Characters_By_Frequency().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String s = DataConvertor.toString(args[0]); 
 
        String res = frequencySort(s); 
        System.out.println(res); 
    } 
 
    // TC: O(n + ulogu + (ulogu + n))
    // SC: O(u + u)
    public String frequencySort(String s) {
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        for(Character ch: s.toCharArray()){
            freqMap.put(ch, freqMap.getOrDefault(ch, 0)+1);
        }

        Queue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<Map.Entry<Character, Integer>>((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        for(Map.Entry<Character, Integer> entry: freqMap.entrySet()){
            maxHeap.offer(entry);
        }

        StringBuilder sb = new StringBuilder("");
        while(!maxHeap.isEmpty()){
            Map.Entry<Character, Integer> polledEntry = maxHeap.poll();

            for(int i=0; i<polledEntry.getValue(); i++){
                sb.append(polledEntry.getKey());
            }
        }

        return sb.toString();
    } 
} 
