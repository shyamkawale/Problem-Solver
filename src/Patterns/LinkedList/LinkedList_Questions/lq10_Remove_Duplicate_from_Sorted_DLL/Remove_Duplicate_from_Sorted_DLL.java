package Patterns.LinkedList.LinkedList_Questions.lq10_Remove_Duplicate_from_Sorted_DLL;

import Helpers.ProblemSolver;
import Helpers.DataStructure.DoublyLinkedList.DoublyListWrapper;
import Helpers.DataStructure.DoublyLinkedList.ListNode;

/*
https://www.geeksforgeeks.org/problems/remove-duplicates-from-a-sorted-doubly-linked-list/1

Input:
1<->1<->1<->2<->3<->4
Output:
1<->2<->3<->4

Input:
1<->2<->2<->3<->3<->4<->4
Output:
1<->2<->3<->4
*/
public class Remove_Duplicate_from_Sorted_DLL extends ProblemSolver {

    public static void main(String[] args) {
        new Remove_Duplicate_from_Sorted_DLL().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode head = DoublyListWrapper.stringToListNode(args[0]);

        ListNode res = deleteDuplicates(head);
        DoublyListWrapper.prettyPrintLinkedList(res);
    }

    private ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }

        ListNode curr = head.next;
        ListNode prevNode = head;

        while (curr != null) {
            if (prevNode.val == curr.val) {
                curr = curr.next;
            } else {
                prevNode.next = curr;
                curr.prev = prevNode;

                prevNode = curr;
                curr = curr.next;
            }
        }

        prevNode.next = null;

        return head;
    }

}
