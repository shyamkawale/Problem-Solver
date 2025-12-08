package Patterns.Graphs.G7_Minimum_Spanning_Tree.Prims_Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;

public class Prims_Algorithm extends ProblemSolver {

    public static void main(String[] args) {
        new Prims_Algorithm().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        List<List<Pair<Integer, Integer>>> adjList = GraphsWrapper.createWeightedUndirectedGraph(n, edges);

        int mstWeight = primsAlgorithm(n, adjList);
        System.out.println(mstWeight);
    }

    // Greedy similar to Djiktras
    // TC: O(ElogE + ElogE) = O(2ElogE) ~ O(ElogE)
    private int primsAlgorithm(int n, List<List<Pair<Integer, Integer>>> adjList) {
        int mstWeight = 0;

        // {node, weight, parent}
        // we are storing distance from parent (not distance till now from src(as no src here))
        // our goal is to find minimum weight set of edges across all nodes
        // Ex: if Node0-Node1 costs 2 and Node0-Node2-Node1 costs 2, then we will select Node0-Node2-Node1 as with the same cost we are able to connect 3 nodes..
        Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        // visited in MST (not in original graph - we are not traversing, we are creating cheapest acyclic skeleton)
        int[] vis = new int[n];
        List<int[]> mstEdges = new ArrayList<>();

        // pushes node 0 as we know in mst all nodes are present(so randomly puts 0)
        queue.offer(new int[]{0, 0, -1});

        while(!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int currNode = polledNode[0];
            int weight = polledNode[1];
            int parentNode = polledNode[2];

            if(vis[currNode] == 1) {
                continue;
            }
            vis[currNode] = 1;
            mstWeight = mstWeight + weight;
            mstEdges.add(new int[]{currNode, parentNode});

            for(Pair<Integer, Integer> neighbor: adjList.get(currNode)) {
                int neighborNode = neighbor.vertex;
                int neighborWeight = neighbor.weight;

                if(vis[neighborNode] != 1) {
                    queue.offer(new int[]{neighborNode, neighborWeight, currNode});
                }
            }
        }

        // Optional: Check if graph is disconnected
        // for (int v : vis) {
        //     if (v == 0) {
        //         throw new IllegalArgumentException("Graph is not connected!");
        //     }
        // }

        for(int[] edges: mstEdges) {
            System.out.println(Arrays.toString(edges));
        }
        return mstWeight;
    }

}
