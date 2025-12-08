package Patterns.Graphs.G6_Shortest_Path.Path_with_Minimum_Effort;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Path_with_Minimum_Effort extends ProblemSolver {

    public static void main(String[] args) {
        new Path_with_Minimum_Effort().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] heights = DataConvertor.to2DIntArray(args[0]);

        int res = minimumEffortPath(heights);
        System.out.println(res);
    }

    public int minimumEffortPath(int[][] heights) {
        int row = heights.length;
        int col = heights[0].length;

        int[][] effort = new int[row][col];
        for(int r=0; r<row; r++) {
            Arrays.fill(effort[r], Integer.MAX_VALUE);
        }
        effort[0][0] = 0;

        int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        queue.offer(new int[]{0, 0, 0});

        while(!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int polledNodeR = polledNode[0];
            int polledNodeC = polledNode[1];
            int polledNodeEffort = polledNode[2];

            if(polledNodeEffort > effort[polledNodeR][polledNodeC]) {
                continue;
            }

            if(polledNodeR == row-1 && polledNodeC == col-1) {
                return polledNodeEffort;
            }

            for(int i=0; i<dir.length; i++) {
                int nr = polledNodeR + dir[i][0];
                int nc = polledNodeC + dir[i][1];

                if(isValidIdx(nr, row) && isValidIdx(nc, col)) {
                    int heightDiff = Math.abs(heights[polledNodeR][polledNodeC] - heights[nr][nc]);
                    int newEffort = Math.max(heightDiff, polledNodeEffort);
                    if(effort[nr][nc] > newEffort) {
                        effort[nr][nc] = newEffort;
                        queue.offer(new int[]{nr, nc, effort[nr][nc]});
                    }
                }
            }
        }

        return 0;
    }

    public boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

}
