package Patterns.Graphs.G5_Matrix_Graph.Number_Of_Enclaves;

import java.util.ArrayDeque;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.

A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the boundary of the grid.

Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of moves.
*/
public class Number_Of_Enclaves extends ProblemSolver {

    public static void main(String[] args) {
        new Number_Of_Enclaves().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] grid = DataConvertor.to2DIntArray(args[0]);

        int res = numEnclaves(grid);
        System.out.println(res);
    }

    // boundary traversal if 1 then mark all its neighboring 1 as visited...
    // then traverse in matrix and count no. of ones that are not visited..
    public int numEnclaves(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        for(int c=0; c<col; c++) {
            // first row..
            if(grid[0][c] == 1) {
                queue.offer(new int[]{0, c});
                grid[0][c] = 0;
            }

            // last row..
            if(grid[row-1][c] == 1) {
                queue.offer(new int[]{row-1, c});
                grid[row-1][c] = 0;
            }
        }

        for(int r=0; r<row; r++) {
            // first col..
            if(grid[r][0] == 1) {
                queue.offer(new int[]{r, 0});
                grid[r][0] = 0;
            }

            // last col..
            if(grid[r][col-1] == 1) {
                queue.offer(new int[]{r, col-1});
                grid[r][col-1] = 0;
            }
        }

        while(!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int r = polledNode[0];
            int c = polledNode[1];

            for(int i=0; i<dir.length; i++) {
                int nr = r + dir[i][0];
                int nc = c + dir[i][1];

                if(validIdx(nr, row) && validIdx(nc, col) && grid[nr][nc] == 1) {
                    grid[nr][nc] = 0;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }


        int count = 0;
        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(grid[r][c] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    public boolean validIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }
}
