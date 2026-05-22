package Patterns.LinkedList.LinkedList_Questions.lq12_Flattening_a_LinkedList;

import java.util.PriorityQueue;
import Helpers.ProblemSolver;

/*
https://www.geeksforgeeks.org/problems/flattening-a-linked-list/1

Given a linked list containing n head nodes where every node in the linked list contains two pointers:
(i) next points to the next node in the list.
(ii) bottom points to a sub-linked list where the current node is the head.
Each of the sub-linked lists nodes and the head nodes are sorted in ascending order based on their data. 
Flatten the linked list such that all the nodes appear in a single level while maintaining the sorted order.

Note:
1. ↓ represents the bottom pointer and → represents the next pointer.
2. The flattened list will be printed using the bottom pointer instead of the next pointer.
*/
public class Flattening_a_LinkedList extends ProblemSolver {

    public static void main(String[] args) {
        new Flattening_a_LinkedList().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        SpecialLinkedList head = new SpecialLinkedList(5);
        head.bottom = new SpecialLinkedList(7);
        head.bottom.bottom = new SpecialLinkedList(8);

        head.next = new SpecialLinkedList(10);
        head.next.bottom = new SpecialLinkedList(20);

        head.next.next = new SpecialLinkedList(19);

        SpecialLinkedList res = flatten(head);
        System.out.println(res);
    }

    public class SpecialLinkedList {
        int val;
        SpecialLinkedList next;
        SpecialLinkedList bottom;

        public SpecialLinkedList(int val) {
            this.val = val;
            this.next = null;
            this.bottom = null;
        }
    }

    public SpecialLinkedList flatten(SpecialLinkedList root) {
        // code here
        PriorityQueue<SpecialLinkedList> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        minHeap.offer(root);

        SpecialLinkedList newHead = new SpecialLinkedList(-1);
        SpecialLinkedList currNode = newHead;

        while (!minHeap.isEmpty()) {
            SpecialLinkedList polledNode = minHeap.poll();

            currNode.next = new SpecialLinkedList(polledNode.val);
            currNode = currNode.next;

            if (polledNode.bottom != null) {
                minHeap.offer(polledNode.bottom);
            }

            if (polledNode.next != null) {
                minHeap.offer(polledNode.next);
            }
        }

        return newHead.next;
    }

}
