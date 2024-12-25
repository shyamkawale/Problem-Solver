package Patterns.Binary_Search.BinarySearch_On_Matrix.bsm3_Row_With_Max_1s; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
/*
https://www.geeksforgeeks.org/problems/row-with-max-1s0023/1?
You are given a 2D binary array arr[][] consisting of only 1s and 0s. 
Each row of the array is sorted in non-decreasing order. 
Your task is to find and return the index of the first row that contains the maximum number of 1s. If no such row exists, return -1.
Input: arr[][] = [[0,1,1,1], 
                  [0,0,1,1], 
                  [1,1,1,1], 
                  [0,0,0,0]]
Output: 2
Explanation: Row 2 contains the most number of 1s (4 1s). Hence, the output is 2.

Concept: BinarySearch on each row
Sol1: Bruteforce(traverse through each row and find first 1) => O(r*c)
Sol2: BinarySearch on Each Row( to find first 1 index) => O(r*logc)
 */
@SuppressWarnings("unused")
public class Row_With_Max_1s extends ProblemSolver { 
    public static void main(String[] args) { 
        new Row_With_Max_1s().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[][] matrix = DataConvertor.to2DIntArray(args[0]); 
 
        int res1 = rowWithMax1s(matrix); 
        int res2 = rowWithMax1s_BinarySearch(matrix); 
        System.out.println(res1 + " " + res2); 
    } 
 
    /*############# Bruteforce ############################################################# */
    // TC: O(r*c)
    // SC: O(1)
    public int rowWithMax1s(int matrix[][]) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;

        int maxFreqCol = -1;
        int maxFreq = 0;

        for(int r=0; r<rowCnt; r++){
            for(int c=0; c<colCnt; c++){
                if(matrix[r][c] == 1){
                    int freq1s = colCnt - c;
                    if(maxFreq < freq1s){
                        maxFreq = freq1s;
                        maxFreqCol = r;
                    }
                    break;
                }
            }
            
        }

        return maxFreqCol;
    }

    /*########## BinarySearch on Each ROW( to find first 1 index) ############################*/
    // TC: O(r*logc)
    // SC: O(1)
    public int rowWithMax1s_BinarySearch(int matrix[][]) {
        int rowCnt = matrix.length;
        int colCnt = matrix[0].length;

        int maxFreqCol = -1;
        int maxFreq = 0;

        for(int r=0; r<rowCnt; r++){
            int freq = findFreqUsingBinarySearch(matrix[r]);
            if(maxFreq < freq){
                maxFreq = freq;
                maxFreqCol = r;
            }
        }

        return maxFreqCol;
    }

    private int findFreqUsingBinarySearch(int[] arr){
        int left = 0;
        int right = arr.length-1;

        int first1idx = -1;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            if(arr[mid] == 1){ // in rbool(T)
                first1idx = mid;
                right = mid - 1;
            }
            else if(arr[mid] == 0){ // in lbool(F)
                left = mid + 1;
            }
        }

        return first1idx == -1 ? 0 : arr.length - first1idx;
    }
 
} 
