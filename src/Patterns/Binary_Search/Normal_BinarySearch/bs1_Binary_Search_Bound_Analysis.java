package Patterns.Binary_Search.Normal_BinarySearch;

import Helpers.Models.BinarySearchResult;

// total 7 cases(In notebook)
public class bs1_Binary_Search_Bound_Analysis {
    public static void main(String[] args) {
        int[] nums = { 2, 3, 5, 5, 6, 6, 7, 7, 9, 9 };
        //      index: 0  1  2  3  4  5  6  7  8  9
        int k = 6;

        // Binary Search(find any == k)
        BinarySearchResult equalTo = findElement(nums, k);
        System.out.println("Element " + k + " found at " + equalTo.index);

        // smallest(first) element that satisfies nums[idx] >= k (first greater than equal(>=) to k)
        BinarySearchResult lowerBound = findLowerBound(nums, k);
        System.out.println("Lower Bound: " + lowerBound.res + " at index: " + lowerBound.index);

        // smallest(first) element that satisfies nums[idx] > k (first greater(>) than k)
        BinarySearchResult upperBound = findUpperBound(nums, k);
        System.out.println("Upper Bound: " + upperBound.res + " at index: " + upperBound.index);

        // largest(last) element that satisfies nums[idx] <= k (last greater then equal k)
        BinarySearchResult floor = findFloor(nums, k);
        System.out.println("Floor: " + floor.res + " at index: " + floor.index);

        // largest(last) element that satisfies nums[idx] < k
        BinarySearchResult predecessor = findPredecessor(nums, k);
        System.out.println("Predecessor: " + predecessor.res + " at index: " + predecessor.index);

        BinarySearchResult firstSmaller = findFirstSmaller(nums, k);
        System.out.println("FirstSmaller: " + firstSmaller.res + " at index: " + firstSmaller.index);

        BinarySearchResult lastGreater = findLastGreater(nums, k);
        System.out.println("LastGreater: " + lastGreater.res + " at index: " + lastGreater.index);
    }

    private static BinarySearchResult findElement(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;

        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            if(nums[mid] == k){
                return new BinarySearchResult(k, mid);
            }
            else if(nums[mid] < k){
                left = mid + 1;
            }
            else if(nums[mid] > k){
                right = mid - 1;
            }
        }

        return new BinarySearchResult(k, -1);
    }

    private static BinarySearchResult findLowerBound(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        int res = -1;
        int index = nums.length;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (nums[mid] < k) { // lbool(F)
                left = mid + 1;
            } else { // nums[mid] >= k //rbool(T)
                res = nums[mid];
                index = mid;
                right = mid - 1;
            }
        }
        return new BinarySearchResult(res, index);
    }

    private static BinarySearchResult findUpperBound(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        int res = -1;
        int index = nums.length;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (nums[mid] <= k) { // lbool(F)
                left = mid + 1;
            } else { // nums[mid] > k //rbool(T)
                res = nums[mid];
                index = mid;
                right = mid - 1;
            }
        }
        return new BinarySearchResult(res, index);
    }

    private static BinarySearchResult findFloor(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        int res = -1;
        int index = 0;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (nums[mid] <= k) { // lbool(T)
                res = nums[mid];
                index = mid;
                left = mid + 1;
            } else { // nums[mid] > k //rbool(F)
                right = mid - 1;
            }
        }
        return new BinarySearchResult(res, index);
    }

    private static BinarySearchResult findPredecessor(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        int res = -1;
        int index = 0;

        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if (nums[mid] < k) { // lbool(T)
                res = nums[mid];
                index = mid;
                left = mid + 1;
            } else { // nums[mid] >= k //rbool(F)
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
