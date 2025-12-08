package Patterns.Dynamic_Programming.DP_on_Subsequences.Minimum_Coin_Change;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Minimum_Coin_Change extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Coin_Change().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] coins = DataConvertor.toIntArray(args[0]);
        int amount = DataConvertor.toInt(args[1]);

        int res1 = coinChange_rec(coins, amount);
        int res2 = coinChange_memo(coins, amount);
        int res3 = coinChange_tabu(coins, amount);
        int res4 = coinChange_tabu_op(coins, amount);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    private static int INF = (int)1e9;

    public int coinChange_rec(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int res = helper1(len-1, amount, coins);
        if(res == INF) return -1;
        return res;
    }

    private int helper1(int n, int amount, int[] coins) {
        if(amount == 0) {
            return 0;
        }

        if(n == 0) {
            if(amount%coins[0] != 0) return INF;
            else return amount/coins[0];
        }

        int notPick = helper1(n-1, amount, coins);
        int pick = INF;
        if(amount-coins[n] >= 0) {
            pick = 1 + helper1(n, amount-coins[n], coins);
        }

        return Math.min(notPick, pick);
    }

    public int coinChange_memo(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int[][] dp = new int[len][amount+1];
        for(int i=0; i<len; i++) {
            Arrays.fill(dp[i], -1);
        }
        int res = helper2(len-1, amount, coins, dp);
        if(res == INF) return -1;
        return res;
    }

    private int helper2(int n, int amount, int[] coins, int[][] dp) {
        if(amount == 0) {
            return 0;
        }

        if(n == 0) {
            if(amount%coins[0] != 0) return INF;
            else return amount/coins[0];
        }

        if(dp[n][amount] != -1) {
            return dp[n][amount];
        }

        int notPick = helper2(n-1, amount, coins, dp);
        int pick = INF;
        if(amount-coins[n] >= 0) {
            pick = 1 + helper2(n, amount-coins[n], coins, dp);
        }

        dp[n][amount] = Math.min(notPick, pick);
        return dp[n][amount];
    }

    private int coinChange_tabu(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int[][] dp = new int[len][amount+1];
        // not required.
        for(int n=0; n<len; n++) {
            dp[n][0] = 0;
        }

        for(int amt=0; amt<=amount; amt++) {
            dp[0][amt] = amt%coins[0] == 0 ? amt/coins[0] : INF;
        }

        for(int n=1; n<len; n++) {
            for(int amt=1; amt<=amount; amt++) {
                int notPick = dp[n-1][amt];
                int pick = INF;
                if(amt-coins[n] >= 0) {
                    pick = 1 + dp[n][amt-coins[n]];
                }

                dp[n][amt] = Math.min(notPick, pick);
            }
        }

        int ans = dp[len-1][amount];
        if(ans >= INF) return -1;
        return ans;
    }

    private int coinChange_tabu_op(int[] coins, int amount) {
        int len = coins.length;
        if(amount == 0) return 0;

        int[] prev = new int[amount+1];
        int[] curr = new int[amount+1];

        for(int amt=0; amt<=amount; amt++) {
            prev[amt] = amt%coins[0] == 0 ? amt/coins[0] : INF;
        }

        Arrays.fill(curr, INF);

        for(int n=1; n<len; n++) {
            for(int amt=1; amt<=amount; amt++) {
                int notPick = prev[amt];
                int pick = INF;
                if(amt-coins[n] >= 0) {
                    pick = 1 + curr[amt-coins[n]];
                }

                curr[amt] = Math.min(notPick, pick);
            }
            prev = curr;
        }

        int ans = curr[amount];
        if(ans >= INF) return -1;
        return ans;
    }
}
