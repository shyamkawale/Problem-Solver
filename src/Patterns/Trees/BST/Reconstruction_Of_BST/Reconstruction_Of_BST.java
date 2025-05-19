package Patterns.Trees.BST.Reconstruction_Of_BST;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Reconstruction_Of_BST extends ProblemSolver {

    public static void main(String[] args) {
        new Reconstruction_Of_BST().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] preorder = DataConvertor.toIntArray(args[0]);
        int[] postorder = DataConvertor.toIntArray(args[1]);
        int[] levelorder = DataConvertor.toIntArray(args[2]);

        TreeNode preOrderTree = createBSTFromPreorder(preorder);
        TreeWrapper.prettyPrintTree(preOrderTree);
        TreeNode postOrderTree = createBSTFromPostorder(postorder);
        TreeWrapper.prettyPrintTree(postOrderTree);
        TreeNode levelOrderTree = createBSTFromLevelorder(levelorder);
        TreeWrapper.prettyPrintTree(levelOrderTree);
    }

    private TreeNode createBSTFromPreorder(int[] preorder) {
        int n = preorder.length;
        if(n == 0){
            return null;
        }

        TreeNode root = new TreeNode(preorder[0]);
        for(int i=1; i<n; i++){
            helper(root, preorder[i]);
        }
        return root;
    }

    private TreeNode createBSTFromPostorder(int[] postorder) {
        int n = postorder.length;
        if(n == 0){
            return null;
        }

        TreeNode root = new TreeNode(postorder[n-1]);
        for(int i=n-2; i>=0; i--){
            helper(root, postorder[i]);
        }
        return root;
    }

    private TreeNode createBSTFromLevelorder(int[] levelorder) {
        int n = levelorder.length;
        if(n == 0){
            return null;
        }

        TreeNode root = new TreeNode(levelorder[0]);
        for(int i=1; i<n; i++){
            helper(root, levelorder[i]);
        }
        return root;
    }

    private void helper(TreeNode root, int elem){

        if(elem < root.val){
            if(root.left == null){
                root.left = new TreeNode(elem);
            }
            else{
                helper(root.left, elem);
            }
        }
        else{
            if(root.right == null){
                root.right = new TreeNode(elem);
            }
            else{
                helper(root.right, elem);
            }
        }
    }
}
