package Patterns.Graphs.G6_Shortest_Path.Number_of_Ways_to_Arrive_at_Destination;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;

public class Number_of_Ways_to_Arrive_at_Destination extends ProblemSolver {

    public static void main(String[] args) {
        new Number_of_Ways_to_Arrive_at_Destination().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] roads = DataConvertor.to2DIntArray(args[1]);

        int res = countPaths(n, roads);
        System.out.println(res);
    }

    public int countPaths(int n, int[][] roads) {
        int mod = 1000000007; // Mod value
        List<List<Pair<Integer, Integer>>> adjList = GraphsWrapper.createWeightedUndirectedGraph(n, roads);

        int srcNode = 0;
        int destNode = n-1;

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[srcNode] = 0;

        int[] ways = new int[n];
        ways[srcNode] = 1;

        Queue<long[]> queue = new PriorityQueue<>((a,b) -> Long.compare(a[1], b[1]));
        queue.offer(new long[]{srcNode, 0});

        while(!queue.isEmpty()) {
            long[] polledNode = queue.poll();
            int currNode = (int)polledNode[0];
            long currWt = polledNode[1];

            if(currWt > dist[currNode]) {
                continue;
            }

            for(Pair<Integer, Integer> neighbor: adjList.get(currNode)) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;

                if(dist[neighborNode] > currWt + weight) {
                    ways[neighborNode] = ways[currNode]; // override ways if found better distance
                    dist[neighborNode] = currWt + weight;
                    queue.offer(new long[]{neighborNode, dist[neighborNode]});
                }
                else if(dist[neighborNode] == currWt + weight) {
                    ways[neighborNode] = (ways[neighborNode] + ways[currNode])%mod; // update weight if found same distance from other path
                }
            }
        }

        return ways[n-1] % mod;
    }

}
