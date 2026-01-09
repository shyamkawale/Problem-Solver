package Patterns.LinkedList.LinkedList_Questions.lq2_Intersection_of_Two_LinkedList;

import java.util.HashSet;
import java.util.Set;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;

// BruteForce Approach: For every Node of list1 traverse list2 elements: O(len1*len2)
// Better Approach (Sol1): Store elements of list1 in set and check first element in list2 that is there in set: TC: O(len1 + len2), SC: O(len1)
// Optimal Solution (Sol2): find length difference and find intersection
// Best Solution (Sol3): 
public class Intersection_of_Two_LinkedList extends ProblemSolver {

    public static void main(String[] args) {
        new Intersection_of_Two_LinkedList().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode headA = null;
        ListNode headB = null;
        ListNode res1 = getIntersectionNode1(headA, headB);
        ListNode res2 = getIntersectionNode2(headA, headB);
        ListNode res3 = getIntersectionNode3(headA, headB);
        System.out.println(res1.val + " " + res2.val + " " + res3.val);
    }

    // TC: O(len1 + len2)
    // SC: O(len1)
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<ListNode>();
        ListNode currA = headA;
        ListNode currB = headB;

        while (currA != null) {
            set.add(currA);
            currA = currA.next;
        }

        while (currB != null) {
            if (set.contains(currB)) {
                return currB;
            }
            currB = currB.next;
        }

        return null;
    }

    // TC: O(max(len1+len2)*2) -- worstcase
    // SC: O(1)
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }

        int diff = getLengthDifference(headA, headB);

        ListNode currA = headA;
        ListNode currB = headB;

        if(diff < 0){
            for(int i=0; i<Math.abs(diff); i++){
                currB = currB.next;
            }
        }
        else{
            for(int i=0; i<diff; i++){
                currA = currA.next;
            }
        }

        while(currA != null && currB != null){
            if(currA == currB){
                return currA;
            }
            currA = currA.next;
            currB = currB.next;
        }

        return null;
    }

    private int getLengthDifference(ListNode headA, ListNode headB) {
        ListNode currA = headA;
        ListNode currB = headB;
        int lenA = 0;
        int lenB = 0;

        while(currA != null || currB != null){
            if(currA != null){
                lenA++;
                currA = currA.next;
            }
            if(currB != null){
                lenB++;
                currB = currB.next;
            }
        }

        return lenA - lenB;
    }

    // Tricky Solution: 🚀💪
    // Intuition: we have a difference in length of A and B.. so shorter list ptr has to cover that length to get 1st same node(intersection)
    // when smaller list ptr will come to tail, bigger list ptr will be behind by lengthDiff
    // so we move smaller list ptr to head of bigger list (to cover that difference till bigger ptr comes to head of smaller list head)
    // when bigger list ptr comes at smaller list head. both ptrs are aligned(same position from intersection)
    // so next time when they meet will be the intersection point;
    // if there is no intersection they both will meet at NULL.. so null will be returned;
    // TC: O(max(len1+len2)*2) -- worstcase
    // SC: O(1)
    public ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
        ListNode currA = headA;
        ListNode currB = headB;

        while (currA != currB) {
            currA = currA == null ? headB : currA.next;
            currB = currB == null ? headA : currB.next;
        }

        return currA;
    }

}
