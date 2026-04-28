package Patterns.Heap.Top_K.topk5_Top_K_Frequent_Words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/top-k-frequent-words/description/

Given an array of strings words and an integer k, return the k most frequent strings.

Return the answer sorted by the frequency from highest to lowest. 
Sort the words with the same frequency by their lexicographical order.

Example:
Input: words = ["i","love","leetcode","i","love","coding"], k = 2
Output: ["i","love"]
Explanation: "i" and "love" are the two most frequent words.
Note that "i" comes before "love" due to a lower alphabetical order.

*/
public class Top_K_Frequent_Words extends ProblemSolver {

    public static void main(String[] args) {
        new Top_K_Frequent_Words().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String[] words = DataConvertor.toTypeArray(args[0], String.class);
        int k = DataConvertor.toInt(args[1]);

        List<String> res1 = topKFrequent_standard(words, k);
        List<String> res2 = topKFrequent_heap2(words, k);
        System.out.println(res1 + " " + res2);
    }

    public List<String> topKFrequent_standard(String[] words, int k) {
        Map<String, Integer> freqMap = new HashMap<String, Integer>();
        for(String s: words){
            freqMap.put(s, freqMap.getOrDefault(s, 0)+1);
        }

        Queue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<Map.Entry<String, Integer>>(new HeapComparator());

        for(Map.Entry<String, Integer> entry: freqMap.entrySet()){
            minHeap.offer(entry);
            if(minHeap.size() > k) {
                minHeap.poll();
            }
        }

        List<String> res = new ArrayList<String>();

        while(!minHeap.isEmpty()){
            res.add(minHeap.poll().getKey());
        }

        Collections.reverse(res);
        return res;
    }

    public class HeapComparator implements Comparator<Map.Entry<String, Integer>>{
        @Override
        public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b){
            int freqComparison = Integer.compare(a.getValue(), b.getValue());

            if(freqComparison != 0){
                return freqComparison;
            }

            int lexicalComparison = b.getKey().compareTo(a.getKey());

            return lexicalComparison;
        }
    }

    public List<String> topKFrequent_heap2(String[] words, int k) {
        Map<String, Integer> freqMap = new HashMap<String, Integer>();
        for (String s : words) {
            freqMap.put(s, freqMap.getOrDefault(s, 0) + 1);
        }

        Queue<String> maxHeap = new PriorityQueue<String>(new HeapComparator2(freqMap));
        for (String key : freqMap.keySet()) {
            maxHeap.offer(key);
        }

        List<String> res = new ArrayList<String>();
        for (int i = 0; i < k; i++) {
            res.add(maxHeap.poll());
        }

        return res;
    }

    public class HeapComparator2 implements Comparator<String> {
        Map<String, Integer> freqMap;

        public HeapComparator2(Map<String, Integer> freqMap) {
            this.freqMap = freqMap;
        }

        @Override
        public int compare(String a, String b) {
            int freqComparison = Integer.compare(freqMap.get(b), freqMap.get(a));

            if (freqComparison != 0) {
                return freqComparison;
            }

            int lexicalComparison = a.compareTo(b);

            return lexicalComparison;
        }
    }
}
