package Helpers.DataStructure.Matrix;

public class MatrixWrapper {
    public static void printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            System.out.println("Matrix is empty or null.");
            return;
        }

        // Calculate the maximum width for each column
        int numColumns = matrix[0].length;
        int[] maxWidths = new int[numColumns];
        
        for (int[] row : matrix) {
            for (int col = 0; col < row.length; col++) {
                maxWidths[col] = Math.max(maxWidths[col], String.valueOf(row[col]).length());
            }
        }

        // Print the matrix with proper alignment
        for (int[] row : matrix) {
            System.out.print("[");
            for (int col = 0; col < row.length; col++) {
                System.out.printf("%" + maxWidths[col] + "d", row[col]);
                if (col < row.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}
