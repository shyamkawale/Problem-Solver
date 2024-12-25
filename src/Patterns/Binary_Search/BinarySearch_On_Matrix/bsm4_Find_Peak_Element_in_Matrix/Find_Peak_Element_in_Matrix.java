package Patterns.Binary_Search.BinarySearch_On_Matrix.bsm4_Find_Peak_Element_in_Matrix; 
 
import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 

/*
https://leetcode.com/problems/find-a-peak-element-ii/
A peak element in a 2D grid is an element that is strictly greater than all of its adjacent neighbors to the left, right, top, and bottom.
You may assume that the entire matrix is surrounded by an outer perimeter with the value -1 in each cell.

Input: matrix = [[1,4],
              [3,2]]
Output: [0,1]
Explanation: Both 3 and 4 are peak elements so [1,0] and [0,1] are both acceptable answers.

Input: matrix = [[10,20,15],
              [21,30,14],
              [7, 16,32]]
Output: [1,1]
Explanation: Both 30 and 32 are peak elements so [1,1] and [2,2] are both acceptable answers.

Concept: 
Sol1: 💡Bruteforce(traverse through each element find its adjacent and check) => O(r*c*4)
Sol2: 💡Bruteforce better(traverse through each element and find maximum element) => O(r*c)
Sol3: 💡Better Greedy Approach(start from [0,0] and keep on going to largest Neigbour till we find peak element) => better but still worst TC: O(r*c*4)

Now further optimization means we can't traverse each element so TC will be less that O(r*c)

sol4: 💡Binary Search on Column(find potential side where element(s) big than maxElem from that row) => O(r*logc)🚀
*/
public class Find_Peak_Element_in_Matrix extends ProblemSolver { 
    public static void main(String[] args) { 
        new Find_Peak_Element_in_Matrix().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[][] matrix = DataConvertor.to2DIntArray(args[0]); 
 
        int[] res1 = findPeakGrid(matrix); 
        int[] res2 = findPeakGrid_Greedy(matrix); 
        int[] res3 = findPeakGrid_BinarySearch(matrix); 
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2) + " " + Arrays.toString(res3)); 
    }
 
    /*##################### BRUTEFORCE ################### */
    // TC: O(r*c*4)
    public int[] findPeakGrid(int[][] matrix) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        //               up      right   down    left

        for(int r=0; r<rowCnt; r++){
            for(int c=0; c<colCnt; c++){
                int adjMax = -1;
                for(int i=0; i<dir.length; i++){
                    int adjRow = r+dir[i][0];
                    int adjCol = c+dir[i][1];
                    int adjElem = isValidIndex(adjRow, adjCol, rowCnt, colCnt) ? matrix[adjRow][adjCol] : -1;
                    adjMax = Math.max(adjMax, adjElem);
                }
                if(adjMax < matrix[r][c]) return new int[]{r, c};
            }
        }

        return null;
    }

    /*########## GREEDY ############################## */
    // TC: O(r*c*4)
    public int[] findPeakGrid_Greedy(int[][] matrix) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        //               up      right   down    left

        int currRow = 0;
        int currCol = 0;

        while(true){
            int adjMax = -1;
            int adjMaxRow = -1;
            int adjMaxCol = -1;
            for(int i=0; i<dir.length; i++){
                int adjRow = currRow+dir[i][0];
                int adjCol = currCol+dir[i][1];
                int adjElem = isValidIndex(adjRow, adjCol, rowCnt, colCnt) ? matrix[adjRow][adjCol] : -1;
                if(adjElem > adjMax){
                    adjMax = adjElem;
                    adjMaxRow = adjRow;
                    adjMaxCol = adjCol;
                }
            }
            if(adjMax < matrix[currRow][currCol]){
                return new int[]{currRow, currCol};
            }

            currRow = adjMaxRow;
            currCol = adjMaxCol;
        }
    }

    /*############ BINARY SEARCH ################################## */
    // TC: O(r*logc) 💪
    public int[] findPeakGrid_BinarySearch(int[][] matrix) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;
    
        int left = 0, right = colCnt - 1;
    
        while (left <= right) {
            int midCol = left + (int)Math.floor((right-left)/2);
    
            // Find the maximum element in the middle column
            int maxRow = 0;
            for (int r = 0; r < rowCnt; r++) {
                if (matrix[r][midCol] > matrix[maxRow][midCol]) {
                    maxRow = r;
                }
            }
    
            // Check if this is a peak
            int midElem = matrix[maxRow][midCol];
            int leftElem = (midCol > 0) ? matrix[maxRow][midCol - 1] : -1;
            int rightElem = (midCol < colCnt - 1) ? matrix[maxRow][midCol + 1] : -1;
    
            if (midElem > leftElem && midElem > rightElem) {
                return new int[]{maxRow, midCol};
            }
    
            if (leftElem > midElem) { // left has big element => go to left
                right = midCol - 1;
            } else {
                left = midCol + 1;// right has big element => go to right
            }
        }
    
        return null;
    }

    private boolean isValidIndex(int row, int col, int rowCnt, int colCnt){
        return (0 <= row && row < rowCnt) && (0 <= col && col < colCnt);
    }
} 
