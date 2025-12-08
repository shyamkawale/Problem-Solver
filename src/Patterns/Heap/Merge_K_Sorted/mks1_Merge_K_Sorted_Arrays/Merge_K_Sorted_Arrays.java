package Patterns.Heap.Merge_K_Sorted.mks1_Merge_K_Sorted_Arrays; 
 
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 

/*
https://www.geeksforgeeks.org/problems/merge-k-sorted-arrays/1?
Given k sorted arrays arranged in the form of a matrix of size k * k. 
The task is to merge them into one sorted array. Return the merged sorted array.

Input: k = 3, arr[][] = {{1,2,3},{4,5,6},{7,8,9}}
Output: 1 2 3 4 5 6 7 8 9
Input: k = 4, arr[][]={{1,2,3,4},{2,2,3,4},{5,5,6,6},{7,8,9,9}}
Output: 1 2 2 2 3 3 4 4 5 5 6 6 7 8 9 9 

sol1: BRUTEFORCE HEAP: Add elements in MinHeap and then poll
sol2: OPTIMIZED HEAP: Add only 1st elements of each rows and then poll() with adding next element of that polled element row.
 */
public class Merge_K_Sorted_Arrays extends ProblemSolver { 
    public static void main(String[] args) { 
        new Merge_K_Sorted_Arrays().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[][] arr = DataConvertor.to2DIntArray(args[0]); 
 
        List<Integer> res1 = mergeKArrays_UsingBruteforceHeap(arr.clone()); 
        System.out.println(res1);
        List<Integer> res2 = mergeKArrays_UsingOptimizedHeap(arr.clone());
        System.out.println(res2); 
    } 

    // TC: O(r*c*log(r*c) + r*c*log(r*c))
    // SC: O(r*c + r*c)
    public List<Integer> mergeKArrays_UsingBruteforceHeap(int[][] arr) 
    {
        int row = arr.length;
        int col = arr[0].length;
        Queue<Integer> minHeap = new PriorityQueue<Integer>();
        for(int r=0; r<row; r++){
            for(int c=0; c<col; c++){
                minHeap.offer(arr[r][c]);
            }
        }

        // ArrayList<Integer> list = new ArrayList<Integer>(minHeap);
        // Collections.sort(list);
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        while(!minHeap.isEmpty()){
            list.add(minHeap.poll());
        }
        
        return list;
    }

    // TC: O(rlogr + r*c*logr)
    // SC: O(3*r + r*c)
    public List<Integer> mergeKArrays_UsingOptimizedHeap(int[][] arr) 
    {
        int row = arr.length;
        int col = arr[0].length;
        Queue<Node> minHeap = new PriorityQueue<Node>((a,b) -> Integer.compare(a.key, b.key));
        for(int r=0; r<row; r++){
            minHeap.offer(new Node(arr[r][0], r, 0));
        }

        List<Integer> list = new ArrayList<Integer>();

        while(!minHeap.isEmpty()){
            Node minElement = minHeap.poll();
            list.add(minElement.key);
            int nextCol = minElement.c + 1;
            if(nextCol < col){
                minHeap.offer(new Node(arr[minElement.r][nextCol], minElement.r, nextCol));
            }
        }
        
        return list;
    }

    public class Node{
        int key;
        int r;
        int c;

        public Node(int key, int row, int col) {
            this.key = key;
            this.r = row;
            this.c = col;
        }
    }
} 
