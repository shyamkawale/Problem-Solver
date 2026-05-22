package Patterns.Graphs.G6_Shortest_Path.Bellman_Ford_Algorithm;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
BELLMAN FORD ALGORITHM

The algorithm finds the minimum distance to reach any node by performing N-1 times Edge Relaxation.

Edge Relaxation:
    This process of updating the distance of any node to optimal distance is known as Edge Relaxation.

Why exactly N-1 iterations?
=> Longest Path in a Graph: 
    The longest possible path without cycles in a graph with N nodes consists of (N-1) edges.
    So after N-1 relaxations, all shortest paths are guaranteed to be found.
    means the Bellman-Ford algorithm updates the shortest path information for one more edge in the path.
=> Ensuring All Paths Are Considered: 
    By repeating the relaxation process (N-1) times, 
    the algorithm ensures that all vertices are updated based on paths that could extend up to (N-1) edges.

How to detect a negative cycle in the graph?
=> If there would not have been a negatice cycle then after N-1 iterations we would have got all relaxed edges.
    So, we can iterate all edges and check if all edges are relaxed or not. If no then we have -ve cycle.

TC: O(V*E)
SC: O(V)
*/
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

        // Relaxing Edges N-1 times
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

        // For checking -ve cycle
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
