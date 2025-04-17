package Patterns.Trees.Binary_Trees.Diameter_and_Width_of_BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Diameter_and_Width_of_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new Diameter_and_Width_of_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        TreeWrapper.prettyPrintTree(root);
        int diameter = diameterOfBT(root);
        System.out.println("Diameter of Tree: " + diameter);

        int width = widthOfBT(root);
        System.out.println("Width of Tree: " + width);
    }

    class Pair{ 
        TreeNode node;
        int idx;

        Pair(TreeNode node, int idx){
            this.node = node;
            this.idx = idx;
        }
    }

    public int widthOfBT(TreeNode root) {
        if(root == null){
            return 0;
        }

        int maxWidth = 0;
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0));

        while(!queue.isEmpty()){
            int size = queue.size();
            int leftMostNodeIdx = queue.peek().idx;
            int rightMostNodeIdx = queue.peek().idx;

            for(int i=0; i<size; i++){
                Pair polledItem = queue.poll();
                TreeNode node = polledItem.node;
                int idx = polledItem.idx;
                rightMostNodeIdx = idx;

                if(node.left != null){
                    queue.offer(new Pair(node.left, 2*idx+1));
                }
                if(node.right != null){
                    queue.offer(new Pair(node.right, 2*idx+2));
                }
            }
            maxWidth = Math.max(maxWidth, rightMostNodeIdx - leftMostNodeIdx + 1);
        }

        return maxWidth;
    }

    int diameter = 0;
    private int diameterOfBT(TreeNode root) {
        helper(root, 0);
        return diameter;
    }

    private int helper(TreeNode root, int height) {
        if(root == null){
            return 0;
        }

        int leftHeight = helper(root.left, height+1);
        int rightHeight = helper(root.right, height+1);

        diameter = Math.max(diameter, leftHeight+rightHeight);

        return Math.max(leftHeight, rightHeight)+1;
    }

}
