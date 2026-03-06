package Patterns.Dynamic_Programming.DP_1D.Frog_Jump_With_K_Distance;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://takeuforward.org/data-structure/dynamic-programming-frog-jump-with-k-distances-dp-4

A frog wants to climb a staircase with n steps. 
Given an integer array heights, where heights[i] contains the height of the ith step, and an integer k. 
To jump from the ith step to the jth step, the frog requires abs(heights[i] - heights[j]) energy, where abs() denotes the absolute difference. 
The frog can jump from the ith step to any step in the range [i + 1, i + k], provided it exists.
 Return the minimum amount of energy required by the frog to go from the 0th step to the (n-1)th step.
*/
public class Frog_Jump_With_K_Distance extends ProblemSolver {

    public static void main(String[] args) {
        new Frog_Jump_With_K_Distance().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int k = DataConvertor.toInt(args[1]);
        int[] heights = DataConvertor.toIntArray(args[2]);

        int res = frogJump_rec(n, k, heights);
        System.out.println(res);
    }

    private int frogJump_rec(int n, int k, int[] heights) {
        return jump1(n-1, k, heights);
    }

    private int jump1(int curr, int k, int[] heights) {
        if(curr == 0) {
            return 0;
        }

        int jumpEffort = Integer.MAX_VALUE;
        for(int i=1; i<=k; i++) {
            int effort = Integer.MAX_VALUE;
            if(curr-i >= 0) {
                effort = jump1(curr-i, k, heights) + Math.abs(heights[curr-i]-heights[curr]);
            }
            jumpEffort = Math.min(jumpEffort, effort);
        }

        return jumpEffort;
    }

}
