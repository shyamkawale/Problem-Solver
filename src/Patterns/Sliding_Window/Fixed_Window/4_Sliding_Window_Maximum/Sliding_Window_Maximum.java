package Patterns.Sliding_Window.Fixed_Window.Sliding_Window_Maximum;

import java.util.*;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/sliding-window-maximum/
You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. 
You can only see the k numbers in the window. Each time the sliding window moves right by one position.
Return the max sliding window.
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation: 
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7


Approach1: Using Priority Queue(maxheap)
Approach2: Using Deque why??
 */
public class Sliding_Window_Maximum extends ProblemSolver {
    public static void main(String[] args) {
        new Sliding_Window_Maximum().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] arr = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int[] res1 = slidingMaximum_UsingPriorityQueue(arr, k);
        int[] res2 = slidingMaximum_UsingDeque(arr, k);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2));
    }

    //Using PriorityQueue
    public int[] slidingMaximum_UsingPriorityQueue(final int[] arr, int k) {
        int len = arr.length;
        int[] res = new int[len - k + 1];

        int start = 0;
        int end = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder()); //maxheap

        while (end < len) {
            pq.add(arr[end]);

            if(end-start+1 < k){ //window size < k
                end++;
            }
            else if(end-start+1 == k) { // window is set
                res[start] = pq.peek(); //store ans

                pq.remove(arr[start]);
                start++;
                end++;
            }
        }
        return res;
    }

    public int[] slidingMaximum_UsingDeque(int[] arr, int k) {
        int len = arr.length;
        int[] res = new int[len - k + 1];

        int start = 0;
        int end = 0;
        Deque<Integer> deque = new ArrayDeque<Integer>(); // queue with both side open

        while (end < len) {
            //achieving sorted deque(removing smaller one from last => so big stays first)
            while (!deque.isEmpty() && deque.peekLast() < arr[end]) {
                deque.pollLast();
            }
            deque.offerLast(arr[end]);

            if(end-start+1 < k){ //window size < k
                end++;
            }
            else if(end-start+1 == k) { //window is set
                res[start] = deque.peekFirst();
                if (!deque.isEmpty() && deque.peekFirst() == arr[start]) {
                    deque.pollFirst();
                }
                start++;
                end++;
            }
        }
        return res;
    }

}
