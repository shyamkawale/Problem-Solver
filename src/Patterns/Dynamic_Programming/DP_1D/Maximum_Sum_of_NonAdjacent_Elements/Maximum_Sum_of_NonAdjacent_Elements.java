package Patterns.Dynamic_Programming.DP_1D.Maximum_Sum_of_NonAdjacent_Elements;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Maximum_Sum_of_NonAdjacent_Elements extends ProblemSolver {

    public static void main(String[] args) {
        new Maximum_Sum_of_NonAdjacent_Elements().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res1 = houseRob_rec1(nums);
        int res2 = houseRob_rec2(nums);
        int res3 = houseRob_memo(nums);
        int res4 = houseRob_tabu(nums);
        int res5 = houseRob_tabuop(nums);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5);
    }

    int maxSum = 0;
    public int houseRob_rec1(int[] nums) {
        maxSum = 0;
        int sum = 0;

        for(int i=0; i<nums.length; i++) {
            helper1(i, sum, nums);
        }
        return maxSum;
    }

    private void helper1(int idx, int sum, int[] nums) {
        if(idx >= nums.length) {
            return;
        }

        sum = sum + nums[idx];
        maxSum = Math.max(maxSum, sum);

        // idx+1 is neighbor element so ignore that, start from idx+2.
        for(int i=idx+2; i<nums.length; i++) {
            helper1(i, sum, nums);
        }
    }

    private int houseRob_rec2(int[] nums) {
        return helper2(nums.length-1, nums);
    }

    // here f(n) means: max sum possible(answer) if there were n elements in array
    private int helper2(int idx, int[] nums) {
        if(idx == 0) {
            return nums[0];
        }
        if(idx < 0) {
            return 0;
        }

        int pick = nums[idx] + helper2(idx-2, nums);
        int notPick = 0 + helper2(idx-1, nums);

        return Math.max(pick, notPick);
    }

    /**
     * Solves the House Robber problem using memoization (top-down dynamic programming).
     * 
     * This method finds the maximum sum of non-adjacent elements in the given array.
     * It uses memoization to store computed results and avoid redundant calculations.
     * 
     * @param nums the array of integers representing house values
     * @return the maximum sum of non-adjacent elements
     * 
     * Time Complexity: O(n) - Each element is computed only once and stored in the dp array.
     *                  Subsequent lookups are O(1). Total calls to helper3 = n.
     * Space Complexity: O(n) - For the dp memoization array and O(n) for the recursion call stack.
     *                   Total: O(n)
     */
    private int houseRob_memo(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        return helper3(n-1, nums, dp);
    }

    private int helper3(int idx, int[] nums, int[] dp) {
        if(idx == 0) {
            return nums[0];
        }
        if(idx < 0) {
            return 0;
        }

        if(dp[idx] != -1) {
            return dp[idx];
        }

        int pick = nums[idx] + helper3(idx-2, nums, dp);
        int notPick = 0 + helper3(idx-1, nums, dp);

        dp[idx] = Math.max(pick, notPick);
        return dp[idx];
    }

    private int houseRob_tabu(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        dp[0] = nums[0];
        if(n == 1) return dp[0];
        dp[1] = Math.max(dp[0], nums[1]);
        if(n == 2) return dp[1];

        for(int i=2; i<n; i++) {
            int pick = nums[i] + dp[i-2];
            int notPick = 0 + dp[i-1];
            dp[i] = Math.max(pick, notPick);
        }

        return dp[n-1];
    }

    private int houseRob_tabuop(int[] nums) {
        int n = nums.length;

        int prev2 = nums[0];
        if(n == 1) return prev2;
        int prev1 = Math.max(prev2, nums[1]);
        if(n == 2) return prev1;
        int curr = 0;

        for(int i=2; i<n; i++) {
            int pick = nums[i] + prev2;
            int notPick = 0 + prev1;
            curr = Math.max(pick, notPick);

            prev2 = prev1;
            prev1 = curr;
        }

        return curr;
    }
}
