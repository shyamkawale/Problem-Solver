package Patterns.Heap.Merge_K_Sorted.mks2_Find_K_Pairs_with_Smallest_Sums; 
 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
 
/*
https://leetcode.com/problems/find-k-pairs-with-smallest-sums/description/

You are given two integer arrays nums1 and nums2 sorted in non-decreasing order and an integer k.
Define a pair (u, v) which consists of one element from the first array and one element from the second array.
Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.

Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]]
Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
Output: [[1,1],[1,1]]
Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]

sol1: BRUTEFORCE HEAP: All all elements in heap with its sum (no use of sorted arrays)
sol2: OPTIMIZED HEAP: Put (0,0) first and then for each polledElement find next two possible elements (0,1) and (1,0) and track visited..
(similar to Find Kth Smallest Sum of a Matrix with Sorted Rows)
 */
public class Find_K_Pairs_with_Smallest_Sums extends ProblemSolver { 
    public static void main(String[] args) { 
        new Find_K_Pairs_with_Smallest_Sums().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums1 = DataConvertor.toIntArray(args[0]); 
        int[] nums2 = DataConvertor.toIntArray(args[1]);
        int k = DataConvertor.toInt(args[2]);
 
        List<List<Integer>> res1 = kSmallestPairs_UsingBruteForceHeap(nums1.clone(), nums2.clone(), k); 
        List<List<Integer>> res2 = kSmallestPairs_UsingOptimizedHeap(nums1.clone(), nums2.clone(), k); 
        System.out.println(res1 + " " + res2);
    } 
 
    // TC: O(n1*n2*logk + klogk)
    // SC: O(3*k + k*2)
    public List<List<Integer>> kSmallestPairs_UsingBruteForceHeap(int[] nums1, int[] nums2, int k) {
        Queue<Node> maxHeap = new PriorityQueue<Node>((a, b) -> Integer.compare(b.sum, a.sum));

        for(int n1: nums1){
            for(int n2: nums2){
                Node currNode = new Node(n1, n2);
                maxHeap.offer(currNode);
                if(maxHeap.size() > k){
                    maxHeap.poll();
                }
            }
        }

        List<List<Integer>> res = new ArrayList<>();

        while(!maxHeap.isEmpty()){
            Node polledNode = maxHeap.poll();
            List<Integer> temp = new ArrayList<Integer>();
            temp.add(polledNode.elem1);
            temp.add(polledNode.elem2);
            res.add(temp);
        }

        return res;
    }

    public class Node{
        int elem1;
        int elem2;
        int sum;

        public Node(int elem1, int elem2){
            this.elem1 = elem1;
            this.elem2 = elem2;
            this.sum = elem1 + elem2;
        }
    } 

    // TC: O(2k*log(2k))
    // SC: O(3k + k)
    public List<List<Integer>> kSmallestPairs_UsingOptimizedHeap(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;

        List<List<Integer>> res = new ArrayList<>();
        Queue<Node2> minHeap = new PriorityQueue<Node2>((a, b) -> Integer.compare(a.sum, b.sum));
        Set<String> visited = new HashSet<String>();

        minHeap.offer(new Node2(nums1[0]+nums2[0], 0, 0));
        visited.add(idxInString(0, 0));

        for(int i=0; i<k; i++){
            Node2 polledNode = minHeap.poll();
            int idx1 = polledNode.idx1;
            int idx2 = polledNode.idx2;

            res.add(List.of(nums1[idx1], nums2[idx2]));

            if (idx1+1 < len1 && !visited.contains(idxInString(idx1+1, idx2))){
                minHeap.offer(new Node2(nums1[idx1+1] + nums2[idx2], idx1+1, idx2));
                visited.add(idxInString(idx1+1, idx2));
            }

            if (idx2+1 < len2 && !visited.contains(idxInString(idx1, idx2+1))) {
                minHeap.offer(new Node2(nums1[idx1] + nums2[idx2+1], idx1, idx2+1));
                visited.add(idxInString(idx1, idx2+1));
            }
        }

        return res;
    }

    public String idxInString(int idx1, int idx2){
        return idx1 + "." + idx2;
    }

    public class Node2{
        int sum;
        int idx1;
        int idx2;

        public Node2(int sum, int idx1, int idx2){
            this.sum = sum;
            this.idx1 = idx1;
            this.idx2 = idx2;
        }
    }
 
} 
