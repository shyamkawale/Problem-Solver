package Patterns.LinkedList.LinkedList_Questions.lq11_Two_Sum_in_DLL;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.DoublyLinkedList.DoublyListWrapper;
import Helpers.DataStructure.DoublyLinkedList.ListNode;

/*
https://www.geeksforgeeks.org/problems/find-pairs-with-given-sum-in-doubly-linked-list/1

Input:  
1 <-> 2 <-> 4 <-> 5 <-> 6 <-> 8 <-> 9
target = 7
Output: (1, 6), (2,5)

Input: 
1 <-> 5 <-> 6
target = 6
Output: (1,5)
*/
public class Two_Sum_in_DLL extends ProblemSolver {

    public static void main(String[] args) {
        new Two_Sum_in_DLL().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode head = DoublyListWrapper.stringToListNode(args[0]);
        int target = DataConvertor.toInt(args[1]);

        List<List<Integer>> res = twoSumInDLL(head, target);
        System.out.println(res);
    }

    private List<List<Integer>> twoSumInDLL(ListNode head, int target) {
        List<List<Integer>> res = new ArrayList<>();

        ListNode start = head;
        int startIdx = 0;
        ListNode end = head;
        int endIdx = 0;
        while(end.next != null) {
            end = end.next;
            endIdx++;
        }

        while(startIdx < endIdx) {
            int sum = start.val + end.val;

            if(sum == target) {
                res.add(List.of(start.val, end.val));
                start = start.next;
                startIdx++;
                end = end.prev;
                endIdx--;
            }
            else if(sum < target) {
                start = start.next;
                startIdx++;
            }
            else {
                end = end.prev;
                endIdx--;
            }
        }
        
        return res;
    }
}
