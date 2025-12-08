package Patterns.Dynamic_Programming.DP_on_Subsequences.Partition_2_subsets_with_minimum_absolute_sum_difference;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Partition_2_subsets_with_minimum_absolute_sum_difference extends ProblemSolver {

    public static void main(String[] args) {
        new Partition_2_subsets_with_minimum_absolute_sum_difference().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res = minDifference(nums);
        System.out.println(res);
    }

    private int minDifference(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for(int num: nums) {
            sum = sum + num;
        }

        boolean[][] dp = new boolean[len][sum+1];
        dp[0][nums[0]] = true;
        for(int i=0; i<len; i++) {
            dp[i][0] = true; 
        }

        for(int i=1; i<len; i++) {
            for(int s=1; s<sum; s++) {
                boolean notPick = dp[i-1][s];
                boolean pick = false;
                if(s-nums[i] > 0) {
                    pick = dp[i-1][s-nums[i]];
                }
                dp[i][s] = notPick || pick;
            }
        }

        // |s1-s2| = diff
        // |s1-(sum-s1)| = diff
        // |2s1-sum| = diff
        int minDiff = Integer.MAX_VALUE;
        for(int s=0; s<sum; s++) {
            if(dp[len-1][s]) {
                minDiff = Math.min(minDiff, Math.abs(2*s-sum));
            }
        }

        return minDiff;
    }

}
