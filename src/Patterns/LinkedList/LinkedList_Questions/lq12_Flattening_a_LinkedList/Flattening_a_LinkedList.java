package Patterns.LinkedList.LinkedList_Questions.lq12_Flattening_a_LinkedList;

import java.util.PriorityQueue;
import Helpers.ProblemSolver;

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
