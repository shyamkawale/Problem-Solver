package Patterns.Trees.Binary_Trees.BT8_ParentMap.Number_of_Good_Leaf_Nodes_Pairs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

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

    /**
     * Solution 1: Parent-map + graph traversal from each leaf
     * - Build parent pointers for every node.
     * - For each leaf, traverse its neighbors (left/right/parent) up to
     *   `distance` and count reachable leaf nodes.
     * - Simple and correct because tree paths are unique; may do extra work.
     */

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

    /*
    Tree DP: https://gemini.google.com/share/cdcfa9a832cc
    */
    /**
     * State Representation: For any given node, we only care about the distances
     * of all leaf nodes located in its subtree. Since the problem constraint for
     * distance is usually very small (for example, 1 <= distance <= 10), we can
     * use a fixed-size integer array of size distance + 1 to represent the
     * frequency of leaves at each distance.
     *
     * Merging Subtrees: At each internal node, we receive the distance
     * frequency arrays from its left and right children. If a leaf in the left
     * subtree is at distance i and a leaf in the right subtree is at distance j,
     * the total distance between them is i + j. If i + j <= distance, we
     * multiply their frequencies to count the number of valid pairs and add
     * this to our global total.
     *
     * Propagating Upwards: After counting the pairs, we prepare the distance
     * array for the current node's parent. Every leaf in the current node's
     * subtree will be one step further away from the parent. Therefore, we
     * shift the frequencies in our array by exactly one index (i.e.,
     * parentDist[i + 1] = leftDist[i] + rightDist[i]).
     */
    // TC: O(N * D^2) where N is the total number of nodes and D is the distance limit.
    // SC: O(H * D) where H is the height of the tree.
    private int goodPairsCount = 0;
    public int countPairs2(TreeNode root, int distance) {
        dfs(root, distance);
        return goodPairsCount;
    }

    private int[] dfs(TreeNode node, int distance) {
        // Array to store the frequency of leaves at distances from 0 to 'distance'
        int[] dist = new int[distance + 1];
        
        if (node == null) {
            return dist;
        }

        // If it's a leaf node, it is at distance 1 from its immediate parent
        if (node.left == null && node.right == null) {
            dist[1] = 1;
            return dist;
        }

        int[] leftDist = dfs(node.left, distance);
        int[] rightDist = dfs(node.right, distance);

        // Count valid pairs across the left and right subtrees
        for (int i = 1; i <= distance; i++) {
            for (int j = 1; j - i <= distance; j++) {
                if (leftDist[i] > 0 && rightDist[j] > 0) {
                    goodPairsCount += leftDist[i] * rightDist[j];
                }
            }
        }

        // Shift distances by 1 to pass up to the parent node.
        // We only care about distances up to 'distance - 1' because anything 
        // strictly greater will exceed the limit when combined at the ancestor level.
        for (int i = 1; i < distance; i++) {
            dist[i + 1] = leftDist[i] + rightDist[i];
        }

        return dist;
    }

}
