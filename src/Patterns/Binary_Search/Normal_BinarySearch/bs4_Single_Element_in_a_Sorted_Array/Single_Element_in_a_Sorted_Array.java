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


Approach1: Iterate and find Freq and store in Hashmap TC: O(n+n)
Approach2: Iterate with two pointers i and j TC: O(n)
Approach3: Iterate and XOR (bit manipulation) TC: O(n)
Approach4: Binary Search
 */
public class Single_Element_in_a_Sorted_Array extends ProblemSolver {
    public static void main(String[] args) {
        new Single_Element_in_a_Sorted_Array().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] arr = DataConvertor.toIntArray(args[0]);

        int res2 = singleNonDuplicate2(arr);
        System.out.println(res2);
        int res3 = singleNonDuplicate3(arr);
        System.out.println(res3);
        BinarySearchResult singleNonDuplicateRes = singleNonDuplicate(arr);
        System.out.println("BS: single Non Duplicate element: " + singleNonDuplicateRes.res + " at index: " + singleNonDuplicateRes.index);
    }

    public int singleNonDuplicate2(int[] nums) {
        int i=0;
        int cnt = 0;

        for(int j=0; j<nums.length; j++){
            if(nums[i] == nums[j]){
                cnt++;
            }
            else{
                return nums[i];
            }

            if(cnt == 2){
                i=j+1;
                cnt=0;
            }
        }

        return nums[i];
    }

    public int singleNonDuplicate3(int[] nums) {
        int ans=0;
        for(int n: nums) {
            ans = ans^n;
        }
        return ans;
    }

    // Binary Search
    public static BinarySearchResult singleNonDuplicate(int[] arr) {
        if (arr.length == 1){
            return new BinarySearchResult(arr[0], 0);
        }
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            // if we are at first or last element then that is the answer
            if (mid - 1 < 0 || mid + 1 >= arr.length) {
                return new BinarySearchResult(arr[mid], mid); 
            }
            else if (arr[mid - 1] != arr[mid] && arr[mid] != arr[mid + 1]) { // element found
                return new BinarySearchResult(arr[mid], mid);
            } 
            // mid equal is in left => odd numbers in left => elem is in left
            else if ((mid % 2 == 0 && arr[mid] == arr[mid + 1]) || (mid % 2 != 0 && arr[mid] == arr[mid - 1])) { 
                left = mid + 1;
            } 
            else {
                right = mid - 1;
            }
        }
        return new BinarySearchResult(-1, -1);
    }

}
