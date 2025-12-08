package Patterns.Dynamic_Programming.DP_on_LIS.Longest_Increasing_Subsequence;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Longest_Increasing_Subsequence extends ProblemSolver {

    public static void main(String[] args) {
        new Longest_Increasing_Subsequence().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res1 = lengthOfLIS_rec(nums);
        int res2 = lengthOfLIS_memo(nums);
        int res3 = lengthOfLIS_tabu(nums);
        int res4 = lengthOfLIS_1D_DP(nums);
        int res5 = lengthOfLIS_BinarySearch(nums);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5);
    }

    public int lengthOfLIS_rec(int[] nums) {
        int len = nums.length;

        return helper1(0, -1, nums);
    }

    public int helper1(int n, int prevIdx, int[] nums) {
        if(n >= nums.length || prevIdx >= nums.length) {
            return 0;
        }

        int notPick = 0 + helper1(n+1, prevIdx, nums);
        int pick = 0;
        if(prevIdx == -1 || nums[n] > nums[prevIdx]) {
            pick = 1 + helper1(n+1, n, nums);
        }

        return Math.max(notPick, pick);
    }

    public int lengthOfLIS_memo(int[] nums) {
        int len = nums.length;
        int[][] dp = new int[len][len+1];
        for(int i=0; i<len; i++) {
            Arrays.fill(dp[i], -1);
        }
        return helper2(0, -1, nums, dp);
    }

    public int helper2(int n, int prevIdx, int[] nums, int[][] dp) {
        if(n >= nums.length) {
            return 0;
        }

        if(dp[n][prevIdx+1] != -1) {
            return dp[n][prevIdx+1];
        }

        int notPick = 0 + helper2(n+1, prevIdx, nums, dp);
        int pick = 0;
        if(prevIdx == -1 || nums[n] > nums[prevIdx]) {
            pick = 1 + helper2(n+1, n, nums, dp);
        }

        dp[n][prevIdx+1] = Math.max(notPick, pick);
        return dp[n][prevIdx+1];
    }

    private int lengthOfLIS_tabu(int[] nums) {
        int len = nums.length;
        int[][] dp = new int[len+1][len+1];

        for(int n=len-1; n>=0; n--) {
            for(int prevIdx=n-1; prevIdx>=-1; prevIdx--) {
                
                int notPick = 0 + dp[n+1][prevIdx+1];
                int pick = 0;
                if(prevIdx == -1 || nums[n] > nums[prevIdx]) {
                    pick = 1 + dp[n+1][n+1];
                }

                dp[n][prevIdx+1] = Math.max(notPick, pick);
            }
        }

        return dp[0][-1+1];
    }

    private int lengthOfLIS_1D_DP(int[] nums) {
        int len = nums.length;

        // store length of LIS from idx 0 to idx n
        int[] dp = new int[len];
        Arrays.fill(dp, 1);

        int maxLen = 1;
        for(int n=0; n<len; n++) {
            for(int prevIdx=0; prevIdx<n; prevIdx++) {
                if(nums[prevIdx] < nums[n]) {
                    dp[n] = Math.max(dp[n], dp[prevIdx]+1);
                }
            }
            maxLen = Math.max(maxLen, dp[n]);
        }

        return maxLen;
    }

    private int lengthOfLIS_BinarySearch(int[] nums) {
        int len = nums.length;
        int[] tails = new int[len];
        int size = 0;

        for(int n=0; n<len; n++) {
            int left = 0, right = size;
            while(left < right) {
                int mid = left + (right - left) / 2;
                if(tails[mid] < nums[n]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            tails[left] = nums[n];
            if(left == size) {
                size++;
            }
        }

        return size;
    }
}
