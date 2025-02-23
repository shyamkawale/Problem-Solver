package Patterns.LinkedList.ll1_General.Doubly_LinkedList_Operations;

import Helpers.ProblemSolver;
import Helpers.DataStructure.DoublyLinkedList.DoublyListWrapper;
import Helpers.DataStructure.DoublyLinkedList.ListNode;
import Patterns.LinkedList.ll1_General.ILinkedListOperation;
import Patterns.LinkedList.ll1_General.Reverse_DLL.Reverse_DLL;

public class Doubly_LinkedList_Operations extends ProblemSolver implements ILinkedListOperation<ListNode>{

    Reverse_DLL reverseOps = new Reverse_DLL();

    public static void main(String[] args) {
        new Doubly_LinkedList_Operations().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        ListNode list = DoublyListWrapper.stringToListNode(args[0]);
        System.out.println("Original List:");
        DoublyListWrapper.prettyPrintLinkedList(list);

        int len = findLength(list);
        System.out.println("Length of List: " + len);

        list = reverseBetween(list, 2, 5);
        System.out.println("Reversed Between 1 and 4: ");
        DoublyListWrapper.prettyPrintLinkedList(list);

        list = insertElementAtPos(list, 7, 1);
        DoublyListWrapper.prettyPrintLinkedList(list);

        list = insertElementAtLast(list, 10);
        DoublyListWrapper.prettyPrintLinkedList(list);

        list = removeElementAtPos(list, 3);
        DoublyListWrapper.prettyPrintLinkedList(list);

        list = removeLastElement(list);
        DoublyListWrapper.prettyPrintLinkedList(list);

        insertElementAtPos(list, 7, 3);
        insertElementAtLast(list, 7);
        insertElementAtLast(list, 7);
        DoublyListWrapper.prettyPrintLinkedList(list);
        list = removeElement(list, 7);
        DoublyListWrapper.prettyPrintLinkedList(list);

        list = removeKthNodeFromLast(list, 3);
        DoublyListWrapper.prettyPrintLinkedList(list);

        boolean isPalindrome = isPalindrome(list);
        System.out.println("Is List Palindrome: " + isPalindrome);

        list = rotateListRight(list, 3);
        System.out.println("Rotated List 3 times Right: ");
        DoublyListWrapper.prettyPrintLinkedList(list);

        list = rotateListLeft(list, 3);
        System.out.println("Rotated List 3 times Left: ");
        DoublyListWrapper.prettyPrintLinkedList(list);
    }

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
        System.out.println("Insert "+num+" at position: "+pos);
        ListNode newNode = new ListNode(num);
        if (head == null) {
            return newNode;
        }

        if (pos <= 1) {
            newNode.next = head;
            head.prev = newNode;
            return newNode;
        }

        ListNode currNode = head;
        ListNode prevNode = null;
        for (int i=1; i<pos && currNode != null; i++) {
            prevNode = currNode;
            currNode = currNode.next;
        }

        prevNode.next = newNode;
        newNode.prev = prevNode;
        newNode.next = currNode;
        if(currNode != null){
            currNode.prev = newNode;
        }

        return head;
    }

    @Override
    public ListNode insertElementAtLast(ListNode head, int num) {
        System.out.println("Insert "+num+" at Last Position");
        ListNode newNode = new ListNode(num);

        if (head == null) {
            return newNode;
        }

        ListNode currNode = head;
        while (currNode.next != null) {
            currNode = currNode.next;
        }

        currNode.next = newNode;
        newNode.prev = currNode;
        return head;
    }

    @Override
    public ListNode removeElementAtPos(ListNode head, int pos) {
        System.out.println("Remove Element at position: "+pos);
        if (head == null || pos <= 0) {
            return head;
        }

        if(pos == 1){
            return head.next;
        }

        ListNode currNode = head;
        ListNode prevNode = null;
        for (int i=1; i<pos && currNode != null; i++) {
            prevNode = currNode;
            currNode = currNode.next;
        }

        if(currNode == null) { // nothing to remove
            return head;
        }

        ListNode nextNode = currNode.next;

        prevNode.next = nextNode;
        if (nextNode != null) {
            nextNode.prev = prevNode;
        }

        return head;
    }

    @Override
    public ListNode removeLastElement(ListNode head) {
        System.out.println("Remove Last Element: ");
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

    // segregate methods to removeNode(head, nodeToDelete)
    @Override
    public ListNode removeElement(ListNode head, int num) {
        System.out.println("Remove "+num+" from List");
        if (head == null) {
            return null;
        }

        ListNode currNode = head;
        ListNode prevNode = null;
        while (currNode != null) {
            if(currNode.val == num){
                ListNode nextNode = currNode.next;
                if(prevNode == null){
                    head = nextNode;
                }
                else{
                    prevNode.next = nextNode;
                }

                if(nextNode != null){
                    nextNode.prev = prevNode;
                }
            }
            else{
                prevNode = currNode;
            }
            currNode = currNode.next;
        }

        return head;
    }

    @Override
    public ListNode removeKthNodeFromLast(ListNode head, int k) {
        System.out.println("Remove "+k+"th node from last: ");
        if (head == null || k < 1) {
            return head;
        }
        ListNode currNode = head;
        int len = 0;
        while (currNode != null) {
            len++;
            currNode = currNode.next;
        }

        if (k > len ) {
            return head;
        }

        int kStart = len - k + 1;
        return removeElementAtPos(head, kStart);
    }

    @Override
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode tail = null;
        while (tail.next != null) {
            tail = tail.next;
        }

        ListNode first = head;
        ListNode last = tail;

        while (first != last && last.prev != first) {
            if (first.val != last.val) {
                return false;
            }
            first = first.next;
            last = last.prev;
        }

        return true;
    }
    
    @Override
    public ListNode rotateListRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        int len = 0;
        ListNode currNode = head;
        while (currNode != null) {
            len++;
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
        newHead.prev = null;
        newTail.next = null;
        while (currNode.next != null) {
            currNode = currNode.next;
        }

        currNode.next = head;
        head.prev = currNode;
        return newHead;
    }

    @Override
    public ListNode rotateListLeft(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        int len = 0;
        ListNode currNode = head;
        while (currNode != null) {
            len++;
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
        newHead.prev = null;
        newTail.next = null;
        while (currNode.next != null) {
            currNode = currNode.next;
        }

        currNode.next = head;
        head.prev = currNode;

        return newHead;
    }

    @Override
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode prevNode = null;
        ListNode newHeadNode = null;
        ListNode currNode = head;

        for (int i = 0; i < left - 1; i++) {
            prevNode = currNode;
            currNode = currNode.next;
        }

        ListNode firstNode = currNode;

        for (int i = left; i <= right; i++) {
            ListNode nextNode = currNode.next;
            currNode.next = newHeadNode;
            currNode.prev = null;
            if (newHeadNode != null) {
                newHeadNode.prev = currNode;
            }
            newHeadNode = currNode;
            currNode = nextNode;
        }

        if (prevNode != null) {
            prevNode.next = newHeadNode;
            if(newHeadNode != null){
                newHeadNode.prev = prevNode;
            }
        } else {
            head = newHeadNode;
        }
        firstNode.next = currNode;
        if(currNode != null){
            currNode.prev = firstNode;
        }

        return head;
    }

    @Override
    public ListNode sortList(ListNode head) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sortList'");
    }

}
