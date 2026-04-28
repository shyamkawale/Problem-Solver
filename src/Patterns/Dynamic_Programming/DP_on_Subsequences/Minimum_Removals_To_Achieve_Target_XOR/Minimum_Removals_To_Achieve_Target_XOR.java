package Patterns.Dynamic_Programming.DP_on_Subsequences.Minimum_Removals_To_Achieve_Target_XOR;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Minimum_Removals_To_Achieve_Target_XOR extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Removals_To_Achieve_Target_XOR().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        int res = minRemovals(nums, target);
        System.out.println(res);
    }

    public int minRemovals(int[] nums, int target) {
        int len = nums.length;

        // Suppress the unchecked cast warning for CP
        @SuppressWarnings("unchecked")
        Map<Integer, Integer>[] dp = new HashMap[len];
        for (int i = 0; i < len; i++) {
            dp[i] = new HashMap<>();
        }

        int maxRemaining = helper1(0, 0, target, nums, dp);

        return maxRemaining < 0 ? -1 : len - maxRemaining;
    }

    private int helper1(int i, int xor, int target, int[] nums, Map<Integer, Integer>[] dp) {
        if (i >= nums.length) {
            // Use a safe minimum to prevent accidental integer underflow
            return xor == target ? 0 : (int) -1e9;
        }

        if (dp[i].containsKey(xor)) {
            return dp[i].get(xor);
        }

        // Option 1: Pick the element (Keep it)
        int pick = 1 + helper1(i+1, xor ^ nums[i], target, nums, dp);

        // Option 2: Do not pick (Remove it)
        int notPick = helper1(i+1, xor, target, nums, dp);

        int maxKeep = Math.max(pick, notPick);
        dp[i].put(xor, maxKeep);

        return maxKeep;
    }

}
