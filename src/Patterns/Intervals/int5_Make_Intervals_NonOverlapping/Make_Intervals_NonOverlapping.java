package Patterns.Intervals.int5_Make_Intervals_NonOverlapping;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/non-overlapping-intervals/description/

Given an array of intervals intervals where intervals[i] = [starti, endi], 
return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

Note that intervals which only touch at a point are non-overlapping. 
For example, [1, 2] and [2, 3] are non-overlapping.

Example 1:
Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
Output: 1
Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.

Example 2:
Input: intervals = [[1,2],[1,2],[1,2]]
Output: 2
Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.
*/
public class Make_Intervals_NonOverlapping extends ProblemSolver {
    public static void main(String[] args) {
        new Make_Intervals_NonOverlapping().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] intervals = DataConvertor.to2DIntArray(args[0]);

        int res = eraseOverlapIntervals(intervals);
        System.out.println(res);
    }

    public int eraseOverlapIntervals(int[][] intervals) {
        int len = intervals.length;

        Arrays.sort(intervals, (a, b) -> {
            if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
            return Integer.compare(a[0], b[0]);
        });

        int cnt = 0;
        int lastEnd = Integer.MIN_VALUE;

        for (int[] interval : intervals) {
            int currStart = interval[0];
            int currEnd = interval[1];

            if (currStart >= lastEnd) {
                lastEnd = currEnd;
            } else {
                cnt++;
            }
        }

        return cnt;
    }
}
