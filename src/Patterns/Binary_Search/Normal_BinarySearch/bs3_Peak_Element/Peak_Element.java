package Patterns.Binary_Search.Normal_BinarySearch.bs3_Peak_Element;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.Models.BinarySearchResult;

/*
https://leetcode.com/problems/find-peak-element
A peak element is an element that is strictly greater than its neighbors.
Given a 0-indexed (un-sorted) integer array arr, find a peak element, and return its index.
If the array contains multiple peaks, return the index to ***any****** of the peaks.

You may imagine that arr[-1] = arr[n] = -∞. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.
You must write an algorithm that runs in O(log n) time.

Example 1:
Input: arr = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.

Example 2:
Input: arr = [1,2,1,3,5,6,4]
Output: 5
Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
 */
public class Peak_Element extends ProblemSolver {

    public static void main(String[] args) {
        new Peak_Element().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] arr = DataConvertor.toIntArray(args[0]);

        BinarySearchResult peakElemRes = findPeakElement(arr);
        System.out.println("Peak element: "+peakElemRes.res+" at index: "+peakElemRes.index);
    }

    public BinarySearchResult findPeakElement(int[] arr) {
        int left = 0;
        int right = arr.length-1;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);
            boolean isLeftLess = false;
            boolean isRightLess = false;

            if(mid == 0) isLeftLess = true;
            else{
                isLeftLess = arr[mid-1] < arr[mid]; 
            }

            if(mid == arr.length-1) isRightLess = true;
            else{
                isRightLess = arr[mid] > arr[mid+1];
            }

            if(isLeftLess && isRightLess) return new BinarySearchResult(arr[mid], mid); // peak element
            else if(isLeftLess){ // right is greater => GO RIGHT
                left = mid + 1;
            }
            else{ // left is greater or both are greater => GO LEFT
                right = mid - 1;
            }
        }

        return new BinarySearchResult(-1, -1);
    }
}
