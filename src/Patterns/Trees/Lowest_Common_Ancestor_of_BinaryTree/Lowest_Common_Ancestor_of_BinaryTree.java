package Patterns.Trees.Lowest_Common_Ancestor_of_BinaryTree;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Lowest_Common_Ancestor_of_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new Lowest_Common_Ancestor_of_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        TreeNode p = root.left;
        TreeNode q = root.right;

        TreeNode res = lowestCommonAncestor(root, p, q);
        TreeWrapper.prettyPrintTree(res);
    }
    TreeNode ans;

    // TC: O(n)
    // SC: RecO(d)
    private TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        helper(root, p, q);
        return ans;
    }

    public boolean helper(TreeNode root, TreeNode p, TreeNode q){
        if(root == null){
            return false;
        }
        boolean flag = (root == p || root == q) ? true : false;

        boolean leftSide = helper(root.left, p, q);
        boolean rightSide = helper(root.right, p, q);

        if(leftSide && rightSide){
            if(ans == null){
                ans = root;
            }
        }

        if(flag && (leftSide || rightSide)){
            if(ans == null){
                ans = root;
            }
        }

        return flag || leftSide || rightSide;
    }

}
