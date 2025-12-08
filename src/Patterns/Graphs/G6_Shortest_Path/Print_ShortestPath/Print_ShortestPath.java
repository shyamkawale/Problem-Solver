package Patterns.Graphs.G6_Shortest_Path.Print_ShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;

public class Print_ShortestPath extends ProblemSolver {

    public static void main(String[] args) {
        new Print_ShortestPath().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        List<List<Pair<Integer, Integer>>> adjList = GraphsWrapper.createWeightedDirectedGraph(n, edges);

        List<Integer> res = printShortestPath(n, adjList);
        System.out.println(res);
    }

    private List<Integer> printShortestPath(int n, List<List<Pair<Integer, Integer>>> adjList) {
        int srcNode = 0;
        int destNode = n - 1;
        List<Integer> res = new ArrayList<>();

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        parent[srcNode] = srcNode;

        Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        queue.offer(new int[] { srcNode, 0 });

        while (!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int currNode = polledNode[0];
            int currWeight = polledNode[1];

            if(currNode == destNode) {
                break;
            }
            if(currWeight > dist[currNode]) {
                continue;
            }

            for (Pair<Integer, Integer> neighbor : adjList.get(currNode)) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;

                if (dist[neighborNode] > currWeight + weight) {
                    dist[neighborNode] = currWeight + weight;
                    queue.offer(new int[] { neighborNode, dist[neighborNode] });
                    parent[neighborNode] = currNode;
                }
            }
        }

        if (dist[destNode] == Integer.MAX_VALUE) {
            return res; // No path
        }

        int node = destNode;
        while(parent[node] != node) {
            res.add(node);
            node = parent[node];
        }
        res.add(srcNode);

        Collections.reverse(res);
        return res;
    }

}
