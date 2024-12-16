package Patterns.Binary_Search.Normal_BinarySearch.bs1_Binary_Search_Bound_Analysis; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.Models.BinarySearchResult; 
 
// total 7 cases(In notebook)
public class Binary_Search_Bound_Analysis extends ProblemSolver { 
    public static void main(String[] args) { 
        new Binary_Search_Bound_Analysis().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        // arr   :{2, 3, 5, 5, 6, 6, 7, 7, 9, 9};
        // index : 0  1  2  3  4  5  6  7  8  9
        int[] arr = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        // Binary Search(find any == k)
        BinarySearchResult equalTo = findElement(arr, k);
        System.out.println("Element " + k + " found at " + equalTo.index);

        // smallest(first) element that satisfies arr[idx] >= k (first greater than equal(>=) to k)
        BinarySearchResult lowerBound = findLowerBound(arr, k);
        System.out.println("Lower Bound: " + lowerBound.res + " at index: " + lowerBound.index);

        // smallest(first) element that satisfies arr[idx] > k (first greater(>) than k)
        BinarySearchResult upperBound = findUpperBound(arr, k);
        System.out.println("Upper Bound: " + upperBound.res + " at index: " + upperBound.index);

        // largest(last) element that satisfies arr[idx] <= k (last greater then equal k)
        BinarySearchResult floor = findFloor(arr, k);
        System.out.println("Floor: " + floor.res + " at index: " + floor.index);

        // largest(last) element that satisfies arr[idx] < k
        BinarySearchResult predecessor = findPredecessor(arr, k);
        System.out.println("Predecessor: " + predecessor.res + " at index: " + predecessor.index);

        BinarySearchResult firstSmaller = findFirstSmaller(arr, k);
        System.out.println("FirstSmaller: " + firstSmaller.res + " at index: " + firstSmaller.index);

        BinarySearchResult lastGreater = findLastGreater(arr, k);
        System.out.println("LastGreater: " + lastGreater.res + " at index: " + lastGreater.index);
    } 
 
    private static BinarySearchResult findElement(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;

        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            if(arr[mid] == k){
                return new BinarySearchResult(k, mid);
            }
            else if(arr[mid] < k){
                left = mid + 1;
            }
            else if(arr[mid] > k){
                right = mid - 1;
            }
        }

        return new BinarySearchResult(k, -1);
    }

    private static BinarySearchResult findLowerBound(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        int res = -1;
        int index = arr.length;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (arr[mid] < k) { // lbool(F)
                left = mid + 1;
            } else { // arr[mid] >= k //rbool(T)
                res = arr[mid];
                index = mid;
                right = mid - 1;
            }
        }
        return new BinarySearchResult(res, index);
    }

    private static BinarySearchResult findUpperBound(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        int res = -1;
        int index = arr.length;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (arr[mid] <= k) { // lbool(F)
                left = mid + 1;
            } else { // arr[mid] > k //rbool(T)
                res = arr[mid];
                index = mid;
                right = mid - 1;
            }
        }
        return new BinarySearchResult(res, index);
    }

    private static BinarySearchResult findFloor(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        int res = -1;
        int index = 0;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (arr[mid] <= k) { // lbool(T)
                res = arr[mid];
                index = mid;
                left = mid + 1;
            } else { // arr[mid] > k //rbool(F)
                right = mid - 1;
            }
        }
        return new BinarySearchResult(res, index);
    }

    private static BinarySearchResult findPredecessor(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        int res = -1;
        int index = 0;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (arr[mid] < k) { // lbool(T)
                res = arr[mid];
                index = mid;
                left = mid + 1;
            } else { // arr[mid] >= k //rbool(F)
                right = mid - 1;
            }
        }
        return new BinarySearchResult(res, index);
    }

    private static BinarySearchResult findFirstSmaller(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;
        int index = -1;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (arr[mid] < target) {
                if (arr[mid] > result) {
                    result = arr[mid];
                    index = mid;
                }
                left++;
            } else {
                right = mid - 1;
            }
        }
        return new BinarySearchResult(result, index); // Index of the leftmost smaller element, or -1 if not found
    }

    private static BinarySearchResult findLastGreater(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = Integer.MAX_VALUE; // Initialize to -1 if no smaller element exists
        int index = -1;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (arr[mid] > target) {
                if (arr[mid] <= result) {
                    result = arr[mid];
                    index = mid;
                }
                left++;
            } else {
                left = mid + 1;
            }
        }

        if (index == -1)
            result = -1;
        return new BinarySearchResult(result, index); // Index of the leftmost smaller element, or -1 if not found
    }
 
} 
