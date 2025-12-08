package Patterns.Graphs.G8_DisJointSets.Minimize_Maximum_Component_Cost;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Patterns.Graphs.G8_DisJointSets.DisjointSet;

/*

Approach: BS on Ans + Disjoint Set
 */
public class Minimize_Maximum_Component_Cost extends ProblemSolver {
    public static void main(String[] args) {
        new Minimize_Maximum_Component_Cost().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        int k = DataConvertor.toInt(args[2]);

        int res = minCost(n, edges, k);
        System.out.println(res);
    }

    public int minCost(int n, int[][] edges, int k) {
        if(edges.length == 0) {
            return 0;
        }

        if(k == n) {
            return 0;
        }
        
        int minWt = Integer.MAX_VALUE;
        int maxWt = Integer.MIN_VALUE;
        for(int[] edge: edges) {
            int wt = edge[2];
            minWt = Math.min(minWt, wt);
            maxWt = Math.max(maxWt, wt);
        }

        int left = minWt;
        int right = maxWt;
        int possibleWt = -1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;

            int noOfComp = getNoOfComponent(mid, n, edges);
            
            if(noOfComp <= k) {
                possibleWt = mid;
                right = mid - 1;
            }
            else if(noOfComp > k) {
                left = mid + 1;
            }
        }

        int res = maxWt;
        for(int[] edge: edges) {
            int wt = edge[2];

            if(wt >= possibleWt) {
                res = Math.min(res, wt);
            }
        }

        return res;
    }

    public int getNoOfComponent(int maxWt, int n, int[][] edges) {
        DisjointSet ds = new DisjointSet(n);

        for(int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            if(wt <= maxWt) {
                ds.unionByRank(u, v);
            }
        }

        return ds.getNoOfComp();
    }

}
