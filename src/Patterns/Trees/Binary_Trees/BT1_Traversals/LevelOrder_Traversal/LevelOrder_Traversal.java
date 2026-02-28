package Patterns.Trees.Binary_Trees.BT1_Traversals.LevelOrder_Traversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class LevelOrder_Traversal extends ProblemSolver {
    public static void main(String[] args) {
        new LevelOrder_Traversal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        List<Integer> res1 = levelOrderTraversal_withoutLevels(root);
        System.out.println(res1);

        List<List<Integer>> res2 = levelOrderTraversal_withLevels(root);
        System.out.println(res2);

        List<List<Integer>> res3 = levelOrderTraversal_withLevels_Rec(root);
        System.out.println(res3);
    }

    // Iterative Approach Without levels
    // TC: O(n)
    // SC: O(w), where w is the max width of a tree => 1 <= w <= 2^l or (n+1)/2
    private List<Integer> levelOrderTraversal_withoutLevels(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        TreeNode curr = root;
        queue.offer(curr);
        while (!queue.isEmpty()) {
            TreeNode polledNode = queue.poll();

            res.add(polledNode.val);

            if (polledNode.left != null) {
                queue.offer(polledNode.left);
            }

            if (polledNode.right != null) {
                queue.offer(polledNode.right);
            }
        }

        return res;
    }

    // Iterative Aproach With levels
    // TC: O(n)
    // SC: O(w), where w is the max width of a tree => 1 <= w <= 2^l or (n+1)/2
    private List<List<Integer>> levelOrderTraversal_withLevels(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        TreeNode curr = root;
        queue.offer(curr);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> levelList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode polledNode = queue.poll();

                levelList.add(polledNode.val);

                if (polledNode.left != null) {
                    queue.offer(polledNode.left);
                }

                if (polledNode.right != null) {
                    queue.offer(polledNode.right);
                }
            }

            res.add(levelList);
        }

        return res;
    }

    // Recursive Aproach With levels (similar to Preorder)
    // TC: O(n)
    // SC: RecO(d), d is the maxdepth of tree
    private List<List<Integer>> levelOrderTraversal_withLevels_Rec(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        helper(root, 0, res);
        return res;
    }

    private void helper(TreeNode root, int level, List<List<Integer>> res) {
        if(root == null){
            return;
        }

        if(res.size() <= level){
            res.add(new ArrayList<>());
        }

        res.get(level).add(root.val);

        helper(root.left, level+1, res);
        helper(root.right, level+1, res);
    }

}
