package Patterns.Dynamic_Programming.DP_on_Strings.Edit_Distance;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Edit_Distance extends ProblemSolver {

    public static void main(String[] args) {
        new Edit_Distance().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String word1 = DataConvertor.toString(args[0]);
        String word2 = DataConvertor.toString(args[1]);

        int res1 = minDistance_rec(word1, word2);
        int res2 = minDistance_memo(word1, word2);
        System.out.println(res1 + " " + res2);
    }

    public int minDistance_rec(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        return helper1(len1 - 1, len2 - 1, word1, word2);
    }

    private int helper1(int idx1, int idx2, String word1, String word2) {
        // edge case
        if (idx1 == -1) {
            return idx2 + 1;
        }

        if (idx2 == -1) {
            return idx1 + 1;
        }

        int ops1 = Integer.MAX_VALUE;
        int ops2 = Integer.MAX_VALUE;
        int ops3 = Integer.MAX_VALUE;
        int ops4 = Integer.MAX_VALUE;
        if (word1.charAt(idx1) == word2.charAt(idx2)) {
            ops1 = 0 + helper1(idx1 - 1, idx2 - 1, word1, word2);
        } else {
            // case 1 (Insert)
            ops2 = 1 + helper1(idx1, idx2 - 1, word1, word2);

            // case 2 (Delete)
            ops3 = 1 + helper1(idx1 - 1, idx2, word1, word2);

            // case 2 (Replace)
            ops4 = 1 + helper1(idx1 - 1, idx2 - 1, word1, word2);
        }

        return Math.min(ops1, Math.min(ops2, Math.min(ops3, ops4)));
    }


    public int minDistance_memo(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1+1][len2+1];
        for(int r=0; r<len1+1; r++) {
            Arrays.fill(dp[r], -1);
        }
        return helper2(len1-1, len2-1, word1, word2, dp);
    }

    private int helper2(int idx1, int idx2, String word1, String word2, int[][] dp) {
        // edge case
        if(idx1 == -1) {
            return idx2+1;
        }

        if(idx2 == -1) {
            return idx1+1;
        }

        if(dp[idx1][idx2] != -1) {
            return dp[idx1][idx2];
        }

        int ops1 = Integer.MAX_VALUE;
        int ops2 = Integer.MAX_VALUE;
        int ops3 = Integer.MAX_VALUE;
        int ops4 = Integer.MAX_VALUE;
        if(word1.charAt(idx1) == word2.charAt(idx2)) {
            ops1 = 0 + helper2(idx1-1, idx2-1, word1, word2, dp);
        }
        else {
            // case 1 (Insert)
            ops2 = 1 + helper2(idx1, idx2-1, word1, word2, dp);

            // case 2 (Delete)
            ops3 = 1 + helper2(idx1-1, idx2, word1, word2, dp);

            // case 2 (Replace)
            ops4 = 1 + helper2(idx1-1, idx2-1, word1, word2, dp);
        }

        return dp[idx1][idx2] = Math.min(ops1, Math.min(ops2, Math.min(ops3, ops4)));
    }

}
