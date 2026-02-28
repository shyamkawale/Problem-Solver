package Patterns.Dynamic_Programming.Longest_Almost_Palindromic_Substring;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Longest_Almost_Palindromic_Substring extends ProblemSolver {

    public static void main(String[] args) {
        new Longest_Almost_Palindromic_Substring().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);

        int res1 = almostPalindromic_rec(s);
        int res2 = almostPalindromic_memo(s);
        int res3 = almostPalindromic_tabu(s);
        int res4 = almostPalindromic_tabuop(s);
        int res5 = almostPalindromic_centerExpansion(s);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5);
    }

    int ans1 = 0;

    public int almostPalindromic_rec(String s) {
        int len = s.length();

        helper1(0, len - 1, s);
        return ans1;
    }

    private int helper1(int left, int right, String s) {
        if (left == right) {
            ans1 = Math.max(ans1, 1);
            return 0;
        }

        if (left + 1 == right) {
            ans1 = Math.max(ans1, 2);
            if (s.charAt(left) == s.charAt(right)) {
                return 0;
            } else {
                return 1;
            }
        }

        int ignoreBoth = s.charAt(left) == s.charAt(right)
                ? helper1(left + 1, right - 1, s)
                : Integer.MAX_VALUE;
        int ignoreleft = helper1(left + 1, right, s) + 1;
        int ignoreRight = helper1(left, right - 1, s) + 1;

        int diff = Math.min(ignoreBoth, Math.min(ignoreleft, ignoreRight));

        if (diff <= 1) {
            ans1 = Math.max(ans1, right - left + 1);
        }

        return diff;
    }

    int ans2 = 0;

    public int almostPalindromic_memo(String s) {
        int len = s.length();
        int[][] dp = new int[len + 1][len + 1];
        for (int i = 0; i < len + 1; i++) {
            Arrays.fill(dp[i], -1);
        }

        helper2(0, len - 1, s, dp);

        return ans2;
    }

    private int helper2(int left, int right, String s, int[][] dp) {
        if (left == right) {
            ans2 = Math.max(ans2, 1);
            return 0;
        }

        if (left + 1 == right) {
            ans2 = Math.max(ans2, 2);
            if (s.charAt(left) == s.charAt(right)) {
                return 0;
            } else {
                return 1;
            }
        }

        if (dp[left][right] != -1) {
            return dp[left][right];
        }

        int ignoreBoth = s.charAt(left) == s.charAt(right)
                ? helper2(left + 1, right - 1, s, dp)
                : Integer.MAX_VALUE;
        int ignoreleft = helper2(left + 1, right, s, dp) + 1;
        int ignoreRight = helper2(left, right - 1, s, dp) + 1;

        int diff = Math.min(ignoreBoth, Math.min(ignoreleft, ignoreRight));

        if (diff <= 1) {
            ans2 = Math.max(ans2, right - left + 1);
        }

        return dp[left][right] = diff;
    }

    private int almostPalindromic_tabu(String s) {
        int len = s.length();

        if(len <= 2) return len;

        int[][] dp = new int[len][len];

        for(int i=0; i<len; i++) {
            dp[i][i] = 0;
        }

        for(int left=0; left<len-1; left++) {
            int right = left+1;
            dp[left][right] = s.charAt(left) == s.charAt(right) ? 0 : 1; 
        }

        int maxAns = 2;

        for(int left = len-2; left >= 0; left--) {
            for(int right = left+2; right < len; right++) {
                int ignoreBoth = (s.charAt(left) == s.charAt(right) && left <= right)
                        ? dp[left + 1][right - 1]
                        : Integer.MAX_VALUE;
                int ignoreleft = dp[left + 1][right] + 1;
                int ignoreRight = dp[left][right - 1] + 1;

                int diff = Math.min(ignoreBoth, Math.min(ignoreleft, ignoreRight));

                if (diff <= 1) {
                    maxAns = Math.max(maxAns, right - left + 1);
                }

                dp[left][right] = diff;
            }
        }

        return maxAns;
    }

    private int almostPalindromic_tabuop(String s) {
        return -1;
    }

    public int almostPalindromic_centerExpansion(String s) {
        int n = s.length();
        if (n < 2) return n; 
        
        int maxLen = 0;
        
        // Treat every character (and the space between characters) as a potential center
        for (int i = 0; i < n; i++) {
            
            // 1. Odd length centers (Center is exactly at character 'i')
            // We start with length 1 (the center char). Pointers start immediately left and right.
            maxLen = Math.max(maxLen, expand(s, i - 1, i + 1, 1, true));  // Branch: Try skipping left on mismatch
            maxLen = Math.max(maxLen, expand(s, i - 1, i + 1, 1, false)); // Branch: Try skipping right on mismatch
            
            // 2. Even length centers (Center is between 'i' and 'i+1')
            // We start with length 0. Pointers start exactly at i and i+1.
            maxLen = Math.max(maxLen, expand(s, i, i + 1, 0, true));  // Branch: Try skipping left on mismatch
            maxLen = Math.max(maxLen, expand(s, i, i + 1, 0, false)); // Branch: Try skipping right on mismatch
        }
        
        return maxLen;
    }

    /**
     * Expands outwards from a center, allowing exactly 1 character deletion.
     */
    private int expand(String s, int left, int right, int currentLen, boolean skipLeftOnMismatch) {
        boolean canSkip = true; // We are allowed exactly one deletion
        
        // CORE TRICK: Use OR (||). We keep going if at least one pointer is valid.
        // This allows us to use our skip on edge characters even if the other pointer is out of bounds.
        while (left >= 0 || right < s.length()) {
            
            // Case 1: Both pointers are valid and characters match
            if (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                currentLen += 2; // Both characters contribute to the substring length
                left--;
                right++;
            } 
            // Case 2: Mismatch found (or one pointer hit an edge), and we STILL have our skip
            else if (canSkip) {
                if (skipLeftOnMismatch) {
                    left--; // Pretend we deleted the left character by shifting past it
                } else {
                    right++; // Pretend we deleted the right character by shifting past it
                }
                currentLen++;    // The deleted character still counts towards the total substring length
                canSkip = false; // Consume our skip
            } 
            // Case 3: Second mismatch found -> We are done expanding
            else {
                break;
            }
        }
        
        return currentLen;
    }

}
