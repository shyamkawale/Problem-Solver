package Patterns.LinkedList.ll1_General;

public interface ILinkedListOperation<ListNode> {
    int findLength(ListNode head);

    // Similar to insert Node
    ListNode insertElementAtPos(ListNode head, int num, int pos);

    // Similar to insert Node
    ListNode insertElementAtLast(ListNode head, int num);

    ListNode removeElementAtPos(ListNode head, int pos);

    ListNode removeLastElement(ListNode head);

    ListNode removeElement(ListNode head, int num);

    // ListNode removeNode(ListNode head, ListNode nodeToRemove); // 

    ListNode removeKthNodeFromLast(ListNode head, int k);

    boolean isPalindrome(ListNode head);

    ListNode reverseBetween(ListNode head, int left, int right);
    
    ListNode rotateListRight(ListNode head, int k);

    ListNode rotateListLeft(ListNode head, int k);

    ListNode sortList(ListNode head);

    // ListNode swapNode(ListNode head, ListNode node1, ListNode node2); // 
}
