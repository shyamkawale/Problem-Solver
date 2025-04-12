# Trees

- root
- parent
- children
- leaves
- subtree
- ancestor
- internal nodes (non-leaf nodes)

### Depth and Height

```text
        A  <-- Depth = 0, Height = 3
       / \
      B   C  <-- Depth = 1, Height = 2
     / \   \
    D   E   F  <-- Depth = 2, Height = 1
   /         \
  G           H  <-- Depth = 3, Height = 0 (leaf)

```

- Depth is more important in Trees.. as it starts from root which is unique.
- Height starts from lowermost leafnode.. which we cannot predict at start and can be multiple.

## Binary Tree

- A Tree whose elements have at most 2 children.

Types of Binary Tree

1. Full Binary Tree

    - Every node has 0 or 2 children(i.e no node has 1 child)
    - No. of Leaf nodes = internal nodes + 1
    - Height = at most log2(n+1)-1

    ```markdown
        1
       / \
      2   3
     / \
    4   5
    ```

2. Complete Binary Tree

    - All levels except the last are completely filled.
    - Last level is filled from left to right
    - Efficiently implemented as an array-based representation (used in Heaps).
    - Number of nodes at height h = 2^h - 1.

    ```markdown
         1
       /  \
      2    3
     / \   /
    4   5 6
    ```

3. Perfect Binary Tree

    - All leaf nodes are at the same level.
    - Number of nodes = 2^h - 1, where h is the height.

    ```markdown
         1
       /  \
      2    3
     / \  / \
    4   5 6  7
    ```

4. Balanced Binary Tree

    - The height difference between left and right subtrees of every node is at most 1.
    - Reduces search time complexity to O(log n).
    - Used in AVL Trees, Red-Black Trees, and B-Trees.

    ```markdown
         10
       /   \
      5    15
     / \   / \
    2   8 12  20
    ```

5. Degenerate (Skewed) Binary Tree

    - Every Parent nodes has only 1 child.
    - Resembles a LinkedList.
    Worst-case time complexity for search = O(n).

    ```markdown
        1    1         1
       /      \       /
      2        2     2
     /        /       \
    3        3         3
    ```

6. Binary Searched Tree (BST)

    - Left subtree contains values smaller than root.
    - Right subtree contains values greater than root.
    - No duplicate values (in standard BST).
    - Search, Insert, Delete in O(log n) time (if balanced).
    - Used in databases and indexing.

    ```markdown
        10
       /  \
      5    15
     / \     \
    3   7     20
    ```

7. AVL Tree (Self-Balancing BST)

8. Red-Black Tree (Self-Balancing BST)

9. Threaded Binary Tree

10. B-Trees (Generalized BST)

## Traversal of Binary Trees

1. Depth-First Search Traversal (DFS)

    - Inorder Traversal (Left Root Right)
    - Preorder Traversal (Root Left Right)
    - Postorder Traversal (Left Right Root)

2. Breadth-First Search Traversal (BFS)

    - Level Order Traversal

Coding perspective

- Inorder Traversal Recursive + Iterative Approach
- PreOrder Traversal Recursive + Iterative Approach
- PostOrder Traversal Recursive + Iterative Approach

- LevelOrder Traversal Iterative Approach (with & without levels)
- LevelOrder Traversal Recursive(DFS) Approach

Construction Of Trees

Construct Tree Using

1. InOrder + PreOrder // Unique Tree
2. InOrder + PostOrder // Unique Tree
3. InOrder + LevelOrder // Unique Tree
4. PreOrder + PostOrder // Multiple trees can be formed if there are missing nodes. (only full binary tree) ?? #TODO 📅
5. PreOrder + LevelOrder // Level Order does not tell which nodes are left or right children.
6. PostOrder + LevelOrder // Level Order does not tell which nodes are left or right children.

Operations on Binary Tree

1. Insertion in a BinaryTree. #TODO 📅
2. Deletion in a BinaryTree. #TODO 📅

Basic Questions

1. [Height/Depth of Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) ✅
4. [Same Tree](https://leetcode.com/problems/same-tree/)


5. [Symmetric Trees](https://leetcode.com/problems/symmetric-tree/) ✅
18. [Check Completeness of a Binary Tree](https://leetcode.com/problems/check-completeness-of-a-binary-tree/) levelorder ✅
2. [Check if the Binary tree is height-balanced or not](https://leetcode.com/problems/balanced-binary-tree/) 💡 ✅

3. [Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/)
14. [Maximum Width of Binary Tree](https://leetcode.com/problems/maximum-width-of-binary-tree/)

7. [Left & Right View](https://leetcode.com/problems/binary-tree-right-side-view/) ✅
8. [Top View](https://www.geeksforgeeks.org/problems/top-view-of-binary-tree/1) ✅
9. [Bottom View](https://www.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1) ✅

6. [Zigzag Traversal of Tree](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) ✅
11. [Reverse Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal-ii/) ✅ LevelOrder + reverse res

10. [Construct Inorder LinkedList](https://leetcode.com/problems/increasing-order-search-tree/) Construction of Tree
12. [Maximum Difference Between Node and Ancestor](https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/)
13. [All Nodes Distance K in Binary Tree](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/)
15. [Lowest Common Ancestor of a Binary Tree](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) ✅
16. [Maximum Level Sum of a Binary Tree](https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/)
17. [Smallest String Starting From Leaf](https://leetcode.com/problems/smallest-string-starting-from-leaf/) leaf node identification

19. [Flatten Binary Tree to Linked List](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/) Inline modifying existing tree to linkedList
20. [All Posible Full BinaryTrees]()

Techniques

- LevelOrder
  - with different levels
  - nulls(Use LinkedList)

- Identify a leaf Node

- Indexing a Binary Tree

- Horizontal Distance, Level in DFS

- Construction of Tree ??

- Top-down -> from root node at the top to all the way down to the leaf node(s)
- Bottom-up -> recursively go to the leaf node(s) and backtrack up to the root node one by one

## Binary Search Tree (BST)

- Left subtree contains values smaller than root.
- Right subtree contains values greater than root.
- No duplicate values (in standard BST).
- Search, Insert, Delete in O(log n) time (if balanced).
- Used in databases and indexing.
- If duplicate values then we usually store frequency with Node.

```markdown
     10
    /  \
  5    15
 / \     \
3   7     20
```

- Search in BST (logn)
- Insertion in BST
- Deletion from BST

Reconstruction of BST

1. Only with PreOrder
2. Only with PostOrder
3. Only with LevelOrder

- Not possible with InOrder?? why?

- InOrder traversal produces output in ascending Order
- No of unique BST's with n distinct keys is Catalan number (Cn) ?? why?
  - C0 = 1, C(n+1) = C0Cn + C1C(n-1) + C2C(n-2) + ... + CnC0 = SUM(i=0 to i=n) CiC(n-i)

Problems

1. Find Min/Max element of BST
2. Ceil/Floor in a BST
