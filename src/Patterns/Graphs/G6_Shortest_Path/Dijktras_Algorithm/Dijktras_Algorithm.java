package Patterns.Graphs.G6_Shortest_Path.Dijktras_Algorithm;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;

public class Dijktras_Algorithm extends ProblemSolver {

    public static void main(String[] args) {
        new Dijktras_Algorithm().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int srcNode = DataConvertor.toInt(args[1]);
        int[][] edges = DataConvertor.to2DIntArray(args[2]);
        List<List<Pair<Integer, Integer>>> adjList = GraphsWrapper.createWeightedDirectedGraph(n, edges);

        int[] res1 = dijkstrasAlgorithm_Queue(n, srcNode, adjList);
        int[] res2 = dijkstrasAlgorithm_PriorityQueue(n, srcNode, adjList);
        int[] res3 = dijkstrasAlgorithm_Set(n, srcNode, adjList);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2) + " " + Arrays.toString(res3));
    }

    // Approach 1: Using BFS + Queue
    // TC: O(V) + O(E+E)
    // SC: O(V+2V) -> dist, queue with int[] with 2 length
    private int[] dijkstrasAlgorithm_Queue(int n, int srcNode, List<List<Pair<Integer, Integer>>> adjList) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { srcNode, 0 });

        while (!queue.isEmpty()) {
            int[] polledNode = queue.poll();

            for (Pair<Integer, Integer> neighbor : adjList.get(polledNode[0])) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;
                if (dist[neighborNode] > dist[polledNode[0]] + weight) {
                    dist[neighborNode] = dist[polledNode[0]] + weight;
                    queue.offer(new int[] { neighborNode, dist[neighborNode] });
                }
            }
        }

        return dist;
    }

    // Approach 2: Using BFS + PriorityQueue
    // TC: O(V) + O(VlogV + E)
    // Always try to go to the minimal first path.. so reaches any node with minimal
    // path..
    private int[] dijkstrasAlgorithm_PriorityQueue(int n, int srcNode, List<List<Pair<Integer, Integer>>> adjList) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        queue.offer(new int[] { srcNode, 0 });

        while (!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int currNode = polledNode[0];
            int currWeight = polledNode[1];

            if (currWeight != dist[currNode]) {
                continue;
            }

            for (Pair<Integer, Integer> neighbor : adjList.get(currNode)) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;
                if (dist[neighborNode] > currWeight + weight) {
                    dist[neighborNode] = currWeight + weight;
                    queue.offer(new int[] { neighborNode, dist[neighborNode] });
                }
            }
        }

        return dist;
    }

    // Approach 3: Using BFS + TreeMap
    private int[] dijkstrasAlgorithm_Set(int n, int srcNode, List<List<Pair<Integer, Integer>>> adjList) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[srcNode] = 0;

        TreeSet<int[]> set = new TreeSet<>((a, b) -> {
            if (a[1] != b[1]) {
                return a[1] - b[1];
            }

            return a[0] - b[0];
        });
        set.add(new int[] { srcNode, 0 });

        while (!set.isEmpty()) {
            int[] polledNode = set.pollFirst();

            for (Pair<Integer, Integer> neighbor : adjList.get(polledNode[0])) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;
                if (dist[neighborNode] > dist[polledNode[0]] + weight) {
                    // this is working becoz of Treeset removes using Comparator and not equals()
                    // method
                    // TreeSet and TreeMap are sorted collections that rely on a `Comparator` (or
                    // natural ordering if no comparator is provided) for both ordering and
                    // equality.
                    set.remove(new int[] { neighborNode, dist[neighborNode] });
                    dist[neighborNode] = dist[polledNode[0]] + weight;
                    set.add(new int[] { neighborNode, dist[neighborNode] });
                }
            }
        }

        return dist;
    }
}
