package Patterns.Heap.Top_K.topk1_Kth_Largest_Element_in_an_Array;

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
sol4: counting sorts
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
        int res3 = findKthLargest_UsingQuickSelect(nums, k);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    // TC: O(nlogn)
    // SC: O(1)
    public int findKthLargest_UsingSorting(int[] nums, int k) {
        int len = nums.length;
        Arrays.sort(nums);

        return nums[len - k];
    }

    // TC: O(nlogk)
    // SC: O(k) - Heap of size k
    public int findKthLargest_UsingHeaps(int[] nums, int k) {
        Queue<Integer> minHeap = new PriorityQueue<Integer>();

        for (int n : nums) {
            minHeap.offer(n);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return minHeap.peek();
    }

    // TC: O(n) average, O(n^2) worst
    // SC: O(1)
    private int findKthLargest_UsingQuickSelect(int[] nums, int k) {
        int targetIdx = nums.length-k;
        return quickSelect(nums, 0, nums.length-1, targetIdx);
    }

    private int quickSelect(int[] nums, int left, int right, int targetIdx) {
        int pivotIdx = partition(nums, left, right);

        if(pivotIdx == targetIdx) {
            return nums[pivotIdx];
        }
        else if(targetIdx < pivotIdx) {
            return quickSelect(nums, left, pivotIdx-1, targetIdx);
        }
        else {
            return quickSelect(nums, pivotIdx+1, right, targetIdx);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i=left;

        for(int j=left; j<right; j++) {
            if(nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);

        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
