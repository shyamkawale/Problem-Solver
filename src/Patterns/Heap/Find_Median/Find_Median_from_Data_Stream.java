package Patterns.Heap.Find_Median;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Find_Median_from_Data_Stream {
    public static class MedianFinder {
        Queue<Integer> maxHeap; // left side
        Queue<Integer> minHeap; // right side

        public MedianFinder() {
            maxHeap = new PriorityQueue<Integer>((a, b) -> Integer.compare(b, a));
            minHeap = new PriorityQueue<Integer>();
        }

        public void addNum(int num) {
            if (maxHeap.size() == 0) {
                maxHeap.offer(num);
            } else if (num <= maxHeap.peek()) {
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }

            while (maxHeap.size() - minHeap.size() > 1) {
                minHeap.offer(maxHeap.poll());
            }

            while (minHeap.size() - maxHeap.size() > 0) {
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() == minHeap.size()) {
                return (double) (maxHeap.peek() + minHeap.peek()) / 2;
            } else {
                return (double) maxHeap.peek();
            }
        }
    }

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
}
