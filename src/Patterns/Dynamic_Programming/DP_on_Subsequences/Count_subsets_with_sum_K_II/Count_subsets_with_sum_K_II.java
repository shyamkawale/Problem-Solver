package Patterns.Dynamic_Programming.DP_on_Subsequences.Count_subsets_with_sum_K_II;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
 * Count subsets with sum K (handles negative numbers and zeros)
 * 
 * Key insight: With negative numbers, intermediate sums can be negative or exceed target,
 * so we use HashMap instead of 2D array for memoization/tabulation.
 */
public class Count_subsets_with_sum_K_II extends ProblemSolver {

    public static void main(String[] args) {
        new Count_subsets_with_sum_K_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        int res1 = cntSubsets_rec(nums, target);
        int res2 = cntSubsets_memo(nums, target);
        int res3 = cntSubsets_tabu(nums, target);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    // Pure Recursion: Try pick/not-pick for each element
    // TC: O(2^n) - exponential, explores all subsets
    // SC: O(n) - recursion stack depth
    private int cntSubsets_rec(int[] nums, int target) {
        return helper1(nums.length - 1, target, nums);
    }

    private int helper1(int n, int target, int[] nums) {
        // Base case: processed all elements
        if (n < 0) {
            return target == 0 ? 1 : 0;
        }

        int notPick = helper1(n - 1, target, nums);
        int pick = helper1(n - 1, target - nums[n], nums);

        return notPick + pick;
    }

    // Memoization with HashMap: Cache results for (index, target) pairs
    // TC: O(n * S) where S = range of possible sums
    // SC: O(n * S) for HashMap storage + O(n) recursion stack
    @SuppressWarnings("unchecked")
    private int cntSubsets_memo(int[] nums, int target) {
        int len = nums.length;
        Map<Integer, Integer>[] dp = new HashMap[len]; // Array of HashMap
        for (int i = 0; i < len; i++) {
            dp[i] = new HashMap<>();
        }
        return helper2(len - 1, target, nums, dp);
    }

    private int helper2(int n, int target, int[] nums, Map<Integer, Integer>[] dp) {
        if (n < 0) {
            return target == 0 ? 1 : 0;
        }

        if (dp[n].containsKey(target)) {
            return dp[n].get(target);
        }

        int notPick = helper2(n - 1, target, nums, dp);
        int pick = helper2(n - 1, target - nums[n], nums, dp);

        int totalWays = notPick + pick;
        dp[n].put(target, totalWays);
        return totalWays;
    }

    // Tabulation with HashMap: Build up from index 0, track all achievable sums
    // TC: O(n * S) where S = range of possible sums
    // SC: O(S) for HashMap (only keep previous row)
    private int cntSubsets_tabu(int[] nums, int target) {
        int len = nums.length;
        
        // dp[sum] = count of subsets that achieve this sum
        Map<Integer, Integer> prev = new HashMap<>();
        
        // Base: empty subset has sum 0
        prev.put(0, 1);
        
        for (int n = 0; n < len; n++) {
            Map<Integer, Integer> curr = new HashMap<>();
            
            for (Map.Entry<Integer, Integer> entry : prev.entrySet()) {
                int sum = entry.getKey();
                int count = entry.getValue();
                
                // Not pick: sum stays same
                curr.merge(sum, count, Integer::sum);
                
                // Pick: sum increases by nums[n]
                curr.merge(sum + nums[n], count, Integer::sum);
            }
            
            prev = curr;
        }
        
        return prev.getOrDefault(target, 0);
    }

}
