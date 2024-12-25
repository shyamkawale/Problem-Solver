package Patterns.Binary_Search.BinarySearch_On_Matrix.bsm2_Search_a_2D_Matrix_II; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/search-a-2d-matrix-ii/
Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix. 
This matrix has the following properties:
1) Integers in each row are sorted in ascending from left to right.
2) Integers in each column are sorted in ascending from top to bottom.
 
Input: matrix = [[1, 4, 7, 11,15],
                 [2, 5, 8, 12,19],
                 [3, 6, 9, 16,22],
                 [10,13,14,17,24],
                 [18,21,23,26,30]], target = 5
Output: true

Input: matrix = [[1, 4, 7, 11,15],
                 [2, 5, 8, 12,19],
                 [3, 6, 9, 16,22],
                 [10,13,14,17,24],
                 [18,21,23,26,30]], target = 20
Output: false

Concept: BinarySearch on Matrix
Sol1: BinarySearch On each ROW* => row*log(col)
Sol2: BinarySearch variation On ROWwise and COLwise sorted MATRIX** => log(row + col); 🚀
 */
public class Search_a_2D_Matrix_II extends ProblemSolver { 
    public static void main(String[] args) { 
        new Search_a_2D_Matrix_II().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[][] matrix = DataConvertor.to2DIntArray(args[0]); 
        int target = DataConvertor.toInt(args[1]);
        

        boolean res1 = searchMatrix_withBinarySearchOnEveryROW(matrix, target);
        boolean res2 = searchMatrix_BinarySearchVariationOnMATRIX(matrix, target);
        System.out.println(res1 + " " + res2);
    } 


    /*###### 👍SOL1: BinarySearch On Every ROWS 👍############################################################################### */
    // TC: O(row*log(col))
    // SC: O(1)
    public boolean searchMatrix_withBinarySearchOnEveryROW(int[][] matrix, int target) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;

        for(int i=0; i<rowCnt; i++){
            int startElem = matrix[i][0];
            int endElem = matrix[i][colCnt-1];
            if(startElem <= target && target <= endElem){
                if(binarySearch(matrix[i], target)){
                    return true;
                }
            }
        }

        return false;
    }
    private boolean binarySearch(int[] nums, int target){
        int left = 0;
        int right = nums.length-1;

        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            if(nums[mid] == target){
                return true;
            }
            else if(nums[mid] > target){
                right = mid-1;
            }
            else if(nums[mid] < target){
                left = mid+1;
            }
        }
        return false;
    }
 
    /*###### 💡SOL2: BinarySearch-Variation(without mid) on MATRIX 💡###################################################### */
    // TC: O(row + col) 🚀
    // SC: O(1)
    public boolean searchMatrix_BinarySearchVariationOnMATRIX(int[][] matrix, int target) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;

        int r = 0;
        int c = colCnt - 1; // initially we are at => top right element

        while(validIdx(r, rowCnt) && validIdx(c, colCnt)){
            int currElem = matrix[r][c];

            if(currElem == target){
                return true;
            }
            else if(currElem > target){ // eliminate column(as below all are bigg element than currElem)
                c = c-1;
            }
            else if(currElem < target){ // eliminate row(as on left all are small element than currELem)
                r = r+1;
            }
        }

        return false;
    }

    private boolean validIdx(int idx, int maxIdx){
        return 0 <= idx && idx < maxIdx;
    }
 
} 
