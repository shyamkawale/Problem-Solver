package Patterns.Trees.Binary_Trees.BT8_ParentMap.All_Nodes_Distance_K_in_BinaryTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/description/

Given the root of a binary tree, the value of a target node target, and an integer k, 
return an array of the values of all nodes that have a distance k from the target node.

You can return the answer in any order.

Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
Output: [7,4,1]
Explanation: The nodes that are a distance 2 from the target node (with value 5) have values 7, 4, and 1.

Input: root = [1], target = 1, k = 3
Output: []

Parent + BFS
*/
public class All_Nodes_Distance_K_in_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new All_Nodes_Distance_K_in_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int target = DataConvertor.toInt(args[1]);
        int k = DataConvertor.toInt(args[2]);

        TreeNode targetNode = getNode(root, target);

        TreeWrapper.prettyPrintTree(root);
        List<Integer> res = distanceK(root, targetNode, k);
        System.out.println(res);
    }

    private TreeNode getNode(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        if (root.val == target) {
            return root;
        }

        TreeNode leftResult = getNode(root.left, target);
        if (leftResult != null) {
            return leftResult;
        }
        return getNode(root.right, target);
    }

    // TC: O(min(dist, max leafnode dist from target)*3) => in worst case: O(n) as
    // we might end up traversing all nodes.
    // SC: O(2n) + O(n) + O(n)
    private List<Integer> distanceK(TreeNode root, TreeNode targetNode, int k) {
        if (root == null || targetNode == null) {
            return new ArrayList<>();
        }
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        constructParentMap(root, parentMap);

        Queue<TreeNode> queue = new ArrayDeque<>();
        Set<TreeNode> visited = new HashSet<>();
        int dist = 0;

        queue.offer(targetNode);
        visited.add(targetNode);

        while (dist != k && !queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.poll();

                // checking parentNode..
                if (parentMap.containsKey(currNode)) {
                    TreeNode parentNode = parentMap.get(currNode);
                    if (!visited.contains(parentNode)) {
                        queue.offer(parentNode);
                        visited.add(parentNode);
                    }
                }

                // checking leftNode..
                if (currNode.left != null && !visited.contains(currNode.left)) {
                    queue.offer(currNode.left);
                    visited.add(currNode.left);
                }

                // checking rightNode..
                if (currNode.right != null && !visited.contains(currNode.right)) {
                    queue.offer(currNode.right);
                    visited.add(currNode.right);
                }
            }
            dist++;
        }

        // return queue.stream()
        //         .map((currNode) -> currNode.val)
        //         .toList();

        // this is more efficient as 
        // 1. it avoids overhead of creating a stream and allocating intermediate objects.
        // 2. no overhead of extra boxing/unboxing, lambda function
        // 3. queue gets empty so space is reclaimed..
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            res.add(queue.poll().val);
        }

        return res;
    }

    // TC: O(n), where n is the no of nodes in tree
    // SC: RecO(h), where h is the height of tree
    private void constructParentMap(TreeNode root, Map<TreeNode, TreeNode> parentMap) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            parentMap.putIfAbsent(root.left, root);
        }
        constructParentMap(root.left, parentMap);

        if (root.right != null) {
            parentMap.putIfAbsent(root.right, root);
        }
        constructParentMap(root.right, parentMap);
    }
}
