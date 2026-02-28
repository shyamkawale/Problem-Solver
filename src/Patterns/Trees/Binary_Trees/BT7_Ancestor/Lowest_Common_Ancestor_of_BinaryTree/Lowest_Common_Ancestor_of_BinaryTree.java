package Patterns.Trees.Binary_Trees.BT7_Ancestor.Lowest_Common_Ancestor_of_BinaryTree;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*


2 Approaches:

1. Elegant Solution (helper func returning TreeNode)
2. Solution helper func returning boolean + global ans
 */
public class Lowest_Common_Ancestor_of_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new Lowest_Common_Ancestor_of_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        TreeNode p = root.left;
        TreeNode q = root.right;

        TreeNode res1 = lowestCommonAncestor1(root, p, q);
        TreeWrapper.prettyPrintTree(res1);
        
        TreeNode res2 = lowestCommonAncestor2(root, p, q);
        TreeWrapper.prettyPrintTree(res2);
    }

    // Elegant Solution
    private TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor1(root.left, p, q);
        TreeNode right = lowestCommonAncestor1(root.right, p, q);

        // We have found a common ancestor of p & q
        if(left != null && right != null){
            return root;
        }

        if(left != null){
            return left;
        }
        return right;
    }

    TreeNode ans;
    // TC: O(n)
    // SC: RecO(d)
    private TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        helper(root, p, q);
        return ans;
    }

    public boolean helper(TreeNode root, TreeNode p, TreeNode q){
        if(root == null){
            return false;
        }
        boolean isCurr = root == p || root == q;

        boolean leftSide = helper(root.left, p, q);
        boolean rightSide = helper(root.right, p, q);

        if(leftSide && rightSide){
            if(ans == null){
                ans = root;
            }
        }

        if(isCurr && (leftSide || rightSide)){
            if(ans == null){
                ans = root;
            }
        }

        return isCurr || leftSide || rightSide;
    }

}
