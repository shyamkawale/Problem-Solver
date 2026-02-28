package Patterns.Trees.Binary_Trees.BT2_Reconstruction_Of_Trees.Create_BinaryTree_From_Descriptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/create-binary-tree-from-descriptions/description/

You are given a 2D integer array descriptions where descriptions[i] = [parenti, childi, isLefti] indicates 
that parenti is the parent of childi in a binary tree of unique values. Furthermore,

If isLefti == 1, then childi is the left child of parenti.
If isLefti == 0, then childi is the right child of parenti.
Construct the binary tree described by descriptions and return its root.

The test cases will be generated such that the binary tree is valid.
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
}
