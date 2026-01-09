package Patterns.LinkedList.General.Singly_LinkedList_Operations;

import Helpers.ProblemSolver;
import Helpers.DataStructure.SinglyLinkedList.ListNode;
import Helpers.DataStructure.SinglyLinkedList.ListWrapper;
import Patterns.LinkedList.General.ILinkedListOperation;
import Patterns.LinkedList.General.Reverse_SLL.Reverse_SLL;

public class LinkedList_Operations extends ProblemSolver implements ILinkedListOperation<ListNode> {

    Reverse_SLL reverseOps = new Reverse_SLL();

    public static void main(String[] args) {
        new LinkedList_Operations().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode list = ListWrapper.stringToListNode(args[0]);
        System.out.println("Original List:");
        ListWrapper.prettyPrintLinkedList(list);

        int len = findLength(list);
        System.out.println("Length of List: " + len);

        list = reverseBetween(list, 2, 5);
        System.out.println("Reversed Between 2 and 5: ");
        ListWrapper.prettyPrintLinkedList(list);

        list = insertElementAtPos(list, 7, 1);
        System.out.println("Adding 7 at 2nd position: ");
        ListWrapper.prettyPrintLinkedList(list);

        list = insertElementAtLast(list, 10);
        System.out.println("Adding 10 at last Position: ");
        ListWrapper.prettyPrintLinkedList(list);

        list = removeElementAtPos(list, 8);
        System.out.println("Remove Element at 1st position: ");
        ListWrapper.prettyPrintLinkedList(list);

        list = removeLastElement(list);
        System.out.println("Remove Last Element: ");
        ListWrapper.prettyPrintLinkedList(list);

        list = insertElementAtLast(list, 1);
        ListWrapper.prettyPrintLinkedList(list);

        list = removeElement(list, 1);
        System.out.println("Remove 1 from List");
        ListWrapper.prettyPrintLinkedList(list);

        list = removeKthNodeFromLast(list, 3);
        ListWrapper.prettyPrintLinkedList(list);

        boolean isPalindrome = isPalindrome(list);
        System.out.println("Is List Palindrome: " + isPalindrome);

        list = rotateListRight(list, 3);
        System.out.println("Rotated List 3 times Right: ");
        ListWrapper.prettyPrintLinkedList(list);

        list = rotateListLeft(list, 3);
        System.out.println("Rotated List 3 times Left: ");
        ListWrapper.prettyPrintLinkedList(list);

        list = sortList(list);
        System.out.println("Sorted List: ");
        ListWrapper.prettyPrintLinkedList(list);
    }

    // TC: O(n)
    @Override
    public int findLength(ListNode head) {
        ListNode currNode = head;
        int len = 0;
        while (currNode != null) {
            len++;
            currNode = currNode.next;
        }
        return len;
    }

    @Override
    public ListNode insertElementAtPos(ListNode head, int num, int pos) {
        ListNode newNode = new ListNode(num);
        if (pos <= 1 || head == null) {
            newNode.next = head;
            return newNode;
        }

        ListNode currNode = head;
        ListNode prevNode = null;
        for (int p = 1; p < pos && currNode != null; p++) {
            prevNode = currNode;
            currNode = currNode.next;
        }

        prevNode.next = newNode;
        newNode.next = currNode;

        return head;
    }

    @Override
    public ListNode insertElementAtLast(ListNode head, int num) {
        ListNode newNode = new ListNode(num);
        if (head == null) {
            return newNode;
        }

        ListNode currNode = head;
        while (currNode.next != null) {
            currNode = currNode.next;
        }

        currNode.next = newNode;
        return head;
    }

    @Override
    public ListNode removeElementAtPos(ListNode head, int pos) {
        if (head == null || pos <= 0) {
            return head;
        }

        if (pos == 1) {
            return head.next;
        }

        ListNode currNode = head;
        ListNode prevNode = null;
        for (int i = 1; i < pos && currNode != null; i++) {
            prevNode = currNode;
            currNode = currNode.next;
        }

        if (currNode == null) { // nothing to remove
            return head;
        }

        prevNode.next = currNode.next;

        return head;
    }

    @Override
    public ListNode removeLastElement(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode currNode = head;
        ListNode prevNode = null;
        while (currNode.next != null) {
            prevNode = currNode;
            currNode = currNode.next;
        }

        prevNode.next = null;
        return head;
    }

    @Override
    public ListNode removeElement(ListNode head, int num) {
        if (head == null) {
            return head;
        }

        ListNode currNode = head;
        ListNode prevNode = null;
        while (currNode != null) {
            if (currNode.val == num) {
                if (prevNode == null) {
                    head = head.next;
                } else {
                    prevNode.next = currNode.next;
                }
                // break;
            }

            prevNode = currNode;
            currNode = currNode.next;
        }

        return head;
    }

    // BruteForce: Two Pass Solution using cal length and then remove len-k+1 th
    // element
    // Two Pointers method (One Pass Solution): TC: O(n)
    @Override
    public ListNode removeKthNodeFromLast(ListNode head, int k) {
        System.out.println("Remove " + k + "th node from last: ");
        if (head == null || k < 1) {
            return head;
        }

        ListNode prevNode = null;
        ListNode back = head;
        ListNode front = head;

        for (int i = 0; i < k; i++) {
            if (front == null) { // k greater than len (not possible)
                return head;
            }
            front = front.next;
        }

        if (front == null) {
            return head.next;
        }

        while (front != null) {
            prevNode = back;
            back = back.next;
            front = front.next;
        }

        prevNode.next = back.next;

        return head;
    }

    // Two Pass Solution using Stack TC: O(n+n), SC: O(n)
    // One Pass Solution(Hare and Tortoise + reverse): TC: O(n/2 + n/2 +n/2 + n/2),
    // SC: O(1)
    @Override
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode head1 = head;
        ListNode head2 = reverseOps.reverseList2(slow); // O(n/2)
        ListNode reversedHead = head2;

        while (head2 != null) {
            if (head1.val != head2.val) {
                reverseOps.reverseList2(reversedHead);
                return false;
            }
            head1 = head1.next;
            head2 = head2.next;
        }

        reverseOps.reverseList2(reversedHead);

        return true;
    }

    @Override
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode prevNode = null;
        ListNode currNode = head;

        // we have to do left-1 iteration to get currNode on pos = left
        for (int i = 0; i < left - 1; i++) {
            prevNode = currNode;
            currNode = currNode.next;
        }

        ListNode firstNode = currNode;

        // constructing new reversed linkedList;
        ListNode newHeadNode = null;
        for (int i = left; i <= right; i++) {
            ListNode nextNode = currNode.next;
            currNode.next = newHeadNode;
            newHeadNode = currNode;
            currNode = nextNode;
        }

        if (prevNode != null) {
            prevNode.next = newHeadNode;
        } else {
            head = newHeadNode;
        }
        firstNode.next = currNode;

        return head;
    }

    // Sol1: lopp k times and rotate one by one TC: O(n*k)
    // Optimized sol: O(n+k)
    @Override
    public ListNode rotateListRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        int len = 0;
        ListNode currNode = head;
        ListNode tail = null;
        while (currNode != null) {
            len++;
            tail = currNode;
            currNode = currNode.next;
        }

        k = k % len;
        if (k == 0) {
            return head;
        }

        currNode = head;
        ListNode newTail = null;
        for (int i = 0; i < len - k; i++) {
            newTail = currNode;
            currNode = currNode.next;
        }

        ListNode newHead = currNode;
        newTail.next = null;
        tail.next = head;
        return newHead;
    }

    @Override
    public ListNode rotateListLeft(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        int len = 0;
        ListNode currNode = head;
        ListNode tail = null;
        while (currNode != null) {
            len++;
            tail = currNode;
            currNode = currNode.next;
        }

        k = k % len;
        if (k == 0) {
            return head;
        }

        currNode = head;
        ListNode newTail = null;
        for (int i = 0; i < k; i++) {
            newTail = currNode;
            currNode = currNode.next;
        }

        ListNode newHead = currNode;
        newTail.next = null;
        tail.next = head;
        return newHead;
    }

    public ListNode swapNodesInPairs(ListNode head) {
        if(head == null) return head;

        ListNode first = head;
        ListNode second = head.next;

        while(first != null && second != null) {
            int temp = first.val;
            first.val = second.val;
            second.val = temp;

            first = first.next.next;
            if(first != null) {
                second = first.next;
            }
        }

        return head;
    }

    @Override
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        
        ListNode prevNode = null;
        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null) {
            prevNode = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prevNode.next = null;
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);
        return merge(l1, l2);
    }

    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode currNode = dummy;

        while(l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                currNode.next = new ListNode(l1.val);
                l1 = l1.next;
            }
            else {
                currNode.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            currNode = currNode.next;
        }

        if(l1 != null) currNode.next = l1;
        if(l2 != null) currNode.next = l2;

        return dummy.next;
    }
}
