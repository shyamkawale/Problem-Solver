package Patterns.LinkedList.LinkedList_Questions.lq6_Insert_Greatest_Common_Divisors_in_Linked_List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/* 
https://leetcode.com/problems/insert-greatest-common-divisors-in-linked-list
Given the head of a linked list head, in which each node contains an integer value.

Between every pair of adjacent nodes, insert a new node with a value equal to the greatest common divisor of them.

Return the linked list after insertion.

Input: head = [18,    6,    10,    3]
Output:       [18, 6, 6, 2, 10, 1, 3]

Input: head = [7]
Output:       [7]
 */
public class Insert_Greatest_Common_Divisors_in_Linked_List extends ProblemSolver{
    @Override
    public void processParameters(String[] args) {
        ListNode head = ListWrapper.stringToListNode(args[0]);
        ListNode result = insertGreatestCommonDivisors(head);
        ListWrapper.prettyPrintLinkedList(result);
    }

    public static void main(String[] args) {
        new Insert_Greatest_Common_Divisors_in_Linked_List().readInput();
    }

    // Time Complexity⏲️: O(len * log(min(a,b))) => O(len * log(M)) where M considerd as maximum of all nodes value => O(NlogM)
    // Space Complexity🚀: O(1)
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode front = head.next;
        ListNode rear = head;

        while(front != null){
            int gcd = getGCD(front.val, rear.val);
            ListNode node = new ListNode(gcd);

            rear.next = node;
            node.next = front;

            rear = front;
            front = front.next;
        }

        return head;
    }

    // 💡iterative GCD algo 
    // TC => O(log(min(a,b))), 
    // SC => O(1)
    private int getGCD(int a, int b){
        if(a == 1 || b == 1){
            return 1;
        }
        while(b != 0){
            int rem = a % b;
            a = b;
            b = rem;
        }
        return a;
    }
}
