package Patterns.Trees.Binary_Trees.BT3_DFS.Reverse_Odd_Levels_Of_BT;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/reverse-odd-levels-of-binary-tree/

Given the root of a perfect binary tree, reverse the node values at each odd level of the tree.

For example, suppose the node values at level 3 are [2,1,3,4,7,11,29,18], then it should become [18,29,11,7,4,3,1,2].
Return the root of the reversed tree.

A binary tree is perfect if all parent nodes have two children and all leaves are on the same level.

The level of a node is the number of edges along the path between it and the root node.
*/
public class Reverse_Odd_Levels_Of_BT extends ProblemSolver {

    public static void main(String[] args) {
        new Reverse_Odd_Levels_Of_BT().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        TreeNode res1 = reverseOddLevels_DFS(root);
        TreeNode res2 = reverseOddLevels_BFS(root);
        TreeWrapper.prettyPrintTree(res1);
        TreeWrapper.prettyPrintTree(res2);
    }

    // DFS Approach (Good Elegant)
    // TC: O(n)
    // SC: RecO(n)
    public TreeNode reverseOddLevels_DFS(TreeNode root) {
        dfs(root.left, root.right, 1);

        return root;
    }

    private void dfs(TreeNode leftRoot, TreeNode rightRoot, int depth) {
        if(leftRoot == null || rightRoot == null) {
            return;
        }

        if(depth % 2 != 0) {
            int temp = leftRoot.val;
            leftRoot.val = rightRoot.val;
            rightRoot.val = temp;
        }

        dfs(leftRoot.left, rightRoot.right, depth+1);
        dfs(leftRoot.right, rightRoot.left, depth+1);
    }


    // BFS Approach (bad approach)
    // TC: O(n^2)
    // SC: O(2n)
    public TreeNode reverseOddLevels_BFS(TreeNode root) {
        if(root == null || (root.left == null && root.right == null)) {
            return root;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        HashMap<Integer, TreeNode> idxMap = new HashMap<>();

        int level = 0;
        queue.offer(root);

        while(!queue.isEmpty()) {
            int size = queue.size();
            int startIdx = (int) Math.pow(2, level)-1;

            for(int s=0; s<size; s++) {
                TreeNode polledNode = queue.poll();

                if(level%2 != 0) {
                    idxMap.put(startIdx+s, polledNode);
                }

                if(polledNode.left != null) {
                    queue.offer(polledNode.left);
                }

                if(polledNode.right != null) {
                    queue.offer(polledNode.right);
                }
            }

            if(level%2 != 0) {
                int left = startIdx;
                int right = startIdx + size - 1;

                while(left < right) {
                    TreeNode leftNode = idxMap.get(left);
                    TreeNode rightNode = idxMap.get(right);

                    int temp = leftNode.val;
                    leftNode.val = rightNode.val;
                    rightNode.val = temp;

                    left++;
                    right--;
                }
            }

            level++;
        }

        return root;
    }

}
