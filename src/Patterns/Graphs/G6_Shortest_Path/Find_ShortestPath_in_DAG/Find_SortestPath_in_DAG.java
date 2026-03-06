package Patterns.Graphs.G6_Shortest_Path.Find_ShortestPath_in_DAG;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;

/*
https://www.geeksforgeeks.org/problems/shortest-path-in-undirected-graph/1 (url is differnt but problem is correct)


 */
public class Find_SortestPath_in_DAG extends ProblemSolver {
    public static void main(String[] args) {
        new Find_SortestPath_in_DAG().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int srcNode = DataConvertor.toInt(args[1]);
        int[][] edges = DataConvertor.to2DIntArray(args[2]);
        List<List<Pair<Integer, Integer>>> adjList = GraphsWrapper.createWeightedDirectedGraph(n, edges);

        int[] res1 = findShortestPathInDAG_DFS(n, srcNode, adjList);
        int[] res2 = findShortestPathInDAG_BFS(n, srcNode, adjList);
        int[] res3 = findShortestPath_Dijkstras(n, srcNode, adjList);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2) + " " + Arrays.toString(res3));
    }

    // Topological Sort + Relaxation (DFS)
    // Works for: DAG only (both positive and NEGATIVE weights)
    // TC: O(V + E) -> O(V+E) for topo sort + O(V+E) for relaxation
    // SC: RecO(V) + O(V + V) -> Recursion stack, visited, stack
    private int[] findShortestPathInDAG_DFS(int n, int srcNode, List<List<Pair<Integer, Integer>>> adjList) {
        // Step 1: Find Topological Sort
        int[] vis = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                dfs(i, vis, stack, adjList);
            }
        }

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        // This is right code.. but optional and should be avoided..
        // while(stack.peek() != srcNode) {
        //     stack.pop();
        // }

        // Step 2: Take out nodes from stack and relax its neighbor's edges..
        while (!stack.isEmpty()) {
            int polledNode = stack.pop();

            if (dist[polledNode] != Integer.MAX_VALUE) {
                for (Pair<Integer, Integer> neighbor : adjList.get(polledNode)) {
                    int neighborNode = neighbor.vertex;
                    int weight = neighbor.weight;

                    // Relaxation
                    dist[neighborNode] = Math.min(dist[neighborNode], dist[polledNode] + weight);
                }
            }
        }

        return dist;
    }

    private void dfs(int node, int[] vis, Deque<Integer> stack, List<List<Pair<Integer, Integer>>> adjList) {
        vis[node] = 1;

        for (Pair<Integer, Integer> neighbor : adjList.get(node)) {
            int neighborNode = neighbor.vertex; // here we will not need to check visited as its a DAG!!
            if (vis[neighborNode] == 0) {
                dfs(neighborNode, vis, stack, adjList);
            }
        }

        stack.push(node);
    }

    // BFS with Queue (Not standard - avoid using)
    // Works for: DAG, Cyclic graphs (both positive and NEGATIVE weights)
    // TC: O(V + E) best case, O(V * E) or exponential worst case (same node processed multiple times)
    // SC: O(V) for dist + O(V) for queue
    private int[] findShortestPathInDAG_BFS(int n, int srcNode, List<List<Pair<Integer, Integer>>> adjList) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(srcNode);

        while (!queue.isEmpty()) {
            int polledNode = queue.poll();

            for (Pair<Integer, Integer> neighbor : adjList.get(polledNode)) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;

                if (dist[neighborNode] > dist[polledNode] + weight) { // Integer Overflow issue can happen
                    dist[neighborNode] = dist[polledNode] + weight;
                    queue.offer(neighborNode);
                }
            }
        }

        return dist;
    }

    // Dijkstra's Algorithm (PriorityQueue)
    // Works for: DAG, Cyclic graphs with NON-NEGATIVE weights only
    // TC: O((V + E) * log V) -> log V for each heap operation
    // SC: O(V) for dist + O(V) for priority queue
    private int[] findShortestPath_Dijkstras(int n, int srcNode, List<List<Pair<Integer, Integer>>> adjList) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        // PriorityQueue: {distance, node} - sorted by distance (min-heap)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, srcNode});

        while (!pq.isEmpty()) {
            int[] polled = pq.poll();
            int currDist = polled[0];
            int currNode = polled[1];

            // Skip if we've already found a better path
            if (currDist > dist[currNode]) {
                continue;
            }

            for (Pair<Integer, Integer> neighbor : adjList.get(currNode)) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;

                // Relaxation
                if (dist[currNode] + weight < dist[neighborNode]) {
                    dist[neighborNode] = dist[currNode] + weight;
                    pq.offer(new int[]{dist[neighborNode], neighborNode});
                }
            }
        }

        return dist;
    }
}
