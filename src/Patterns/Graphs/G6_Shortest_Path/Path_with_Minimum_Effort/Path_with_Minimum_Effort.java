package Patterns.Graphs.G6_Shortest_Path.Path_with_Minimum_Effort;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/path-with-minimum-effort/

You are a hiker preparing for an upcoming hike. 
You are given heights, a 2D array of size rows x columns, where heights[row][col] represents the height of cell (row, col). 
You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). 
You can move up, down, left, or right, and you wish to find a route that requires the minimum effort.

A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.

Return the minimum effort required to travel from the top-left cell to the bottom-right cell.

Example 1:
Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
Output: 2
Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.

Example 2:
Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
Output: 1
Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, 
which is better than route [1,3,5,3,5].

Example 3:
Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
Output: 0
Explanation: This route does not require any effort.
*/
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

        boolean[][] vis = new boolean[row][col];

        while(!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int polledNodeR = polledNode[0];
            int polledNodeC = polledNode[1];
            int polledNodeEffort = polledNode[2];

            if(vis[polledNodeR][polledNodeC]) continue;
            vis[polledNodeR][polledNodeC] = true;

            if(polledNodeR == row-1 && polledNodeC == col-1) {
                return polledNodeEffort;
            }

            for(int i=0; i<dir.length; i++) {
                int nr = polledNodeR + dir[i][0];
                int nc = polledNodeC + dir[i][1];

                if(isValidIdx(nr, row) && isValidIdx(nc, col) && !vis[nr][nc]) {
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
