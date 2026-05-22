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
        int depth1 = findHeight1(root);
        int depth2 = findHeight2(root);
        System.out.println(depth1 + " " + depth2);
    }

    // Height of the tree
    // TC: O(n), where n is the no. of nodes in tree
    // SC: O(d), where d is the depth of the tree
    // counting starts from root node (depth approach)
    private int findHeight1(TreeNode root) {
        return helper(root, 0);
    }

    private int helper(TreeNode root, int depth) {
        if(root == null){
            return depth;
        }

        int leftHeight = helper(root.left, depth+1);
        int rightHeight = helper(root.right, depth+1);

        return Math.max(leftHeight, rightHeight);
    }

    // counting starts from leaf nodes..(height approach)
    public int findHeight2(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int leftDepth = findHeight2(root.left);
        int rightDepth = findHeight2(root.right);

        return Math.max(leftDepth, rightDepth)+1;
    }
}
