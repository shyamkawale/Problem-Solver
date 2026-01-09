package Patterns.LinkedList.LinkedList_Questions.lq8_Remove_Duplicates_from_Sorted_List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/*
https://leetcode.com/problems/remove-duplicates-from-sorted-list/
Given the head of a sorted linked list, delete all duplicates such that each element appears only once. 
Return the linked list sorted as well.

Input: head = [1,1,2]
Output: [1,2]

Input: head = [1,1,2,3,3]
Output: [1,2,3]
*/
public class Remove_Duplicates_from_Sorted_List extends ProblemSolver {

    public static void main(String[] args) {
        new Remove_Duplicates_from_Sorted_List().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode head = ListWrapper.stringToListNode(args[0]);

        ListNode res = deleteDuplicates(head);
        ListWrapper.prettyPrintLinkedList(res);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        
        ListNode curr = head.next;
        ListNode prevNode = head;

        while (curr != null) {
            if(prevNode.val == curr.val) {
                curr = curr.next;
            }
            else {
                prevNode.next = curr;
                prevNode = curr;
                curr = curr.next;
            }
        }

        prevNode.next = null;

        return head;
    }

}
