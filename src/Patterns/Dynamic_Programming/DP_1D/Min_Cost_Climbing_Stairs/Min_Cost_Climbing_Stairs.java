package Patterns.Dynamic_Programming.DP_1D.Min_Cost_Climbing_Stairs;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/min-cost-climbing-stairs/

You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost, you can either climb one or two steps.

You can either start from the step with index 0, or the step with index 1.

Return the minimum cost to reach the top of the floor.

Example 1:
Input: cost = [10,15,20]
Output: 15
Explanation: You will start at index 1.
- Pay 15 and climb two steps to reach the top.
The total cost is 15.

Example 2:
Input: cost = [1,100,1,1,1,100,1,1,100,1]
Output: 6
Explanation: You will start at index 0.
- Pay 1 and climb two steps to reach index 2.
- Pay 1 and climb two steps to reach index 4.
- Pay 1 and climb two steps to reach index 6.
- Pay 1 and climb one step to reach index 7.
- Pay 1 and climb two steps to reach index 9.
- Pay 1 and climb one step to reach the top.
The total cost is 6.

*/
public class Min_Cost_Climbing_Stairs extends ProblemSolver {
    public static void main(String[] args) {
        new Min_Cost_Climbing_Stairs().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] cost = DataConvertor.toIntArray(args[0]);

        int res = minCostClimbingStairs(cost);
        System.out.println(res);
    }

    public int minCostClimbingStairs(int[] cost) {
        int len = cost.length;

        int[] dp = new int[len];
        Arrays.fill(dp, -1);
        // becoz we are not doing pick/not-pick.. and problem says we can start from 0th stair or 1st stair
        return Math.min(helper(0, cost, dp), helper(1, cost, dp));
    }

    private int helper(int curr, int[] cost, int[] dp) {
        int len = cost.length;
        if(curr >= len) {
            return 0;
        }

        if(dp[curr] != -1) {
            return dp[curr];
        }

        int goNext = helper(curr+1, cost, dp) + cost[curr];
        int skipNext = helper(curr+2, cost, dp) + cost[curr];

        dp[curr] = Math.min(goNext, skipNext);
        return dp[curr];
    }
}
