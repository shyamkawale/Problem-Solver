package Patterns.Dynamic_Programming.DP_on_LIS.Longest_Increasing_Subsequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/**
 * LeetCode 300: Longest Increasing Subsequence
 * https://leetcode.com/problems/longest-increasing-subsequence/
 * 
 * Problem: Given an integer array nums, return the length of the longest 
 * strictly increasing subsequence.
 * 
 * Example: nums = [10,9,2,5,3,7,101,18] → Output: 4 (LIS: [2,3,7,101])
 */
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
        int res6 = lengthOfLIS_BinarySearch_V2(nums);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5 + " " + res6);
    }

    /**
     * Approach 1: Recursion (Brute Force)
     * 
     * Idea: For each element, we have 2 choices - pick or not pick.
     * We pick only if current element > previous element (to maintain increasing order).
     * 
     * TC: O(2^n) - Each element has 2 choices, leading to exponential branches
     * SC: O(n)   - Recursion stack depth (max n levels deep)
     */
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

    /**
     * Approach 2: Memoization (Top-Down DP)
     * 
     * Idea: Cache results of subproblems to avoid recomputation.
     * State: dp[n][prevIdx+1] = LIS length starting from index n with previous index prevIdx
     * 
     * TC: O(n²) - n * n unique states, each computed once
     * SC: O(n²) - dp array of size n*n + O(n) recursion stack
     */
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

    /**
     * Approach 3: Tabulation (Bottom-Up DP)
     * 
     * Idea: Fill dp table iteratively from base case to answer.
     * State: dp[n][prevIdx+1] = LIS length starting from index n with previous index prevIdx
     * 
     * TC: O(n²) - Two nested loops over n
     * SC: O(n²) - dp array of size (n+1) * (n+1)
     */
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

    /**
     * Approach 4: 1D DP (Space Optimized)
     * 
     * Idea: dp[i] = length of LIS ending at index i
     * For each element, check all previous elements and extend the best LIS.
     * 
     * TC: O(n²) - Two nested loops
     * SC: O(n)  - 1D dp array
     */
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

    /**
     * Approach 5: Binary Search with Tails Array (Optimal)
     * 
     * Idea: Maintain a "tails" array where tails[i] = smallest tail element for LIS of length i+1.
     * For each element, use binary search to find its position in tails array.
     * - If element is larger than all tails, extend LIS (size++)
     * - Otherwise, replace the first tail >= element (keeps smallest possible tail)
     * 
     * TC: O(n log n) - For each of n elements, binary search takes O(log n)
     * SC: O(n)       - Tails array of size n
     */
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

    /**
     * Approach 6: Binary Search with ArrayList (Optimal - Cleaner Code)
     * 
     * Idea: Same as Approach 5, but uses ArrayList and Collections.binarySearch().
     * Collections.binarySearch() returns:
     * - Index of element if found
     * - -(insertionPoint + 1) if not found
     * 
     * TC: O(n log n) - For each of n elements, binary search takes O(log n)
     * SC: O(n)       - ArrayList of max size n
     * 
     * Step-by-Step Walkthrough
     * Input: [2, 6, 8, 3, 4, 5]
     * 
     * | Step | Num (x) | Action  | tails Array (State) | Logic                                                                         |
     * |------|---------|---------|---------------------|-------------------------------------------------------------------------------|
     * | 1    | 2       | Add     | [2]                 | List empty, start new sequence.                                              |
     * | 2    | 6       | Add     | [2, 6]              | 6 > 2, extend sequence.                                                      |
     * | 3    | 8       | Add     | [2, 6, 8]           | 8 > 6, extend sequence.                                                      |
     * | 4    | 3       | Replace | [2, 3, 8]           | 3 is smaller than 6. Replacing 6 with 3 creates a "stronger" potential list. |
     * | 5    | 4       | Replace | [2, 3, 4]           | 4 fits after 3 but is smaller than 8. Replace 8.                             |
     * | 6    | 5       | Add     | [2, 3, 4, 5]        | 5 > 4, extend sequence.                                                      |
     * 
     * Final LIS Length: 4
     */
    public int lengthOfLIS_BinarySearch_V2(int[] nums) {
        List<Integer> tails = new ArrayList<>();

        for(int n: nums) {
            int idx = Collections.binarySearch(tails, n);

            if(idx < 0) {
                idx = -(idx + 1);
            }

            if(idx < tails.size()) {
                tails.set(idx, n);
            }
            else {
                tails.add(n);
            }
        }

        return tails.size();
    }
}
