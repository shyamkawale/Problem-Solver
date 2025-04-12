package Patterns.Trees;

import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Binary_Search_Tree {
    public static void main(String[] args) {
        TreeNode root = null;
        root = insertNode(root, 3);
        root = insertNode(root, 5);
        root = insertNode(root, 7);
        root = insertNode(root, 1);
        root = insertNode(root, 2);
        root = insertNode(root, 6);

        TreeWrapper.prettyPrintTree(root);

        boolean isContain1 = searchBST_Recursive(root, 4);
        boolean isContain2 = searchBST_Iterative(root, 4);
        System.out.println(isContain1);
        System.out.println(isContain2);
    }

    private static boolean searchBST_Recursive(TreeNode root, int elem) {
        if(root == null){
            return false;
        }

        if(root.val == elem){
            return true;
        }
        
        if(elem < root.val){
            return searchBST_Recursive(root.left, elem);
        }
        else{
            return searchBST_Recursive(root.right, elem);
        }
    }

    private static boolean searchBST_Iterative(TreeNode root, int elem) {
        if(root == null){
            return false;
        }

        while(root != null){
            if(elem == root.val){
                break;
            }

            if(elem < root.val){
                root = root.left;
            }
            else{
                root = root.right;
            }
        }

        return root == null ? false : true;
    }

    public static TreeNode insertNode(TreeNode root, int n) {
        if (root == null) {
            return new TreeNode(n);
        }

        if (n < root.val) {
            root.left = insertNode(root.left, n);
        } else {
            root.right = insertNode(root.right, n);
        }

        return root;
    }
}