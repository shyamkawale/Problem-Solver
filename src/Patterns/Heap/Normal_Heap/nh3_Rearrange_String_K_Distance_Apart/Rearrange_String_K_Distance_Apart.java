package Patterns.Heap.Normal_Heap.nh3_Rearrange_String_K_Distance_Apart;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
/*
https://takeuforward.org/plus/dsa/problems/rearrange-string-k-distance-apart

Given a string s and an integer k, rearrange s such that the same characters are at least distance k from each other. 
If it is not possible to rearrange the string, return an empty string "".

Examples:
Input: s = "aabbcc", k = 3
Output: "abcabc"
Explanation: The same letters are at least a distance of 3 from each other.

Input: s = "aaabc", k = 3
Output: ""
Explanation: It is not possible to rearrange the string.

Solution: Pop Element and push again after k wait time (waitQueue)
*/
public class Rearrange_String_K_Distance_Apart extends ProblemSolver {
    public static void main(String[] args) {
        new Rearrange_String_K_Distance_Apart().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        int k = DataConvertor.toInt(args[1]);

        String res = rearrangeString(s, k);
        System.out.println(res);
    }

    public String rearrangeString(String s, int k) {
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

            // Release characters back to Heap only if they have waited k distance
            if (waitQueue.size() >= k) {
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
