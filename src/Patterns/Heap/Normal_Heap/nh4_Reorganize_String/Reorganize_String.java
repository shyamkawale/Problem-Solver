package Patterns.Heap.Normal_Heap.nh4_Reorganize_String;

import java.util.HashMap;
import java.util.LinkedList;
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

sol1: Heap using Map.Entry
sol2: Heap + WaitQueue (similar to similar to (Rearrange String K Distance Apart))
 */
public class Reorganize_String extends ProblemSolver {

    public static void main(String[] args) {
        new Reorganize_String().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);

        String res1 = reorganizeString(s);
        String res2 = reorganizeString2(s);
        System.out.println(res1 + " " + res2);
    }

    // TC: O(n + ulogu + nlogu) u=> unique elements
    // SC: O(u + u)
    public String reorganizeString(String s) {
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        for (Character ch : s.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        Queue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<Map.Entry<Character, Integer>>(
                (a, b) -> Integer.compare(b.getValue(), a.getValue()));
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            maxHeap.offer(entry);
        }

        StringBuilder sb = new StringBuilder("");
        Map.Entry<Character, Integer> prevEntry = null;

        while (!maxHeap.isEmpty()) {
            Map.Entry<Character, Integer> currEntry = maxHeap.poll();

            sb.append(currEntry.getKey());
            currEntry.setValue(currEntry.getValue() - 1);

            if (prevEntry != null && prevEntry.getValue() > 0) {
                maxHeap.offer(prevEntry);
            }

            prevEntry = currEntry;
        }

        if (sb.length() != s.length()) {
            return "";
        }

        return sb.toString();
    }

    // Approach similar to (Rearrange String K Distance Apart)
    public String reorganizeString2(String s) {
        // map to store character with its frequency.
        Map<Character, Integer> map = new HashMap<>();
        for(Character ch: s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0)+1);
        }

        // maxHeap to get highest frequency character
        Queue<Character> maxHeap = new PriorityQueue<>((a, b) ->  map.get(b) - map.get(a));
        for(Character ch: map.keySet()) {
            maxHeap.offer(ch);
        }

        // Queue to hold characters that are "cooling down"
        Queue<Character> waitQueue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        while(!maxHeap.isEmpty()) {
            Character currCh = maxHeap.poll();
            sb.append(currCh);

            // Decrease the frequency
            map.put(currCh, map.get(currCh) - 1);

            // Add to waitQueue to block it from being used immediately
            waitQueue.offer(currCh);

            // Release characters back to Heap only if they have waited k=1 distance
            if (waitQueue.size() > 1) {
                Character released = waitQueue.poll();
                // Only add back if it still has occurrences left
                if (map.get(released) > 0) {
                    maxHeap.offer(released);
                }
            }
        }

        return sb.length() == s.length() ? sb.toString() : "";
    }
}
