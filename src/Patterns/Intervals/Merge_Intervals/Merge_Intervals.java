package Patterns.Intervals.Merge_Intervals;

import java.util.Arrays;
import java.util.Stack;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

/*
https://leetcode.com/problems/merge-intervals/description/

Given an array of intervals where intervals[i] = [starti, endi], 
merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.

Example 1:
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].

Example 2:
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.
*/
public class Merge_Intervals extends ProblemSolver {

    public static void main(String[] args) {
        new Merge_Intervals().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] intervals = DataConvertor.to2DIntArray(args[0]);

        int[][] res = merge(intervals);
        MatrixWrapper.printMatrix(res);
    }

    // Sort by start time, then iterate and merge overlapping intervals using a
    // stack
    // TC: O(nlogn + n) - sorting dominates
    // SC: O(n) - stack can hold all intervals in worst case
    private int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        Stack<int[]> mergedStack = new Stack<int[]>();
        for (int i = 0; i < intervals.length; i++) {
            if (mergedStack.isEmpty() || mergedStack.peek()[1] < intervals[i][0]) {
                mergedStack.push(intervals[i]);
            } else {
                mergedStack.peek()[1] = Math.max(mergedStack.peek()[1], intervals[i][1]);
            }
        }

        return mergedStack.toArray(new int[mergedStack.size()][]);
    }

}
