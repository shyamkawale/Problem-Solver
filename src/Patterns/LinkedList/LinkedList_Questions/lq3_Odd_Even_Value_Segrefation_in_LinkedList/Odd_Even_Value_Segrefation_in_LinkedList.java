package Patterns.LinkedList.LinkedList_Questions.lq3_Odd_Even_Value_Segrefation_in_LinkedList;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/*
https://takeuforward.org/data-structure/segregate-even-and-odd-nodes-in-linkedlist

input:  1->2->4->3->5->6->null
output: 2->4->6->1->3->5->null

 */
public class Odd_Even_Value_Segrefation_in_LinkedList extends ProblemSolver {
    public static void main(String[] args) {
        new Odd_Even_Value_Segrefation_in_LinkedList().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode list = ListWrapper.stringToListNode(args[0]);

        list = oddEvenValueSegregation(list);
        ListWrapper.prettyPrintLinkedList(list);
    }


    // TC: O(n), SC: O(1)
    private ListNode oddEvenValueSegregation(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode oddHead = new ListNode(-1); // Dummy Node for oddHead to create odd elements linkedlist
        ListNode oddCurr = oddHead;
        ListNode evenHead = new ListNode(-1); // Dummy Node form evenHead to create even elements linkedlist
        ListNode evenCurr = evenHead;

        ListNode currNode = head;

        while (currNode != null) {
            boolean isEvenVal = (currNode.val % 2) == 0;
            if (isEvenVal) {
                evenCurr.next = new ListNode(currNode.val);
                evenCurr = evenCurr.next;
            } else {
                oddCurr.next = new ListNode(currNode.val);
                oddCurr = oddCurr.next;
            }
            currNode = currNode.next;
        }

        evenCurr.next = oddHead.next;

        return evenHead.next;
    }

}
