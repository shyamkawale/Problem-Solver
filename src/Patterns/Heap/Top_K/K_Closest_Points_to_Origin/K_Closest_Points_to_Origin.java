package Patterns.Heap.Top_K.K_Closest_Points_to_Origin;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

/*
Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, 
return the k closest points to the origin (0, 0).
You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).

Input: points = [[3,3],[5,-1],[-2,4]], k = 2
Output: [[3,3],[-2,4]]
Explanation: The answer [[-2,4],[3,3]] would also be accepted.

sol1: Sorting TC: O(nlogn + k)
sol2: Heap TC: O(nlogk + klogk)
 */
public class K_Closest_Points_to_Origin extends ProblemSolver {
    public static void main(String[] args) {
        new K_Closest_Points_to_Origin().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] points = DataConvertor.to2DIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int[][] res1 = kClosest_UsingSorting(points, k);
        int[][] res2 = kClosest_UsingHeap(points, k);
        MatrixWrapper.printMatrix(res1);
        MatrixWrapper.printMatrix(res2);
    }

    // TC: O(nlogn + k)
    // SC: O(k*2)
    private int[][] kClosest_UsingSorting(int[][] points, int k) {
        Arrays.sort(points, (int[] a, int[] b) -> {
            double distA = Math.sqrt(a[0] * a[0] + a[1] * a[1]);
            double distB = Math.sqrt(b[0] * b[0] + b[1] * b[1]);

            return Double.compare(distA, distB);
        });

        int[][] res = new int[k][2];
        for(int i = 0; i < k; i++) {
            res[i] = points[i];
        }

        return res;
    }

    // TC: O(nlogk + klogk)
    // SC: O(k + k*2)
    private int[][] kClosest_UsingHeap(int[][] points, int k) {
        int[][] res = new int[k][2];
        int row = points.length;
        Queue<Node> maxHeap = new PriorityQueue<Node>((Node a, Node b) -> Double.compare(b.dist, a.dist));

        for (int r = 0; r < row; r++) {
            Node node = new Node(points[r][0], points[r][1]);
            maxHeap.offer(node);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        for (int i = 0; i < k; i++) {
            Node polledNode = maxHeap.poll();
            res[i] = polledNode.idx;
        }

        return res;
    }

    public class Node {
        double dist;
        int[] idx;

        public Node(int r, int c) {
            this.dist = this.findDist(r, c);
            this.idx = new int[2];
            this.idx[0] = r;
            this.idx[1] = c;
        }

        public double findDist(int r, int c) {
            return Math.sqrt(r * r + c * c);
        }
    }

}
