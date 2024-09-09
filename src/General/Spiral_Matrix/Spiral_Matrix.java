package General.Spiral_Matrix;

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
        List<Integer> result = new ArrayList<Integer>();
        Set<Integer> set = new HashSet<Integer>(); //to track visited cells (💡 Can be done using 1) boolean visited array or 2) modifying existing array to store out or range integer)
        int row = 0;
        int col = 0;
        int[][] dir = new int[][]{{0,1}, {1,0}, {0, -1}, {-1, 0}}; // 💪for directions
        int route = 0; // 0(to right), 1(to bottom), 2(to left), 3(to up) // 💪to track route

        for(int i=0; i<matrix.length*matrix[0].length; i++){ // to run loop row*col times(i.e total elems in matrix)
            result.add(matrix[row][col]);
            set.add(hashCordinates(row, col));
            
            int nextRow = row + dir[route][0];
            int nextCol = col + dir[route][1];
            boolean rowCheck = nextRow < matrix.length && nextRow >= 0;
            boolean colCheck = nextCol < matrix[0].length && nextCol >=0;
            if(!rowCheck || !colCheck || set.contains(hashCordinates(nextRow, nextCol))){
                route = (route+1)%4; // cyclic turn 0->1->2->3->0->1->2->.......
            }

            row = row + dir[route][0];
            col = col + dir[route][1];
        }

        return result;
    }

    // hashfunction = x*hash+y
    // where hash comes from constraint of cordinates/matrix length(0 < row and col < 10)
    private int hashCordinates(int x, int y){
        int hashMultiplyer = 13; // 10 + 3(prime number - anything).
        return x*hashMultiplyer + y;
    }
}
