package Patterns.LinkedList.LinkedList_Questions.lq13_Copy_List_with_Random_Pointer;

import java.util.HashMap;
import java.util.Map;

import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/copy-list-with-random-pointer/

A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null.
Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node has its value set to the value of its corresponding original node. 
Both the next and random pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.
For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two nodes x and y in the copied list, x.random --> y.
Return the head of the copied linked list.

The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:

Your code will only be given the head of the original linked list.

Example 1:
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

Example 2:
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]

Example 3:
Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]

#### In Simple words we need to create exact *DEEP COPY** of given linkedlist (containing next and random pointers)
*/
public class Copy_List_with_Random_Pointer extends ProblemSolver {
    public static void main(String[] args) {
        new Copy_List_with_Random_Pointer().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        // for actual input, output visit problem on leetcode..
        Node head = new Node(0);

        Node res = copyRandomList(head);
        System.out.println(res);
    }

    public Node copyRandomList(Node head) {
        Node newHead = new Node(-1);
        Node currNewNode = newHead;
        Node currOldNode = head;

        // oldNode -> newNode
        Map<Node, Node> map = new HashMap<>();
        map.put(null, null);

        while (currOldNode != null) {
            Node newNode = new Node(currOldNode.val);

            map.put(currOldNode, newNode);

            currNewNode.next = newNode;
            currNewNode = currNewNode.next;
            currOldNode = currOldNode.next;
        }

        for (Map.Entry<Node, Node> entry : map.entrySet()) {
            Node oldNode = entry.getKey();
            Node newNode = entry.getValue();

            if (oldNode == null && newNode == null)
                continue;

            // little hard to get (look again🔥🔥)
            newNode.random = map.get(oldNode.random);
        }

        return newHead.next;
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

}
