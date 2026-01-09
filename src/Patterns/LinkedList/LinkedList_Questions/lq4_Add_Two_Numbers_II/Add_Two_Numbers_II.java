package Patterns.LinkedList.LinkedList_Questions.lq4_Add_Two_Numbers_II;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/* 
https://leetcode.com/problems/add-two-numbers-ii/

You are given two non-empty linked lists representing two non-negative integers. 
The most significant digit comes first and each of their nodes contains a single digit. 
Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.
*/
public class Add_Two_Numbers_II extends ProblemSolver {

    public static void main(String[] args) {
        new Add_Two_Numbers_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode list1 = ListWrapper.stringToListNode(args[0]);
        ListNode list2 = ListWrapper.stringToListNode(args[1]);

        ListNode res = addTwoNumbers(list1, list2);
        ListWrapper.prettyPrintLinkedList(res);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Deque<Integer> stack1 = new ArrayDeque<Integer>();
        Deque<Integer> stack2 = new ArrayDeque<Integer>();

        ListNode curr = l1;
        while(curr != null){
            stack1.push(curr.val);
            curr = curr.next;
        }

        curr = l2;
        while(curr != null){
            stack2.push(curr.val);
            curr = curr.next;
        }

        int carry = 0;
        ListNode head = null;
        while(!stack1.isEmpty() || !stack2.isEmpty()){
            int l1Val = stack1.isEmpty() ? 0 : stack1.pop();
            int l2Val = stack2.isEmpty() ? 0 : stack2.pop();

            int sum = l1Val + l2Val + carry;
            ListNode node = new ListNode(sum%10);
            node.next = head;
            head = node;

            carry = sum/10;
        }

        if(carry != 0){
            ListNode node = new ListNode(carry);
            node.next = head;
            head = node;
        }

        return head;
    }

}
