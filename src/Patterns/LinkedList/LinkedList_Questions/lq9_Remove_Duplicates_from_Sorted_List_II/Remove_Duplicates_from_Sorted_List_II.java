package Patterns.LinkedList.LinkedList_Questions.lq9_Remove_Duplicates_from_Sorted_List_II;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/*
https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. 
Return the linked list sorted as well.

Input: head = [1,2,3,3,4,4,5]
Output: [1,2,5]

Input: head = [1,1,1,2,3]
Output: [2,3]
*/
public class Remove_Duplicates_from_Sorted_List_II extends ProblemSolver {
    public static void main(String[] args) {
        new Remove_Duplicates_from_Sorted_List_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode head = ListWrapper.stringToListNode(args[0]);

        ListNode res = deleteDuplicates(head);
        ListWrapper.prettyPrintLinkedList(res);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }

        ListNode currNode = head.next;
        ListNode prevNode = head;

        ListNode newHead = new ListNode(-1);
        ListNode newHeadCurr = newHead;

        while(currNode != null) {
            if(prevNode.val == currNode.val) {
                currNode = currNode.next;
            }
            else {
                if(prevNode.next == currNode) {
                    newHeadCurr.next = new ListNode(prevNode.val);
                    newHeadCurr = newHeadCurr.next;
                }

                prevNode = currNode;
                currNode = currNode.next;
            }
        }

        if(prevNode.next == null) {
            newHeadCurr.next = new ListNode(prevNode.val);
        }

        return newHead.next;
    }

}
