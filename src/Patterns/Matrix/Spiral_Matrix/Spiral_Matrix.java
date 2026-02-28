package Patterns.Matrix.Spiral_Matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/* 
https://leetcode.com/problems/spiral-matrix/description/
Given an m x n matrix, return all elements of the matrix in spiral order.

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 10
-100 <= matrix[i][j] <= 100
*/
public class Spiral_Matrix extends ProblemSolver{

    @Override
    public void processParameters(String[] args) {
        int[][] matrix = DataConvertor.to2DIntArray(args[0]);
        List<Integer> result = spiralOrder(matrix);
        System.out.println(result);
    }

    public static void main(String[] args) {
        new Spiral_Matrix().readInput();
    }

    // TimeComplexity⏲️: O(row*col)
    // Space Complexity🚀: O(row*col)(for list of result) + O(row*col)(for tracking set) => O(row*col)
    public List<Integer> spiralOrder(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        List<Integer> result = new ArrayList<Integer>();

        Set<Long> vis = new HashSet<Long>(); //to track visited cells (💡 Can be done using 1) boolean visited array or 2) modifying existing array to store out or range integer)
        int[][] dir = new int[][]{{0,1}, {1,0}, {0, -1}, {-1, 0}}; // 💪for directions
        int route = 0; // 0(to right), 1(to bottom), 2(to left), 3(to up) // 💪to track route
        int r = 0;
        int c = 0;

        for(int i=0; i<row*col; i++){ // to run loop row*col times(i.e total elems in matrix)
            result.add(matrix[r][c]);
            vis.add(encode(r, c));
            
            int nr = r + dir[route][0];
            int nc = c + dir[route][1];
            
            if(!isValidIdx(nr, row) || !isValidIdx(nc, col) || vis.contains(encode(nr, nc))){
                route = (route+1)%4; // cyclic turn 0->1->2->3->0->1->2->.......
            }

            r = r + dir[route][0];
            c = c + dir[route][1];
        }

        return result;
    }

    public boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

    private static long encode(int a, int b) {
        return ((long) a << 32) | (b & ((1L << 32) - 1));
    }
}
