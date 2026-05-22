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

/*
https://takeuforward.org/plus/dsa/problems/print-shortest-path-

Given a weighted undirected graph having n vertices numbered from 1 to n and m edges describing there are edges, 
where edges[i]=[ai,bi,wi], representing an edge from vertex ai to bi with weight wi.

Find the shortest path between the vertex 1 and the vertex n and if path does not exist then return a list consisting of only -1.

If there exists a path, then return a list whose first element is the weight of the path and 
the remaining elements represent the shortest path from vertex 1 to vertex n.

Input: n = 5, edges = [[1,2,2], [2,5,5], [2,3,4], [1,4,1],[4,3,3],[3,5,1]]
Output: 5 1 4 3 5
*/
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

        Queue<int[]> queue = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        queue.offer(new int[] { srcNode, 0 });

        boolean[] vis = new boolean[n];

        while (!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int currNode = polledNode[0];
            int currWeight = polledNode[1];

            if(vis[currNode]) continue;
            vis[currNode] = true;

            if(currNode == destNode) {
                break;
            }

            for (Pair<Integer, Integer> neighbor : adjList.get(currNode)) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;

                if (!vis[neighborNode] && dist[neighborNode] > currWeight + weight) {
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
