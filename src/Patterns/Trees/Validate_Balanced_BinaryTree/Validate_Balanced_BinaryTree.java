package Patterns.Trees.Validate_Balanced_BinaryTree;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/balanced-binary-tree/
Given a binary tree, determine if it is height-balanced.

Sol1: Top-Down (Starts at root and checks height separately)- Repeated Height Calculation
Sol2: Bottom-Up (Calculates height while checking balance)
 */
public class Validate_Balanced_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new Validate_Balanced_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        // TreeWrapper.prettyPrintTree(root);
        boolean isBalanced1 = isBalanced1(root);
        boolean isBalanced2 = isBalanced2(root);
        System.out.println(isBalanced1 + " " + isBalanced2);
    }

    // TC: O(n*logn) for full binary tree, O(n*n) for skewed tree
    // SC: RecO(d)
    private boolean isBalanced1(TreeNode root) {
        if (root == null) {
            return true;
        }

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }

        return isBalanced1(root.left) && isBalanced1(root.right);
    }

    public int height(TreeNode curr) {
        if (curr == null) {
            return 0;
        }

        int leftHeight = height(curr.left);
        int rightHeight = height(curr.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Optimized Solution
    // TC: O(n)
    // SC: RecO(d)
    private boolean isBalanced2(TreeNode root) {
        return heightWithBalanceChecking(root) != -1;
    }

    private int heightWithBalanceChecking(TreeNode root) {
        if (root == null) {
            return 0;
        }
    
        int leftHeight = heightWithBalanceChecking(root.left);
        if (leftHeight == -1) return -1; // Unbalanced
    
        int rightHeight = heightWithBalanceChecking(root.right);
        if (rightHeight == -1) return -1; // Unbalanced
    
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // Mark as unbalanced
        }
    
        return Math.max(leftHeight, rightHeight) + 1;
    }

}
