package Patterns.Graphs.G6_Shortest_Path.Find_ShortestPath_in_Undirected_Graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;

public class Find_ShortestPath_in_Undirected_Graph extends ProblemSolver {
    public static void main(String[] args) {
        new Find_ShortestPath_in_Undirected_Graph().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int srcNode = DataConvertor.toInt(args[1]);
        int[][] edges = DataConvertor.to2DIntArray(args[2]);
        List<List<Integer>> adjList = GraphsWrapper.createUndirectedGraph(n, edges);

        int[] res1 = findShortestPathInUndirectedGraph_Queue(n, srcNode, adjList);
        System.out.println(Arrays.toString(res1));
    }

    // Djiktras - Queue Approach
    private int[] findShortestPathInUndirectedGraph_Queue(int n, int srcNode, List<List<Integer>> adjList) {
        int[] dist = new int[n]; // we don't need vis array as dist array will act as visited array (dist =
                                 // infinity will be non visited)
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(srcNode);

        while (!queue.isEmpty()) {
            int polledNode = queue.poll();

            for (int neighbor : adjList.get(polledNode)) {
                int weight = 1;
                if (dist[neighbor] > dist[polledNode] + weight) {
                    dist[neighbor] = dist[polledNode] + weight;
                    queue.offer(neighbor);
                }
            }
        }

        return dist;
    }

}
