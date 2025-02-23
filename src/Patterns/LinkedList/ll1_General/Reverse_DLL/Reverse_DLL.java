package Patterns.LinkedList.ll1_General.Reverse_DLL;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.ProblemSolver;
import Helpers.DataStructure.DoublyLinkedList.DoublyListWrapper;
import Helpers.DataStructure.DoublyLinkedList.ListNode;
import Patterns.LinkedList.ll1_General.ILinkedListOperation;
import Patterns.LinkedList.ll1_General.Doubly_LinkedList_Operations.Doubly_LinkedList_Operations;

/*
Sol 1: Using Stack TC: O(n + n), SC: O(n)
Sol 2: Inline Approach TC: O(n), SC: O(1)
Sol 3: Recursion with Accumulator TC:O(n) SC: O(n)
Sol 4: Normal Recursion with InsertElementAtLast() TC: O(n^2), SC: O(n)
 */
public class Reverse_DLL extends ProblemSolver {

    ILinkedListOperation<ListNode> listOps = new Doubly_LinkedList_Operations();

    public static void main(String[] args) {
        new Reverse_DLL().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode list = DoublyListWrapper.stringToListNode(args[0]);

        // ListNode res = reverseList1(list);
        // ListNode res = reverseList2(list);
        // ListNode res = reverseList3(list);
        ListNode res = reverseList4(list);
        DoublyListWrapper.prettyPrintLinkedList(res);
    }

    // Stack Approach 1: TC: O(n + n), SC: O(n)
    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        Deque<Integer> stack = new ArrayDeque<Integer>();
        ListNode currNode = head;

        while (currNode != null) {
            stack.push(currNode.val);
            currNode = currNode.next;
        }

        currNode = head;
        while (currNode != null) {
            currNode.val = stack.pop();
            currNode = currNode.next;
        }

        return head;
    }

    // Inline Approach: TC: O(n), SC: O(1)
    // Swap links
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode currNode = head;
        ListNode prevNode = null;

        while (currNode != null) {
            ListNode nextNode = currNode.next;
            currNode.prev = currNode.next; // only extra step compared to Singly LL
            currNode.next = prevNode;
            prevNode = currNode;
            currNode = nextNode;
        }

        return prevNode;
    }

    // reverse linkedlist (recursive way)
    // TC:O(n)
    // SC: O(n) - recStack
    public ListNode reverseList3(ListNode head) {
        return helper(head, null);
    }

    private ListNode helper(ListNode currNode, ListNode newHeadNode) {
        if (currNode == null) {
            return newHeadNode;
        }

        ListNode nextNode = currNode.next;
        currNode.next = newHeadNode;
        currNode.prev = null;

        if (newHeadNode != null) {
            newHeadNode.prev = currNode;
        }

        return helper(nextNode, currNode);
    }

    // Normal recursion
    // TC: O(n^2)
    // SC: O(n) - recStack
    public ListNode reverseList4(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode currNode = head;

        // remove currNode from list
        head = head.next;
        currNode.next = null;

        // recursiveCall to new head;
        ListNode list = reverseList4(head);

        // Add currNode to list;
        list = listOps.insertElementAtLast(list, currNode.val);

        return list;
    }
}
