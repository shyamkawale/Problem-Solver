package Patterns.LinkedList.LinkedList_Questions.lq1_Detect_Cycle_in_LinkedList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;

// BruteForce Approach: Using visitedNode set
// Hare and Tortoise Solution: O(n)
public class Detect_Cycle_in_LinkedList extends ProblemSolver {
    public static void main(String[] args) {
        new Detect_Cycle_in_LinkedList().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode head = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode fourth = new ListNode(4);
        ListNode fifth = new ListNode(5);

        // Create a loop from fifth to second
        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = fifth;
        // This creates a loop
        fifth.next = second; 


        ListNode list = head;

        boolean res1 = hasCycle1(list);
        boolean res2 = hasCycle2(list);

        ListNode startNode = startNodeOfCycle(list);

        int cycleLength1 = findLengthOfCycle1(list);
        int cycleLength2 = findLengthOfCycle2(list);
        System.out.println("HasCycle: " + res1 + " " + res2);
        System.out.println("StartNode: " + startNode.val);
        System.out.println("Cyclelength: " + cycleLength1 + " "+ cycleLength2);
    }

    /*
        ***************** Detect Cycle *********************
    */

    // TC: O(n)
    // SC: O(n)
    private boolean hasCycle1(ListNode head) {
        if (head == null) {
            return false;
        }

        Set<ListNode> visitedNodes = new HashSet<ListNode>();

        ListNode currNode = head;
        while (currNode != null) {
            if (visitedNodes.contains(currNode)) {
                return true;
            }
            visitedNodes.add(currNode);
            currNode = currNode.next;
        }
        return false;
    }

    // Hare and Tortoise
    // It works because in cycle we are slow by 1 and fast by 2..
    // so distance between fast and slow is decreasing(when there is cycle) by 1 and ultimatly become 0
    // and fast == slow (if we are in loop)

    // Time Complexity: O(N), where N is the number of nodes in the linked list.
    // This is because in the worst-case scenario, the fast pointer, which moves
    // quicker, will either reach the end of the list (in case of no loop) or meet
    // the slow pointer (in case of a loop) in a linear time relative to the length
    // of the list.
    // The key insight into why this is O(N) and not something slower is that each
    // step of the algorithm reduces the distance between the fast and slow pointers
    // (when they are in the loop) by one.
    // Therefore, the maximum number of steps needed for them to meet is
    // proportional to the number of nodes in the list.
    private boolean hasCycle2(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /*
        **************  Finding STARTNODE of the Cycle  ****************
    */
    // Approach 1: using visitedNode set: TC: O(n) SC: O(n)
    // Hare and Tortoise: TC: O(n+n) SC: O(1)
    // Mathematical and Diagram proof (tip: calculate distances)
    private ListNode startNodeOfCycle(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (fast == slow) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return fast;
            }
        }

        return null;
    }

    /*
        **************  Finding LENGTH of the Cycle  ****************
    */
    // Approach 1: using visitedNode Map and position: 
    // TC: O(n) SC: O(n)
    private int findLengthOfCycle1(ListNode head) {
        if(head == null){
            return -1;
        }

        Map<ListNode, Integer> map = new HashMap<>();

        ListNode currNode = head;

        int pos = 1;
        while(currNode != null){
            if(map.containsKey(currNode)){
                return pos - map.get(currNode);
            }
            map.put(currNode, pos);
            pos++;
            currNode = currNode.next;
        }

        return -1;
    }

    // Hare and Tortoise method: after finding loop just traverse and 1 pointer till it meets again and cal distance
    // TC: O(~>n)
    // SC: O(1)
    private int findLengthOfCycle2(ListNode head) {
        if (head == null) {
            return -1;
        }

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (fast == slow) {
                slow = slow.next;
                int dist = 1;
                while (slow != fast) {
                    dist++;
                    slow = slow.next;
                }
                return dist;
            }
        }

        return -1;
    }
}
