package Helpers.DataStructure.Graphs;

import java.util.ArrayList;
import java.util.List;

public class GraphsWrapper {
    public static List<List<Integer>> createUndirectedGraph(int n, int[][] edges) {
        List<List<Integer>> adjList = new ArrayList<>(n);
        for(int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for(int[] edge : edges) {
            int u = edge[0], v = edge[1];
            adjList.get(u).add(v);
            adjList.get(v).add(u); // for undirected graph
        }
        return adjList;
    }

    public static List<List<Integer>> createDirectedGraph(int n, int[][] edges) {
        List<List<Integer>> adjList = new ArrayList<>(n);
        for(int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for(int[] edge : edges) {
            int u = edge[0], v = edge[1];
            adjList.get(u).add(v);
        }
        return adjList;
    }

    public static List<List<Pair<Integer, Integer>>> createWeightedUndirectedGraph(int n, int[][] edges) {
        List<List<Pair<Integer, Integer>>> adjList = new ArrayList<>(n);

        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }

        for(int[] edge: edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            adjList.get(u).add(new Pair<>(v, w));
            adjList.get(v).add(new Pair<>(u, w));
        }

        return adjList;
    }

    public static List<List<Pair<Integer, Integer>>> createWeightedDirectedGraph(int n, int[][] edges) {
        List<List<Pair<Integer, Integer>>> adjList = new ArrayList<>(n);

        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }

        for(int[] edge: edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            adjList.get(u).add(new Pair<>(v, w));
        }

        return adjList;
    }

    public static List<List<Pair<Integer, Integer>>> createWeightedDirected1IndexedGraph(int n, int[][] edges) {
        List<List<Pair<Integer, Integer>>> adjList = new ArrayList<>(n);

        for(int i=0; i<n+1; i++) {
            adjList.add(new ArrayList<>());
        }

        for(int[] edge: edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            adjList.get(u).add(new Pair<>(v, w));
        }

        return adjList;
    }


}
