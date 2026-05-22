package Patterns.Graphs.G6_Shortest_Path.Number_of_Ways_to_Arrive_at_Destination;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;

/*
https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/

You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some intersections. 

The inputs are generated such that you can reach any intersection from any other intersection and that there is at most one road between any two intersections.

You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] 
means that there is a road between intersections ui and vi that takes timei minutes to travel. 
You want to know in how many ways you can travel from intersection 0 to intersection n - 1 in the shortest amount of time.

Return the number of ways you can arrive at your destination in the shortest amount of time. 
Since the answer may be large, return it modulo 10^9 + 7.

Example 1:
Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
Output: 4
Explanation: The shortest amount of time it takes to go from intersection 0 to intersection 6 is 7 minutes.
The four ways to get there in 7 minutes are:
- 0 ➝ 6
- 0 ➝ 4 ➝ 6
- 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
- 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6

Example 2:
Input: n = 2, roads = [[1,0,10]]
Output: 1
Explanation: There is only one way to go from intersection 0 to intersection 1, and it takes 10 minutes.
*/
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

    // cannot use classical Dijiktras
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
