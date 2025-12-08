package Patterns.Graphs.G8_DisJointSets.Minimum_Time_for_K_Connected_Components;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Patterns.Graphs.G8_DisJointSets.DisjointSet;

/*

Approach: BS on Ans + Disjoint Set
 */
public class Minimum_Time_for_K_Connected_Components extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Time_for_K_Connected_Components().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        int k = DataConvertor.toInt(args[2]);

        int res = minTime(n, edges, k);
        System.out.println(res);
    }

    public int minTime(int n, int[][] edges, int k) {
        int minTime = 0;
        int maxTime = 0;
        for(int[] edge: edges) {
            maxTime = Math.max(maxTime, edge[2]);
        }

        int left = minTime;
        int right = maxTime;
        int ans = -1;

        while(left <= right) {
            int mid = left + (right - left)/2;

            int noOfComp = findNoOfComp(mid, n, edges);

            if(noOfComp >= k) {
                ans = mid;
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }
        return ans;
    }

    public int findNoOfComp(int maxTime, int n, int[][] edges) {
        DisjointSet ds = new DisjointSet(n);

        for(int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];
            int time = edge[2];

            // don't add edge nodes in component/disjoint-set (don't connect edge nodes) 
            // if its time <= maxTime.. (because we need to find minTime - so we ignore minTime first) 
            if(time > maxTime) {
                ds.unionByRank(u, v);
            }
        }

        return ds.getNoOfComp();
    }
}
