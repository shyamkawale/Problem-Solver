package Patterns.Trees.Binary_Trees.BT3_DFS.Maximum_Difference_Between_Node_and_Ancestor;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/
Given the root of a binary tree, 
find the maximum value v for which there exist different nodes a and b where v = |a.val - b.val| and a is an ancestor of b.

A node a is an ancestor of b if either: any child of a is equal to b or any child of a is an ancestor of b.

PURE DFS PROBLEM
 */
public class Maximum_Difference_Between_Node_and_Ancestor extends ProblemSolver {
    public static void main(String[] args) {
        new Maximum_Difference_Between_Node_and_Ancestor().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        int res1 = maxAncestorDiff(root);
        int res2 = maxAncestorDiff_elegant(root);
        System.out.println(res1 + " " + res2);
    }

    int res = 0;
    // TC: O(n)
    // SC: RecO(h)
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
        res = Math.max(res, max-min);

        helper(root.left, max, min);
        helper(root.right, max, min);
    }

    public int maxAncestorDiff_elegant(TreeNode root) {
        return helper2(root, root.val, root.val);
    }

    public int helper2(TreeNode root, int max, int min){
        if(root == null){
            return max - min;
        }

        max = Math.max(max, root.val);
        min = Math.min(min, root.val);

        return Math.max(helper2(root.left, max, min), helper2(root.right, max, min));
    }
}
