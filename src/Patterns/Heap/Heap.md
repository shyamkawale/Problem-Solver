# Heap Data Structure

## Overview
A **Heap** is a complete binary tree data structure where:
- **Min Heap**: Parent node is smaller than or equal to its children (smallest element at root)
- **Max Heap**: Parent node is larger than or equal to its children (largest element at root)

### Key Use Cases
- **Scheduling/Assignment Tasks**: Min heaps are ideal for priority-based scheduling
- **Frequency-Based Problems**: Sort elements by frequency in increasing/decreasing order
- **Follow-up**: Interviews often expect O(n) non-heap solutions using quick-select and Lomuto's partition algorithm

---

## Foundational Problems (Design & Implementation)

1. Design Priority Queue
2. [Does Array Represent MaxHeap](https://www.geeksforgeeks.org/problems/does-array-represent-heap4345/1) - Validate max heap property
3. [Kth Largest Element in a Stream](https://leetcode.com/problems/kth-largest-element-in-a-stream/) - Add elements and find Kth largest dynamically
4. [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/) - Maintain median of streaming data
5. [Design Twitter](https://leetcode.com/problems/design-twitter/) `#TODO`
6. [Task Scheduler](https://leetcode.com/problems/task-scheduler/) `#TODO`

---

## Heap Problem Patterns

### 1. Top K Pattern
Extract K largest/smallest elements or find top K frequent items.

1. [Kth Smallest/Largest Element in Array](https://leetcode.com/problems/kth-largest-element-in-an-array/) - Classic top K problem
2. [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin) - Distance-based top K
3. [Find K Closest Elements](https://leetcode.com/problems/find-k-closest-elements/) - Closest values on number line
4. [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements) - Most frequent items
5. [Top K Frequent Words](https://leetcode.com/problems/top-k-frequent-words/) - Lexicographic tiebreaker
6. [Reward Top K Students](https://leetcode.com/problems/reward-top-k-students/) - Multiple sorting criteria `#EASY`

### 2. Normal Heap Pattern
Standard heap operations for sorting, rearranging, or generating sequences.

1. [Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/) - Sort by frequency
2. [Rank of Elements in Array](https://leetcode.com/problems/rank-transform-of-an-array/) - Transform to ranks
3. [Rearrange String K Distance Apart](https://leetcode.com/problems/rearrange-string-k-distance-apart/) - Constraint-based rearrangement
4. [Reorganize String](https://leetcode.com/problems/reorganize-string/) - No adjacent duplicates
5. [Ugly Number II](https://leetcode.com/problems/ugly-number-ii/) - Generate sequence with heap
6. [Last Stone Weight](https://leetcode.com/problems/last-stone-weight/) - Simulation problem `#EASY`

### 3. Merge K Sorted Pattern
Merge multiple sorted arrays/lists efficiently.

1. [Merge K Sorted Arrays](https://www.geeksforgeeks.org/problems/merge-k-sorted-arrays/1) - Merge multiple arrays
2. [Merge K Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) - Merge linked lists
3. [Find K Pairs with Smallest Sums](https://leetcode.com/problems/find-k-pairs-with-smallest-sums/) - K smallest pair sums
4. [Find Kth Smallest Sum of Matrix (Sorted Rows)](https://leetcode.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/) - 2D matrix traversal
5. [Kth Smallest Element in Sorted Matrix](https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix) - 2D matrix
6. [Maximum Sum Combination](https://www.interviewbit.com/problems/maximum-sum-combinations/) - Optimal pairing
7. [Smallest Range Covering Elements from K Lists](https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/) `#TODO` `#HARD` - Window across multiple lists

8. [Maximum Sum with at most K elements](https://leetcode.com/problems/maximum-sum-with-at-most-k-elements/) [Greedy lmao]

### 4. Two Heaps Pattern (Find Median)
Maintain two heaps to efficiently find median of streaming/window data.

1. [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/) `[DESIGN]` - Streaming median
2. [Sliding Window Median](https://leetcode.com/problems/sliding-window-median/) - Median in fixed window
3. [Maximize Capital (IPO)](https://leetcode.com/problems/ipo/) `#TODO` - Greedy capital growth

### 5. Minimum Operations Pattern
Find minimum cost operations for connection, scheduling, or resource allocation.

1. [Minimum Cost to Connect Ropes/Sticks](https://leetcode.com/problems/minimum-cost-to-connect-sticks/) `#TODO` `#PVT` - Optimal connection order
2. [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) `#TODO` `#PVT` - Minimum conference rooms
3. [Employee Free Time](https://leetcode.com/problems/employee-free-time/) `#TODO` `#PVT` - Common free intervals
4. [Minimum Cost to Hire K Workers](https://leetcode.com/problems/minimum-cost-to-hire-k-workers/) `#TODO` - Worker cost optimization
5. [Task Scheduler](https://leetcode.com/problems/task-scheduler/) `#TODO` - Minimum CPU time with task cooldown
6. [Minimum Number of Refueling Stops](https://leetcode.com/problems/minimum-number-of-refueling-stops/) `#TODO` - Greedy fuel stops

---

## TODO List:

Problems requiring Heap + other techniques:

1. [Hand of Straights](https://leetcode.com/problems/hand-of-straights/description/) `#TODO`
2. [Course Schedule III](https://leetcode.com/problems/course-schedule-iii/description/)
3. [Car Pooling](https://leetcode.com/problems/car-pooling/description/) - Event-based heap simulation

## With Other Concepts
1. [Furthest Building You Can Reach](https://leetcode.com/problems/furthest-building-you-can-reach/description/) - Greedy + prefix sum

---

## Easy Problems [Not Written]

1. [K Weakest Rows in a Matrix](https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/) - Top K rows by strength
2. [Sum of Elements Between K1th and K2th Smallest](https://www.geeksforgeeks.org/problems/sum-of-elements-between-k1th-and-k2th-smallest-elements3133/0) - Range sum with heap

---

## Time Complexity Reference (check again becoz in java remove() method takes O(n))

| Operation | Time | Space |
|-----------|------|-------|
| Insert/Add | O(log n) | O(1) |
| Delete/Remove | O(log n) | O(1) |
| Peek (Get Min/Max) | O(1) | - |
| Heapify (Build) | O(n) | O(1) |
| Top K Elements | O(n log k) | O(k) |
| Merge K Lists | O(n log k) | O(k) |
| Find Median (2-Heap) | O(log n) add, O(1) median | O(n) |

