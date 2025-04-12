package Patterns.Heap.Top_K.Kth_Largest_Element_in_an_Array; 
 
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/kth-largest-element-in-an-array/
Given an integer array nums and an integer k, return the kth largest element in the array.

sol1: Using Sorting TC: O(nlogn)
sol2: Heap TC: O(nlogk)
sol3: quick-select(maybe similar to quick sort)
sol4: counting sort
 */
public class Kth_Largest_Element_in_an_Array extends ProblemSolver { 
    public static void main(String[] args) { 
        new Kth_Largest_Element_in_an_Array().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);
 
        int res1 = findKthLargest_UsingSorting(nums, k); 
        int res2 = findKthLargest_UsingHeaps(nums, k); 
        System.out.println(res1 + " " + res2); 
    }

    // TC: O(nlogn)
    // SC: O(1)
    public int findKthLargest_UsingSorting(int[] nums, int k) {
        int len = nums.length;
        Arrays.sort(nums);

        return nums[len-k];
    }
 
    // TC: O(nlogk)
    // SC: O(k) - Heap of size k
    public int findKthLargest_UsingHeaps(int[] nums, int k) {
        Queue<Integer> minHeap = new PriorityQueue<Integer>();

        for(int n: nums){
            minHeap.offer(n);
            if(minHeap.size() > k){
                minHeap.poll();
            }
        }

        return minHeap.peek();
    } 
 
} 
