package Patterns.Dynamic_Programming.DP_on_Subsequences.Count_partitions_with_given_difference;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Count_partitions_with_given_difference extends ProblemSolver {

    public static void main(String[] args) {
        new Count_partitions_with_given_difference().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int diff = DataConvertor.toInt(args[0]);
        int[] nums = DataConvertor.toIntArray(args[1]);

        int res = countPartitions(diff, nums);
        System.out.println(res);
    }

    private static final int MOD = (int)1e9 + 7;

    // s1+s2 = sum;
    // s1-s2 = diff;
    // s1+(s1-diff) = sum;
    //s1 = (sum+diff)/2;
    public int countPartitions(int diff, int[] nums) {
        int len = nums.length;
        int sum = 0;
        for(int num: nums) {
            sum = sum + num;
        }

        if((sum+diff)%2 != 0 || sum < diff) {
            return 0;
        }
        int target = (sum+diff)/2;
        int[][] dp = new int[len][target+1];
        for(int n=0; n<len; n++) {
            dp[n][0] = 1;
        }
        dp[0][nums[0]] = nums[0] == 0 ? 2 : 1;

        for(int n=1; n<len; n++) {
            for(int t=0; t<=target; t++) {
                int notTake = dp[n-1][t];
                int take = 0;
                if(t-nums[n] >= 0) {
                    take = dp[n-1][t-nums[n]];
                }

                dp[n][t] = (take + notTake) % MOD;
            }
        }

        return dp[len-1][target];
    }

}
