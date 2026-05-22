package Patterns.Intervals.int6_Meeting_Rooms_1;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://takeuforward.org/plus/dsa/problems/meeting-rooms

Given an array of meeting time intervals where intervals[i] = [starti, endi].
Determine if a person could attend all meetings.

Example 1
Input: intervals = [[1, 5], [3, 8], [6, 10], [12, 15]]
Output: false
Explanation: Overlapping meetings exist at [1,5] and [3,8], making it impossible to attend all.

Example 2
Input: intervals = [[2, 6], [7, 9], [10, 14], [15, 18]]
Output: true
Explanation: No overlapping meetings, so all can be attended.
*/
public class Meeting_Rooms_1 extends ProblemSolver {
    public static void main(String[] args) {
        new Meeting_Rooms_1().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] intervals = DataConvertor.to2DIntArray(args[0]);

        boolean res = canAttendMeetings(intervals);
        System.out.println(res);
    }

    public boolean canAttendMeetings(int[][] intervals) {
        int n = intervals.length;

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        for (int i=0; i<n-1; i++) {
            if (intervals[i][1] > intervals[i+1][0]) {
                return false;
            }
        }

        return true;
    }
}
