package Patterns.Graphs.G2_Traversals.BFS_Traversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;

public class BFS_Traversal extends ProblemSolver {

    public static void main(String[] args) {
        new BFS_Traversal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]); // number of vertices
        int[][] edges = DataConvertor.to2DIntArray(args[1]);

        List<List<Integer>> adjList = GraphsWrapper.createUndirectedGraph(n, edges);
        List<Integer> res = bfs(n, adjList);

        System.out.println(res);
    }

    private List<Integer> bfs(int n, List<List<Integer>> adjList) {
        List<Integer> res = new ArrayList<>();
        Set<Integer> vis = new HashSet<>(n);
        Queue<Integer> queue = new ArrayDeque<>();

        vis.add(0);
        queue.offer(0);

        while(!queue.isEmpty()) {
            Integer polledNode = queue.poll();
            res.add(polledNode);

            List<Integer> neighbors = adjList.get(polledNode);
            for(int neighbor: neighbors) {
                if(!vis.contains(neighbor)) {
                    vis.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return res;
    }

}
