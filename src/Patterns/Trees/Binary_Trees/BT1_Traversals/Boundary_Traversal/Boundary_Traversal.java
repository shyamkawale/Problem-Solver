package Patterns.Trees.Binary_Trees.BT1_Traversals.Boundary_Traversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://takeuforward.org/plus/dsa/problems/boundary-traversal

Given a root of Binary Tree, perform the boundary traversal of the tree. 

The boundary traversal is the process of visiting the boundary nodes of the binary tree 
in the anticlockwise direction, starting from the root.

Example 1
Input : root = [1, 2, 3, 4, 5, 6, 7, null, null, 8, 9]
Output : [1, 2, 4, 8, 9, 6, 7, 3]

Example 2
Input : root = [1, 2, null, 4, 9, 6, 5, 3, null, null, null, null, null, 7, 8]
Output : [1, 2, 4, 6, 5, 7, 8]
*/
public class Boundary_Traversal extends ProblemSolver {
    public static void main(String[] args) {
        new Boundary_Traversal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        List<Integer> res = boundaryTraversal(root);
        System.out.println(res);
    }

    List<Integer> res;

    public List<Integer> boundaryTraversal(TreeNode root) {
        res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        // 1. Adding Root Node
        res.add(root.val);

        // 2. Adding Left Boundary
        TreeNode curr = root.left;
        while (!isLeaf(curr)) {
            res.add(curr.val);
            if (curr.left != null) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        // 3. Adding All Leaves
        addLeaves(root);

        // 4. Adding Right Boundary
        curr = root.right;
        Deque<Integer> stack = new ArrayDeque<>();
        while (!isLeaf(curr)) {
            stack.push(curr.val);
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }

        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }

        return res;
    }

    public void addLeaves(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            res.add(root.val);
        }
        addLeaves(root.left);
        addLeaves(root.right);
    }

    public boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

}
