package Patterns.Graphs.G5_Matrix_Graph.Rotting_Oranges;

import java.util.ArrayDeque;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/rotting-oranges/

 */
public class Rotting_Oranges extends ProblemSolver {
    public static void main(String[] args) {
        new Rotting_Oranges().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] grid = DataConvertor.to2DIntArray(args[0]);

        int res = orangesRotting(grid);
        System.out.println(res);
    }

    public int orangesRotting(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int res = -1;
        int freshCount = 0;

        Queue<int[]> queue = new ArrayDeque<>();
        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(grid[r][c] == 2) {
                    queue.offer(new int[]{r, c});
                }
                if(grid[r][c] == 1) {
                    freshCount++;
                }
            }
        }

        if(freshCount == 0) {
            return 0;
        }

        res = bfs(grid, queue);

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(grid[r][c] == 1) {
                    return -1;
                }
            }
        }

        return res;
    }

    public int bfs(int[][] grid, Queue<int[]> queue) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int row = grid.length;
        int col = grid[0].length;
        int time = -1;

        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int s=0; s<size; s++) {
                int[] polledNode = queue.poll();
                for(int i=0; i<dir.length; i++) {
                    int nr = polledNode[0] + dir[i][0];
                    int nc = polledNode[1] + dir[i][1];

                    if(isValidIdx(nr, row) && isValidIdx(nc, col) && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            time++;
        }

        return time == -1 ? 0 : time;
    }

    public boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

}
