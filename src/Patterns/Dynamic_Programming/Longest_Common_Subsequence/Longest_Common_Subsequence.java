package Patterns.Dynamic_Programming.Longest_Common_Subsequence;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Longest_Common_Subsequence extends ProblemSolver {

    public static void main(String[] args) {
        new Longest_Common_Subsequence().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s1 = DataConvertor.toString(args[0]);
        String s2 = DataConvertor.toString(args[1]);

        int res1 = longestCommonSubsequence_rec(s1, s2);
        int res2 = longestCommonSubsequence_memo(s1, s2);
        int res3 = longestCommonSubsequence_tabu(s1, s2);
        int res4 = longestCommonSubsequence_tabuop(s1, s2);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    private int longestCommonSubsequence_rec(String s1, String s2) {
        return helper1(s1, s1.length()-1, s2, s2.length()-1);
    }

    private int helper1(String s1, int idx1, String s2, int idx2) {
        if(idx1 < 0 || idx2 < 0) {
            return 0;
        }

        // pick elements
        if(s1.charAt(idx1) == s2.charAt(idx2)) {
            return 1 + helper1(s1, idx1-1, s2, idx2-1);
        }
        
        int skips1 = helper1(s1, idx1-1, s2, idx2);
        int skipS2 = helper1(s1, idx1, s2, idx2-1);

        return Math.max(skips1, skipS2);
    }

    private int longestCommonSubsequence_memo(String s1, String s2) {
        int s1len = s1.length();
        int s2len = s2.length();

        int[][] dp = new int[s1len][s2len];
        for(int i=0; i<s1len; i++) {
            Arrays.fill(dp[i], -1);
        }

        return helper2(s1, s1len-1, s2, s2len-1, dp);
    }

    private int helper2(String s1, int idx1, String s2, int idx2, int[][] dp) {
        if(idx1 < 0 || idx2 < 0) {
            return 0;
        }

        if(dp[idx1][idx2] != -1) {
            return dp[idx1][idx2];
        }

        // pick elements
        if(s1.charAt(idx1) == s2.charAt(idx2)) {
            dp[idx1][idx2] = 1 + helper2(s1, idx1-1, s2, idx2-1, dp);
            return dp[idx1][idx2];
        }
        
        int skips1 = helper2(s1, idx1-1, s2, idx2, dp);
        int skipS2 = helper2(s1, idx1, s2, idx2-1, dp);

        dp[idx1][idx2] = Math.max(skips1, skipS2);
        return dp[idx1][idx2];
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

    private int longestCommonSubsequence_tabuop(String s1, String s2) {
        int s1len = s1.length();
        int s2len = s2.length();

        int[] prev = new int[s2len+1];
        for(int idx2=0; idx2<=s2len; idx2++) {
            prev[idx2] = 0;
        }

        for(int idx1=1; idx1<s1len+1; idx1++) {
            int[] curr = new int[s2len+1];
            for(int idx2=1; idx2<s2len+1; idx2++) {
                // pick elements
                if(s1.charAt(idx1-1) == s2.charAt(idx2-1)) {
                    curr[idx2] = 1 + prev[idx2-1];
                }
                else {
                    int skips1 = prev[idx2];
                    int skipS2 = curr[idx2-1];

                    curr[idx2] = Math.max(skips1, skipS2);
                }
            }

            prev = curr;
        }

        return prev[s2len];
    }
}
