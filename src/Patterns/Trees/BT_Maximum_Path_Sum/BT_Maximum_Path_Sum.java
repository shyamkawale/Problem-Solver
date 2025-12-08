package Patterns.Trees.BT_Maximum_Path_Sum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;
/*
https://leetcode.com/problems/binary-tree-maximum-path-sum/description/

2 Approaches:

1. Basic thought process as of Problem statement..
2. Using DFS + complex tracking of maxPath (IMP)
 */
public class BT_Maximum_Path_Sum extends ProblemSolver {

    public static void main(String[] args) {
        new BT_Maximum_Path_Sum().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        int res = maxPathSum(root);
        System.out.println(res);

        int res2 = maxPathSum2(root);
        System.out.println(res2);
    }

    int maxPath = Integer.MIN_VALUE;
    Map<TreeNode, TreeNode> parentMap;
    public int maxPathSum(TreeNode root) {
        if(root == null){
            return 0;
        }

        parentMap = new HashMap<>();
        parentMap.put(root, null);
        constructParentMap(root);

        dfs(root);

        return maxPath;
    }

    private void dfs(TreeNode root) {
        if(root == null){
            return;
        }

        // System.out.println("Root val: "+ root.val);
        findAllPaths(root, root.val, new HashSet<TreeNode>());
        // System.out.println();

        dfs(root.left);
        dfs(root.right);
    }

    private void findAllPaths(TreeNode root, int pathSum, Set<TreeNode> visited) {
        if(root == null){
            System.out.println();
            return;
        }
        visited.add(root);

        maxPath = Math.max(maxPath, pathSum);
        // System.out.print("sum: " + pathSum);

        TreeNode parentNode = parentMap.get(root);
        if(parentNode != null && !visited.contains(parentNode)){
            findAllPaths(parentNode, pathSum+parentNode.val, visited);
        }

        TreeNode leftNode = root.left;
        if(leftNode != null && !visited.contains(leftNode)){
            findAllPaths(root.left, pathSum+root.left.val, visited);
        }

        TreeNode rightNode = root.right;
        if(rightNode != null && !visited.contains(rightNode)){
            findAllPaths(root.right, pathSum+rightNode.val, visited);
        }
    }

    private void constructParentMap(TreeNode root) {
        if(root == null){
            return;
        }

        if(root.left != null){
            parentMap.put(root.left, root);
        }
        constructParentMap(root.left);
        if(root.right != null){
            parentMap.put(root.right, root);
        }
        constructParentMap(root.right);
    }


    ////////////////////////////////////////////////////////////////////////
    /// 2nd Approach  (USing DFS)
    ////////////////////////////////////////////////////////////////////////
    int maxSum = Integer.MIN_VALUE;
    private int maxPathSum2(TreeNode root) {
        if(root == null){
            return 0;
        }

        maxGain(root);

        return maxSum;
    }

    private int maxGain(TreeNode node) {
        if (node == null) return 0;

        // Only consider positive contributions (ignore negative paths)
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);

        // Path that passes through this node and both children
        int priceNewPath = node.val + leftGain + rightGain;

        // Update global max if this path is better
        maxSum = Math.max(maxSum, priceNewPath);

        // For parent: only one side can be taken (left or right)
        return node.val + Math.max(leftGain, rightGain);
    }


}
