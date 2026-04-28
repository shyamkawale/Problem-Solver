package Patterns.Heap.Merge_K_Sorted.mks6_Maximum_Sum_Combination; 
 
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://www.interviewbit.com/problems/maximum-sum-combinations/
Given two equally sized 1-D arrays nums1, nums2 containing n integers each.
A sum combination is made by adding one element from array nums1 and another element of array nums2.
Return the maximum k valid sum combinations from all the possible sum combinations.

Input: nums1 = [3, 2], nums2 = [1, 4], k = 2
Output: [7, 6]
Input: nums1 = [1, 4, 2, 3], nums2 = [2, 5, 1, 6], k = 4
Output: [10, 9, 9, 8]

sol1: BRUTEFORCE HEAP: All all elements in heap with its sum
sol2: OPTIMIZED HEAP: Put (last,last) last and then for each polledElement find next two possible elements (last,last-1) and (last-1,last) and track visited..

 */
public class Maximum_Sum_Combination extends ProblemSolver { 
    public static void main(String[] args) { 
        new Maximum_Sum_Combination().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums1 = DataConvertor.toIntArray(args[0]); 
        int[] nums2 = DataConvertor.toIntArray(args[1]); 
        int k = DataConvertor.toInt(args[2]);
 
        int[] res1 = maxSumCombinations_UsingBruteForceHeap(nums1.clone(), nums2.clone(), k); 
        int[] res2 = maxSumCombinations_UsingOptimizedHeap(nums1.clone(), nums2.clone(), k);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2)); 
    } 
 
    // TC: O((n1*n2)logk + klogk)
    // SC: O(k + k)
    public int[] maxSumCombinations_UsingBruteForceHeap(int[] nums1, int[] nums2, int k) {        
        Queue<Integer> minHeap = new PriorityQueue<Integer>();

        for(int n1: nums1){
            for(int n2: nums2){
                int sum = n1+n2;
                minHeap.offer(sum);
                if(minHeap.size() > k){
                    minHeap.poll();
                }
            }
        }
        
        int[] res = new int[k];
        
        for(int i=k-1; i>=0; i--){
            res[i] = minHeap.poll();
        }
        
        return res;
    }

    // TC: O(2klog(2k))
    // SC: O(3k + k + k)
    public int[] maxSumCombinations_UsingOptimizedHeap(int[] nums1, int[] nums2, int k) {        
        int len1 = nums1.length;
        int len2 = nums2.length;
        
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        Queue<Node> maxHeap = new PriorityQueue<Node>((a,b) -> Long.compare(b.sum, a.sum));
        Set<Long> visited = new HashSet<>();
        int[] res = new int[k];
        
        maxHeap.offer(new Node(nums1[len1-1]+nums2[len2-1], len1-1, len2-1));
        visited.add(encode(len1-1, len2-1));
        
        for(int i=0; i<k; i++){
            Node polledNode = maxHeap.poll();
            int idx1 = polledNode.idx1;
            int idx2 = polledNode.idx2;
            
            res[i] = polledNode.sum;
            
            if(0 <= idx1-1 && idx1-1 < len1 && !visited.contains(encode(idx1-1, idx2))){
                maxHeap.offer(new Node(nums1[idx1-1]+nums2[idx2], idx1-1, idx2)); 
                visited.add(encode(idx1-1, idx2));
            }
            
            if(0 <= idx2-1 && idx2-1 < len2 && !visited.contains(encode(idx1, idx2-1))){
                maxHeap.offer(new Node(nums1[idx1]+nums2[idx2-1], idx1, idx2-1));
                visited.add(encode(idx1, idx2-1));
            }
        }
        
        return res;
    }

    public long encode(int a, int b){
        // return idx1 + "." + idx2;
        return ((long) a << 32) | (b & ((1L << 32) - 1));
    }
    
    public class Node{
        int sum;
        int idx1;
        int idx2;
        
        public Node(int sum, int idx1, int idx2){
            this.sum = sum;
            this.idx1 = idx1;
            this.idx2 = idx2;
        }
    }
 
} 
