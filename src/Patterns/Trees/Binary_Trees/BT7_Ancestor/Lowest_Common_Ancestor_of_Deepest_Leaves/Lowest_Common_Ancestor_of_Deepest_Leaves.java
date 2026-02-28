package Patterns.Trees.Binary_Trees.BT7_Ancestor.Lowest_Common_Ancestor_of_Deepest_Leaves;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/description/

Given the root of a binary tree, return the lowest common ancestor of its deepest leaves.

Recall that:

The node of a binary tree is a leaf if and only if it has no children
The depth of the root of the tree is 0. if the depth of a node is d, the depth of each of its children is d + 1.
The lowest common ancestor of a set S of nodes, is the node A with the largest depth such that every node in S is in the subtree with root A.

Input: root = [3,5,1,6,2,0,8,null,null,7,4]
Output: 2
Explanation: 7 and 4 are deepest nodes whose lca is 2

Input: root = [1]
Output: [1]
Explanation: The root is the deepest node in the tree, and it's the lca of itself.

Input: root = [0,1,3,null,2]
Output: [2]
Explanation: The deepest leaf node in the tree is 2, the lca of one node is itself.
 */
public class Lowest_Common_Ancestor_of_Deepest_Leaves extends ProblemSolver {

    public static void main(String[] args) {
        new Lowest_Common_Ancestor_of_Deepest_Leaves().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        // TreeWrapper.prettyPrintTree(root);
        TreeNode lca = lcaDeepestLeaves(root);
        TreeWrapper.prettyPrintTree(lca);
    }

    TreeNode lca = null;
    int maxDepth = 0;

    public TreeNode lcaDeepestLeaves(TreeNode root) {
        helper(root, 0);
        return lca;
    }

    public int helper(TreeNode root, int depth) {
        maxDepth = Math.max(depth, maxDepth);
        if (root == null) {
            return depth;
        }

        int leftDepth = helper(root.left, depth + 1);
        int rightDepth = helper(root.right, depth + 1);

        if (leftDepth == maxDepth && rightDepth == maxDepth) {
            lca = root;
        }

        return Math.max(leftDepth, rightDepth);
    }

}
