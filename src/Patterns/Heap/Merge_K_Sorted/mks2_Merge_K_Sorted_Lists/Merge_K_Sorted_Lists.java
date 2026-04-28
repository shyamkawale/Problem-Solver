package Patterns.Heap.Merge_K_Sorted.mks2_Merge_K_Sorted_Lists;

import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/*
You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
Merge all the linked-lists into one sorted linked-list and return it.

Example 1:
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted linked list:
1->1->2->3->4->4->5->6

Example 2:
Input: lists = []
Output: []

Example 3:
Input: lists = [[]]
Output: []
*/
public class Merge_K_Sorted_Lists extends ProblemSolver {

    public static void main(String[] args) {
        new Merge_K_Sorted_Lists().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode res = mergeKLists(new ListNode[1]);
        ListWrapper.prettyPrintLinkedList(res);
    }

    public ListNode mergeKLists(ListNode[] lists) {
        Queue<ListNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        for(ListNode head: lists) {
            if(head != null) {
                minHeap.offer(head);
            }
        }

        ListNode res = new ListNode(-1);
        ListNode curr = res;

        while(!minHeap.isEmpty()) {
            ListNode polledNode = minHeap.poll();
            if(polledNode.next != null) {
                minHeap.offer(polledNode.next);
            }

            curr.next = new ListNode(polledNode.val);
            curr = curr.next;
        }

        return res.next;
    }

}
