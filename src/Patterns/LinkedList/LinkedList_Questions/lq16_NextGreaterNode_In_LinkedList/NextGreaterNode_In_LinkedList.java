package Patterns.LinkedList.LinkedList_Questions.lq16_NextGreaterNode_In_LinkedList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;

public class NextGreaterNode_In_LinkedList extends ProblemSolver {

    public static void main(String[] args) {
        new NextGreaterNode_In_LinkedList().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode head = ListWrapper.stringToListNode(args[0]);

        int[] res1 = nextLargerNodes_2Pass(head);
        int[] res2 = nextLargerNodes_1Pass(head);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2));
    }

    // 2 Pass but Intuitive, Backbard traversal to find Next Greater Element
    // TC: O(n+n)
    // SC: O(n+n)
    public int[] nextLargerNodes_2Pass(ListNode head) {
        ListNode currNode = head;

        List<Integer> nodes = new ArrayList<>();
        while(currNode != null) {
            nodes.add(currNode.val);
            currNode = currNode.next;
        }

        int len = nodes.size();
        int[] res = new int[len];

        Deque<Integer> stack = new ArrayDeque<>();

        for(int i=len-1; i>=0; i--) {
            while(!stack.isEmpty() && stack.peek() <= nodes.get(i)) {
                stack.pop();
            }

            res[i] = stack.isEmpty() ? 0 : stack.peek();

            stack.push(nodes.get(i));
        }

        return res;
    }

    // Forward Pass for finding Next Greater Element (non-intuitive)
    // TC: O(n+n)
    // SC: O(2n)
    public int[] nextLargerNodes_1Pass(ListNode head) {
        int len = 0;
        ListNode currNode = head;
        while (currNode != null) {
            currNode = currNode.next;
            len++;
        }

        int[] res = new int[len];
        // 0 -> node, 1 -> index of node
        Deque<int[]> stack = new ArrayDeque<>();

        currNode = head;
        int idx = 0;
        while(currNode != null) {
            while(!stack.isEmpty() && stack.peek()[0] < currNode.val) {
                int[] node = stack.pop();
                res[node[1]] = currNode.val;
            }

            stack.push(new int[]{currNode.val, idx});

            currNode = currNode.next;
            idx++;
        }

        return res;
    }

}
