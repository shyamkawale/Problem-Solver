package Patterns.Trees.Binary_Trees.BT3_DFS.Smallest_String_Starting_From_Leaf;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/smallest-string-starting-from-leaf/

You are given the root of a binary tree where each node has a value in the range [0, 25] representing the letters 'a' to 'z'.

Return the lexicographically smallest string that starts at a leaf of this tree and ends at the root.

As a reminder, any shorter prefix of a string is lexicographically smaller.

For example, "ab" is lexicographically smaller than "aba".
A leaf of a node is a node that has no children.

DFS + Leaf node identification
 */
public class Smallest_String_Starting_From_Leaf extends ProblemSolver {
    public static void main(String[] args) {
        new Smallest_String_Starting_From_Leaf().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        String res = smallestFromLeaf(root);
        System.out.println(res);
    }

    String res = "";

    // TC: O(n),
    // SC: RecO(h)
    public String smallestFromLeaf(TreeNode root) {
        helper(root, new StringBuilder());
        return res;
    }

    public void helper(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }
        sb.insert(0, (char) (root.val + 'a'));

        // Leaf node identification
        if (root.left == null && root.right == null) {
            if (res == "" || res.compareTo(sb.toString()) > 0) {
                res = sb.toString();
            }
        }

        helper(root.left, sb);
        helper(root.right, sb);

        sb.deleteCharAt(0); // Backtrack
    }
}
