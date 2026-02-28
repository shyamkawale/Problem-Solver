package Patterns.Trees.Binary_Trees.BT8_ParentMap.Number_of_Good_Leaf_Nodes_Pairs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/

You are given the root of a binary tree and an integer distance. 
A pair of two different leaf nodes of a binary tree is said to be good if 
the length of the shortest path between them is less than or equal to distance.

Return the number of good leaf node pairs in the tree.

Input: root = [1,2,3,null,4], distance = 3
Output: 1
Explanation: The leaf nodes of the tree are 3 and 4 and the length of the shortest path between them is 3. 
This is the only good pair.

Input: root = [1,2,3,4,5,6,7], distance = 3
Output: 2
Explanation: The good pairs are [4,5] and [6,7] with shortest path = 2. 
The pair [4,6] is not good because the length of ther shortest path between them is 4.

Input: root = [7,1,4,6,null,5,3,null,null,null,null,null,2], distance = 3
Output: 1
Explanation: The only good pair is [2,5].
*/
public class Number_of_Good_Leaf_Nodes_Pairs extends ProblemSolver {
    public static void main(String[] args) {
        new Number_of_Good_Leaf_Nodes_Pairs().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int distance = DataConvertor.toInt(args[1]);

        int ans = countPairs(root, distance);
        System.out.println(ans);
    }

    int res = 0;
    Map<TreeNode, TreeNode> parentMap = new HashMap<>();

    public int countPairs(TreeNode root, int distance) {
        parentMap.put(root, null);
        constructParentMap(root);
        preOrder(root, distance);

        return res/2;
    }

    public void preOrder(TreeNode root, int distance) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            // dfs on leaf node
            dfs(root, 0, distance, new HashSet<TreeNode>());
        }

        preOrder(root.left, distance);
        preOrder(root.right, distance);
    }

    public void dfs(TreeNode root, int depth, int distance, Set<TreeNode> visited) {
        if (root == null || visited.contains(root)) {
            return;
        }

        visited.add(root);

        // leaf node
        if (root.left == null && root.right == null) {
            if (depth > 0 && depth <= distance) {
                res++;
            }
        }

        dfs(root.left, depth + 1, distance, visited);
        dfs(root.right, depth + 1, distance, visited);
        dfs(parentMap.get(root), depth + 1, distance, visited);
    }

    public void constructParentMap(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            parentMap.putIfAbsent(root.left, root);
        }
        constructParentMap(root.left);

        if (root.right != null) {
            parentMap.putIfAbsent(root.right, root);
        }
        constructParentMap(root.right);
    }

    public int countPairs2(TreeNode root, int distance) {
        return postOrder(root, distance)[11];
    }

    private int[] postOrder(TreeNode currentNode, int distance) {
        if (currentNode == null) return new int[12];
        else if (currentNode.left == null && currentNode.right == null) {
            int[] current = new int[12];
            // Leaf node's distance from itself is 0
            current[0] = 1;
            return current;
        }

        // Leaf node count for a given distance i
        int[] left = postOrder(currentNode.left, distance);
        int[] right = postOrder(currentNode.right, distance);

        int[] current = new int[12];

        // Combine the counts from the left and right subtree and shift by
        // +1 distance
        for (int i = 0; i < 10; i++) {
            current[i + 1] += left[i] + right[i];
        }

        // Initialize to total number of good leaf nodes pairs from left and right subtrees.
        current[11] += left[11] + right[11];

        // Iterate through possible leaf node distance pairs
        for (int d1 = 0; d1 <= distance; d1++) {
            for (int d2 = 0; d2 <= distance; d2++) {
                if (2 + d1 + d2 <= distance) {
                    // If the total path distance is less than the given distance limit,
                    // then add to the total number of good pairs
                    current[11] += left[d1] * right[d2];
                }
            }
        }

        return current;
    }

}
