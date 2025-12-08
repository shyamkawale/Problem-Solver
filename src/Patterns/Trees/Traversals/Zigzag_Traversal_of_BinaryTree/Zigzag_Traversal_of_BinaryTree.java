package Patterns.Trees.Traversals.Zigzag_Traversal_of_BinaryTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
 */
public class Zigzag_Traversal_of_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new Zigzag_Traversal_of_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        TreeWrapper.prettyPrintTree(root);
        List<List<Integer>> res = zigzagLevelOrder(root);
        System.out.println(res);
    }

    // TC: O(n)
    // SC: O(w), where w is the max width of a tree => 1 <= w <= 2^l or (n+1)/2
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null){
            return res;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        TreeNode curr = root;
        queue.offer(curr);
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for(int i=0; i<size; i++){
                TreeNode polledNode = queue.poll();

                if(res.size()%2 != 0){
                    list.addFirst(polledNode.val);
                }
                else{
                    list.addLast(polledNode.val);
                }

                if(polledNode.left != null){
                    queue.offer(polledNode.left);
                }

                if(polledNode.right != null){
                    queue.offer(polledNode.right);
                }
            }

            res.add(list);
        }

        return res;
    }

}
