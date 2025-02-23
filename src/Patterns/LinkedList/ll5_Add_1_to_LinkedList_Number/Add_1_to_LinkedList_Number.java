package Patterns.LinkedList.ll5_Add_1_to_LinkedList_Number;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/*
https://www.geeksforgeeks.org/problems/add-1-to-a-number-represented-as-linked-list/1

Input:  4->5->7
Output: 4->5->8

Input:  4->5->9
Output: 4->6->0

Input:  9
Output: 1->0

Input:  9->9
Output: 1->0->0

Input: null
Output: -1
 */
public class Add_1_to_LinkedList_Number extends ProblemSolver {
    public static void main(String[] args) {
        new Add_1_to_LinkedList_Number().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode list = ListWrapper.stringToListNode(args[0]);

        ListNode res = addOne(list);
        ListWrapper.prettyPrintLinkedList(res);
    }

    // TC: O(n) // Recursively traverse list
    // SC: O(n) // recStack
    public ListNode addOne(ListNode head) {
        if (head == null) {
            return new ListNode(-1);
        }

        ListNode curr = head;
        int carry = helper(curr);

        // if carry is still there (ex input: 459, 9, 99)
        if (carry != 0) {
            ListNode node = new ListNode(carry);
            node.next = head;
            return node;
        }

        return head;
    }

    // recursively add carry from last
    public int helper(ListNode currNode) {
        if (currNode == null) {
            return 1; // to add one
        }

        int carry = helper(currNode.next);
        int sum = carry + currNode.val;
        currNode.val = sum % 10;

        return sum / 10;
    }
}
