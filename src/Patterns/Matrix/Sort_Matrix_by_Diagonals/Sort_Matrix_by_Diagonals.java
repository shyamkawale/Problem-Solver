package Patterns.Matrix.Sort_Matrix_by_Diagonals;

import java.util.PriorityQueue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

/*
https://leetcode.com/problems/sort-matrix-by-diagonals/description/

You are given an n x n square matrix of integers grid. Return the matrix such that:

The diagonals in the bottom-left triangle (including the middle diagonal) are sorted in non-increasing order.
The diagonals in the top-right triangle are sorted in non-decreasing order.

Example 1:
Input: grid = [[1,7,3],[9,8,2],[4,5,6]]
Output: [[8,2,3],[9,6,7],[4,5,1]]

Example 2:
Input: grid = [[0,1],[1,2]]
Output: [[2,1],[1,0]]
*/
public class Sort_Matrix_by_Diagonals extends ProblemSolver {

    public static void main(String[] args) {
        new Sort_Matrix_by_Diagonals().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] grid = DataConvertor.to2DIntArray(args[0]);

        int[][] res = sortMatrix(grid);
        MatrixWrapper.printMatrix(res);
    }

    public int[][] sortMatrix(int[][] grid) {
        int n = grid.length;

        // traverse bottom left diagnal and sort
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> Integer.compare(b, a));
        for(int r=n-1; r>=0; r--) {
            int nr = r;
            for(int c=0; c<n-r  & nr < n; c++) {
                // System.out.print(grid[nr][c]);
                maxHeap.offer(grid[nr][c]);
                nr++;
            }

            nr = r;
            for(int c=0; c<n-r  & nr < n; c++) {
                grid[nr][c] = maxHeap.poll();
                nr++;
            }
        }

        // traverse top right diagnal and sort
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int c=n-1; c>0; c--) {
            int nc = c;
            for(int r=0; r<n-c  & nc < n; r++) {
                // System.out.print(grid[r][nc]);
                minHeap.offer(grid[r][nc]);
                nc++;
            }

            nc = c;
            for(int r=0; r<n-c  & nc < n; r++) {
                grid[r][nc] = minHeap.poll();
                nc++;
            }
        }

        return grid;
    }

}
