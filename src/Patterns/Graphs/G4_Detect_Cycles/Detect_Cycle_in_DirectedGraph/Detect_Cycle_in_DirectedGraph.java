package Patterns.Graphs.G4_Detect_Cycles.Detect_Cycle_in_DirectedGraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;

/*
https://www.geeksforgeeks.org/problems/detect-cycle-in-a-directed-graph/1

Detect Cycle in Directed Graph

Approach 1: Using DFS
Approach 2: Using BFS (KAHN's Algorithm)
 */
public class Detect_Cycle_in_DirectedGraph extends ProblemSolver {
    public static void main(String[] args) {
        new Detect_Cycle_in_DirectedGraph().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        List<List<Integer>> adjList = GraphsWrapper.createDirectedGraph(n, edges);

        boolean res1 = detectCycle_DFS(n, adjList);
        boolean res2 = detectCycle_BFS(n, adjList);
        System.out.println(res1 + " " + res2);
    }

    // Path Visited concept
    private boolean detectCycle_DFS(int n, List<List<Integer>> adjList) {
        int[] vis = new int[n];
        int[] pathVis = new int[n];
        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                if (dfs(i, adjList, vis, pathVis)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(int currNode, List<List<Integer>> adjList, int[] vis, int[] pathVis) {
        vis[currNode] = 1;
        pathVis[currNode] = 1;

        for (int neighbor : adjList.get(currNode)) {
            if (vis[neighbor] == 0) {
                if (dfs(neighbor, adjList, vis, pathVis)) {
                    return true;
                }
            }
            // if node is already vis and pathVis also visited
            else if (pathVis[neighbor] == 1) {
                return true;
            }
        }
        pathVis[currNode] = 0;
        return false;
    }

    // Kahn's Algorithm
    private boolean detectCycle_BFS(int n, List<List<Integer>> adjList) {
        int[] inDeg = new int[n];
        Queue<Integer> queue = new ArrayDeque<>();
        List<Integer> topoSort = new ArrayList<>();

        for (List<Integer> list : adjList) {
            for (int v : list) {
                inDeg[v]++;
            }
        }

        for (int i = 0; i < n; i++) {
            if (inDeg[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int polledNode = queue.poll();

            for (int neighbor : adjList.get(polledNode)) {
                if (inDeg[neighbor] > 0) {
                    inDeg[neighbor]--;
                    if (inDeg[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }

            topoSort.add(polledNode);
        }

        return topoSort.size() < n;
    }
}
