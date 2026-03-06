package Patterns.Graphs.G5_Topological_Sort.Find_Topological_Sort;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
/*
Find Topological Sort

Approach 1: Using DFS
Approach 2: Using BFS (KAHN's Algorithm)
 */
public class Find_Topological_Sort extends ProblemSolver {
    public static void main(String[] args) {
        new Find_Topological_Sort().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        List<List<Integer>> adjList = GraphsWrapper.createDirectedGraph(n, edges);

        List<Integer> res1 = findTopologicalSort_DFS(n, adjList);
        List<Integer> res2 = findTopologicalSort_BFS(n, adjList);
        System.out.println(res1 + " " + res2);
    }

    // Topological Sort using DFS + Stack
    // TC: O(V + E) + O(V) = O(2V + E)
    // SC: RecO(V) + O(V + V + V) -> rec-stack, vis, pathVis, stack
    private List<Integer> findTopologicalSort_DFS(int n, List<List<Integer>> adjList) {
        List<Integer> res = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        int[] vis = new int[n];
        int[] pathVis = new int[n];

        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                if(!dfs(i, stack, vis, pathVis, adjList)) {
                    System.out.println("TopologicalSort:DFS - Graph is not DAG!!");
                    return Arrays.asList();
                }
            }
        }

        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }
        return res;
    }

    private boolean dfs(int node, Deque<Integer> stack, int[] vis, int[] pathVis, List<List<Integer>> adjList) {
        vis[node] = 1;
        pathVis[node] = 1;

        for (int neighbor : adjList.get(node)) {
            if (vis[neighbor] == 0) {
                if(!dfs(neighbor, stack, vis, pathVis, adjList)) {
                    return false;
                }
            }
            else if(pathVis[neighbor] == 1){
                return false;
            }
        }

        stack.push(node);
        pathVis[node] = 0;
        return true;
    }

    // KAHN's ALGO (BFS + indegree)
    // TC: O(V+E) + O(V+E) -> indegree calculation, BFS
    // SC: O(V + V) -> indegree, queue
    // Componentwise checking not required here because every component has at least one node with indeg=0
    // and hence atleast one node of every component will get included in the queue initially
    private List<Integer> findTopologicalSort_BFS(int n, List<List<Integer>> adjList) {
        List<Integer> res = new ArrayList<>();
        int[] inDeg = new int[n];
        Queue<Integer> queue = new ArrayDeque<>(); // this queue will have no dependency nodes (indeg = 0)

        for (List<Integer> list : adjList) {
            for (int v : list) {
                inDeg[v]++; // no. of dependencies dalo sabki
            }
        }

        for (int i = 0; i < n; i++) {
            if (inDeg[i] == 0) { // Atleast one indegree will be 0 because of DAG
                queue.offer(i); // jiski dependency zero hai woh dalo queue me (unse suruwat karte..)
            }
        }

        while (!queue.isEmpty()) {
            int polledNode = queue.poll();

            for (int neighbor : adjList.get(polledNode)) {
                inDeg[neighbor]--; // (ek baar idhar jake aye toh iska indegree kam karo.. dependency ek se kam karo..)
                if (inDeg[neighbor] == 0) {
                    queue.offer(neighbor); // indegree became 0, so now we can add in the result (kyuki koi dependency nhi hai abhi)
                }
            }

            res.add(polledNode);
        }

        if(res.size() != n) {
            System.out.println("TopologicalSort:BFS - Graph is not DAG!!");
        }
        return res;
    }
}
