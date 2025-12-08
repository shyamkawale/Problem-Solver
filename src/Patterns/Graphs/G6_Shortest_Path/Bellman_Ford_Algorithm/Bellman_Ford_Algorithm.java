package Patterns.Graphs.G6_Shortest_Path.Bellman_Ford_Algorithm;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Bellman_Ford_Algorithm extends ProblemSolver {

    public static void main(String[] args) {
        new Bellman_Ford_Algorithm().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int srcNode = DataConvertor.toInt(args[1]);
        int[][] edges = DataConvertor.to2DIntArray(args[2]);

        int[] res = bellmanFord(n, srcNode, edges);
        System.out.println(Arrays.toString(res));
    }

    private int[] bellmanFord(int n, int srcNode, int[][] edges) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        for(int i=0; i<n-1; i++) {
            for(int[] edge: edges) {
                int u = edge[0];
                int v = edge[1];
                int wt = edge[2];

                if(dist[u] != Integer.MAX_VALUE && dist[v] > dist[u] + wt) {
                    dist[v] = dist[u] + wt;
                }
            }
        }

        for(int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            if(dist[u] != Integer.MAX_VALUE && dist[v] > dist[u] + wt) {
                System.out.println("Negative Cycle Detected");
                return new int[] {-1};
            }
        }

        return dist;
    }

}
