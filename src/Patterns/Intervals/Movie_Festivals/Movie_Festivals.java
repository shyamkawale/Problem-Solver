package Patterns.Intervals.Movie_Festivals;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://cses.fi/problemset/task/1629

In a movie festival n movies will be shown. You know the starting and ending time of each movie. 
What is the maximum number of movies you can watch entirely?
*/
public class Movie_Festivals extends ProblemSolver {
    public static void main(String[] args) {
        new Movie_Festivals().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] intervals = DataConvertor.to2DIntArray(args[0]);

        int res = movieFestivals(intervals);
        System.out.println(res);
    }

    // Maximum Number of Non-Overlapping Intervals.
    // This class implements a greedy algorithm to find the maximum number of movie intervals
    // that can be attended without any overlap (also known as the Activity Selection Problem
    // or Interval Scheduling Maximization Problem).
    // 
    // Algorithm:
    // 1. Sort all intervals by their end time (and by start time as a tiebreaker)
    // 2. Greedily select intervals: pick an interval if its start time is >= the end time
    //    of the last selected interval
    // 3. This greedy approach guarantees the maximum number of non-overlapping intervals
    // TC: O(n log n) due to sorting
    // SC: O(n) for storing intervals
    private int movieFestivals(int[][] intervals) {
        int n = intervals.length;
        Arrays.sort(intervals, (a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];
            return a[0] - b[0];
        });

        int cnt = 0;
        int lastEnd = -1;
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            if (start >= lastEnd) {
                cnt++;
                lastEnd = end;
            }
        }

        return cnt;
    }

}
