package Patterns.Trees.Binary_Trees.BT1_Traversals.PreOrder_Traversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class PreOrder_Traversal extends ProblemSolver {
    public static void main(String[] args) {
        new PreOrder_Traversal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        List<Integer> res1 = new ArrayList<>();
        preOrderTraversal_Rec(root, res1);
        System.out.println(res1);

        List<Integer> res2 = preOrderTraversal_Itr1(root);
        System.out.println(res2);

        List<Integer> res3 = preOrderTraversal_Itr2(root);
        System.out.println(res3);
    }

    // Recursive Approach
    // TC: O(n), where n is no of nodes in tree
    // SC: RecO(h), where h is the height of the tree, log2(n+1)-1 <= h <= n
    private void preOrderTraversal_Rec(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        res.add(root.val);
        preOrderTraversal_Rec(root.left, res);
        preOrderTraversal_Rec(root.right, res);
    }

    // Iterative Approach
    // TC: O(n), where n is no of nodes in tree
    // SC: O(h), where h is the height of tree
    private List<Integer> preOrderTraversal_Itr1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;

        while(!stack.isEmpty() || curr != null){
            while(curr != null){
                stack.push(curr);
                res.add(curr.val);
                curr = curr.left;
            }

            TreeNode popNode = stack.pop();
            curr = popNode.right;
        }

        return res;
    }

    // Iterative Approach
    // TC: O(n), where n is no of nodes in tree
    // SC: O(h), where h is the height of tree
    private List<Integer> preOrderTraversal_Itr2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while(!stack.isEmpty()){
            TreeNode popNode = stack.pop();
            res.add(popNode.val);

            if(popNode.right != null){
                stack.push(popNode.right);
            }

            if(popNode.left != null){
                stack.push(popNode.left);
            }
        }

        return res;
    }

    

}
