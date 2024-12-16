package Patterns.Binary_Search.Normal_BinarySearch.bs2_Rotated_Sorted_Array; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.Models.BinarySearchResult; 
 
public class Rotated_Sorted_Array extends ProblemSolver { 
    public static void main(String[] args) { 
        new Rotated_Sorted_Array().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        //  int[] arr = {4, 4, 5, 5, 5, 5, 6, 7, 8, 8, 1, 1, 2, 3, 3, 4, 4};
        //       index:  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16
        int[] arr = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        BinarySearchResult targetResult = findTarget(arr, k);
        System.out.println("index of target "+k+" is: "+targetResult.index);

        BinarySearchResult firstTargetResult = findFirstTarget(arr, k);
        System.out.println("index of first occurence of target "+k+" is: "+firstTargetResult.index);

        BinarySearchResult lastTargetResult = findLastTarget(arr, k);
        System.out.println("index of last occurence of target "+k+" is: "+lastTargetResult.index);

        BinarySearchResult minElem = findMin(arr);
        System.out.println("smallest elem is: "+ minElem.res + " at index: "+minElem.index);

        // Works only with distinct array
        int res = findKRotation(arr);                    
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

    //Works only with distinct array (OR gives first min element in an array incase of duplicates)
    private static BinarySearchResult findMin(int[] arr){
        int left = 0;
        int right = arr.length-1;
        int minElem = Integer.MAX_VALUE;
        int index = -1;
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
                if(arr[mid] < minElem){ // finding min of arr[mid] , minElem
                    minElem = arr[mid];
                    index = mid;
                }
                right = mid-1;
            }
        }
        return new BinarySearchResult(minElem, index);
    }

    // works only with distinct array
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
