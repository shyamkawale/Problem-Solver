package Patterns.Trees.Binary_Trees.Smallest_String_Starting_From_Leaf;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/smallest-string-starting-from-leaf/

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

    public void helper(TreeNode root, StringBuilder sb){
        if(root == null){
            return;
        }
        sb.insert(0, (char)(root.val + 'a'));

        // Leaf node identification
        if(root.left == null && root.right == null){
            if(res == "" || res.compareTo(sb.toString()) > 0){
                res = sb.toString();
            }
        }

        helper(root.left, sb);
        helper(root.right, sb);
        sb.deleteCharAt(0);
    }
}
