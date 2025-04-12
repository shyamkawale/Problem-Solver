package Patterns.Heap.Merge_K_Sorted.Kth_Smallest_Element_in_a_Sorted_Matrix;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix.
Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 6
Output: 12
Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 6th smallest number is 12
Input: matrix = [[-5]], k = 1
Output: -5

sol1: Sorting TC: O(r*c + r*c*log(r*c))
sol2: Bruteforce Heap TC: O(r*c*logk + logk)
sol3: Optimized Heap TC: O(rlogr + klogk)
sol4: Binary Search TC: O(log(range)*(r+c)) where range = maxElem-minElem of matrix

 */
public class Kth_Smallest_Element_in_a_Sorted_Matrix extends ProblemSolver {
    public static void main(String[] args) {
        new Kth_Smallest_Element_in_a_Sorted_Matrix().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] matrix = DataConvertor.to2DIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int res1 = kthSmallest_UsingSorting(matrix.clone(), k);
        int res2 = kthSmallest_UsingBruteforceHeap(matrix.clone(), k);
        int res3 = kthSmallest_UsingOptimizedHeap(matrix.clone(), k);
        int res4 = kthSmallest_UsingBinarySearch(matrix.clone(), k);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    // TC: O(r*c + r*c*log(r*c))
    // SC: O(r*c)
    public int kthSmallest_UsingSorting(int[][] matrix, int k) {
        int row = matrix.length;
        int col = matrix[0].length;

        int[] sorted = new int[row * col];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                sorted[c % col + col * r] = matrix[r][c]; // Flattening Array
            }
        }

        Arrays.sort(sorted);

        return sorted[k - 1];
    }

    // TC: O(r*c*logk + logk)
    public int kthSmallest_UsingBruteforceHeap(int[][] matrix, int k) {
        int row = matrix.length;
        int col = matrix[0].length;

        Queue<Integer> maxHeap = new PriorityQueue<Integer>((a, b) -> Integer.compare(b, a));

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                maxHeap.offer(matrix[r][c]);

                if (maxHeap.size() > k) {
                    maxHeap.poll();
                }
            }
        }

        return maxHeap.poll();
    }

    // valid solution when matrix is sorted both rowwise and colwise
    // TC: O(rlogr + klogk)
    // SC: O(n) n depends on situation. if k<row then n=k otherwise n=row+k
    public int kthSmallest_UsingOptimizedHeap(int[][] matrix, int k) {
        int row = matrix.length;
        int col = matrix[0].length;

        Queue<Node> minHeap = new PriorityQueue<Node>((a, b) -> Integer.compare(a.elem, b.elem));

        for (int r = 0; r < Math.min(row, k); r++) {
            minHeap.offer(new Node(matrix[r][0], r, 0));
        }

        // running for k times... res to hold and overwrite minElements and at last will hold kth smallest Element.
        int res = -1;
        for (int i = 0; i < k; i++) {
            Node peekNode = minHeap.poll();
            int nextRow = peekNode.r;
            int nextCol = peekNode.c + 1;
            if (nextCol < col) {
                minHeap.offer(new Node(matrix[nextRow][nextCol], nextRow, nextCol));
            }

            res = peekNode.elem;
        }

        return res;
    }

    public class Node {
        int elem;
        int r;
        int c;

        public Node(int elem, int r, int c) {
            this.elem = elem;
            this.r = r;
            this.c = c;
        }
    }

    // valid solution when matrix is sorted both rowwise and colwise
    // TC: O(log(range)*(r+c)) where range = maxElem-minElem of matrix
    // SC: O(1)
    private int kthSmallest_UsingBinarySearch(int[][] matrix, int k) {
        int row = matrix.length;
        int col = matrix[0].length;

        int left = matrix[0][0];
        int right = matrix[row-1][col-1];

        int res = -1;

        while(left <= right) {
            int mid = left + (int)Math.floor((right-left)/2);

            int cntNumsLessThanMid = getCountOfNumberLessThanMid(matrix, mid);

            if(cntNumsLessThanMid < k){ // we need to go right to get bigger mid
                left = mid + 1;
            } 
            else{
                res = mid;
                right = mid - 1;
            }
        }

        return res;
    }

    // TC: O(r+c), iterates over row from last column to 0th so at max (r+c) 
    private int getCountOfNumberLessThanMid(int[][] matrix, int mid) {
        int row = matrix.length;
        int col = matrix[0].length;

        int count = 0;
        int c = col-1;

        for(int r=0; r<row; r++) {
            while(c >= 0 && matrix[r][c] > mid) {
                c--;
            }
            count = count + (c+1);
        }
        return count;
    }

}
