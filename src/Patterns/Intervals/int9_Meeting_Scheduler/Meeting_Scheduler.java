package Patterns.Intervals.int9_Meeting_Scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://takeuforward.org/plus/dsa/problems/meeting-scheduler

You are given two lists of availability slots for two people. 
Each slot is a list of two integers [start, end], 
representing the inclusive start time and the exclusive end time of that person's available time.

Your task is to find the earliest time slot that is at least duration minutes long and 
is common between both people's availability. 
If there is no such slot, return an empty list.

Example 1
Input: slots1 = [[10, 50], [60, 120], [140, 210]], slots2 = [[0, 15], [60, 70]], duration = 8
Output: [60, 68]
Explanation:
The only overlapping slot that is at least 8 minutes long is [60, 68].

Example 2
Input: slots1 = [[10, 50], [60, 120], [140, 210]], slots2 = [[0, 15], [60, 70]], duration = 12
Output: []
Explanation:
Although [60, 70] overlaps, it is only 10 minutes long, which is less than the required 12 minutes.
*/
public class Meeting_Scheduler extends ProblemSolver {
    public static void main(String[] args) {
        new Meeting_Scheduler().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] slots1 = DataConvertor.to2DIntArray(args[0]);
        int[][] slots2 = DataConvertor.to2DIntArray(args[1]);
        int duration = DataConvertor.toInt(args[2]);

        List<Integer> res = minAvailableDuration(slots1, slots2, duration);
        System.out.println(res);
    }

    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        //Your Code Goes Here
        int len1 = slots1.length;
        int len2 = slots2.length;

        Arrays.sort(slots1, (a, b) -> {
            if(a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        Arrays.sort(slots2, (a, b) -> {
            if(a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        int idx1 = 0;
        int idx2 = 0;

        while(idx1 < len1 && idx2 < len2) {
            int intersectStart = Math.max(slots1[idx1][0], slots2[idx2][0]);
            int intersectEnd = Math.min(slots1[idx1][1], slots2[idx2][1]);
            
            if (intersectEnd - intersectStart >= duration) {
                return List.of(intersectStart, intersectStart + duration);
            }

            if(slots1[idx1][1] < slots2[idx2][1]) {
                idx1++;
            }
            else {
                idx2++;
            }
        }

        return new ArrayList<>();
    }
}
