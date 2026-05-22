package Patterns.Graphs.G6_Shortest_Path.Find_the_City_With_the_Smallest_Number_of_Neighbors_at_a_Threshold_Distance;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/description/

There are n cities numbered from 0 to n-1. 
Given the array edges where edges[i] = [fromi, toi, weighti] represents a bidirectional and weighted edge between cities fromi and toi, 
and given the integer distanceThreshold.

Return the city with the smallest number of cities that are reachable through some path 
and whose distance is at most distanceThreshold, If there are multiple such cities, 
return the city with the greatest number.

Notice that the distance of a path connecting cities i and j is equal to the 
sum of the edges' weights along that path.

Sol: Floyd Warshall Algorithm

*/
public class Find_the_City_With_the_Smallest_Number_of_Neighbors_at_a_Threshold_Distance extends ProblemSolver {

    public static void main(String[] args) {
        new Find_the_City_With_the_Smallest_Number_of_Neighbors_at_a_Threshold_Distance().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        int distanceThreshold = DataConvertor.toInt(args[2]);

        int res = findTheCity(n, edges, distanceThreshold);
        System.out.println(res);
    }

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] adjMat = createAdjacencyMatrix(n, edges);

        long[][] dist = new long[n][n];
        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                if(r == c || adjMat[r][c] != 0) {
                    dist[r][c] = adjMat[r][c];
                }
                else{
                    dist[r][c] = Integer.MAX_VALUE;
                }
            }
        }

        for(int via=0; via<n; via++) {
            for(int r=0; r<n; r++) {
                for(int c=0; c<n; c++) {
                    if (dist[r][via] != Integer.MAX_VALUE && dist[via][c] != Integer.MAX_VALUE) {
                        dist[r][c] = Math.min(dist[r][c], dist[r][via] + dist[via][c]);

                    }
                }
            }
        }

        int cityWithFewestReachable = -1;
        int fewestReachableCount = n;

        for(int r=0; r<n; r++) {
            int reachableCount = 0;
            for(int c=0; c<n; c++) {
                if(r == c) {
                    continue;
                }

                if(dist[r][c] <= distanceThreshold) {
                    reachableCount++;
                }
            }

            if (reachableCount <= fewestReachableCount) {
                fewestReachableCount = reachableCount;
                cityWithFewestReachable = r; // as r going from 0 to n.. higher n will always override when reachableCount = fewestReachableCount;
            }
        }

        return cityWithFewestReachable;
    }

    public int[][] createAdjacencyMatrix(int n, int[][] edges) {
        int[][] adjMat = new int[n][n];

        for(int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            adjMat[u][v] = wt;
            adjMat[v][u] = wt;
        }

        return adjMat;
    }
}
