package Patterns.Heap.Find_Median.Sliding_Window_Median;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Sliding_Window_Median extends ProblemSolver {

    public static void main(String[] args) {
        new Sliding_Window_Median().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        double[] res = medianSlidingWindow(nums, k);
        System.out.println(Arrays.toString(res));
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] res = new double[n-k+1];
        MedianFinderOp medianFinder = new MedianFinderOp();
        // MedianFinder medianFinder = new MedianFinder();
        int start = 0;
        int end = 0;

        while (end < n) {
            medianFinder.add(nums[end]);

            if (end - start + 1 < k) {
                end++;
            } else {
                res[start] = medianFinder.getMedian();

                medianFinder.remove(nums[start]);
                start++;
                end++;
            }
        }

        return res;
    }

    /*
     * MedianFinder Analysis:
     * 
     * THE PROBLEM: Why does the naive approach get TLE?
     * =====================================================
     * - Bottleneck: maxHeap.remove(num) and minHeap.remove(num) are O(k) operations
     *   (k = heap size) because PriorityQueue must iterate through its internal
     *   array to find and remove the element.
     * 
     * - Complexity: For N elements in sliding window of size k:
     *   Total Time = O(N * k) for all removals
     * 
     * - Worst Case: When k = N/2, this approaches O(N^2), which TLEs for N = 100,000
     *
     * THE SOLUTION: Lazy Removal (Delayed Removal Strategy)
     * ======================================================
     * Instead of immediately removing elements, we defer the actual removal:
     * 
     * 1. RECORD DELETIONS (Don't physically remove):
     *    - When a number slides out of the window, store it in a HashMap
     *    - Map key = number to remove, value = count of deletions needed
     *    - This is O(1) per operation!
     * 
     * 2. PRUNE ON ACCESS (Clean up when needed):
     *    - When accessing heap.peek() (for median or rebalancing), check if the
     *      top element is in the "delayed removal" map
     *    - If it is, actually poll() it and decrement the count
     *    - Continue until the top element is valid
     * 
     * 3. TRACK BALANCE MANUALLY:
     *    - heap.size() now includes "dead" (to-be-removed) elements
     *    - Maintain separate counters: leftSize, rightSize (valid elements only)
     *    - Use these for rebalancing logic, not heap.size()
     *
     * RESULT: O(n log n) time complexity instead of O(n^2)
     */
    static class MedianFinderOp {
        private PriorityQueue<Integer> maxHeap;  // first-half
        private int leftSize;
        private PriorityQueue<Integer> minHeap;  // second-half
        private int rightSize;

        private Map<Integer, Integer> delayedRemovalMap;
        
        public MedianFinderOp() {
            maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
            leftSize = 0;
            minHeap = new PriorityQueue<>();
            rightSize = 0;
            delayedRemovalMap = new HashMap<>();
        }

        public void add(int num) {
            if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
                maxHeap.offer(num);
                leftSize++;
            } else {
                minHeap.offer(num);
                rightSize++;
            }

            rebalance();
        }

        public double getMedian() {
            prune(maxHeap);
            prune(minHeap);

            if (leftSize == rightSize) {
                return (double)maxHeap.peek()/2 + (double)minHeap.peek()/2;
            } else {
                return (double)maxHeap.peek();
            }
        }

        public void remove(int num) {
            if (!maxHeap.isEmpty() && num <= maxHeap.peek()) {
                leftSize--;
            } else {
                rightSize--;
            }

            delayedRemovalMap.put(num, delayedRemovalMap.getOrDefault(num, 0)+1);
        }

        private void rebalance() {
            while (leftSize - rightSize > 1) {
                rightSize++;
                leftSize--;
                minHeap.offer(maxHeap.poll());
                prune(maxHeap);
            }

            while (rightSize > leftSize) {
                rightSize--;
                leftSize++;
                maxHeap.offer(minHeap.poll());
                prune(minHeap);
            }
        }

        private void prune(PriorityQueue<Integer> heap) {
            while(delayedRemovalMap.containsKey(heap.peek())) {
                delayedRemovalMap.computeIfPresent(heap.peek(), (key, val) -> val == 1 ? null : val-1);
                heap.poll();
            }
        }
    }

    static class MedianFinder {
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;
        
        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a)); // first-half
            minHeap = new PriorityQueue<>(); // second-half
        }

        public void add(int num) {
            if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }

            rebalance();
        }

        public double getMedian() {
            if (maxHeap.size() == minHeap.size()) {
                return (double) (maxHeap.peek() + minHeap.peek()) / 2;
            } else {
                return (double) maxHeap.peek();
            }
        }

        // O(n) - as maxHeap.remove is linear timecomplexity.
        public void remove(int num) {
            if (!maxHeap.remove(num)) {
                minHeap.remove(num);
            }

            rebalance();
        }

        private void rebalance() {
            while (maxHeap.size() - minHeap.size() > 1) {
                minHeap.offer(maxHeap.poll());
            }

            while (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }
    }

}
