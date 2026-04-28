package Patterns.Intervals.Insert_Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

/*
https://leetcode.com/problems/insert-interval/description/

You are given an array of non-overlapping intervals intervals 
where intervals[i] = [starti, endi] represent the start and the end of the ith interval 
and intervals is sorted in ascending order by starti. 

You are also given an interval newInterval = [start, end] that represents the start and end of another interval.

Insert newInterval into intervals such that intervals is still sorted in ascending order by starti 
and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).

Return intervals after the insertion.

Note that you don't need to modify intervals in-place. You can make a new array and return it.

Example 1:
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]

Example 2:
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
*/
public class Insert_Interval extends ProblemSolver {
    public static void main(String[] args) {
        new Insert_Interval().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] intervals = DataConvertor.to2DIntArray(args[0]);
        int[] newInterval = DataConvertor.toIntArray(args[1]);

        int[][] res1 = insert1(intervals, newInterval);
        int[][] res2 = insert2(intervals, newInterval);
        MatrixWrapper.printMatrix(res1);
        MatrixWrapper.printMatrix(res2);
    }

    // Approach 1: Sort and Merge - O(n log n)
    // Add new interval to list, sort by start time, then merge overlapping intervals
    public int[][] insert1(int[][] intervals, int[] newInterval) {
        List<int[]> list = new ArrayList<>();
        for (int[] arr : intervals) {
            list.add(arr);
        }
        list.add(newInterval);

        Collections.sort(list, (int[] a, int[] b) -> a[0] - b[0]);

        LinkedList<int[]> res = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            if (res.isEmpty() || res.getLast()[1] < list.get(i)[0]) {
                res.add(list.get(i));
            } else {
                res.getLast()[1] = Math.max(res.getLast()[1], list.get(i)[1]);
            }
        }

        return res.toArray(new int[res.size()][]);
    }

    // Approach 2: Three-Phase Linear Scan - O(n)
    // Since intervals are sorted: 
    // 1) Add non-overlapping before, 
    // 2) Merge overlapping, 
    // 3) Add remaining
    private int[][] insert2(int[][] intervals, int[] newInterval) {
        List<int[]> list = new ArrayList<>();
        int i = 0;

        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            list.add(intervals[i]);
            i++;
        }

        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }

        list.add(newInterval);

        while (i < intervals.length) {
            list.add(intervals[i]);
            i++;
        }

        int[][] res = new int[list.size()][2];
        for(int k=0 ; k<list.size(); k++) {
            res[k] = list.get(k);
        }

        return res;
    }

}
