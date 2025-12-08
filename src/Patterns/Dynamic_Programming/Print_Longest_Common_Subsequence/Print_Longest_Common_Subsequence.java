package Patterns.Dynamic_Programming.Print_Longest_Common_Subsequence;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Print_Longest_Common_Subsequence extends ProblemSolver {

    public static void main(String[] args) {
        new Print_Longest_Common_Subsequence().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s1 = DataConvertor.toString(args[0]);
        String s2 = DataConvertor.toString(args[1]);

        String res1 = printLongestCommonSubsequence_rec(s1, s2);
        String res2 = printLongestCommonSubsequence_memo(s1, s2);
        String res3 = printLongestCommonSubsequence_tabu(s1, s2);
        String res4 = printLongestCommonSubsequence_tabuop(s1, s2);
        String res5 = printLongestCommonSubsequence_tabu2(s1, s2);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5);
    }

    private String printLongestCommonSubsequence_rec(String s1, String s2) {
        int s1len = s1.length();
        int s2len = s2.length();

        return helper1(s1, s1len-1, s2, s2len-1);
    }

    private String helper1(String s1, int idx1, String s2, int idx2) {
        if(idx1 < 0 || idx2 < 0) {
            return "";
        }

        if(s1.charAt(idx1) == s2.charAt(idx2)) {
            return helper1(s1, idx1-1, s2, idx2-1) + s1.charAt(idx1);
        }

        String skipS1 = helper1(s1, idx1-1, s2, idx2);
        String skipS2 = helper1(s1, idx1, s2, idx2-1);

        return skipS1.length() > skipS2.length() ? skipS1 : skipS2;
    }

    private String printLongestCommonSubsequence_memo(String s1, String s2) {
        int s1len = s1.length();
        int s2len = s2.length();
        String[][] dp = new String[s1len][s2len];
        for(int i=0; i<s1len; i++) {
            Arrays.fill(dp[i], null);
        }

        return helper2(s1, s1len-1, s2, s2len-1, dp);
    }

    private String helper2(String s1, int idx1, String s2, int idx2, String[][] dp) {
        if(idx1 < 0 || idx2 < 0) {
            return "";
        }

        if(dp[idx1][idx2] != null) {
            return dp[idx1][idx2];
        }

        if(s1.charAt(idx1) == s2.charAt(idx2)) {
            dp[idx1][idx2] = helper2(s1, idx1-1, s2, idx2-1, dp) + s1.charAt(idx1);
            return dp[idx1][idx2];
        }

        String skipS1 = helper2(s1, idx1-1, s2, idx2, dp);
        String skipS2 = helper2(s1, idx1, s2, idx2-1, dp);

        dp[idx1][idx2] = skipS1.length() > skipS2.length() ? skipS1 : skipS2;
        return dp[idx1][idx2];
    }

    private String printLongestCommonSubsequence_tabu(String s1, String s2) {
        int s1len = s1.length();
        int s2len = s2.length();
        String[][] dp = new String[s1len+1][s2len+1];

        for(int idx1=0; idx1<=s1len; idx1++) {
            dp[idx1][0] = "";
        }

        for(int idx2=0; idx2<=s2len; idx2++) {
            dp[0][idx2] = "";
        }

        for(int idx1=1; idx1<=s1len; idx1++) {
            for(int idx2=1; idx2<=s2len; idx2++) {
                if(s1.charAt(idx1-1) == s2.charAt(idx2-1)) {
                    dp[idx1][idx2] = dp[idx1-1][idx2-1] + s1.charAt(idx1-1);
                }
                else {
                    String skipS1 = dp[idx1-1][idx2];
                    String skipS2 = dp[idx1][idx2-1];

                    dp[idx1][idx2] = skipS1.length() > skipS2.length() ? skipS1 : skipS2;
                }
            }
        }

        return dp[s1len][s2len];
    }

    private String printLongestCommonSubsequence_tabuop(String s1, String s2) {
        int s1len = s1.length();
        int s2len = s2.length();
        String[] prev = new String[s2len+1];

        for(int idx2=0; idx2<=s2len; idx2++) {
            prev[idx2] = "";
        }

        for(int idx1=1; idx1<=s1len; idx1++) {
            String[] curr = new String[s2len+1];
            curr[0] = "";
            for(int idx2=1; idx2<=s2len; idx2++) {
                if(s1.charAt(idx1-1) == s2.charAt(idx2-1)) {
                    curr[idx2] = prev[idx2-1] + s1.charAt(idx1-1);
                }
                else {
                    String skipS1 = prev[idx2];
                    String skipS2 = curr[idx2-1];

                    curr[idx2] = skipS1.length() > skipS2.length() ? skipS1 : skipS2;
                }
            }
            prev = curr;
        }

        return prev[s2len];
    }

    private String printLongestCommonSubsequence_tabu2(String s1, String s2) {
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

        String res = "";
        int idx1 = s1len;
        int idx2 = s2len;

        while(idx1 > 0 && idx2 > 0) {
            if(s1.charAt(idx1-1) == s2.charAt(idx2-1)) {
                res = s1.charAt(idx1-1) + res;
                idx1--;
                idx2--;
            }
            else {
                if(dp[idx1-1][idx2] > dp[idx1][idx2-1]) {
                    idx1--;
                }
                else{
                    idx2--;
                }
            }
        }
        return res;
    }
}
