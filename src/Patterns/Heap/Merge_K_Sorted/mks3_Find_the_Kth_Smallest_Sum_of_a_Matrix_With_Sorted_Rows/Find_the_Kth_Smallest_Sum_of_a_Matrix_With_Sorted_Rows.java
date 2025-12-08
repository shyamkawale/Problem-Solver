package Patterns.Heap.Merge_K_Sorted.mks3_Find_the_Kth_Smallest_Sum_of_a_Matrix_With_Sorted_Rows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/

You are given an m x n matrix mat that has its rows sorted in non-decreasing order and an integer k.
You are allowed to choose exactly one element from each row to form an array.
Return the kth smallest array sum among all possible arrays.

Example 1:
Input: mat = [[1,3,11],[2,4,6]], k = 5
Output: 7
Explanation: Choosing one element from each row, the first k smallest sum are:
[1,2], [1,4], [3,2], [3,4], [1,6]. Where the 5th sum is 7.

Example 2:
Input: mat = [[1,3,11],[2,4,6]], k = 9
Output: 17

Example 3:
Input: mat = [[1,10,10],[1,4,5],[2,3,6]], k = 7
Output: 9
Explanation: Choosing one element from each row, the first k smallest sum are:
[1,1,2], [1,1,3], [1,4,2], [1,4,3], [1,1,6], [1,5,2], [1,5,3]. Where the 7th sum is 9.  
*/
public class Find_the_Kth_Smallest_Sum_of_a_Matrix_With_Sorted_Rows extends ProblemSolver {

    public static void main(String[] args) {
        new Find_the_Kth_Smallest_Sum_of_a_Matrix_With_Sorted_Rows().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] mat = DataConvertor.to2DIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int res = kthSmallest(mat, k);
        System.out.println(res);
    }

    public int kthSmallest(int[][] mat, int k) {
        int row = mat.length;
        int col = mat[0].length;

        Queue<State> minHeap = new PriorityQueue<>((a, b) -> a.sum-b.sum);

        int sum = 0;
        for(int r=0; r<row; r++) {
            sum = sum + mat[r][0];
        }
        State state = new State(sum, new int[row]);
        minHeap.offer(state);

        Set<String> vis = new HashSet<>();
        vis.add(state.key());

        while(k>0 && !minHeap.isEmpty()) {
            State currElem = minHeap.poll();
            k--;
            if(k == 0) {
                return currElem.sum;
            }

            for(int r=0; r<row; r++) {
                int cc = currElem.idx[r];
                if(cc+1 < col) {
                    int[] nextElem = currElem.idx.clone();
                    int newSum = currElem.sum - mat[r][cc] + mat[r][cc+1];
                    nextElem[r]++;
                    State newState = new State(newSum, nextElem);

                    if(!vis.contains(newState.key())) {
                        minHeap.offer(newState);
                        vis.add(newState.key());
                    }
                }
            }

            // System.out.println(Arrays.toString(currElem.idx));
        }

        return -1;
    }

    private static class State {
        int sum;
        int[] idx;

        public State(int s, int[] i) {
            sum = s;
            idx = i;
        }

        String key() {
            return Arrays.toString(idx);
        }
    }

}
