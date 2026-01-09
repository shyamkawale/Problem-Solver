package Patterns.LinkedList.LinkedList_Questions.lq14_Reorder_List;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/*
https://leetcode.com/problems/reorder-list/

You are given the head of a singly linked-list. The list can be represented as:

L0 → L1 → … → Ln - 1 → Ln
Reorder the list to be on the following form:

L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
You may not modify the values in the list's nodes. Only nodes themselves may be changed.

Input: head = [1,2,3,4]
Output: [1,4,2,3]

Input: head = [1,2,3,4,5]
Output: [1,5,2,4,3]

Approach 1: Using Stack
Approach 2: Using Hare & Tortoise, Reverse SecondHalf, Merge
*/
public class Reorder_List extends ProblemSolver {

    public static void main(String[] args) {
        new Reorder_List().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode head = ListWrapper.stringToListNode(args[0]);

        reorderList_usingStack(head);
        // reorderList_usingReversingHalfMerging(head);
        ListWrapper.prettyPrintLinkedList(head);
    }

    // using stack
    // TC: O(n)
    // SC: O(n) - stack size
    public void reorderList_usingStack(ListNode head) {
        if(head == null || head.next == null) {
            return;
        }

        Deque<ListNode> stack = new ArrayDeque<>();

        ListNode currNode = head;
        while(currNode != null) {
            stack.push(currNode);
            currNode = currNode.next;
        }

        currNode = head;
        while(currNode != null && currNode.next != null && currNode.next.next != null) {
            ListNode nextNode = currNode.next;
            ListNode poppedNode = stack.pop();

            currNode.next = poppedNode;
            poppedNode.next = nextNode;
            currNode = nextNode;
            stack.peek().next = null;
        }
    }

    // Using Hare & Tortoise, reversing, merging
    // TC: O(n)
    // SC: O(1)
    public void reorderList_usingReversingHalfMerging(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        // Finding Middle Node
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the Second Half
        ListNode prev = null;
        ListNode curr = slow.next;
        slow.next = null;

        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        // 'prev' is now the head of the reversed second half

        // Merge the Two Halves
        ListNode first = head;
        ListNode second = prev;
        while (second != null) {
            ListNode firstNextNode = first.next;
            ListNode secondNextNode = second.next;

            first.next = second;
            second.next = firstNextNode;

            first = firstNextNode;
            second = secondNextNode;
        }
    }

}
