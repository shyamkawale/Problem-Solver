# Trees

**Terminology:**

- root
- parent
- children
- leaves
- subtree
- ancestor
- internal nodes (non-leaf nodes)

---

## Depth and Height

```text
        A  ← Depth = 0, Height = 3
       / \
      B   C  ← Depth = 1, Height = 2
     / \   \
    D   E   F  ← Depth = 2, Height = 1
   /         \
  G           H  ← Depth = 3, Height = 0 (leaf)
```

- **Depth** - Distance from root (top-down). Root is unique, so depth is deterministic.
- **Height** - Distance from deepest leaf (bottom-up). Multiple leaves possible.

---

## Binary Tree

- A Tree whose elements have at most 2 children.


Types of Binary Tree

1. Full Binary Tree

    - Every node has 0 or 2 children(i.e no node has 1 child)
    - Always have odd number of nodes (i.e tree with even (2, 4, 6, ..) nodes can never be Full BT)
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

### Implementation Checklist

1. [ ] Inorder Traversal - Recursive + Iterative
2. [ ] Preorder Traversal - Recursive + Iterative
3. [ ] Postorder Traversal - Recursive + Iterative (Tricky)
4. [ ] LevelOrder Traversal - Iterative (with/without levels) + Recursive(DFS with depth)

### Problems

1. [Zigzag Traversal](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) ✅
2. [Boundary Traversal]() ✅

---

## Tree Reconstruction

| Combination | Result |
|-------------|--------|
| InOrder + PreOrder | ✅ Unique Tree |
| InOrder + PostOrder | ✅ Unique Tree |
| InOrder + LevelOrder | ✅ Unique Tree |
| PreOrder + PostOrder | ⚠️ Multiple possible (if missing nodes) |
| PreOrder/PostOrder + LevelOrder | ❌ Levelorder Can't determine left/right |

### Problems

1. [InOrder + PreOrder](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)
2. [InOrder + PostOrder](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)
3. [InOrder + LevelOrder]()
4. [PreOrder + PostOrder](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/)
5. [Create BinaryTree From Descriptions](https://leetcode.com/problems/create-binary-tree-from-descriptions/)

---

## Tree Operations

1. Insertion in a BinaryTree. #TODO 📅
2. Deletion in a BinaryTree. #TODO 📅

### Successor/Predecessor of a node in BT

1. InOrder Successor/ Predecessor of a node.
2. PreOrder Successor/ Predecessor of a node.
3. PostOrder Successor/ Predecessor of a node.
4. LevelOrder Successor/ Predecessor of a node.

### Basic Problems

1. [Flatten Binary Tree to Linked List](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/) - Inline modify tree to linkedlist
2. [Construct Inorder LinkedList](https://leetcode.com/problems/increasing-order-search-tree/) contruction of tree.

---

## DFS Problems

1. [Height/Depth of Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) ✅
2. [Smallest String Starting From Leaf](https://leetcode.com/problems/smallest-string-starting-from-leaf/) ✅ `DFS + leaf identification`
3. [Delete Leaves With Given Value](https://leetcode.com/problems/delete-leaves-with-a-given-value/) ✅
4. [Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/) ✅ ⭐ HARD
5. [Max Difference Between Node and Ancestor](https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/) ✅
6. [Odd Levels Of Binary Tree](https://leetcode.com/problems/reverse-odd-levels-of-binary-tree) ✅
7. [Distribute Coins in Binary Tree](https://leetcode.com/problems/distribute-coins-in-binary-tree/) ✅ `DFS + PostOrder` (Tricky)

---

## Binary Tree Views

1. [Left View](https://leetcode.com/problems/binary-tree-right-side-view/) ✅
2. [Right View](https://leetcode.com/problems/binary-tree-right-side-view/) ✅
3. [Top View](https://www.geeksforgeeks.org/problems/top-view-of-binary-tree/1) ✅
4. [Bottom View](https://www.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1) ✅

---

## Tree Validation

1. [Symmetric Trees](https://leetcode.com/problems/symmetric-tree/) ✅
2. [Check Completeness of BT](https://leetcode.com/problems/check-completeness-of-a-binary-tree/) ✅ [LevelOrder]
3. [Check if the Binary tree is height-balanced or not](https://leetcode.com/problems/balanced-binary-tree/) 💡 ✅

---

## Diameter and Width

1. [Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/) ✅
2. [Maximum Width of Binary Tree](https://leetcode.com/problems/maximum-width-of-binary-tree/) ✅ [`BFS + indexing`]

---

## Ancestor Problems

1. [Lowest Common Ancestor](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) ✅
2. [LCA of Deepest Leaves](https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/) ✅
3. [Step-By-Step Directions From a Binary Tree Node to Another](https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/) ✅ [`LCA + DFS`]

---

## ParentMap Technique

**Use when:** Need to traverse upward or treat tree as undirected graph.

1. [All Nodes Distance K](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/) 💡 ✅
2. [Find Distance in BinaryTree](https://leetcode.com/problems/find-distance-in-a-binary-tree/) ✅ [`ParentMap + LCA/BFS`]
3. [Good Leaf Nodes Pairs](https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/) [`ParentMap + DFS`]

```java
private void constructParentMap(TreeNode root, Map<TreeNode, TreeNode> parentMap) {
    if (root == null) return;
    if (root.left != null) parentMap.putIfAbsent(root.left, root);
    if (root.right != null) parentMap.putIfAbsent(root.right, root);
    constructParentMap(root.left, parentMap);
    constructParentMap(root.right, parentMap);
}
```

---

## Tree → Graph Conversion

**Use when:** BFS/DFS from any node (not just root), or multi-source traversal.

1. [Time for Binary Tree to be Infected](https://leetcode.com/problems/amount-of-time-for-binary-tree-to-be-infected/)
2. [Minimum Height Trees](https://leetcode.com/problems/minimum-height-trees)

```java
private void createUndirectedGraph(TreeNode root, TreeNode parent, Map<Integer, List<Integer>> adjList) {
    if (root == null) return;
    adjList.putIfAbsent(root.val, new ArrayList<>());
    if (parent != null) {
        adjList.putIfAbsent(parent.val, new ArrayList<>());
        adjList.get(root.val).add(parent.val);
        adjList.get(parent.val).add(root.val);
    }
    createUndirectedGraph(root.left, root, adjList);
    createUndirectedGraph(root.right, root, adjList);
}
```

---

## Miscellaneous

**Not a Literal Tree:**
- [Validate Binary Tree Nodes](https://leetcode.com/problems/validate-binary-tree-nodes/)

**DP on Trees:**
- [All Possible Full BinaryTrees](https://leetcode.com/problems/all-possible-full-binary-trees/) 💡 [`Memoization`]

### Easy Problems

1. [Reverse Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal-ii/) ✅ LevelOrder + reverse res
2. [Maximum Level Sum of a Binary Tree](https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/) ✅ LevelOrder + maintaining sum of each level
3. [Same Tree](https://leetcode.com/problems/same-tree/) ✅
4. [Min Depth of BT](https://leetcode.com/problems/minimum-depth-of-binary-tree/) ✅
5. [Even Odd Tree](https://leetcode.com/problems/even-odd-tree/) ✅ levelOrder - Try to do it with DFS ⏲️


---

## Indexing a Tree

| 0-based (common)       | 1-based (Heap/CP)      |
|------------------------|------------------------|
| root = 0               | root = 1               |
| left = 2*idx + 1       | left = 2*idx           |
| right = 2*idx + 2      | right = 2*idx + 1      |
| parent = (idx - 1) / 2 | parent = idx / 2       |

**⚠️ Edge Cases:**

1. **Integer Overflow** - Indices grow as 2^depth. Use `long` or normalize per level.
2. **Skewed Trees** -  A right-skewed tree of height h has only h nodes but rightmost index = 2^h - 1. Wastes space in array representation.
3. **Width of BT (LC 662)** -  Classic overflow trap! Solution: Normalize indices per level by subtracting `leftMostIdx` so each level starts from 0.
   ```java
   int normalizedIdx = idx - leftMostNodeIdx;
   leftChild = 2 * normalizedIdx;
   rightChild = 2 * normalizedIdx + 1;
   ```

---

## Key Techniques Summary

| Technique | When to Use |
|-----------|-------------|
| **LevelOrder with levels** | Views, width, level-based processing |
| **LevelOrder with nulls** | Use LinkedList, check completeness |
| **Recursive DFS with depth** | Views, level tracking without BFS |
| **Leaf Node Identification** | `node.left == null && node.right == null` |
| **Tree Indexing** | Width, array representation |
| **Horizontal Distance + Level in DFS** | Top/Bottom views |
| **Track min/max in branch** | Max difference ancestor problems |
| **Top-down DFS** | Root → Leaf, from root node at the top to all the way down to the leaf node(s) |
| **Bottom-up DFS** | Leaf → Root, recursively go to the leaf node(s) and backtrack up to the root node one by one up |
| **LCA** | Find meeting point of two nodes |
| **ParentMap + BFS/DFS** | Traverse up, distance between nodes |
| **Tree → Graph** | Multi-directional BFS, infection spread |

---

## Binary Search Tree (BST)

- Left subtree contains values smaller than root.
- Right subtree contains values greater than root.
- No duplicate values (in standard BST).
- Search, Insert, Delete in O(log n) time (if balanced).
- Used in databases and indexing.
- If duplicate values then we usually store frequency with Node.

```text
     10
    /  \
   5    15     Left < Root < Right
  / \     \    No duplicates (or store frequency)
 3   7    20   O(log n) if balanced
```

**Properties:**
- Inorder traversal → sorted (ascending)
- Unique BSTs with n keys = Catalan number Cₙ
    - C0 = 1, C(n+1) = C0Cn + C1C(n-1) + C2C(n-2) + ... + CnC0 = SUM(i=0 to i=n) CiC(n-i)

**Core Operations:** (all O(log n) if balanced)
- Search in BST (logn) ✅
- Insertion in BST ✅
- Deletion from BST ✅

### BST Reconstruction

| From | Possible? |
|------|-----------|
| PreOrder only | ✅ |
| PostOrder only | ✅ |
| LevelOrder only | ✅ |
| InOrder only | ❌ (always sorted, no structure info) |

### BST Problems

1. [LCA of BST](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/) ✅
2. Find Min/Max element ✅
3. Find Ceil/Floor ✅
4. [Find Kth largest/smallest in a BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/) ✅
5. [Validate BST](https://leetcode.com/problems/validate-binary-search-tree/) ✅

---

## TODO



