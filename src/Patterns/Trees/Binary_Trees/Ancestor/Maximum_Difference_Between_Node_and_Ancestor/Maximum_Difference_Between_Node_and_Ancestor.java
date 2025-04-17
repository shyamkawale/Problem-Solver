package Patterns.Trees.Binary_Trees.Ancestor.Maximum_Difference_Between_Node_and_Ancestor;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Maximum_Difference_Between_Node_and_Ancestor extends ProblemSolver {
    public static void main(String[] args) {
        new Maximum_Difference_Between_Node_and_Ancestor().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        int res = maxAncestorDiff(root);
        System.out.println(res);
    }

    int res = 0;
    public int maxAncestorDiff(TreeNode root) {
        helper(root, root.val, root.val);
        return res;
    }

    public void helper(TreeNode root, int max, int min){
        if(root == null){
            return;
        }

        max = Math.max(max, root.val);
        min = Math.min(min, root.val);
        res = Math.max(res, Math.abs(max-min));

        helper(root.left, max, min);
        helper(root.right, max, min);
    }

}
