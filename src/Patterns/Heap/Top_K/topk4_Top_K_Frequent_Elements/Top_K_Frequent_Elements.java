package Patterns.Heap.Top_K.topk4_Top_K_Frequent_Elements;

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
        int[] res3 = topKFrequent_UsingQuickSelect(nums, k);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2) + " " + Arrays.toString(res3));
    }

    // TC: O(n + nlogk + klogk)
    // SC: O(n + k + k)
    public int[] topKFrequent_UsingHeap1(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        for (int n : nums) {
            freqMap.put(n, freqMap.getOrDefault(n, 0) + 1);
        }

        Queue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.getValue(), b.getValue())
        );

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = minHeap.poll().getKey();
        }

        return res;
    }

    // TC: (n + nlogk + klogk) (Slower than sol1 as get() method of map can be
    // O(logn) with hash collisions)
    // SC: (n + k + k) (Faster than sol1 as no overhaed of maintaining Map.Entry)
    public int[] topKFrequent_UsingHeap2(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        for (int n : nums) {
            freqMap.put(n, freqMap.getOrDefault(n, 0) + 1);
        }

        // we will store keys(numbers) but compare based on their frequency from freqMap
        Queue<Integer> minHeap = new PriorityQueue<Integer>((a, b) -> Integer.compare(freqMap.get(a), freqMap.get(b)));

        for (int key : freqMap.keySet()) {
            minHeap.offer(key);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = minHeap.poll();
        }

        return res;
    }

    private int[] topKFrequent_UsingQuickSelect(int[] nums, int k) {

        // Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

        // for (int n : nums) {
        //     freqMap.put(n, freqMap.getOrDefault(n, 0) + 1);
        // }

        // int[] distinctNums = freqMap.keySet()
        //                     .stream()
        //                     .mapToInt(Integer::intValue)
        //                     .toArray();
        // int targetIdx = distinctNums.length-k;
        // int[] ans = new int[];
        // quickSelect(distinctNums, 0, distinctNums.length-1, freqMap, targetIdx);
        return nums;
    }

    // private int quickSelect(int[] nums, int left, int right, Map<Integer, Integer> freqMap, int targetIdx) {
    //     int pivotIdx = partition(nums, left, right, freqMap);

    //     if(targetIdx < pivotIdx) {
    //         return quickSelect(nums, left, pivotIdx-1, freqMap, targetIdx);
    //     }
    //     else {
    //         return quickSelect(nums, pivotIdx+1, right, freqMap, targetIdx);
    //     }
    // }

    // private int partition(int[] nums, int left, int right, Map<Integer, Integer> freqMap) {
    //     int pivot = nums[right];
    //     int i=left;

    //     for(int j=left; j<right; j++) {
    //         if(freqMap.get(nums[j]) <= freqMap.get(pivot)) {
    //             swap(nums, i, j);
    //             i++;
    //         }
    //     }
    //     swap(nums, i, right);

    //     return i;
    // }

    // private void swap(int[] nums, int i, int j) {
    //     int temp = nums[i];
    //     nums[i] = nums[j];
    //     nums[j] = temp;
    // }

}
