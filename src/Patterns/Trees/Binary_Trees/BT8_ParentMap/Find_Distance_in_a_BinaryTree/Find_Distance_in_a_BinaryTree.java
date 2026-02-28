package Patterns.Trees.Binary_Trees.BT8_ParentMap.Find_Distance_in_a_BinaryTree;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/find-distance-in-a-binary-tree/
https://leetcode.ca/2020-09-04-1740-Find-Distance-in-a-Binary-Tree/

Given the root of a binary tree and two integers p and q, 
return the distance between the nodes of value p and value q in the tree.

The distance between two nodes is the number of edges on the path from one to the other.

1st Approach: Using ParentMap + BFS
2nd Approach: Using LCA + distance caln of p & q from lca
 */
public class Find_Distance_in_a_BinaryTree extends ProblemSolver {

    public static void main(String[] args) {
        new Find_Distance_in_a_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int p = DataConvertor.toInt(args[1]);
        int q = DataConvertor.toInt(args[2]);

        int res1 = findDistance1(root, p, q);
        System.out.println(res1);

        int res2 = findDistance2(root, p, q);
        System.out.println(res2);
    }

    TreeNode pNode = null;
    TreeNode qNode = null;
    // 1st Approach: Using ParentMap + BFS
    private int findDistance1(TreeNode root, int p, int q) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        // parentMap.put(root, null);

        constructParentMap(root, parentMap, p, q);
        if(pNode == null || qNode == null){
            return -1;
        }

        int level = 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        Set<TreeNode> visited = new HashSet<>();

        queue.offer(pNode);
        visited.add(pNode);

        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0; i<size; i++){
                TreeNode currNode = queue.poll();
                if(currNode == null) continue;

                if(currNode.val == q){
                    return level;
                }

                // checking parentNode..
                if (parentMap.containsKey(currNode)) {
                    TreeNode parentNode = parentMap.get(currNode);
                    if (!visited.contains(parentNode)) {
                        queue.offer(parentNode);
                        visited.add(parentNode);
                    }
                }

                // checking leftNode..
                if (currNode.left != null && !visited.contains(currNode.left)) {
                    queue.offer(currNode.left);
                    visited.add(currNode.left);
                }

                // checking rightNode..
                if (currNode.right != null && !visited.contains(currNode.right)) {
                    queue.offer(currNode.right);
                    visited.add(currNode.right);
                }
            }
            level++;
        }

        return -1;
    }

    private void constructParentMap(TreeNode root, Map<TreeNode, TreeNode> parentMap, int p, int q) {
        if(root == null){
            return;
        }

        if(root.val == p){
            pNode = root;
        }
        if(root.val == q){
            qNode = root;
        }

        if(root.left != null){
            parentMap.putIfAbsent(root.left, root);
        }
        constructParentMap(root.left, parentMap, p, q);

        if(root.right != null){
            parentMap.putIfAbsent(root.right, root);
        }
        constructParentMap(root.right, parentMap, p, q);
    }


    // 2nd Approach: Using LCA + distance caln of p & q from lca
    private int findDistance2(TreeNode root, int p, int q) {
        TreeNode lca = lca(root, p, q);

        int d1 = findDist(lca, p, 0);
        int d2 = findDist(lca, q, 0);

        return d1+d2;
    }

    private TreeNode lca(TreeNode root, int p, int q) {
        if (root == null || root.val == p || root.val == q) {
            return root;
        }

        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);

        if(left != null && right != null){
            return root;
        }

        if(left != null){
            return left;
        }
        return right;
    }

    private int findDist(TreeNode root, int target, int depth) {
        if(root == null){
            return -1;
        }

        if(root.val == target){
            return depth;
        }

        int left = findDist(root.left, target, depth+1);
        if(left != -1){
            return left;
        }

        return findDist(root.right, target, depth+1);
    }

}
