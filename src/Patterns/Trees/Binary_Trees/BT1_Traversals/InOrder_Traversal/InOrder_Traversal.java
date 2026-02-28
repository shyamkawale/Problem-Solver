package Patterns.Trees.Binary_Trees.BT1_Traversals.InOrder_Traversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class InOrder_Traversal extends ProblemSolver {
    public static void main(String[] args) {
        new InOrder_Traversal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        List<Integer> res = new ArrayList<>();
        inOrderTraversal_Rec(root, res);
        System.out.println(res);

        List<Integer> res2 = inOrderTraversal_Itr(root);
        System.out.println(res2);
    }

    // Recursive Approach
    // TC: O(n), where n is no of nodes in tree
    // SC: RecO(h), where h is the height of the tree, log2(n+1)-1 <= h <= n
    private void inOrderTraversal_Rec(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        inOrderTraversal_Rec(root.left, res);
        res.add(root.val);
        inOrderTraversal_Rec(root.right, res);
    }

    // Iterative Approach
    // TC: O(n), where n is no of nodes in tree
    // SC: O(h), where h is the height of tree
    private List<Integer> inOrderTraversal_Itr(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;

        while(!stack.isEmpty() || curr != null){
            while(curr != null){
                stack.push(curr);
                curr = curr.left;
            }

            TreeNode popNode = stack.pop();
            res.add(popNode.val);
            curr = popNode.right;
        }

        return res;
    }

}
