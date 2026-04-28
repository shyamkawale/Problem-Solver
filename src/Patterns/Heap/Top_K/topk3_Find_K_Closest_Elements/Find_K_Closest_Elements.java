package Patterns.Heap.Top_K.topk3_Find_K_Closest_Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/find-k-closest-elements/description/
Given a sorted 💡 integer array arr, two integers k and x, return the k closest integers to x in the array. 
The result should also be sorted in ascending order.

An integer a is closer to x than an integer b if:
|a - x| < |b - x|, or
|a - x| == |b - x| and a < b

Input: arr = [1,2,3,4,5], k = 4, x = 3
Output: [1,2,3,4]
Input: arr = [1,1,2,3,4,5], k = 4, x = -1
Output: [1,1,2,3]

sol1: Using Sorting TC: O(nlogn + k + nlogn)
sol2: Using Heap TC: O(nlogn + klogn + nlogn)
sol3: Two Pointers TC: O(n)

 */
public class Find_K_Closest_Elements extends ProblemSolver {

    public static void main(String[] args) {
        new Find_K_Closest_Elements().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);
        int x = DataConvertor.toInt(args[2]);

        List<Integer> res1 = findClosestElements_UsingSorting(nums, k, x);
        List<Integer> res2 = findClosestElements_UsingHeap(nums, k, x);
        List<Integer> res3 = findClosestElements_UsingHeap2(nums, k, x);
        List<Integer> res4 = findClosestElements_UsingTwoPointers(nums, k, x);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    // Using Sorting
    // TC: O(nlogn + klogn + nlogn)
    // SC: O(n + n)
    public List<Integer> findClosestElements_UsingSorting(int[] nums, int k, int x) {
        Integer[] numsObj = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        Arrays.sort(numsObj, new ArrayComparator(x));

        List<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < k; i++) {
            res.add(numsObj[i]);
        }

        Collections.sort(res);

        return res;
    }

    public class ArrayComparator implements Comparator<Integer> {
        int x;

        public ArrayComparator(int x) {
            this.x = x;
        }

        @Override
        public int compare(Integer a, Integer b) {
            int diffComparison = Integer.compare(Math.abs(a - x), Math.abs(b - x));
            if (diffComparison != 0) {
                return diffComparison;
            }

            int elemComparison = Integer.compare(a, b);

            return elemComparison;
        }
    }

    // ----------------------------------------------------------------------------------------------------------------

    // Using Heap
    // TC: O(nlogn + klogn + nlogn)
    // SC: O(n + n)
    public List<Integer> findClosestElements_UsingHeap(int[] nums, int k, int x) {
        Queue<Node> maxHeap = new PriorityQueue<Node>(new HeapComparator());
        Node.x = x;

        for (int n : nums) {
            Node node = new Node(n);
            maxHeap.offer(node);
            if(maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // List<Integer> res = new ArrayList<Integer>();
        // while (!maxHeap.isEmpty()) {
        //     res.add(maxHeap.poll().elem);
        // }
        // Collections.sort(res);
        // return res;

        List<Integer> ans = maxHeap.stream()
                .map(node -> node.elem)
                .sorted()
                .toList();

        return ans;
    }

    public class Node {
        static int x;
        int elem;
        int diff;

        public Node(int elem) {
            this.elem = elem;
            this.diff = Math.abs(elem - x);
        }
    }

    public class HeapComparator implements Comparator<Node> {
        @Override
        public int compare(Node a, Node b) {
            int diffComparison = Integer.compare(b.diff, a.diff);
            if (diffComparison != 0) {
                return diffComparison;
            }

            int elemComparison = Integer.compare(b.elem, a.elem);

            return elemComparison;
        }
    }

    // Using Heap with lambda comparator
    // TC: O(nlogk + nlogn)
    // SC: O(n + n)
    public List<Integer> findClosestElements_UsingHeap2(int[] nums, int k, int x) {
        int len = nums.length;
        Queue<Integer> maxHeap = new PriorityQueue<>((a, b) -> {
            if(Math.abs(b-x) != Math.abs(a-x)) {
                return Integer.compare(Math.abs(b-x), Math.abs(a-x));
            }
            return Integer.compare(b, a);
        });

        for(int n: nums) {
            maxHeap.offer(n);
            if(maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        List<Integer> res = maxHeap.stream()
                                .sorted()
                                .toList();

        return res;
    }

    // -----------------------------------------------------------------------------------------

    // Two Pointers Approach (Becoz array is sorted)
    // TC: O(n)
    // SC: O(n)
    public List<Integer> findClosestElements_UsingTwoPointers(int[] nums, int k, int x) {
        int left = 0;
        int right = nums.length - 1;

        while (right - left >= k) {
            if (Math.abs(nums[left] - x) > Math.abs(nums[right] - x)) {
                left++;
            } else {
                right--;
            }
        }

        List<Integer> res = new ArrayList<Integer>(k);
        for (int i = left; i <= right; i++) {
            res.add(nums[i]);
        }

        return res;
    }

}
