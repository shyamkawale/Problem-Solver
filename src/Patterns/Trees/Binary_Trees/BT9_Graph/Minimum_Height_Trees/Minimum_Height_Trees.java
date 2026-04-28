package Patterns.Trees.Binary_Trees.BT9_Graph.Minimum_Height_Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/minimum-height-trees

A tree is an undirected graph in which any two vertices are connected by exactly one path. 
In other words, any connected graph without simple cycles is a tree.

Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges 
where edges[i] = [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the tree, 
you can choose any node of the tree as the root. When you select a node x as the root, the result tree has height h. 
Among all possible rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).

Return a list of all MHTs' root labels. You can return the answer in any order.

The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
*/
public class Minimum_Height_Trees extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Height_Trees().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);

        List<Integer> res = findMinHeightTrees(n, edges);
        System.out.println(res);
    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1)
            return Arrays.asList(0);
        int[] inDeg = new int[n];
        List<List<Integer>> adjList = createUndirectedGraph(n, edges, inDeg);

        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (inDeg[i] == 1) {
                queue.offer(i);
            }
        }

        // We need to stop this when <= 2 nodes are remaining
        int remaining = n;
        while (remaining > 2) {
            int size = queue.size();
            remaining = remaining - size;

            for (int s = 0; s < size; s++) {
                int polledNode = queue.poll();

                inDeg[polledNode]--;

                for (int neighbor : adjList.get(polledNode)) {
                    inDeg[neighbor]--;
                    if (inDeg[neighbor] == 1) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        res.add(queue.poll());
        if (!queue.isEmpty()) {
            res.add(queue.poll());
        }

        return res;
    }

    private List<List<Integer>> createUndirectedGraph(int n, int[][] edges, int[] inDeg) {
        List<List<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            adjList.get(u).add(v);
            adjList.get(v).add(u);

            inDeg[u]++;
            inDeg[v]++;
        }

        return adjList;
    }

}
