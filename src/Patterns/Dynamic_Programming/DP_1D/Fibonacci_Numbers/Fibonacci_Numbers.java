package Patterns.Dynamic_Programming.DP_1D.Fibonacci_Numbers;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Fibonacci_Numbers extends ProblemSolver {

    public static void main(String[] args) {
        new Fibonacci_Numbers().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);

        int res1 = fib1(n);

        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);
        int res2 = fib2(n, dp);

        int res3 = fib3(n);
        int res4 = fib4(n);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    public int fib1(int n) {
        if(n <= 1) {
            return n;
        }

        return fib1(n - 1) + fib1(n - 2);
    }

    // Memoization (Top-Down)
    // TC: O(n)
    // SC: recO(n) + O(n)
    public int fib2(int n, int[] dp) {
        if(n <= 1) {
            return n;
        }

        if(dp[n] != -1){
            return dp[n];
        }

        dp[n] = fib2(n-1, dp) + fib2(n-2, dp);
        return dp[n];
    }

    // Tabulation (Bottom-Up)
    // TC: O(n)
    // SC: O(n)
    public int fib3(int n) {
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;

        for(int i=2; i<=n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }

        return dp[n];
    }

    // Tabulation with Space Optimized
    // TC: O(n)
    // SC: O(3)
    public int fib4(int n) {
        int prev1 = 1;
        int prev2 = 0;
        int curr = -1;

        for(int i=2; i<=n; i++) {
            curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }

        return curr;
    }

}
