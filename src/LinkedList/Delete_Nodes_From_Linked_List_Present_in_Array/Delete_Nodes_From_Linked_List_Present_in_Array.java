package LinkedList.Delete_Nodes_From_Linked_List_Present_in_Array;

import java.util.HashSet;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.LinkedList.ListNode;
import Helpers.DataStructure.LinkedList.ListWrapper;

/* 
https://leetcode.com/problems/delete-nodes-from-linked-list-present-in-array/
You are given an array of integers nums and the head of a linked list. 
Return the head of the modified linked list after removing all nodes from the linked list that have a value that exists in nums.
*/
public class Delete_Nodes_From_Linked_List_Present_in_Array extends ProblemSolver {

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        ListNode head = ListWrapper.stringToListNode(args[1]);

        ListNode result = modifiedList(nums, head);
        ListWrapper.prettyPrintLinkedList(result);
    }

    public static void main(String[] args) {
        new Delete_Nodes_From_Linked_List_Present_in_Array().readInput();
    }

    // TimeComplexity: O(nums.length + linkedlist length) => O(n+m) => O(N)
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> valuesToRemove = new HashSet<Integer>();
        for(int n: nums){
            valuesToRemove.add(n);
        }

        ListNode curr = head;
        ListNode prev = null;
        while(curr != null){
            //remove current node
            if(valuesToRemove.contains(curr.val)){
                if(curr == head){
                    head = curr.next; //handle if curr is head(i.e start node)
                }
                else{
                    prev.next = curr.next; //handle other cases
                }
            }
            else{
                prev = curr; //if arrays don't contain curr then just move forward
            }
            curr = curr.next;
        }
        return head;
    }
    
}
