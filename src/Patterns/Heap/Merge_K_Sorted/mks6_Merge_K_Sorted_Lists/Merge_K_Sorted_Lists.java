package Patterns.Heap.Merge_K_Sorted.mks6_Merge_K_Sorted_Lists;

import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

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
