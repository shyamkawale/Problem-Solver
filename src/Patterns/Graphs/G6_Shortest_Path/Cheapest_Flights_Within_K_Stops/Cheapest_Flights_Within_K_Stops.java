package Patterns.Graphs.G6_Shortest_Path.Cheapest_Flights_Within_K_Stops;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Helpers.DataStructure.Graphs.Pair;

public class Cheapest_Flights_Within_K_Stops extends ProblemSolver {

    public static void main(String[] args) {
        new Cheapest_Flights_Within_K_Stops().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] flights = DataConvertor.to2DIntArray(args[1]);
        int srcNode = DataConvertor.toInt(args[2]);
        int destNode = DataConvertor.toInt(args[3]);
        int k = DataConvertor.toInt(args[4]);

        int res = findCheapestPrice(n, flights, srcNode, destNode, k);
        System.out.println(res);
    }

    public int findCheapestPrice(int n, int[][] flights, int srcNode, int destNode, int k) {
        List<List<Pair<Integer, Integer>>> adjList = GraphsWrapper.createWeightedDirectedGraph(n, flights);

        int minPrice = Integer.MAX_VALUE;
        int[] price = new int[n];
        Arrays.fill(price, Integer.MAX_VALUE);
        price[srcNode] = 0;

        Queue<int[]> queue = new PriorityQueue<>((a, b) -> {
            if(a[2] != b[2]) {
                return a[2] - b[2]; // sort by stops
            }

            return a[1] - b[1]; // if stops are same then sort by price (we have minPrice variable to store minimum Price.. for stops <= k)
        });
        queue.offer(new int[]{srcNode, 0, -1});

        while(!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int currNode = polledNode[0];
            int currPrice = polledNode[1];
            int currStops = polledNode[2];

            if(currStops > k) {
                continue;
            }

            if(currNode == destNode && currStops <= k) {
                minPrice = Math.min(minPrice, currPrice);
            }

            for(Pair<Integer, Integer> neighbor: adjList.get(currNode)) {
                int neighborNode = neighbor.vertex;
                int neighborWt = neighbor.weight;
                if(price[neighborNode] > currPrice + neighborWt) {
                    price[neighborNode] = currPrice + neighborWt;
                    queue.offer(new int[]{neighborNode, price[neighborNode], currStops+1});
                }
            }
        }

        return minPrice == Integer.MAX_VALUE ? -1 : minPrice;
    }

}
