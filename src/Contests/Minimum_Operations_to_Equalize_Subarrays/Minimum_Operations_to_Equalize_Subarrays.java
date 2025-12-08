package Contests.Minimum_Operations_to_Equalize_Subarrays;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/minimum-operations-to-equalize-subarrays/


*/
public class Minimum_Operations_to_Equalize_Subarrays extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Operations_to_Equalize_Subarrays().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);
        int[][] queries = DataConvertor.to2DIntArray(args[2]);

        long[] res = minOperations(nums, k, queries);
        System.out.println(Arrays.toString(res));
    }

    public long[] minOperations(int[] nums, int k, int[][] queries) {
        int qlen = queries.length;
        long[] ans = new long[qlen];

        int len = nums.length;

        for(int i=0; i<qlen; i++) {
            int start = queries[i][0];
            int end = queries[i][1];

            if(start == end) continue;
            int ops = 0;
            for(int idx=start; idx<end; idx++) {
                int diff = Math.abs(nums[idx+1] - nums[idx]);
                if(diff%k != 0) {
                    ops = -1;
                    break;
                }

                ops = ops + diff/k;
            }
            ans[i] = ops;
        }

        return ans;
    }

}
