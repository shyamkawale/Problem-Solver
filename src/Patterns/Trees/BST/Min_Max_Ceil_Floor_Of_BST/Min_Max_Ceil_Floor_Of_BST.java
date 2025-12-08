package Patterns.Trees.BST.Min_Max_Ceil_Floor_Of_BST;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Min_Max_Ceil_Floor_Of_BST extends ProblemSolver {
    public static void main(String[] args) {
        new Min_Max_Ceil_Floor_Of_BST().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int n = DataConvertor.toInt(args[1]);

        int min = findMin(root);
        System.out.println(min);

        int max = findMax(root);
        System.out.println(max);

        int ceil = findCeil(root, n);
        System.out.println(ceil);

        int floor = findFloor(root, n);
        System.out.println(floor);
    }

    int min = Integer.MAX_VALUE;
    private int findMin(TreeNode root) {
        if (root == null) {
            return min;
        }

        min = Math.min(min, root.val);

        return findMin(root.left);
    }

    int max = Integer.MIN_VALUE;
    private int findMax(TreeNode root) {
        if (root == null) {
            return max;
        }

        max = Math.max(max, root.val);

        return findMax(root.right);
    }

    private int findCeil(TreeNode root, int n) {
        if (root == null) {
            return -1;
        }

        if (root.val == n) {
            return root.val;
        }
        else if (n < root.val) { // left subtree
            int ceil = findCeil(root.left, n);
            if (ceil == -1) {
                return root.val;
            }

            return Math.min(ceil, root.val);
        } 
        else { // right subtree
            return findCeil(root.right, n);
        }
    }

    private int findFloor(TreeNode root, int n) {
        if(root == null){
            return -1;
        }

        if(root.val == n){
            return root.val;
        }
        else if(n < root.val){ // left subtree
            return findFloor(root.left, n);
        }
        else{ // right subtree
            int floor = findFloor(root.right, n);
            if(floor == -1){
                return root.val;
            }
            
            return Math.min(floor, root.val);
        }
    }
}
