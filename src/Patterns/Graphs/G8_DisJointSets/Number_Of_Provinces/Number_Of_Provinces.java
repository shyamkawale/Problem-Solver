package Patterns.Graphs.G8_DisJointSets.Number_Of_Provinces;

import java.util.HashSet;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Patterns.Graphs.G8_DisJointSets.DisjointSet;

/*
https://leetcode.com/problems/number-of-provinces/

There are n cities. Some of them are connected, while some are not. 
If city a is connected directly with city b, and city b is connected directly with city c, 
then city a is connected indirectly with city c.

A province is a group of directly or indirectly connected cities and no other cities outside of the group.

You are given an n x n matrix isConnected where isConnected[i][j] = 1 
if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.

Return the total number of provinces.


*/
public class Number_Of_Provinces extends ProblemSolver {

    public static void main(String[] args) {
        new Number_Of_Provinces().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] isConnected = DataConvertor.to2DIntArray(args[0]);

        int res1 = findProvincesCount(isConnected);
        int res2 = findProvincesCount_UsingDisjointSet(isConnected);
        System.out.println(res1 + " " + res2);
    }

    public int findProvincesCount(int[][] adjMat) {
        int count = 0;
        int n = adjMat.length;

        Set<Integer> visited = new HashSet<>(n);

        for(int i=0; i<n; i++) {
            if(!visited.contains(i)) {
                count++;
                dfs(i, visited, adjMat);
            }
        }

        return count;
    }

    public void dfs(int node, Set<Integer> visited, int[][] adjMat) {
        visited.add(node);

        for(int c=0; c<adjMat.length; c++) {
            if(c == node) {
                continue;
            }
            if(adjMat[node][c] == 1) {
                if(!visited.contains(c)) {
                    dfs(c, visited, adjMat);
                }
            }
        }
    }

    // Using Disjoint Set
    private int findProvincesCount_UsingDisjointSet(int[][] adjMat) {
        int n = adjMat.length;
        DisjointSet ds = new DisjointSet(n);

        for(int r=0; r<n; r++) {
            for(int c=r+1; c<n; c++) {
                if(adjMat[r][c] == 1) {
                    ds.unionByRank(r, c);
                }
            }
        }

        int count = 0;
        for(int i=0; i<n; i++) {
            if(ds.getParent()[i] == i) {
                count++;
            }
        }

        return count;
    }

}
