package LinkedList.Insert_Greatest_Common_Divisors_in_Linked_List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.LinkedList.ListNode;
import Helpers.DataStructure.LinkedList.ListWrapper;

/* 
https://leetcode.com/problems/insert-greatest-common-divisors-in-linked-list
Given the head of a linked list head, in which each node contains an integer value.

Between every pair of adjacent nodes, insert a new node with a value equal to the greatest common divisor of them.

Return the linked list after insertion.

The greatest common divisor of two numbers is the largest positive integer that evenly divides both numbers.
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

    // 💡recursive GCD algo 
    // TC => O(log(min(a,b))), 
    // SC => O(log(min(a, b))) because each recursive call adds a new frame to the call stack, and the recursion depth is logarithmic in the size of the smaller number.
    // private int getGCD(int a, int b){
    //     if (b == 0) {
    //         return a;
    //     }
    //     return getGCD(b, a % b);
    // }
}
