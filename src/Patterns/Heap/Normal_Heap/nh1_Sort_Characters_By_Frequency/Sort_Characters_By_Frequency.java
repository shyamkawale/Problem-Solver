package Patterns.Heap.Normal_Heap.nh1_Sort_Characters_By_Frequency;

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
sol1: Heap without Map.Entry
 */
public class Sort_Characters_By_Frequency extends ProblemSolver {
    public static void main(String[] args) {
        new Sort_Characters_By_Frequency().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);

        String res1 = frequencySort1(s);
        String res2 = frequencySort2(s);
        System.out.println(res1 + " " + res2);
    }

    // TC: O(n + ulogu + (ulogu + n))
    // SC: O(u + u)
    public String frequencySort1(String s) {
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        for (Character ch : s.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        Queue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getValue(), a.getValue()));
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            maxHeap.offer(entry);
        }

        StringBuilder sb = new StringBuilder("");
        while (!maxHeap.isEmpty()) {
            Map.Entry<Character, Integer> polledEntry = maxHeap.poll();

            for (int i = 0; i < polledEntry.getValue(); i++) {
                sb.append(polledEntry.getKey());
            }
        }

        return sb.toString();
    }

    public String frequencySort2(String s) {
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        for (Character ch : s.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        Queue<Character> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(freqMap.get(b), freqMap.get(a)));
        for (Character key : freqMap.keySet()) {
            maxHeap.offer(key);
        }

        StringBuilder sb = new StringBuilder("");
        while (!maxHeap.isEmpty()) {
            Character polledCh = maxHeap.poll();

            for (int i = 0; i < freqMap.get(polledCh); i++) {
                sb.append(polledCh);
            }
        }

        return sb.toString();
    }
}
