package Patterns.Graphs.G6_Shortest_Path.ShortestPath_in_Binary_Maze;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/shortest-path-in-binary-matrix/

Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix. 
If there is no clear path, return -1.

A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:

All the visited cells of the path are 0.
All the adjacent cells of the path are 8-directionally connected (i.e., they are different and they share an edge or a corner).
The length of a clear path is the number of visited cells of this path.

Example 1:
Input: grid = [[0,1],[1,0]]
Output: 2

Example 2:
Input: grid = [[0,0,0],[1,1,0],[1,1,0]]
Output: 4

Example 3:
Input: grid = [[1,0,0],[1,1,0],[1,1,0]]
Output: -1
*/
public class ShortestPath_in_Binary_Maze extends ProblemSolver {

    public static void main(String[] args) {
        new ShortestPath_in_Binary_Maze().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] grid = DataConvertor.to2DIntArray(args[0]);

        int res1 = shortestPathBinaryMatrix(Arrays.stream(grid)
                .map(int[]::clone)
                .toArray(int[][]::new));
        System.out.println(res1);
    }

    // We can use Queue here as it is UnWeighted graph.. so BFS + Queue will always
    // give shortest path first time..
    public int shortestPathBinaryMatrix(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[] srcNodeIdx = new int[] { 0, 0 };
        int[] destNodeIdx = new int[] { row - 1, col - 1 };

        if (grid[srcNodeIdx[0]][srcNodeIdx[1]] != 0) {
            return -1;
        }

        int[][] dir = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
        
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { srcNodeIdx[0], srcNodeIdx[1] });
        grid[srcNodeIdx[0]][srcNodeIdx[1]] = 1; // to mark them visited

        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                int[] polledNode = queue.poll();
                int currNodeR = polledNode[0];
                int currNodeC = polledNode[1];

                if (currNodeR == destNodeIdx[0] && currNodeC == destNodeIdx[1]) {
                    return level;
                }

                for (int i = 0; i < dir.length; i++) {
                    int nr = currNodeR + dir[i][0];
                    int nc = currNodeC + dir[i][1];

                    if (isValid(nr, row) && isValid(nc, col) && grid[nr][nc] == 0) {
                        queue.offer(new int[] { nr, nc });
                        grid[nr][nc] = 1;
                    }
                }
            }

            level++;
        }

        return -1;

    }

    public boolean isValid(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

}
