package Patterns.Heap.Normal_Heap.nh2_Rank_of_Elements_in_Array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/rank-transform-of-an-array/

Given an array of integers arr, replace each element with its rank.

Example 1:
Input: arr = [40,10,20,30]
Output: [4,1,2,3]
Explanation: 40 is the largest element. 10 is the smallest. 20 is the second smallest. 30 is the third smallest.

Example 2:
Input: arr = [100,100,100]
Output: [1,1,1]
Explanation: Same elements share the same rank.

Example 3:
Input: arr = [37,12,28,9,100,56,80,5,12]
Output: [5,3,4,2,8,6,7,1,3]
*/
public class Rank_of_Elements_in_Array extends ProblemSolver {

    public static void main(String[] args) {
        new Rank_of_Elements_in_Array().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int[] res = arrayRankTransform(nums);
        System.out.println(Arrays.toString(res));
    }

    public int[] arrayRankTransform(int[] nums) {
        int len = nums.length;

        Queue<Integer> minHeap = new PriorityQueue<Integer>();
        for (int n : nums) {
            minHeap.offer(n);
        }
        
        Map<Integer, Integer> rankMap = new HashMap<Integer, Integer>();
        while (!minHeap.isEmpty()) {
            int polledElem = minHeap.poll();
            rankMap.putIfAbsent(polledElem, rankMap.size() + 1);
        }

        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = rankMap.get(nums[i]);
        }

        return res;
    }

}
