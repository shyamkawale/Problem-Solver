package Patterns.Binary_Search.Normal_BinarySearch;

import Helpers.Models.BinarySearchResult;

public class bs2_Rotated_Sorted_Array{

    public static void main(String[] args) {
        /*
         * [4,5,6,7,8,0,1,2] & 5
            [1,0,1,1,1] & 0
            [16, 18, 22, 23, 24, 36, 43, 1, 2] & 2
            [1,1,2,3,3,4,4,8,8] & -1
         */
        // int[] arr1 = {5, 5, 5, 5, 5, 5};
        int[] arr1 = {4, 4, 5, 5, 5, 5, 6, 7, 8, 8, 1, 1, 2, 3, 3, 4, 4};
        //    index:  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16
        int k = 5;

        BinarySearchResult targetResult = findTarget(arr1, k);
        System.out.println("index of target "+k+" is: "+targetResult.index);

        BinarySearchResult firstTargetResult = findFirstTarget(arr1, k);
        System.out.println("index of first occurence of target "+k+" is: "+firstTargetResult.index);

        BinarySearchResult lastTargetResult = findLastTarget(arr1, k);
        System.out.println("index of last occurence of target "+k+" is: "+lastTargetResult.index);

        BinarySearchResult minElem = findMin(arr1);
        System.out.println("smallest elem is: "+ minElem.res + " at index: "+minElem.index);

        int res = findKRotation(arr1);                    
        System.out.println("rotation done on this array: "+res);
    }

    private static BinarySearchResult findTarget(int[] arr, int k) {
        int left = 0;
        int right = arr.length-1;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);
            if(arr[mid] == k) return new BinarySearchResult(k, mid);
            // edge case(when arr contains duplicate - ex: (left)1,1,1,(mid)1,*,2,3,*,(right)1 )
            else if(arr[left] == arr[mid] && arr[mid] == arr[right]){ // cannot decide if left is sorted or right!! => so just eliminate left and right..
                left++;
                right--;
            }
            else if(arr[left] <= arr[mid]){ //left is sorted
                if(arr[left] <= k && k < arr[mid]){ // target exist in sorted left part
                    right = mid - 1;
                }
                else{
                    left = mid + 1;
                }
            }
            else{ //right is sorted
                if(arr[mid] < k && k <= arr[right]){ // target exist in sorted right part
                    left = mid + 1;
                }
                else{
                    right = mid - 1;
                }
            }
        }
        return new BinarySearchResult(k, -1);
    }

    private static BinarySearchResult findFirstTarget(int[] arr, int k){
        int left = 0;
        int right = arr.length-1;
        int index = -1;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);
            if(arr[mid] == k){
                index = mid;
                right = mid - 1;
            }
            else if(arr[left] == arr[mid] && arr[mid] == arr[right]){ // cannot decide if left is sorted or right!! => so just eliminate left and right..
                left++;
                right--;
            }
            else if(arr[left] <= arr[mid]){ // left bool
                if(arr[left] <= k && k < arr[mid]){ // k is in sorted left part
                    right = mid - 1;
                }
                else{
                    left = mid + 1;
                }
            }
            else{ //right bool
                if(arr[mid] < k && k <= arr[right]){
                    left = mid + 1;
                }
                else{
                    right = mid - 1;
                }
            }

        }
        return new BinarySearchResult(k, index);
    }

    private static BinarySearchResult findLastTarget(int[] arr, int k){
        int left = 0;
        int right = arr.length-1;
        int index = -1;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);
            if(arr[mid] == k){
                index = mid;
                left = mid + 1;
            }
            else if(arr[left] == arr[mid] && arr[mid] == arr[right]){ // cannot decide if left is sorted or right!! => so just eliminate left and right..
                left++;
                right--;
            }
            else if(arr[left] <= arr[mid]){ // left bool
                if(arr[left] <= k && k < arr[mid]){ // k is in sorted left part
                    right = mid - 1;
                }
                else{
                    left = mid + 1;
                }
            }
            else{ //right bool
                if(arr[mid] < k && k <= arr[right]){
                    left = mid + 1;
                }
                else{
                    right = mid - 1;
                }
            }

        }
        return new BinarySearchResult(k, index);
    }

    private static BinarySearchResult findMin(int[] arr){
        int left = 0;
        int right = arr.length-1;
        int minElem = arr[0];
        int index = 0;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            if(arr[left] <= arr[mid]){ //left is sorted
                if(arr[left] < minElem){ // finding min of arr[left] , minElem
                    minElem = arr[left];
                    index = left;
                }
                left = mid+1;
            }
            else{ //right is sorted
                if(arr[mid] < minElem){ // finding min of arr[left] , minElem
                    minElem = arr[mid];
                    index = mid;
                }
                right = mid-1;
            }
        }
        return new BinarySearchResult(minElem, index);
    }

    public static int findKRotation(int[] arr) {
        int left = 0;
        int right = arr.length-1;
        int minElem = arr[0];
        int index = 0;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            if(arr[left] <= arr[mid]){ //left is sorted
                if(arr[left] < minElem){ // finding min of arr[left] , minElem
                    minElem = arr[left];
                    index = left;
                }
                left = mid+1;
            }
            else{ //right is sorted
                if(arr[mid] < minElem){ // finding min of arr[left] , minElem
                    minElem = arr[mid];
                    index = mid;
                }
                right = mid-1;
            }
        }
        return index;
    }

}
