package Patterns.Trees.BST.Operations_On_BST;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Operations_On_BST extends ProblemSolver {

    public static void main(String[] args) {
        new Operations_On_BST().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int searchElem = DataConvertor.toInt(args[1]);
        int insertElem = DataConvertor.toInt(args[2]);
        int deleteElem = DataConvertor.toInt(args[3]);

        System.out.println("Original Tree: ");
        TreeWrapper.prettyPrintTree(root);

        boolean isContains = searchElem(root, searchElem);
        System.out.println(searchElem + " is there in Tree: " + isContains);

        System.out.println("Tree after inserting " + insertElem + " : ");
        insertElement(root, insertElem);
        TreeWrapper.prettyPrintTree(root);

        System.out.println("Tree after deleting " + deleteElem + " : ");
        TreeNode res = deleteElement(root, deleteElem);
        TreeWrapper.prettyPrintTree(res);
    }

    private boolean searchElem(TreeNode root, int searchElem) {
        if(root == null){
            return false;
        }

        if(root.val == searchElem){
            return true;
        }
        else if(searchElem < root.val){ // left subtree
            return searchElem(root.left, searchElem);
        }
        else{ // right subtree
            return searchElem(root.right, searchElem);
        }
    }

    private void insertElement(TreeNode root, int insertElem) {
        if(root == null){
            return;
        }

        if(insertElem <= root.val){
            if(root.left == null){
                root.left = new TreeNode(insertElem);
                return; 
            }
            insertElement(root.left, insertElem);
        }
        else{
            if(root.right == null){
                root.right = new TreeNode(insertElem);
                return;
            }
            insertElement(root.right, insertElem);
        }
    }

    private TreeNode deleteElement(TreeNode root, int deleteElem) {
        if(root == null){
            return null;
        }

        if(root.val == deleteElem){
            // Element found to delete
            if(root.left == null){
                return root.right;
            }
            else if(root.right == null){
                return root.left;
            }

            // if left and right are not null..
            
            // inorder successor (smallest in right subtree)
            TreeNode temp = root.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            // Replace node's value with successor's value
            root.val = temp.val;
            // Delete successor from right subtree
            root.right = deleteElement(root.right, temp.val);
        }
        else if(deleteElem < root.val){ // left subtree
            root.left = deleteElement(root.left, deleteElem);
        }
        else{ // right subtree
            root.right = deleteElement(root.right, deleteElem);
        }

        return root;
    }

}
