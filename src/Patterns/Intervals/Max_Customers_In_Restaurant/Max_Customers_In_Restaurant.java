package Patterns.Intervals.Max_Customers_In_Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://cses.fi/problemset/task/1619

You are given the arrival and leaving times of n customers in a restaurant.
What was the maximum number of customers in the restaurant at any time?
*/
public class Max_Customers_In_Restaurant extends ProblemSolver {

    public static void main(String[] args) {
        new Max_Customers_In_Restaurant().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] intervals = DataConvertor.to2DIntArray(args[0]);

        int res = maxCustomerInRestaurant(intervals);
        System.out.println(res);
    }

    // maximum overlapping intervals
    // Time Complexity: O(nlogn) due to sorting events
    // Space Complexity: O(n) for storing events list
    private int maxCustomerInRestaurant(int[][] intervals) {
        int n = intervals.length;
        List<int[]> events = new ArrayList<>(2 * n);

        for (int i = 0; i < n; i++) {
            events.add(new int[]{intervals[i][0], +1});  // arrival
            events.add(new int[]{intervals[i][1], -1});  // leaving
        }

        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0]; // Sort by time ascending

            // If times are equal, process arrival (+1) before leaving (-1)
            // This maximizes the overlap count (i.e if person1 leaves and person2 comes at time t then both will be counted)
            // +1 should come before -1. In descending order of type: 1 > -1.
            return b[1] - a[1];
        });

        int currCustomers = 0;
        int maxCustomers = 0;
        for (int[] evt : events) {
            currCustomers = currCustomers + evt[1];
            maxCustomers = Math.max(maxCustomers, currCustomers);
        }

        return maxCustomers;
    }

}
