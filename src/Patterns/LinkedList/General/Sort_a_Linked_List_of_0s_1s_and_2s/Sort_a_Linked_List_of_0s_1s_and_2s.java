package Patterns.LinkedList.General.Sort_a_Linked_List_of_0s_1s_and_2s;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

public class Sort_a_Linked_List_of_0s_1s_and_2s extends ProblemSolver {

    public static void main(String[] args) {
        new Sort_a_Linked_List_of_0s_1s_and_2s().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode head = ListWrapper.stringToListNode(args[0]);

        ListNode res = sortList(head);
        ListWrapper.prettyPrintLinkedList(res);
    }

    public ListNode sortList(ListNode head) {
        ListNode zeroList = new ListNode(-1);
        ListNode zeroListCurr = zeroList;
        ListNode oneList = new ListNode(-1);
        ListNode oneListCurr = oneList;
        ListNode twoList = new ListNode(-1);
        ListNode twoListCurr = twoList;

        ListNode curr = head;
        while(curr != null) {
            if(curr.val == 0) {
                zeroListCurr.next = curr;
                zeroListCurr = curr;
            }
            else if(curr.val == 1) {
                oneListCurr.next = curr;
                oneListCurr = curr;
            }
            else {
                twoListCurr.next = curr;
                twoListCurr = curr;
            }

            curr = curr.next;
        }

        if(oneList.next != null) {
            zeroListCurr.next = oneList.next;
            oneListCurr.next = twoList.next;
        }
        else {
            zeroListCurr.next = twoList.next;
        }
        twoListCurr.next = null;

        return zeroList.next;
    }

}
