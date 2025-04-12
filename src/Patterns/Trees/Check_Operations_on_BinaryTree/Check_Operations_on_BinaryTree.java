package Patterns.Trees.Check_Operations_on_BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Check_Operations_on_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new Check_Operations_on_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        TreeWrapper.prettyPrintTree(root);
        boolean isSymmetric = isSymmetric(root);
        System.out.println("isSymmetric: " + isSymmetric);

        boolean isComplete = isComplete(root);
        System.out.println("isComplete: " + isComplete);

        boolean isHeightBalanced = isHeightBalanced(root);
        System.out.println("isHeightBalanced: " + isHeightBalanced);
    }

    private boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return helper(root.left, root.right);
    }

    private boolean helper(TreeNode leftSubTree, TreeNode rightSubTree) {
        if(leftSubTree == null && rightSubTree == null){
            return true;
        }
        else if(leftSubTree == null || rightSubTree == null){
            return false;
        }
        else if(leftSubTree.val != rightSubTree.val){
            return false;
        }

        return helper(leftSubTree.left, rightSubTree.right) && helper(leftSubTree.right, rightSubTree.left);
    }

    private boolean isComplete(TreeNode root) {
        if(root == null){
            return true;
        }
        boolean isEnd = false;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            TreeNode polledNode = queue.poll();
            if(polledNode == null){
                isEnd = true;
                continue;
            }
            
            if(isEnd){
                return false;
            }
            queue.offer(polledNode.left);
            queue.offer(polledNode.right);
        }

        return true;
    }

    private boolean isHeightBalanced(TreeNode root) {
        return false;
    }

}
