package Patterns.Graphs.G6_Shortest_Path.Floyd_Warshall_Algorithm;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

public class Floyd_Warshall_Algorithm extends ProblemSolver {

    public static void main(String[] args) {
        new Floyd_Warshall_Algorithm().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        
        int[][] adjMat = createAdjacencyMatrix(n, edges);

        long[][] res = floydWarshall(n, adjMat);
        MatrixWrapper.printMatrix(res);
    }

    // TC: O(n^3)
    // Similar to Bruteforce for finding shortest path between every nodes
    // relates to DP as we are using already computed shortest distance if there is any stored in array.
    private long[][] floydWarshall(int n, int[][] adjMat) {
        long[][] dist = new long[n][n];

        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                if(r == c || adjMat[r][c] != 0) {
                    dist[r][c] = adjMat[r][c];
                }
                else {
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

        // optional as it is contaminating the result as valid dist can be -1 
        // for (int r=0; r<n; r++) {
        //     for (int c=0; c<n; c++) {
        //         if (dist[r][c] == Integer.MAX_VALUE) {
        //             dist[r][c] = -1;
        //         }
        //     }
        // }

        for (int r=0; r<n; r++) {
            for (int c=0; c<n; c++) {
                if (r==c && dist[r][c] < 0) { // or != 0
                    System.out.println("Negative Cycle Detected!!");
                    return new long[n][n];
                }
            }
        }

        return dist;
    }

    private int[][] createAdjacencyMatrix(int n, int[][] edges) {
        int[][] adjMat = new int[n][n];

        for(int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            adjMat[u][v] = wt;
        }

        return adjMat;
    }

}
