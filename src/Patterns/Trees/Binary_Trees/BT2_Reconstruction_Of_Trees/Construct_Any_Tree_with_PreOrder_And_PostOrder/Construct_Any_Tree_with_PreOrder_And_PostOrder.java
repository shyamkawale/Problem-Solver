package Patterns.Trees.Binary_Trees.BT2_Reconstruction_Of_Trees.Construct_Any_Tree_with_PreOrder_And_PostOrder;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;
/*
https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/

Given two integer arrays, preorder and postorder where preorder is the preorder traversal of a binary tree of distinct values and postorder is the postorder traversal of the same tree, reconstruct and return the binary tree.

If there exist multiple answers, you can return any of them.
*/
public class Construct_Any_Tree_with_PreOrder_And_PostOrder extends ProblemSolver {

    public static void main(String[] args) {
        new Construct_Any_Tree_with_PreOrder_And_PostOrder().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] inOrder = DataConvertor.toIntArray(args[0]);
        int[] levelOrder = DataConvertor.toIntArray(args[1]);

        TreeNode root = constructFromPrePost(inOrder, levelOrder);
        TreeWrapper.prettyPrintTree(root);
    }

    int preIdx = 0;
    int postIdx = 0;

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        int rootVal = preorder[preIdx++];
        TreeNode root = new TreeNode(rootVal);

        if (rootVal != postorder[postIdx]) {
            root.left = constructFromPrePost(preorder, postorder);
        }

        if (rootVal != postorder[postIdx]) {
            root.right = constructFromPrePost(preorder, postorder);
        }

        postIdx++;
        return root;
    }

}
