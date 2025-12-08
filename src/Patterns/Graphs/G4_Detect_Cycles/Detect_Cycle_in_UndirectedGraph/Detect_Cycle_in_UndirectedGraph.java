package Patterns.Graphs.G4_Detect_Cycles.Detect_Cycle_in_UndirectedGraph;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;

/*
https://www.geeksforgeeks.org/problems/detect-cycle-in-an-undirected-graph/1

Detect if Cycle exists in Undirected Graph
Approach1: Using DFS
Approach2: Using BFS
 */
public class Detect_Cycle_in_UndirectedGraph extends ProblemSolver {
    public static void main(String[] args) {
        new Detect_Cycle_in_UndirectedGraph().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        List<List<Integer>> adjList = GraphsWrapper.createUndirectedGraph(n, edges);

        boolean res1 = detectCycle_BFS(n, adjList);
        boolean res2 = detectCycle_DFS(n, adjList);
        System.out.println(res1 + " " + res2);
    }

    private boolean detectCycle_DFS(int n, List<List<Integer>> adjList) {
        int[] vis = new int[n];
        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                if (dfs(i, -1, vis, adjList)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(int currNode, int parentNode, int[] vis, List<List<Integer>> adjList) {
        vis[currNode] = 1;

        for (int neighbor : adjList.get(currNode)) {
            if (vis[neighbor] == 0) {
                if (dfs(neighbor, currNode, vis, adjList)) {
                    return true;
                }
            }
            // if neighbor is already visited and its not curr's parent...
            else if (neighbor != parentNode) {
                return true;
            }
        }

        return false;
    }

    private boolean detectCycle_BFS(int n, List<List<Integer>> adjList) {
        int[] vis = new int[n];
        Queue<Node> queue = new ArrayDeque<>();

        queue.offer(new Node(0, -1));
        vis[0] = 1;

        while (!queue.isEmpty()) {
            Node polledNode = queue.poll();
            for (int neighbor : adjList.get(polledNode.vertex)) {
                if (vis[neighbor] == 0) {
                    queue.offer(new Node(neighbor, polledNode.vertex));
                    vis[neighbor] = 1;
                }
                // 🔥 If someone has visited and its not parent
                // (that means from different path we are visiting that node again - hence there
                // is a cycle) ✅
                else if (neighbor != polledNode.parent) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class Node {
        int vertex;
        int parent;

        public Node(int vertex, int parent) {
            this.vertex = vertex;
            this.parent = parent;
        }
    }
}
