package Patterns.LinkedList.General.Find_Middle_Element_in_LinkedList; 

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;
 
// Bruteforce approach: Two Pass Solution (find len and then mid)

// Hare and Tortoise Solution: One Pass Solution
// mid = left + Math.floor((right-left)/2);
// Odd List:  [1, 2, 3*, 4, 5]  ===> mid is 3  (slow is at middle(3), fast is at tail(5))
// Even List: [1, 2, 3*, 4]     ===> mid is 3  (slow is at middle(3), fast is at null)
public class Find_Middle_Element_in_LinkedList extends ProblemSolver { 
    public static void main(String[] args) { 
        new Find_Middle_Element_in_LinkedList().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        ListNode list = ListWrapper.stringToListNode(args[0]);
 
        int res = findMiddleElemInLL(list); 
        System.out.println(res); 
    } 
 
    // Hare and Tortoise Method (one pass method)
    // TC: O(n/2)
    // SC: O(n)
    public int findMiddleElemInLL(ListNode head) { 
        if(head == null){
            return -1;
        }
        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow.val;
    } 
} 
