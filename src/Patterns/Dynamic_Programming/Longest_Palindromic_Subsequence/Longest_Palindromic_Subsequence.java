package Patterns.Dynamic_Programming.Longest_Palindromic_Subsequence;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Longest_Palindromic_Subsequence extends ProblemSolver {

    public static void main(String[] args) {
        new Longest_Palindromic_Subsequence().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        int res1 = longestPalindromeSubseq_usingLCS(s);
        int res2 = longestPalindromeSubseq_rec(s);
        int res3 = longestPalindromeSubseq_memo(s);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    // longest palindromic subsequence = lcs(original string, reverse of original string)
    public int longestPalindromeSubseq_usingLCS(String s) {
        // reverse string..
        String s2 = "";
        for(int i=0; i<s.length(); i++) {
            s2 = s.charAt(i) + s2;
        }

        return longestCommonSubsequence_tabu(s, s2);
    }

    private int longestCommonSubsequence_tabu(String s1, String s2) {
        int s1len = s1.length();
        int s2len = s2.length();

        int[][] dp = new int[s1len+1][s2len+1];
        for(int idx1=0; idx1<=s1len; idx1++) {
            dp[idx1][0] = 0;
        }
        for(int idx2=0; idx2<=s2len; idx2++) {
            dp[0][idx2] = 0;
        }

        for(int idx1=1; idx1<s1len+1; idx1++) {
            for(int idx2=1; idx2<s2len+1; idx2++) {
                // pick elements
                if(s1.charAt(idx1-1) == s2.charAt(idx2-1)) {
                    dp[idx1][idx2] = 1 + dp[idx1-1][idx2-1];
                }
                else {
                    int skips1 = dp[idx1-1][idx2];
                    int skipS2 = dp[idx1][idx2-1];

                    dp[idx1][idx2] = Math.max(skips1, skipS2);
                }
            }
        }

        return dp[s1len][s2len];
    }

    // dp indexes start extreme and meet mid
    // normal dp with idx1 starts increasing 0 to mid and idx2 decrementing from n to mid
    private int longestPalindromeSubseq_rec(String s) {
        int len = s.length();
        return helper(s, 0, len-1);
    }

    private int helper(String s, int i, int j) {
        
        if(i > j) {
            return 0;
        }
        if(i == j) {
            return 1;
        }

        if(s.charAt(i) == s.charAt(j)) {
            return 2 + helper(s, i+1, j-1);
        }

        return Math.max(helper(s, i, j-1), helper(s, i+1, j));
    }

    public int longestPalindromeSubseq_memo(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];
        for(int i=0; i<len; i++) {
            Arrays.fill(dp[i], -1);
        }
        return helper(s, 0, len-1, dp);
    }

    private int helper(String s, int i, int j, int[][] dp) {
        if(i > j) {
            return 0;
        }
        if(i==j) {
            return 1;
        }

        if(dp[i][j] != -1) {
            return dp[i][j];
        }

        if(s.charAt(i) == s.charAt(j)) {
            dp[i][j] = 2 + helper(s, i+1, j-1, dp);
            return dp[i][j];
        }

        dp[i][j] = Math.max(helper(s, i, j-1, dp), helper(s, i+1, j, dp));
        return dp[i][j];
    }

}
