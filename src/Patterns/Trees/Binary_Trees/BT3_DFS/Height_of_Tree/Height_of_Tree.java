package Patterns.Trees.Binary_Trees.BT3_DFS.Height_of_Tree;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Height_of_Tree extends ProblemSolver {
    public static void main(String[] args) {
        new Height_of_Tree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        // TreeWrapper.prettyPrintTree(root);
        int depth = findHeight(root);
        System.out.println(depth);
    }

    // Height of the tree
    // TC: O(n), where n is the no. of nodes in tree
    // SC: O(d), where d is the depth of the tree
    private int findHeight(TreeNode root) {
        return helper(root, 0);
    }

    private int helper(TreeNode root, int height) {
        if(root == null){
            return 0;
        }

        int leftHeight = helper(root.left, height);
        int rightHeight = helper(root.right, height);

        return Math.max(leftHeight, rightHeight)+1;
    }
}
