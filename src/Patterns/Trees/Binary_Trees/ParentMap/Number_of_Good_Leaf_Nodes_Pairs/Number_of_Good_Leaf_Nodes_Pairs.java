package Patterns.Trees.Binary_Trees.ParentMap.Number_of_Good_Leaf_Nodes_Pairs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Number_of_Good_Leaf_Nodes_Pairs extends ProblemSolver {
    public static void main(String[] args) {
        new Number_of_Good_Leaf_Nodes_Pairs().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int distance = DataConvertor.toInt(args[1]);

        int ans = countPairs(root, distance);
        System.out.println(ans);
    }

    int res = 0;
    Map<TreeNode, TreeNode> parentMap = new HashMap<>();

    public int countPairs(TreeNode root, int distance) {
        parentMap.put(root, null);
        constructParentMap(root);
        preOrder(root, distance);

        return res/2;
    }

    public void preOrder(TreeNode root, int distance) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            // dfs on leaf node
            dfs(root, 0, distance, new HashSet<TreeNode>());
        }

        preOrder(root.left, distance);
        preOrder(root.right, distance);
    }

    public void dfs(TreeNode root, int depth, int distance, Set<TreeNode> visited) {
        if (root == null || visited.contains(root)) {
            return;
        }

        visited.add(root);

        // leaf node
        if (root.left == null && root.right == null) {
            if (depth > 0 && depth <= distance) {
                res++;
            }
        }

        dfs(root.left, depth + 1, distance, visited);
        dfs(root.right, depth + 1, distance, visited);
        dfs(parentMap.get(root), depth + 1, distance, visited);
    }

    public void constructParentMap(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            parentMap.putIfAbsent(root.left, root);
        }
        constructParentMap(root.left);

        if (root.right != null) {
            parentMap.putIfAbsent(root.right, root);
        }
        constructParentMap(root.right);
    }

}
