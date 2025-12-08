package Patterns.Dynamic_Programming.DP_1D.Climbing_Stairs;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/climbing-stairs/description/

 */
public class Climbing_Stairs extends ProblemSolver {

    public static void main(String[] args) {
        new Climbing_Stairs().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);

        int res1 = climbStairs_rec1(n);
        int res2 = climbStairs_rec2(n);
        int res3 = climbStairs_memo(n);
        int res4 = climbStairs_tabu(n);
        int res5 = climbStairs_tabuop(n);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5);
    }

    // very noob recursion
    public int climbStairs_rec1(int n) {
        return cntStairs(0, n);
    }

    public int cntStairs(int currStep, int n) {
        if (currStep == n) {
            return 1;
        }

        int oneStepCnt = 0;
        if (currStep + 1 <= n) {
            oneStepCnt = cntStairs(currStep + 1, n);
        }

        int twoStepCnt = 0;
        if (currStep + 2 <= n) {
            twoStepCnt = cntStairs(currStep + 2, n);
        }

        return oneStepCnt + twoStepCnt;
    }

    // good recursion for DP
    public int climbStairs_rec2(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int oneStepCnt = climbStairs_rec2(n - 1);
        int twoStepCnt = climbStairs_rec2(n - 2);

        return oneStepCnt + twoStepCnt;
    }

    // memoization
    public int climbStairs_memo(int n) {
        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);
        return climbStairs_memo(n, dp);
    }

    private int climbStairs_memo(int n, int[] dp) {
        if (n == 0 || n == 1) {
            return 1;
        }

        if(dp[n] != -1) {
            return dp[n];
        }

        int oneStepCnt = climbStairs_memo(n - 1, dp);
        int twoStepCnt = climbStairs_memo(n - 2, dp);

        return oneStepCnt + twoStepCnt;
    }

    // tabulation
    private int climbStairs_tabu(int n) {
        int dp[] = new int[n+1];

        dp[0] = 1;
        dp[1] = 1;

        for(int i=2; i<=n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }

        return dp[n];
    }

    // tabulation
    private int climbStairs_tabuop(int n) {
        int prev1 = 1;
        int prev2 = 1;
        int curr = -1;

        for(int i=2; i<=n; i++) {
            curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }

        return curr;
    }
}
