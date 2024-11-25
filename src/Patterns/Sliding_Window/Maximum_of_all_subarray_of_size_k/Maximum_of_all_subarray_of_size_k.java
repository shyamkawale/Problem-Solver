package Patterns.Sliding_Window.Maximum_of_all_subarray_of_size_k;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.PriorityQueue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Maximum_of_all_subarray_of_size_k extends ProblemSolver {

    @Override
    public void processParameters(String[] args) {
        int[] arr = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int[] res1 = slidingMaximum_UsingPriorityQueue(arr, k);
        System.out.println(Arrays.toString(res1));

        int[] res2 = slidingMaximum_UsingDeque(arr, k);
        System.out.println(Arrays.toString(res2));
    }

    public static void main(String[] args) {
        new Maximum_of_all_subarray_of_size_k().readInput();
    }

    public int[] slidingMaximum_UsingPriorityQueue(final int[] arr, int k) {

        int n = arr.length;
        int[] res = new int[n - k + 1];

        int start = 0;
        int end = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        while (end < n) {
            pq.add(arr[end]);
            if (end - start + 1 == k) {
                res[start] = pq.peek();
                pq.remove(arr[start]);
                start++;
            }
            end++;
        }
        return res;
    }

    public int[] slidingMaximum_UsingDeque(int[] arr, int k) {
        int n = arr.length;
        int[] res = new int[n - k + 1];

        int start = 0;
        int end = 0;
        Deque<Integer> deque = new ArrayDeque<Integer>(); // queue with both side open

        while (end < n) {
            //achieving sorted deque(removing smaller one from last => so big stays first)
            while (!deque.isEmpty() && deque.peekLast() < arr[end]) {
                deque.pollLast();
            }
            deque.offerLast(arr[end]);

            if (end - start + 1 == k) {
                res[start] = deque.peekFirst();
                if (!deque.isEmpty() && deque.peekFirst() == arr[start]) {
                    deque.pollFirst();
                }
                start++;
            }
            end++;
        }
        return res;
    }

}
