package Patterns.Matrix.Sort_the_Matrix_Diagonally;

import java.util.HashMap;
import java.util.PriorityQueue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

/*
https://leetcode.com/problems/sort-the-matrix-diagonally/

A matrix diagonal is a diagonal line of cells starting from some cell in either the topmost row or leftmost column and going in the bottom-right direction until reaching the matrix's end. For example, the matrix diagonal starting from mat[2][0], where mat is a 6 x 3 matrix, includes cells mat[2][0], mat[3][1], and mat[4][2].

Given an m x n matrix mat of integers, sort each matrix diagonal in ascending order and return the resulting matrix.

Example 1:
Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]

Example 2:
Input: mat = [[11,25,66,1,69,7],[23,55,17,45,15,52],[75,31,36,44,58,8],[22,27,33,25,68,4],[84,28,14,11,5,50]]
Output: [[5,17,4,1,52,7],[11,11,25,45,8,69],[14,23,25,44,58,15],[22,27,31,36,50,66],[84,28,75,33,55,68]]
*/
public class Sort_the_Matrix_Diagonally extends ProblemSolver {
    public static void main(String[] args) {
        new Sort_the_Matrix_Diagonally().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] matrix = DataConvertor.to2DIntArray(args[0]);

        int[][] res = diagonalSort(matrix);
        MatrixWrapper.printMatrix(res);
    }

    public int[][] diagonalSort(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        HashMap<Integer, PriorityQueue<Integer>> map = new HashMap<>();

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                map.putIfAbsent(r - c, new PriorityQueue<>());
                map.get(r - c).add(matrix[r][c]);
            }
        }
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                matrix[r][c] = map.get(r - c).poll();
            }
        }

        return matrix;
    }

}
