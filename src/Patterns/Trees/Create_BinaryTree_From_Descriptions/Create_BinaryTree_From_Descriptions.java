package Patterns.Trees.Create_BinaryTree_From_Descriptions;

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
https://leetcode.com/problems/create-binary-tree-from-descriptions/description/
 */
public class Create_BinaryTree_From_Descriptions extends ProblemSolver {

    public static void main(String[] args) {
        new Create_BinaryTree_From_Descriptions().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] description = DataConvertor.to2DIntArray(args[0]);

        TreeNode root1 = createBinaryTree(description);
        TreeWrapper.prettyPrintTree(root1);
        TreeNode root2 = createBinaryTree2(description);
        TreeWrapper.prettyPrintTree(root2);
    }

    private TreeNode createBinaryTree(int[][] description) {
        Map<Integer, TreeNode> nodeMap = new HashMap<>();
        Set<Integer> childSet = new HashSet<>();

        for (int[] desc : description) {
            int parent = desc[0];
            int child = desc[1];
            boolean isLeft = desc[2] == 1;

            // Get or create parent and child nodes

            // same as computeIfAbsent()
            // if (!nodeMap.containsKey(parent)) {
            //     nodeMap.put(parent, new TreeNode(parent));
            // }
            // TreeNode parentNode = nodeMap.get(parent);
            TreeNode parentNode = nodeMap.computeIfAbsent(parent, val -> new TreeNode(val));
            TreeNode childNode = nodeMap.computeIfAbsent(child, val -> new TreeNode(val));

            // Assign child
            if (isLeft) {
                parentNode.left = childNode;
            } else {
                parentNode.right = childNode;
            }

            childSet.add(child);
        }

        for(int[] desc: description){
            if(!childSet.contains(desc[0])){
                return nodeMap.get(desc[0]);
            }
        }

        return null;
    }

    public class Children {
        Integer left;
        Integer right;
    }

    public TreeNode createBinaryTree2(int[][] descriptions) {
        // to store all parent-childrens(pair)..
        Map<Integer, Children> treeMap = new HashMap<>();

        // to store all childrens
        Set<Integer> childSet = new HashSet<>();

        for(int[] desc: descriptions){
            int parent = desc[0];
            int child = desc[1];
            boolean isLeft = desc[2] == 1;

            treeMap.putIfAbsent(parent, new Children());

            if(isLeft){
                treeMap.get(parent).left = child;
            }
            else{
                treeMap.get(parent).right = child;
            }

            childSet.add(child);
        }

        // find root value (search any parent which is not a child)
        int rootVal = -1;
        for (int parent : treeMap.keySet()) {
            if (!childSet.contains(parent)) {
                rootVal = parent;
                continue;
            }
        }
        TreeNode root = new TreeNode(rootVal);

        // construct tree from root node
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode curr = queue.poll();

            if(!treeMap.containsKey(curr.val)){
                continue;
            }

            TreeNode leftNode = treeMap.get(curr.val).left == null ? null : new TreeNode(treeMap.get(curr.val).left);
            TreeNode rightNode = treeMap.get(curr.val).right == null ? null : new TreeNode(treeMap.get(curr.val).right);

            curr.left = leftNode;
            curr.right = rightNode;

            if(leftNode != null){
                queue.offer(leftNode);
            }

            if(rightNode != null){
                queue.offer(rightNode);
            }
        }

        return root;
    }
}
