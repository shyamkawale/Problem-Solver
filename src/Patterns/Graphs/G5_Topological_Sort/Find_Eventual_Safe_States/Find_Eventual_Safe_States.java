package Patterns.Graphs.G5_Topological_Sort.Find_Eventual_Safe_States;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;

/*
https://leetcode.com/problems/find-eventual-safe-states/

There is a directed graph of n nodes with each node labeled from 0 to n - 1. 
The graph is represented by a 0-indexed 2D integer array graph where graph[i] is an integer array of nodes adjacent to node i, 
meaning there is an edge from node i to each node in graph[i].

A node is a terminal node if there are no outgoing edges. 
A node is a safe node if every possible path starting from that node leads to a terminal node (or another safe node).

Return an array containing all the safe nodes of the graph. 
The answer should be sorted in ascending order.

Example 1:
Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
Output: [2,4,5,6]
Explanation: The given graph is shown above.
Nodes 5 and 6 are terminal nodes as there are no outgoing edges from either of them.
Every path starting at nodes 2, 4, 5, and 6 all lead to either node 5 or 6.

Example 2:
Input: graph = [[1,2,3,4],[1,2],[3,4],[0,4],[]]
Output: [4]
Explanation:
Only node 4 is a terminal node, and every path starting at node 4 leads to node 4.
*/
public class Find_Eventual_Safe_States extends ProblemSolver {

    public static void main(String[] args) {
        new Find_Eventual_Safe_States().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        List<List<Integer>> adjList = GraphsWrapper.createDirectedGraph(n, edges);

        List<Integer> res1 = findEventualSafeNodes1(n, adjList);
        List<Integer> res2 = findEventualSafeNodes2(n, adjList);
        List<Integer> res3 = findEventualSafeNodes3(n, adjList);

        List<Integer> res4 = findEventualSafeNodes4(n, adjList);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    // BFS Topological sort (Concept is important here- on why elements in topological sort list are our result)
    private List<Integer> findEventualSafeNodes4(int n, List<List<Integer>> adjList) {
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> adjListRev = new ArrayList<>();
        int[] outDegree = new int[n];
        for(int i=0; i<n; i++) {
            adjListRev.add(new ArrayList<>());
        }

        for(int i=0; i<n; i++) {
            for(int neighbor: adjList.get(i)) {
                adjListRev.get(neighbor).add(i);
            }
            outDegree[i] = adjList.get(i).size();
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for(int i=0; i<n; i++) {
            if(outDegree[i] == 0) {
                queue.offer(i);
                res.add(i);
            }
        }

        while(!queue.isEmpty()) {
            int polledNode = queue.poll();

            for(int neighbor: adjListRev.get(polledNode)) {
                outDegree[neighbor]--;
                if(outDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                    res.add(neighbor);
                }
            }
        }

        Collections.sort(res);
        return res;
    }

    private List<Integer> findEventualSafeNodes1(int n, List<List<Integer>> adjList) {
        List<Integer> res = new ArrayList<>();

        for(int i=0; i<n; i++) {
            // for every nodes find that all path leading from that node leads to any cycle or not... 🔥
            if(dfs1(i, new int[n], new int[n], adjList)) {
                res.add(i);
            }
        }

        return res;
    }

    private boolean dfs1(int node, int[] vis, int[] pathVis, List<List<Integer>> adjList) {
        vis[node] = 1;
        pathVis[node] = 1;

        for(int neighbor: adjList.get(node)) {
            if(vis[neighbor] == 0) {
                if(!dfs1(neighbor, vis, pathVis, adjList)) {
                    return false;
                }
            }
            else if(pathVis[neighbor] == 1) {
                return false;
            }
        }

        pathVis[node] = 0;
        return true;
    }


    private List<Integer> findEventualSafeNodes2(int n, List<List<Integer>> adjList) {
        List<Integer> res = new ArrayList<>();
        int[] vis = new int[n];
        int[] pathVis = new int[n];
        // (vis, pathVis) ==> (0,0) = unvisited, (1, 1) = visited unsafe, (1, 0) = visited safe

        for(int i=0; i<n; i++) {
            // for every nodes find that all path leading from that node leads to any cycle or not... 🔥
            if(dfs2(i, vis, pathVis, adjList)) {
                res.add(i);
            }
        }

        return res;
    }

    private boolean dfs2(int node, int[] vis, int[] pathVis, List<List<Integer>> adjList) {
        if(pathVis[node] == 1) return false;

        vis[node] = 1;
        pathVis[node] = 1;

        for(int neighbor: adjList.get(node)) {
            if(vis[neighbor] == 0) {
                if(!dfs2(neighbor, vis, pathVis, adjList)) {
                    return false;
                }
            }
            else if(pathVis[neighbor] == 1) {
                return false;
            }
        }

        pathVis[node] = 0;
        return true;
    }


    private List<Integer> findEventualSafeNodes3(int n, List<List<Integer>> adjList) {
        List<Integer> res = new ArrayList<>();

        // 0 = unvisited, 1 = visited unsafe, 2 = visited safe
        int[] state = new int[n];

        for(int i=0; i<n; i++) {
            // for every nodes find that all path leading from that node leads to any cycle or not... 🔥
            if(dfs3(i, state, adjList)) {
                res.add(i);
            }
        }

        return res;
    }

    private boolean dfs3(int node, int[] state, List<List<Integer>> adjList) {
        if (state[node] == 1) return false; // cycle detected
        if (state[node] == 2) return true;  // already safe

        state[node] = 1; // mark as visiting

        for (int neighbor : adjList.get(node)) {
            if (!dfs3(neighbor, state, adjList)) {
                return false;
            }
        }

        state[node] = 2; // mark as safe
        return true;
    }
}
