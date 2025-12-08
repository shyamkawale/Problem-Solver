package Patterns.Graphs.G3_Bipartite_Graph.Is_Graph_Bipartite;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;

/*
https://leetcode.com/problems/is-graph-bipartite/

Check if Undirected Graph is Bipartite or not.

Approach1: Using DFS
Approach2: Using BFS
 */
public class Is_Graph_Bipartite extends ProblemSolver {
    public static void main(String[] args) {
        new Is_Graph_Bipartite().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        List<List<Integer>> adjList = GraphsWrapper.createUndirectedGraph(n, edges);

        boolean isBipartite1 = isGraphBipartite_DFS(n, adjList);
        boolean isBipartite2 = isGraphBipartite_BFS(n, adjList);
        System.out.println(isBipartite1 + " " + isBipartite2);
    }

    private boolean isGraphBipartite_DFS(int n, List<List<Integer>> adjList) {
        int[] vis = new int[n];
        Arrays.fill(vis, -1);

        for(int i=0; i<n; i++) {
            if(vis[i] == -1) {
                if(!dfs(i, 0, vis, adjList)){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean dfs(int node, int color, int[] vis, List<List<Integer>> adjList) {
        vis[node] = color;

        for(int neighbor: adjList.get(node)) {
            if(vis[neighbor] == -1) {
                if(!dfs(neighbor, color^1, vis, adjList)) {
                    return false;
                }
            }
            else if(vis[neighbor] == color) {
                return false;
            }
        }

        return true;
    }

    private boolean isGraphBipartite_BFS(int n, List<List<Integer>> adjList) {
        int[] vis = new int[n];
        Arrays.fill(vis, -1);

        for(int i=0; i<n; i++) {
            if(vis[i] == -1) {
                if(!bfs(i, vis, adjList)){
                    return false;
                }
            }
        }

        return true;
    }


    private boolean bfs(int node, int[] vis, List<List<Integer>> adjList) {
        int color = 0;
        Queue<Integer> queue = new ArrayDeque<>();

        queue.offer(node);
        vis[node] = color;

        while(!queue.isEmpty()) {
            int polledNode = queue.poll();
            for(int neighbor: adjList.get(polledNode)) {
                if(vis[neighbor] == -1) {
                    queue.offer(neighbor);
                    vis[neighbor] = vis[polledNode]^1;
                }
                else if(vis[neighbor] == vis[polledNode]) {
                    return false;
                }
            }
        }

        return true;
    }

}
