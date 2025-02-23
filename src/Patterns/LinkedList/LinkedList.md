# LinkedList

## Some Pointers while working with LinkedList

- Never tamper the head..
- In linkedlist always use 1-indexed bcz LinkedList doesn't have index.
- Think of edge cases first and then build algo on it.
- In SLL, if next is null (could be Tail)
- In DLL, if prev is null (could be Head) and if next is null (could be Tail)
- In DLL mark next in BLUE normal arrow, prev in BLACK colored arrow.

## Terminologies used for naming

```java
- prevNode
- currNode
- nextNode

- posNode

- head
- newHead
- tail
- newTail
- first
- last

- temp

- fast
- slow
```

## General Problems

1. LinkedList Operations (Both SLL and DLL)
    1. Find Length
    2. Insert Element At Position
    3. Insert Last Element
    4. Remove Element At Position
    5. Remove Last Element
    6. Remove Node (#TODO 📅)
    7. [Remove Kth Node From Last](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)
    8. [Check if Palindrome](https://leetcode.com/problems/palindrome-linked-list/)
    9. Reverse List Between Left and Right (Both Included)
    10. [Reverse LinkedList in Group](https://leetcode.com/problems/reverse-nodes-in-k-group/) #TODO 📅
    11. Rotate List K times Right
    12. Rotate List K times Left
    13. Swap 2 nodes (#TODO 📅)
    14. [Swap nodes in Pair](https://leetcode.com/problems/swap-nodes-in-pairs/) #TODO 📅 ??
    15. [Sort List](https://leetcode.com/problems/sort-list/) (#TODO 📅)
    16. Sort a LL of 0's 1's and 2's by changing links #TODO 📅 ??
2. Reverse Singly LinkedList
3. Reverse Doubly LinkedList
4. [Find Middle Element in LinkedList](https://leetcode.com/problems/middle-of-the-linked-list/)

## Other

1. [Detect Cycle in LinkedList](https://leetcode.com/problems/linked-list-cycle/)
2. [Intersection of Two LinkedList](https://leetcode.com/problems/intersection-of-two-linked-lists/)
3. Odd Even Value Segregation in LinkedList
4. [Add Two Numbers](https://leetcode.com/problems/add-two-numbers-ii/) #TODO 📅
5. [Add 1 to LinkedList Number](https://www.geeksforgeeks.org/problems/add-1-to-a-number-represented-as-linked-list/1)
6. [Insert Greatest Common Divisor in LinkedList](https://leetcode.com/problems/insert-greatest-common-divisors-in-linked-list/)
7. [Split LinkedList in K Parts](https://leetcode.com/problems/split-linked-list-in-parts/)

8. [Remove Duplicates from Sorted List](https://leetcode.com/problems/remove-duplicates-from-sorted-list/) #TODO 📅
9. [Remove Duplicates from Sorted List II](https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/) #TODO 📅
10. [Remove Duplicate from Sorted DLL](https://www.geeksforgeeks.org/problems/remove-duplicates-from-a-sorted-doubly-linked-list/1) #TODO 📅
11. [Find pairs with given sum in doubly linked list](https://www.geeksforgeeks.org/problems/find-pairs-with-given-sum-in-doubly-linked-list/1) #TODO 📅
12. [Flattening a Linked List](https://www.geeksforgeeks.org/problems/flattening-a-linked-list/1) #TODO 📅
13. [Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/) #TODO 📅
14. [Reorder List](https://leetcode.com/problems/reorder-list/) #TODO 📅

## Other Concepts

1. [Merge Two Sorted LinkedList](https://leetcode.com/problems/merge-two-sorted-lists/) #TODO 📅
2. [Merge K Sorted LinkedList](https://leetcode.com/problems/merge-k-sorted-lists/) #TODO 📅
3. [Next Greater Node in LinkedList](https://leetcode.com/problems/next-greater-node-in-linked-list) #TODO 📅

## Techniques

1. Hare and Tortoise Method(Slow and fast pointer)
2. Recursion in LinkedList
3. DummyNode Concept
4. Length Difference for finding intersection of LinkedList

## Traversal techniques in LinkedList

```java
// base condition
if(head == null){
    // do something
}
```

pos = 1 indexed (as it is not index)

```java
// Traversal 1
// currNode will always be node at pos(if pos <= len otherwise tail).
// currNode will never be null BUT 🌟 prevNode can be null
// if pos = 1 : currNode=head,          prevNode=null,            (0 iteration)
// if pos<len : currNode=posNode,       prevNode=prev of posNode, (pos-1 iterations)
// if pos=len : currNode=tail(posNode), prevNode=prev of posNode, (pos-1=len-1 iterations)
// if pos>len : currNode=tail,          prevNode=prev of tail,    (len-1 iterations)
ListNode currNode = head;
ListNode prevNode = null;
for (int p=1; p<pos && currNode.next != null; p++) {
    prevNode = currNode;
    currNode = currNode.next;
}

// Traversal 1.1
// currNode will always be node at pos( if pos <= len otherwise null).
// BUT 🌟 currNode can be null and 🌟 prevNode can be null
// if pos = 1 : currNode=head,          prevNode=null,            (0 iteration)
// if pos<len : currNode=posNode,       prevNode=prev of posNode, (pos-1 iterations)
// if pos=len : currNode=tail(posNode), prevNode=prev of posNode, (pos-1=len-1 iterations)
// if pos>len : currNode=null,          prevNode=prev of tail,    (len-1 iterations)
ListNode currNode = head;
ListNode prevNode = null;
for (int p=1; p<pos && currNode != null; p++) {
    prevNode = currNode;
    currNode = currNode.next;
}

// Traversal 2
// currNode will generally be node at pos-1 (i.e prev of posNode). BUT 🌟 not for pos = 1;
// currNode will never be null
// if pos = 1 : currNode=head,              (0 iteration)
// if pos<len : currNode=prev of posNode,   (pos-2 iterations)
// if pos=len : currNode=prev of tail,      (pos-2=len-2 iterations)
// if pos>len : currNode=tail,              (len-1 iterations)
ListNode currNode = head;
for (int p=1; p<pos-1 && currNode.next != null; p++) {
    currNode = currNode.next;
}

// Traversal 3 (when we have to go till last Element)
// currNode will always be tail. (len-1 iterations)
// currNode will never be null
ListNode currNode = head;
while(currNode.next != null){
    currNode = currNode.next;
}

// At last currNode will be tail (inside while we will have currNode -> head to prev of tail)
// Traversal 3 (when we have to go till last Element)
// currNode always will be tail. (len-1 iterations)
// (len=1) : currNode=tail, prevNode=null
// (len>1) : currNode=tail, prevNode= prev of tail
ListNode currNode = head;
ListNode prevNode = null;
while(currNode.next != null){
    prevNode = currNode;
    currNode = currNode.next;
}

// At last currNode will be null (inside while we will have curNode -> head to tail)
// Traversal 3 (when we have to go till null)
// currNode will be at null. (len iterations)
ListNode currNode = head;
while(currNode != null){
    currNode = currNode.next;
}
```
