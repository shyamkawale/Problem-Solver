package Patterns.Dynamic_Programming.DP_1D.Frog_Jump;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://www.naukri.com/code360/problems/frog-jump_3621012

There is a frog on the '1st' step of an 'N' stairs long staircase. 
The frog wants to reach the 'Nth' stair. 'HEIGHT[i]' is the height of the '(i+1)th' stair.
If Frog jumps from 'ith' to 'jth' stair, the energy lost in the jump is given by absolute value of ( HEIGHT[i-1] - HEIGHT[j-1] ). 
If the Frog is on 'ith' staircase, he can jump either to '(i+1)th' stair or to '(i+2)th' stair. 
Your task is to find the minimum total energy used by the frog to reach from '1st' stair to 'Nth' stair.

For Example
If the given ‘HEIGHT’ array is [10,20,30,10], 
the answer 20 as the frog can jump from 1st stair to 2nd stair (|20-10| = 10 energy lost) 
and then a jump from 2nd stair to last stair (|10-20| = 10 energy lost). So, the total energy lost is 20.
*/
public class Frog_Jump extends ProblemSolver {

    public static void main(String[] args) {
        new Frog_Jump().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[] heights = DataConvertor.toIntArray(args[1]);

        int res1 = frogJump_rec(n, heights);
        int res2 = frogJump_memo(n, heights);
        int res3 = frogJump_tabu(n, heights);
        int res4 = frogJump_tabuop(n, heights);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    private int frogJump_rec(int n, int[] heights) {
        return jump1(n-1, heights);
    }

    private int jump1(int curr, int[] heights) {
        if(curr == 0) {
            return 0;
        }

        int left = Integer.MAX_VALUE;
        if(curr-1 >= 0) {
            left = jump1(curr-1, heights) + Math.abs(heights[curr-1]-heights[curr]);
        }

        int right = Integer.MAX_VALUE;
        if(curr-2 >= 0) {
            right = jump1(curr-2, heights) + Math.abs(heights[curr-2]-heights[curr]);
        }

        return Math.min(left, right);
    }

    public static int frogJump_memo(int n, int heights[]) {
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return jump(n-1, heights, dp);
    }

    public static int jump(int curr, int[] heights, int[] dp) {
        if(curr == 0) {
            return 0;
        }

        if(dp[curr] != -1) {
            return dp[curr];
        }

        int left = -1;
        if(curr-1 >= 0) {
            left = jump(curr-1, heights, dp) + Math.abs(heights[curr-1]-heights[curr]);
        }

        int right = Integer.MAX_VALUE;
        if(curr-2 >= 0) {
            right = jump(curr-2, heights, dp) + Math.abs(heights[curr-2]-heights[curr]);
        }

        dp[curr] = Math.min(left, right);
        return dp[curr];
    }

    private int frogJump_tabu(int n, int[] heights) {
        int[] dp = new int[n];
        dp[0] = 0;
        dp[1] = Math.abs(heights[0]-heights[1]);

        for(int i=2; i<n; i++) {
            dp[i] = Math.min(dp[i-1] + Math.abs(heights[i]-heights[i-1]), dp[i-2] + Math.abs(heights[i]-heights[i-2]));
        }

        return dp[n-1];
    }

    private int frogJump_tabuop(int n, int[] heights) {
        int prev2 = 0;
        int prev1 = Math.abs(heights[0]-heights[1]);
        int curr = -1;

        for(int i=2; i<n; i++) {
            curr = Math.min(prev1 + Math.abs(heights[i]-heights[i-1]), prev2 + Math.abs(heights[i]-heights[i-2]));
            prev2 = prev1;
            prev1 = curr;
        }

        return curr;
    }


    // other way:
    public static int frogJump(int n, int heights[]) {
        return helper(0, heights);
    }

    private static int helper(int curr, int[] heights) {
        int len = heights.length;
        // we have reached last staircase (no effort now)
        if(curr == len-1) {
            return 0;
        }

        int goNext = helper(curr+1, heights) + Math.abs(heights[curr+1] - heights[curr]);

        int skipNext = Integer.MAX_VALUE;
        if(curr+2 < len) {
            skipNext = helper(curr+2, heights) + Math.abs(heights[curr+2] - heights[curr]);
        }

        return Math.min(goNext, skipNext);
    }
}
