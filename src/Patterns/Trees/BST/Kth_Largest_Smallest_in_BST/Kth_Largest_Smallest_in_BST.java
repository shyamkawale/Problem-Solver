package Patterns.Trees.BST.Kth_Largest_Smallest_in_BST;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/kth-smallest-element-in-a-bst/


 */
public class Kth_Largest_Smallest_in_BST extends ProblemSolver {

    public static void main(String[] args) {
        new Kth_Largest_Smallest_in_BST().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int kSmallest = findKthSmallestInBST(root, k);
        int kGreatest = findKthGreatestInBST(root, k);
        System.out.println(kSmallest);
        System.out.println(kGreatest);
    }

    int ks = -1;
    int kSmallest = -1;

    private int findKthSmallestInBST(TreeNode root, int k) {
        ks = k;
        inorderKSmall(root);
        return kSmallest;
    }

    private void inorderKSmall(TreeNode root) {
        if (root == null) {
            return;
        }

        if (ks < 0) {
            return;
        }

        inorderKSmall(root.left);
        ks--;
        if (ks == 0) {
            kSmallest = root.val;
            return;
        }
        inorderKSmall(root.right);
    }

    int kg = -1;
    int kGreatest = -1;

    private int findKthGreatestInBST(TreeNode root, int k) {
        ks = k;
        inorderKGreat(root);
        return kGreatest;
    }

    private void inorderKGreat(TreeNode root) {
        if (root == null) {
            return;
        }

        if (ks < 0) {
            return;
        }

        inorderKGreat(root.right);
        ks--;
        if (ks == 0) {
            kGreatest = root.val;
            return;
        }
        inorderKGreat(root.left);
    }

}
