package Patterns.Dynamic_Programming.Number_of_ZigZag_Arrays_I;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Number_of_ZigZag_Arrays_I extends ProblemSolver {

    public static void main(String[] args) {
        new Number_of_ZigZag_Arrays_I().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int level = DataConvertor.toInt(args[0]);
        int l = DataConvertor.toInt(args[1]);
        int r = DataConvertor.toInt(args[2]);

        int res1 = zigZagArrays_rec(level, l, r);
        int res2 = zigZagArrays_memo(level, l, r);
        int res3 = zigZagArrays_tabu(level, l, r);
        int res4 = zigZagArrays_tabu_prefixSum(level, l, r);
        int res5 = zigZagArrays_tabu_prefixSum_op1(level, l, r);
        int res6 = zigZagArrays_tabu_prefixSum_op2(level, l, r);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4 + " " + res5 + " " + res6);
    }

    private static final int MOD = 1000000007;
    public int zigZagArrays_rec(int level, int l, int r) {
        int res = 0;

        for(int n=l; n<=r; n++) {
            res = (res + helper1(n, level-1, 0, l, r))%MOD;
        }
        return res;
    }

    public int helper1(int n, int level, int prevDir, int l, int r) {
        if(n < l || n > r) {
            return 0;
        }

        if(level <= 0) {
            return 1;
        }

        int pick = 0;
        for(int n1=l; n1<=r; n1++) {
            if(n1==n) continue;
            int currDir = (n < n1) ? 1 : -1;
            if(currDir == prevDir) continue;
            
            pick = (pick + helper1(n1, level-1, currDir, l, r))%MOD;
        }

        return pick;
    }

    public int zigZagArrays_memo(int level, int l, int r) {
        int res = 0;
        // [l, r] * [level-1, 0] * [-1, 1]
        int[][][] dp = new int[r-l+1][level][3];
        for(int i=0; i<r-l+1; i++) {
            for(int lvl=0; lvl<level; lvl++) {
                Arrays.fill(dp[i][lvl], -1);
            }
        }

        for(int n=l; n<=r; n++) {
            res = (res + helper2(n, level-1, 0, l, r, dp))%MOD;
        }
        return res;
    }

    public int helper2(int n, int level, int prevDir, int l, int r, int[][][] dp) {
        if(n < l || n > r) {
            return 0;
        }

        if(level <= 0) {
            return 1;
        }

        if(dp[n-l][level][prevDir+1] != -1) {
            return dp[n-l][level][prevDir+1];
        }

        int pick = 0;
        for(int n1=l; n1<=r; n1++) {
            if(n1==n) continue;
            int currDir = (n < n1) ? 1 : -1;
            if(currDir == prevDir) continue;
            
            pick = (pick + helper2(n1, level-1, currDir, l, r, dp))%MOD;
        }

        dp[n-l][level][prevDir+1] = pick;
        return dp[n-l][level][prevDir+1];
    }

    private int zigZagArrays_tabu(int level, int l, int r) {
        // [l, r] * [level-1, 0] * [-1, 1]
        int[][][] dp = new int[r-l+1][level][3];

        for(int n=l; n<=r; n++) {
            for(int dir=-1; dir<=1; dir++) {
                dp[n-l][0][dir+1] = 1;
            }
        }

        for(int lvl=1; lvl<level; lvl++) {
            for(int n=l; n<=r; n++) {
                for(int prevDir=-1; prevDir<=1; prevDir++) {
                    int pick = 0;
                    for(int n1=l; n1<=r; n1++) {
                        if(n1==n) continue;
                        int currDir = (n < n1) ? 1 : -1;
                        if(currDir == prevDir) continue; // if(prevDir != 0 && currDir == prevDir) continue;
                        
                        pick = (pick + dp[n1-l][lvl-1][currDir+1])%MOD;
                    }
                    dp[n-l][lvl][prevDir+1] = pick;
                }
            }
        }

        int res = 0;
        for(int n=l; n<=r; n++) {
            res = (res + dp[n-l][level-1][0+1])%MOD;
        }
        return res;
    }

    private int zigZagArrays_tabu_prefixSum(int level, int l, int r) {
        // [l, r] * [level-1, 0] * [-1, 1]
        int[][][] dp = new int[r-l+1][level][3];

        for(int n=l; n<=r; n++) {
            for(int dir=-1; dir<=1; dir++) {
                dp[n-l][0][dir+1] = 1;
            }
        }

        for(int lvl=1; lvl<level; lvl++) {
            int[] prefixSumPos = new int[r-l+2]; // prefixSumPos[i] = sum of dp[x][lvl-1][1] for x in [l, l+i-1]
            int[] prefixSumNeg = new int[r-l+2]; // prefixSumNeg[i] = sum of dp[x][lvl-1][-1] for x in [l, l+i-1]
            
            for(int n=l; n<=r; n++) {
                prefixSumPos[n-l+1] = (prefixSumPos[n-l] + dp[n-l][lvl-1][1+1]) % MOD;
                prefixSumNeg[n-l+1] = (prefixSumNeg[n-l] + dp[n-l][lvl-1][-1+1]) % MOD;
            }

            for(int n=l; n<=r; n++) {
                for(int prevDir=-1; prevDir<=1; prevDir++) {
                    int pick = 0;
                    int sumGreater = (prefixSumPos[r-l+1] - prefixSumPos[n-l+1] + MOD) % MOD;
                    int sumSmaller = (prefixSumNeg[n-l] - prefixSumNeg[0] + MOD) % MOD;

                    // sum of elements end --- start-1 = P[end] - P[start]
                    if(prevDir == -1) {
                        pick = sumGreater;
                    }
                    else if(prevDir == 1) {
                        pick = sumSmaller;
                    }
                    else {
                        pick = (sumGreater + sumSmaller) % MOD;
                    }

                    dp[n-l][lvl][prevDir+1] = pick;
                }
            }
        }

        int res = 0;
        for(int n=l; n<=r; n++) {
            res = (res + dp[n-l][level-1][0+1])%MOD;
        }
        return res;
    }

    public int zigZagArrays_tabu_prefixSum_op1(int level, int l, int r) {
        // [l, r] * [level-1, 0] * [-1, 1]
        int[][][] dp = new int[r-l+1][level][3];

        for(int n=l; n<=r; n++) {
            for(int dir=-1; dir<=1; dir++) {
                dp[n-l][0][dir+1] = 1;
            }
        }

        for(int lvl=1; lvl<level; lvl++) {
            int[] prefixSumPos = new int[r-l+2]; // prefixSumPos[i] = sum of dp[x][lvl-1][1] for x in [l, l+i-1]
            int[] prefixSumNeg = new int[r-l+2]; // prefixSumNeg[i] = sum of dp[x][lvl-1][-1] for x in [l, l+i-1]
            
            for(int n=l; n<=r; n++) {
                prefixSumPos[n-l+1] = (prefixSumPos[n-l] + dp[n-l][lvl-1][1+1]) % MOD;
                prefixSumNeg[n-l+1] = (prefixSumNeg[n-l] + dp[n-l][lvl-1][-1+1]) % MOD;
            }

            for(int n=l; n<=r; n++) {
                int sumGreater = prefixSumPos[r-l+1] - prefixSumPos[n-l+1];
                if(sumGreater < 0) {
                    sumGreater = sumGreater + MOD;
                }
                int sumSmaller = prefixSumNeg[n-l];
                
                dp[n-l][lvl][-1+1] = sumGreater;
                dp[n-l][lvl][0+1] = (sumGreater + sumSmaller) % MOD;
                dp[n-l][lvl][1+1] = sumSmaller;
            }
        }

        int res = 0;
        for(int n=l; n<=r; n++) {
            res = (res + dp[n-l][level-1][0+1])%MOD;
        }
        return res;
    }

    public int zigZagArrays_tabu_prefixSum_op2(int level, int l, int r) {
        // [l, r] * [level-1, 0] * [-1, 1]

        int[][] prev = new int[r-l+1][3];
        // int[][][] dp = new int[r-l+1][level][3];

        for(int n=l; n<=r; n++) {
            for(int dir=-1; dir<=1; dir++) {
                prev[n-l][dir+1] = 1;
            }
        }

        for(int lvl=1; lvl<level; lvl++) {
            int[][] curr = new int[r-l+1][3];
            int[] prefixSumPos = new int[r-l+2]; // prefixSumPos[i] = sum of prev[x][1] for x in [l, l+i-1]
            int[] prefixSumNeg = new int[r-l+2]; // prefixSumNeg[i] = sum of prev[x][-1] for x in [l, l+i-1]
            
            for(int n=l; n<=r; n++) {
                prefixSumPos[n-l+1] = (prefixSumPos[n-l] + prev[n-l][1+1]) % MOD;
                prefixSumNeg[n-l+1] = (prefixSumNeg[n-l] + prev[n-l][-1+1]) % MOD;
            }

            for(int n=l; n<=r; n++) {
                int sumGreater = prefixSumPos[r-l+1] - prefixSumPos[n-l+1];
                if(sumGreater < 0) {
                    sumGreater = sumGreater + MOD;
                }
                int sumSmaller = prefixSumNeg[n-l];
                
                curr[n-l][-1+1] = sumGreater;
                curr[n-l][0+1] = (sumGreater + sumSmaller) % MOD;
                curr[n-l][1+1] = sumSmaller;
            }

            prev = curr;
        }

        int res = 0;
        for(int n=l; n<=r; n++) {
            res = (res + prev[n-l][0+1])%MOD;
        }
        return res;
    }
    
}
