package Patterns.Binary_Search.BinarySearch_On_Answer.bsa6_Allocate_Books;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://www.naukri.com/code360/problems/allocate-books_1090540

Given an array ‘arr of integer numbers, ‘ar[i]’ represents the number of pages in the ‘i-th’ book. 
There are a ‘m’ number of students, and the task is to allocate all the books to the students.
Allocate books in such a way that:
1) Each student gets at least one book.
2) Each book should be allocated to only one student.
3) Book allocation should be in a contiguous manner.

You have to allocate the book to ‘m’ students such that the maximum number of pages assigned to a student is minimum. 
Find that minimum maximum number of pages and if the allocation of books is not possible. return -1

Concept: Binary Search on Answer
 */
public class Allocate_Books extends ProblemSolver {
    public static void main(String[] args) {
        new Allocate_Books().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] arr = DataConvertor.toIntArray(args[0]);
        int m = DataConvertor.toInt(args[1]);

        int res = findPages(arr, m);
        System.out.println(res);
    }

    // TC: O(n*log(sum-maxElem))
    public int findPages(int[] arr, int m) {
        int min = Integer.MIN_VALUE; // max number of pages 
        int max = 0; // sum of pages in all books
        for (int n : arr) {
            min = Math.max(min, n);
            max = max + n;
        }

        int left = min;
        int right = max;
        int res = -1;

        // for (int i = left; i <= right; i++) {
        //     if(isPossibleToDistributeBooks(arr, i, m)){
        //         return i;
        //     }
        // }

        // len(arr)=5, 4, 3, 2, 1(min students req)
        //          >  <= <  < <
        //          F  T  T  T  T
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            if(isPossibleToDistributeBooks(arr, mid, m)){
                res = mid;
                right = mid - 1;
            }
            else{
                left = mid + 1;
            }
        }
        return res;
    }

    private boolean isPossibleToDistributeBooks(int[] arr, int i, int m) {
        int cnt = 0;
        int pageSum = 0;
        for(int n: arr){
            pageSum = pageSum + n;
            if(pageSum > i){
                cnt++;
                pageSum = n;
            }
        }
        cnt++;

        return cnt <= m;
    }

}
