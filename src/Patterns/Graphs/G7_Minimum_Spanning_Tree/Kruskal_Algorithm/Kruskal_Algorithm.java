package Patterns.Graphs.G7_Minimum_Spanning_Tree.Kruskal_Algorithm;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;
import Patterns.Graphs.G8_DisJointSets.DisjointSet;

public class Kruskal_Algorithm extends ProblemSolver {
    public static void main(String[] args) {
        new Kruskal_Algorithm().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] edges = DataConvertor.to2DIntArray(args[1]);
        List<List<Pair<Integer, Integer>>> adjList = GraphsWrapper.createWeightedUndirectedGraph(n, edges);

        int mstWeight = kruskalAlgorithm(n, adjList);
        System.out.println(mstWeight);
    }

    // TC: O()
    private int kruskalAlgorithm(int n, List<List<Pair<Integer, Integer>>> adjList) {
        int mstWeight = 0;
        List<Edge> edges = new ArrayList<>();

        for(int i=0; i<n; i++) {
            for(Pair<Integer, Integer> neighbor: adjList.get(i)) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;
                if (i < neighborNode) { // only one direction (avoids duplicate entry of edge in list of edges)
                    edges.add(new Edge(i, neighborNode, weight));
                }
            }
        }

        edges.sort((a, b) -> a.wt - b.wt);
        DisjointSet ds = new DisjointSet(n);
        
        for(Edge edge: edges) {
            int u = edge.u;
            int v = edge.v;
            int wt = edge.wt;

            if(ds.findUPar(u) != ds.findUPar(v)) {
                mstWeight = mstWeight + wt;
                ds.unionByRank(u, v);
            }
        }

        return mstWeight;
    }

    public class Edge {
        int u;
        int v;
        int wt;

        public Edge(int u, int v, int wt) {
            this.u = u; this.v = v; this.wt = wt;
        }
    }
}
