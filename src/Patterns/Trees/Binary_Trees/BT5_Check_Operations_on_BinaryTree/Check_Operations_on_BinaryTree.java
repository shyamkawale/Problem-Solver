package Patterns.Trees.Binary_Trees.BT5_Check_Operations_on_BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Check_Operations_on_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new Check_Operations_on_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        TreeWrapper.prettyPrintTree(root);
        boolean isSymmetric = isSymmetric(root);
        System.out.println("isSymmetric: " + isSymmetric);

        boolean isComplete = isComplete(root);
        System.out.println("isComplete: " + isComplete);

        /*
        https://leetcode.com/problems/balanced-binary-tree/
        Given a binary tree, determine if it is height-balanced.

        Sol1: Top-Down (Starts at root and checks height separately)- Repeated Height Calculation
        Sol2: Bottom-Up (Calculates height while checking balance)
        */
        boolean isHeightBalanced1 = isHeightBalanced1(root);
        boolean isHeightBalanced2 = isHeightBalanced2(root);
        System.out.println("isHeightBalanced: " + isHeightBalanced1 + " " + isHeightBalanced2);
    }

    private boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return helper(root.left, root.right);
    }

    private boolean helper(TreeNode leftSubTree, TreeNode rightSubTree) {
        if(leftSubTree == null && rightSubTree == null){
            return true;
        }
        else if(leftSubTree == null || rightSubTree == null){
            return false;
        }
        else if(leftSubTree.val != rightSubTree.val){
            return false;
        }

        return helper(leftSubTree.left, rightSubTree.right) && helper(leftSubTree.right, rightSubTree.left);
    }

    private boolean isComplete(TreeNode root) {
        if(root == null){
            return true;
        }
        boolean nullEncountered = false;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            TreeNode polledNode = queue.poll();
            
            if(polledNode == null){
                nullEncountered = true;
                continue;
            }
            
            // If null has been encountered previously, and now we see a non-null node
            if(nullEncountered){
                return false;
            }
            queue.offer(polledNode.left);
            queue.offer(polledNode.right);
        }

        return true;
    }

    // TC: O(n*logn) for full binary tree, O(n*n) for skewed tree
    // SC: RecO(d)
    private boolean isHeightBalanced1(TreeNode root) {
        if (root == null) {
            return true;
        }

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }

        return isHeightBalanced1(root.left) && isHeightBalanced1(root.right);
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
    private boolean isHeightBalanced2(TreeNode root) {
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
