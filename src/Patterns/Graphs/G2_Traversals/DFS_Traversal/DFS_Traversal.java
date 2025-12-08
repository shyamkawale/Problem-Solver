package Patterns.Graphs.G2_Traversals.DFS_Traversal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;

public class DFS_Traversal extends ProblemSolver {

    public static void main(String[] args) {
        new DFS_Traversal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]); // number of vertices
        int[][] edges = DataConvertor.to2DIntArray(args[1]);

        List<List<Integer>> adjList = GraphsWrapper.createUndirectedGraph(n, edges);
        List<Integer> res = new ArrayList<>(n);
        Set<Integer> visited = new HashSet<>();
        dfs(adjList, 0, visited, res);

        System.out.println(res);
    }

    private void dfs(List<List<Integer>> adjList, int currNode, Set<Integer> visited, List<Integer> res) {
        visited.add(currNode);
        res.add(currNode);

        for(int node: adjList.get(currNode)){
            if(!visited.contains(node)) {
                dfs(adjList, node, visited, res);
            }
        }
    }
}
