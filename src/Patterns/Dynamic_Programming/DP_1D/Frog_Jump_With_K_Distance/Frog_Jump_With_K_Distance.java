package Patterns.Dynamic_Programming.DP_1D.Frog_Jump_With_K_Distance;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

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
