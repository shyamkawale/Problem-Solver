package Patterns.LinkedList.General.Reverse_SLL;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;
import Patterns.LinkedList.General.ILinkedListOperation;
import Patterns.LinkedList.General.Singly_LinkedList_Operations.LinkedList_Operations;

/*
Sol 1: Using Stack TC: O(n + n), SC: O(n)
Sol 2: Inline Approach TC: O(n), SC: O(1)
Sol 3: Recursion with Accumulator TC:O(n) SC: O(n)
Sol 4: Normal Recursion with InsertElementAtLast() TC: O(n^2), SC: O(n)
 */
public class Reverse_SLL extends ProblemSolver {

    ILinkedListOperation<ListNode> listOps = new LinkedList_Operations();

    public static void main(String[] args) {
        new Reverse_SLL().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode list = ListWrapper.stringToListNode(args[0]);

        // ListNode res = reverseList1(list);
        // ListNode res = reverseList2(list);
        // ListNode res = reverseList3(list);
        ListNode res = reverseList4(list);

        ListWrapper.prettyPrintLinkedList(res);
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

    // Inline Approach 2: TC: O(n), SC: O(1)
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode currNode = head;
        ListNode prevNode = null; // can be called newHead

        while (currNode != null) {
            ListNode nextNode = currNode.next;
            currNode.next = prevNode;
            prevNode = currNode;
            currNode = nextNode;
        }

        return prevNode;
    }

    // Tail Recursion with Accumulator
    // TC:O(n)
    // SC: O(n) - recStack
    public ListNode reverseList3(ListNode head) {
        return reverseHelper(head, null);
    }

    // same as iterative approach
    private ListNode reverseHelper(ListNode currNode, ListNode newHead) {
        // Base case: when current is null, acc is the new head of the reversed list.
        if (currNode == null) {
            return newHead;
        }

        // Reverse the link: point current to the accumulated reversed list.
        ListNode nextNode = currNode.next;
        currNode.next = newHead;

        // Tail recursive call: move to the next node with the updated accumulator.
        return reverseHelper(nextNode, currNode);
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
