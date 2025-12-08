package Patterns.Graphs.G6_Shortest_Path.Network_Delay_Time;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;

public class Network_Delay_Time extends ProblemSolver {

    public static void main(String[] args) {
        new Network_Delay_Time().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] times = DataConvertor.to2DIntArray(args[0]);
        int n = DataConvertor.toInt(args[1]);
        int srcNode = DataConvertor.toInt(args[2]);

        int res = networkDelayTime(times, n, srcNode);
        System.out.println(res);
    }

    public int networkDelayTime(int[][] times, int n, int srcNode) {
        List<List<Pair<Integer, Integer>>> adjList = GraphsWrapper.createWeightedDirected1IndexedGraph(n, times);
        
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = -1; // becoz vertices are 1-based and -1 dist is not possible..
        dist[srcNode] = 0;

        Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        queue.offer(new int[]{srcNode, 0});

        while(!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int currNode = polledNode[0];
            int currWt = polledNode[1];

            if(currWt > dist[currNode]) {
                continue;
            }

            for(Pair<Integer, Integer> neighbor: adjList.get(currNode)) {
                int neighborNode = neighbor.vertex;
                int weight = neighbor.weight;

                if(dist[neighborNode] > currWt + weight) {
                    dist[neighborNode] = currWt + weight;
                    queue.offer(new int[]{neighborNode, dist[neighborNode]});
                }
            }
        }

        int minTime = Integer.MIN_VALUE;
        for(int i=1; i<n+1; i++) {
            if(dist[i] == Integer.MAX_VALUE) { // If any of the node is not reached.
                return -1;
            }
            minTime = Math.max(minTime, dist[i]);
        }

        return minTime;
    }

}
