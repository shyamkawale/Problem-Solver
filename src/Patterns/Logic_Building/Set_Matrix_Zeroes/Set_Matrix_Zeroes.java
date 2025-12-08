package Patterns.Logic_Building.Set_Matrix_Zeroes;

import java.util.HashSet;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

public class Set_Matrix_Zeroes extends ProblemSolver {

    public static void main(String[] args) {
        new Set_Matrix_Zeroes().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] mat = DataConvertor.to2DIntArray(args[0]);

        setMatrixZeroes(mat);
        MatrixWrapper.printMatrix(mat);
    }

    // Space TC: O(m + n)
    private void setMatrixZeroes(int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;
        Set<Integer> zeroRows = new HashSet<>();
        Set<Integer> zeroCols = new HashSet<>();

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(mat[r][c] == 0) {
                    zeroRows.add(r);
                    zeroCols.add(c);
                }
            }
        }

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(zeroRows.contains(r)) {
                    mat[r][c] = 0;
                }
                if(zeroCols.contains(c)) {
                    mat[r][c] = 0;
                }
            }
        }
    }

    // Space TC: O(1)
    @SuppressWarnings("unused")
    private void setMatrixZeroes2(int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;
        boolean firstColZero = false;

        for(int r=0; r<row; r++) {
            if(mat[r][0] == 0) {
                firstColZero = true;
            }
            for(int c=1; c<col; c++) {
                if(mat[r][c] == 0) {
                    mat[r][0] = 0;
                    mat[0][c] = 0;
                }
            }
        }

        for(int r=row-1; r>=0; r--) {
            if(firstColZero) {
                mat[r][0] = 0;
            }
            for(int c=1; c<col; c++) {
                if(mat[r][0] == 0 || mat[0][c] == 0) {
                    mat[r][c] = 0;
                }
            }
        }
    }

}
