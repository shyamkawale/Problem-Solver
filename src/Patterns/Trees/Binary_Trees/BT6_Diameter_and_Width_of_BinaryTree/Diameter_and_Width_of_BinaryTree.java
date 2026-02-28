package Patterns.Trees.Binary_Trees.BT6_Diameter_and_Width_of_BinaryTree;

import java.util.Deque;
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

        int width2 = widthOfBT2(root);
        System.out.println("Width of Tree: " + width2);
    }

    private int widthOfBT2(TreeNode root) {
        if(root == null){
            return 0;
        }

        Deque<TreeNode> deque = new LinkedList<>();
        deque.offerLast(root);
        int width = 0;

        while(!deque.isEmpty()){
            while(!deque.isEmpty() && deque.peekFirst() == null){
                deque.pollFirst();
            }

            while(!deque.isEmpty() && deque.peekLast() == null){
                deque.pollLast();
            }

            int size = deque.size();
            if(size == 0){
                break;
            }
            width = Math.max(width, size);

            for(int i=0; i<size; i++){
                TreeNode polledNode = deque.pollFirst();

                if(polledNode == null){
                    deque.offerLast(null);
                    deque.offerLast(null);
                }
                else{
                    deque.offerLast(polledNode.left);
                    deque.offerLast(polledNode.right);
                }
            }
        }

        return width;
    }

    class Pair{ 
        TreeNode node;
        int idx;

        Pair(TreeNode node, int idx){
            this.node = node;
            this.idx = idx;
        }
    }

    // added: Little variation of storing 0 to n idx for every level of tree.. 🚀🚀🚀⭐ (VIMP)
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
            int rightMostNodeIdx = -1;

            for(int i=0; i<size; i++){
                Pair polledItem = queue.poll();
                TreeNode polledNode = polledItem.node;
                int idx = polledItem.idx;
                // int normalizedIdx = polledItem.idx - leftMostNodeIdx;
                rightMostNodeIdx = idx;

                if (polledNode.left != null) {
                    queue.offer(new Pair(polledNode.left, 2*idx+1));
                    // this variation will impress Interviewer instead of above:
                    // queue.offer(new Pair(polledNode.left, 2*normalizedIdx+1)); // This will make leftMostNodeIdx as 0 for every level
                }
                if (polledNode.right != null) {
                    queue.offer(new Pair(polledNode.right, 2*idx+2));
                    // this variation will impress Interviewer instead of above:
                    // queue.offer(new Pair(polledNode.right, 2*normalizedIdx+2)); // This will make leftMostNodeIdx as 0 for every level
                }
            }
            maxWidth = Math.max(maxWidth, rightMostNodeIdx - leftMostNodeIdx + 1);
        }

        return maxWidth;
    }

    // Diameter of Binary Tree : https://leetcode.com/problems/diameter-of-binary-tree/
    // The diameter of a binary tree is the length of the longest path between any two nodes in a tree. 
    // This path may or may not pass through the root.
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
