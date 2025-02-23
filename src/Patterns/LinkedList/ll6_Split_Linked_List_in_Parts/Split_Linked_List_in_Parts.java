package Patterns.LinkedList.ll6_Split_Linked_List_in_Parts;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

/* 
https://leetcode.com/problems/split-linked-list-in-parts/description
Given the head of a singly linked list and an integer k, split the linked list into k consecutive linked list parts.

The length of each part should be as equal as possible: no two parts should have a size differing by more than one. 
This may lead to some parts being null.

The parts should be in the order of occurrence in the input list, and parts occurring earlier should always have a size greater than or equal to parts occurring later.

Return an array of the k parts. 

Input: head = [1,2,3], k = 5
Output: [[1],[2],[3],[],[]]

Input: head = [1,2,3,4,5,6,7,8,9,10], k = 3
Output: [[1,2,3,4],[5,6,7],[8,9,10]]
*/
public class Split_Linked_List_in_Parts extends ProblemSolver {
    public static void main(String[] args) {
        new Split_Linked_List_in_Parts().readInput();
    }
    
    @Override
    public void processParameters(String[] args) {
        ListNode head = ListWrapper.stringToListNode(args[0]);
        int k = DataConvertor.toInt(args[1]);

        ListNode[] result = this.splitListToParts(head, k);
        for(ListNode listNode: result){
            ListWrapper.prettyPrintLinkedList(listNode);
        }
    }

    // 🏆 Crucial Thinking => Spliting N elements in k parts => 
    // If there are N elements in the list, and k parts to split, then every part has N/k elements, except the first N%k parts have an extra one.


    // Time Complexity ⏲️: O(len) + O(k(outer for loop) * n(len/k)) => O(len +k*(len/k)) => O(len + len) => O(len)
    // Space Complexity 🚀: O(k) why? => result array of size k, which takes O(k) space.
    // The algorithm uses a constant amount of extra space for variables like curr, last, etc., which is O(1). So SC is O(k)
    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode curr = head;
        int len = 0;
        while(curr != null){
            len++;
            curr = curr.next;
        }

        int rem = len%k;
        int n = len/k;

        ListNode[] result = new ListNode[k];
        curr = head;
        for(int i=0; i<rem; i++){
            ListNode currHead = curr;
            ListNode last = null;
            result[i] = currHead;
            for(int j=0; j<n+1; j++){
                last = curr;
                curr = curr.next;
            }
            last.next = null;
        }

        for(int i=rem; i<k; i++){
            ListNode currHead = curr;
            ListNode last = null;
            result[i] = currHead;
            if(currHead == null) continue;
            for(int j=0; j<n; j++){
                last = curr;
                curr = curr.next;
            }
            last.next = null;
        }

        return result;
    }
}
