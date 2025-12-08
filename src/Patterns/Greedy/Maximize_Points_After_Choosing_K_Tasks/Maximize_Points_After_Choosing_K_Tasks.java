package Patterns.Greedy.Maximize_Points_After_Choosing_K_Tasks;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Maximize_Points_After_Choosing_K_Tasks extends ProblemSolver {

    public static void main(String[] args) {
        new Maximize_Points_After_Choosing_K_Tasks().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] tech1 = DataConvertor.toIntArray(args[0]);
        int[] tech2 = DataConvertor.toIntArray(args[1]);
        int k = DataConvertor.toInt(args[2]);

        long res1 = maxPoints_greedy(tech1, tech2, k);
        long res2 = maxPoints_memo(tech1, tech2, k);
        long res3 = maxPoints_tabu(tech1, tech2, k);
        long res4 = maxPoints_tabuop(tech1, tech2, k);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    // greedy
    public long maxPoints_greedy(int[] tech1, int[] tech2, int k) {
        int n = tech1.length;
        int[] diff = new int[n];
        long sum = 0L;
        
        for(int i=0; i<n; i++) {
            diff[i] = tech1[i] - tech2[i];
            sum = sum + tech2[i];
        }

        Arrays.sort(diff);

        for(int i = n-1; i>=n-k; i--) {
            sum = sum + diff[i];
        }

        for(int i=n-k-1; i>=0; i--) {
            if(diff[i] > 0) {
                sum = sum + diff[i];
            }
            else {
                break;
            }
        }

        return sum;
    }

    public long maxPoints_memo(int[] tech1, int[] tech2, int k) {
        int len = tech1.length;
        // n -> [0, n-1], used -> [0, n]
        Long[][] dp = new Long[len][len+1];
        return helper(0, 0, tech1, tech2, k, dp);
    }

    private long helper(int n, int used, int[] tech1, int[] tech2, int k, Long[][] dp) {
        if(n >= tech1.length) {
            if(used >= k) return 0;
            return Long.MIN_VALUE;
        }

        if(dp[n][used] != null) {
            return dp[n][used];
        } 

        long pickTech1 = helper(n+1, used+1, tech1, tech2, k, dp) + tech1[n];
        long pickTech2 = helper(n+1, used, tech1, tech2, k, dp) + tech2[n];

        dp[n][used] = Math.max(pickTech1, pickTech2);
        return dp[n][used];
    }

    public long maxPoints_tabu(int[] tech1, int[] tech2, int k) {
        int len = tech1.length;
        // n -> [0, n] => (index shifted), used -> [0, n]
        long[][] dp = new long[len+1][len+1];
        for(int used=0; used<=len; used++) {
            if(used >= k) dp[len][used] = 0L;
            else dp[len][used] = Long.MIN_VALUE;
        }

        for(int n=len-1; n>=0; n--) {
            for(int used=0; used<=n; used++) {
                long pickTech1 = dp[n+1][used+1] + tech1[n];
                long pickTech2 = dp[n+1][used]+ tech2[n];

                dp[n][used] = Math.max(pickTech1, pickTech2);
            }
        }

        return dp[0][0];
    }

    public long maxPoints_tabuop(int[] tech1, int[] tech2, int k) {
        int len = tech1.length;
        // used -> [0, n]
        long[] dp = new long[len+1];
        for(int used=0; used<=len; used++) {
            if(used >= k) dp[used] = 0L;
            else dp[used] = Long.MIN_VALUE;
        }

        for(int n=len-1; n>=0; n--) {
            for(int used=0; used<=n; used++) {
                long pickTech1 = dp[used+1] + tech1[n];
                long pickTech2 = dp[used]+ tech2[n];

                dp[used] = Math.max(pickTech1, pickTech2);
            }
        }

        return dp[0];
    }

}
