package Patterns.Trees.Binary_Trees.BT3_DFS.Delete_Leaf_leaves_of_given_value;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/delete-leaves-with-a-given-value/description/

Given a binary tree root and an integer target, delete all the leaf nodes with value target.

Note that once you delete a leaf node with value target, if its parent node becomes a leaf node and has the value target, 
it should also be deleted (you need to continue doing that until you cannot).

*/
public class Delete_Leaf_leaves_of_given_value extends ProblemSolver {

    public static void main(String[] args) {
        new Delete_Leaf_leaves_of_given_value().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int target = Integer.parseInt(args[1]);

        TreeNode res = removeLeafNodes(root, target);
        TreeWrapper.prettyPrintTree(res);
    }

    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if(root == null){
            return null;
        }

        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);

        if(root.left == null && root.right == null && root.val == target){
            return null;
        }
        
        return root;
    }

}
