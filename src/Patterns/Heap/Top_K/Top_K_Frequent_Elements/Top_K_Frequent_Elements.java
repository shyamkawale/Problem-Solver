package Patterns.Heap.Top_K.Top_K_Frequent_Elements; 
 
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/top-k-frequent-elements/description/

Given an integer array nums and an integer k, return the k most frequent elements. 
You may return the answer in any order.

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Input: nums = [1], k = 1
Output: [1]

sol1: Heap with Map.Entry<Integer, Integer> TC : O(n + nlogk + klogk)
sol2: Heap with Integer TC : O(n + nlogk + klogk) but Slower than sol1 with many hashcollision becoz of get() method in comparator
sol3: QuickSelect (Hoare, Lomuto) https://leetcode.com/problems/top-k-frequent-elements/editorial/
 */
public class Top_K_Frequent_Elements extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Top_K_Frequent_Elements().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int[] res1 = topKFrequent_UsingHeap1(nums, k); 
        int[] res2 = topKFrequent_UsingHeap2(nums, k);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2)); 
    } 
 
    // TC: O(n + nlogk + klogk)
    // SC: O(n + k + k)
    public int[] topKFrequent_UsingHeap1(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        for(int n: nums){
            freqMap.put(n, freqMap.getOrDefault(n, 0)+1);
        }

        Queue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<Map.Entry<Integer, Integer>>((a, b) -> Integer.compare(a.getValue(), b.getValue()));

        for(Map.Entry<Integer, Integer> entry: freqMap.entrySet()){
            minHeap.offer(entry);
            if(minHeap.size() > k){
                minHeap.poll();
            }
        }

        int[] res = new int[k];
        for(int i=0; i<k; i++){
            res[i] = minHeap.poll().getKey();
        }

        return res;
    }

    // TC: (n + nlogk + klogk) (Slower than sol1 as get() method of map can be O(logn) with hash collisions)
    // SC: (n + k + k) (Faster than sol1 as no overhaed of maintaining Map.Entry)
    public int[] topKFrequent_UsingHeap2(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        for(int n: nums){
            freqMap.put(n, freqMap.getOrDefault(n, 0)+1);
        }

        Queue<Integer> minHeap = new PriorityQueue<Integer>((a, b) -> Integer.compare(freqMap.get(a), freqMap.get(b)));

        for(int key: freqMap.keySet()){
            minHeap.offer(key);
            if(minHeap.size() > k){
                minHeap.poll();
            }
        }

        int[] res = new int[k];
        for(int i=0; i<k; i++){
            res[i] = minHeap.poll();
        }

        return res;
    }
    
} 
