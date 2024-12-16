package Patterns.Binary_Search.Normal_BinarySearch.bs4_Single_Element_in_a_Sorted_Array; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.Models.BinarySearchResult; 
 
/*
https://leetcode.com/problems/single-element-in-a-sorted-array/
You are given a sorted array consisting of only integers where every element appears exactly **twice**, except for one element which appears exactly once.
Return the single element that appears only once.

Your solution must run in O(log n) time and O(1) space.
Example 1:
Input: nums = [1,1,2,3,3,4,4,8,8]
Output: 2

Example 2:
Input: nums = [3,3,7,7,10,11,11]
Output: 10
 */
public class Single_Element_in_a_Sorted_Array extends ProblemSolver { 
    public static void main(String[] args) { 
        new Single_Element_in_a_Sorted_Array().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        BinarySearchResult singleNonDuplicateRes = singleNonDuplicate(arr); 
        System.out.println("single Non Duplicate element: "+singleNonDuplicateRes.res + " at index: "+singleNonDuplicateRes.index); 
    } 
 
    public static BinarySearchResult singleNonDuplicate(int[] arr) {
        if(arr.length == 1) return new BinarySearchResult(arr[0], 0);
        int left = 0;
        int right = arr.length-1;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);
            if(mid-1 < 0 || mid+1 >= arr.length) return new BinarySearchResult(arr[mid], mid); //if we are at first or last element then that is the answer
            else if(arr[mid-1] != arr[mid] && arr[mid] != arr[mid+1]){ // element found
                return new BinarySearchResult(arr[mid], mid);
            }
            else if((mid%2 == 0 && arr[mid] == arr[mid+1]) || (mid%2 != 0 && arr[mid] == arr[mid-1])){ // mid equal is in left => odd numbers in left => elem is in left
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }
        return new BinarySearchResult(-1, -1);
    }
 
} 
