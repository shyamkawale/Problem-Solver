package Patterns.LinkedList.LinkedList_Questions.lq15_Merge_TwoSorted_LinkedList;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

public class Merge_TwoSorted_LinkedList extends ProblemSolver {

    public static void main(String[] args) {
        new Merge_TwoSorted_LinkedList().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode list1 = ListWrapper.stringToListNode(args[0]);
        ListNode list2 = ListWrapper.stringToListNode(args[1]);

        ListNode res = mergeTwoLists(list1, list2);
        ListWrapper.prettyPrintLinkedList(res);
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(-1);
        ListNode currNode = head;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                currNode.next = list1;
                currNode = list1;
                list1 = list1.next;
            } else {
                currNode.next = list2;
                currNode = list2;
                list2 = list2.next;
            }
        }

        if (list2 != null) {
            currNode.next = list2;
        } else {
            currNode.next = list1;
        }

        return head.next;
    }

}
