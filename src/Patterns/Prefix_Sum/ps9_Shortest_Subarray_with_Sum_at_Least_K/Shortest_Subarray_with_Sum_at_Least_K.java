package Patterns.Prefix_Sum.ps9_Shortest_Subarray_with_Sum_at_Least_K;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Shortest_Subarray_with_Sum_at_Least_K extends ProblemSolver {

    public static void main(String[] args) {
        new Shortest_Subarray_with_Sum_at_Least_K().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int res = shortestSubarray(nums, k);
        System.out.println(res);
    }

    private int shortestSubarray(int[] nums, int k) {
        int len = nums.length;
        int[] P = new int[len + 1];
        for (int i = 0; i < len; i++) {
            P[i + 1] = P[i] + nums[i];
        }

        int minWin = Integer.MAX_VALUE;
        Deque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < len + 1; i++) {
            while (!deque.isEmpty() && P[i] - P[deque.peekFirst()] >= k) {
                minWin = Math.min(minWin, i - deque.pollFirst());
            }

            while (!deque.isEmpty() && P[i] <= P[deque.peekLast()]) {
                deque.pollLast();
            }

            deque.offerLast(i);
        }
        return minWin == Integer.MAX_VALUE ? -1 : minWin;
    }

}
