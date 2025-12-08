package Patterns.Trees.Binary_Trees.Ancestor.Lowest_Common_Ancestor_of_Deepest_Leaves;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/description/
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
