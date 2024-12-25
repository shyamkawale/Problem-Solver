package Patterns.Binary_Search.BinarySearch_On_Matrix.bsm1_Search_a_2D_Matrix; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/search-a-2d-matrix/
You are given an m x n integer matrix matrix with the following two properties:
1) Each row is sorted in non-decreasing order.
2) The first integer of each row is greater than the last integer of the previous row.
Given an integer target, return true if target is in matrix or false otherwise.

Input: matrix = [[1, 3, 5, 7 ],
                 [10,11,16,20],
                 [23,30,34,60]], target = 3
Output: true

Input: matrix = [[1, 3, 5, 7],
                 [10,11,16,20],
                 [23,30,34,60]], target = 14
Output: false


Concept: BinarySearch On Matrix
sol1: BruteForce(traverse through matrix => O(row*col))
sol2: BinarySearch On ROW* => row*log(col)
sol3: BinarySearch On MATRIX**(💡Flatten matrix => 1D array hypothetically💡) => log(row*col) 🚀
 */
public class Search_a_2D_Matrix extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Search_a_2D_Matrix().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[][] matrix = DataConvertor.to2DIntArray(args[0]); 
        int target = DataConvertor.toInt(args[1]);
 
        boolean res1 = searchMatrix_BinarySearchOnRow(matrix, target);
        boolean res2 = searchMatrix_BinarySearchOnMatrix_Optimal(matrix, target); 
        System.out.println(res1 + " " + res2); 
    } 

    // sol2: BinarySearch On ROW*
    // TC: O(row*log(col))
    // SC: O(1)
    public boolean searchMatrix_BinarySearchOnRow(int[][] matrix, int target) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;

        for(int i=0; i<rowCnt; i++){
            int startElem = matrix[i][0];
            int endElem = matrix[i][colCnt-1];
            if(startElem <= target && target <= endElem){
                return binarySearch(matrix[i], target);
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
 
    // sol3: BinarySearch On MATRIX**(💡Flatten matrix => 1D array hypothetically💡)
    // TC: O(log(row*col)) 🚀
    // SC: O(1)
    public boolean searchMatrix_BinarySearchOnMatrix_Optimal(int[][] matrix, int target) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;

        int left = 0; // first index of hypothetical array
        int right = rowCnt * colCnt - 1; // last index of hypothetical array

        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            int midRow = mid/colCnt; // formula to convert hypothetical array index to matrix ROW
            int midCol = mid%colCnt; // formula to convert hypothetical array index to matrix COL
            int midElem = matrix[midRow][midCol];

            if(midElem == target){
                return true;
            }
            else if(midElem > target){
                right = mid-1;
            }
            else if(midElem < target){
                left = mid+1;
            }
        }
        return false;
    }
 
} 
