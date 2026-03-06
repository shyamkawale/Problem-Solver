package Patterns.Dynamic_Programming.DP_on_Grids.Ninjas_Training;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
/*
https://www.naukri.com/code360/problems/ninja-s-training_3621003

Ninja is planing this ‘N’ days-long training schedule. 
Each day, he can perform any one of these three activities. 
(Running, Fighting Practice or Learning New Moves). 
Each activity has some merit points on each day. 
As Ninja has to improve all his skills, he can’t do the same activity in two consecutive days. 
Can you help Ninja find out the maximum merit points Ninja can earn?

You are given a 2D array of size N*3 ‘POINTS’ with the points corresponding to each day and activity. 
Your task is to calculate the maximum number of merit points that Ninja can earn.
*/
public class Ninjas_Training extends ProblemSolver {

    public static void main(String[] args) {
        new Ninjas_Training().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] points = DataConvertor.to2DIntArray(args[0]);

        int res1 = ninjaTraining_rec(points);
        int res2 = ninjaTraining_memo(points);
        int res3 = ninjaTraining_tabu(points);
        int res4 = ninjaTraining_tabuop(points);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    public int ninjaTraining_rec(int points[][]) {
        int n = points.length;

        // 3 tasks - 0, 1, 2. -1 => no task done on previous day
        return train1(n-1, -1, points);
    }

    public int train1(int day, int prevTask, int[][] points) {
        if(day < 0) {
            return 0;
        }

        int task1Max = 0;
        if(prevTask != 0) {
            task1Max = points[day][0] + train1(day-1, 0, points);
        }

        int task2Max = 0;
        if(prevTask != 1) {
            task2Max = points[day][1] + train1(day-1, 1, points);
        }

        int task3Max = 0;
        if(prevTask != 2) {
            task3Max = points[day][2] + train1(day-1, 2, points);
        }

        return Math.max(Math.max(task1Max, task2Max), task3Max);
    }

    private int ninjaTraining_memo(int[][] points) {
        int n = points.length;
        int[][] dp = new int[n][3];
        for(int i=0; i<n; i++) {
            Arrays.fill(dp[i], -1);
        }

        int ans = train2(n-1, -1, points, dp);
        return ans;
    }

    public int train2(int day, int prevTask, int[][] points, int[][] dp) {
        if(day < 0) {
            return 0;
        }

        int maxDayPoints = 0;

        if(prevTask != 0) {
            if(dp[day][0] == -1) {
                dp[day][0] = points[day][0] + train2(day-1, 0, points, dp);
            }
            maxDayPoints = Math.max(maxDayPoints, dp[day][0]);
        }

        if(prevTask != 1) {
            if(dp[day][1] == -1) {
                dp[day][1] = points[day][1] + train2(day-1, 1, points, dp);
            }
            maxDayPoints = Math.max(maxDayPoints, dp[day][1]);
        }

        if(prevTask != 2) {
            if(dp[day][2] == -1) {
                dp[day][2] = points[day][2] + train2(day-1, 2, points, dp);
            }
            maxDayPoints = Math.max(maxDayPoints, dp[day][2]);
        }

        return maxDayPoints;
    }

    private int ninjaTraining_tabu(int[][] points) {
        int n = points.length;
        int[][] dp = new int[n][3];
        
        dp[0][0] = points[0][0];
        dp[0][1] = points[0][1];
        dp[0][2] = points[0][2];

        for(int i=1; i<n; i++) {
            // task0
            dp[i][0] = points[i][0] + Math.max(dp[i-1][1], dp[i-1][2]);

            // task1
            dp[i][1] = points[i][1] + Math.max(dp[i-1][0], dp[i-1][2]);

            // task2
            dp[i][2] = points[i][2] + Math.max(dp[i-1][0], dp[i-1][1]);
        }

        return Math.max(Math.max(dp[n-1][0], dp[n-1][1]), dp[n-1][2]);
    }

    private int ninjaTraining_tabuop(int[][] points) {
        int n = points.length;
        
        int task0Prev = points[0][0];
        int task1Prev = points[0][1];
        int task2Prev = points[0][2];
        int task0Curr = task0Prev;
        int task1Curr = task1Prev;
        int task2Curr = task2Prev;

        for(int i=1; i<n; i++) {
            // task0
            task0Curr = points[i][0] + Math.max(task1Prev, task2Prev);

            // task1
            task1Curr = points[i][1] + Math.max(task0Prev, task2Prev);

            // task2
            task2Curr = points[i][2] + Math.max(task0Prev, task1Prev);

            task0Prev = task0Curr;
            task1Prev = task1Curr;
            task2Prev = task2Curr;
        }

        return Math.max(Math.max(task0Curr, task1Curr), task2Curr);
    }
}
