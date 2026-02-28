package Patterns.Trees.Binary_Trees.BT2_Reconstruction_Of_Trees.InOrder_And_PreOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/

Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree.
*/
public class InOrder_And_PreOrder extends ProblemSolver {
    public static void main(String[] args) {
        new InOrder_And_PreOrder().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] inOrder = DataConvertor.toIntArray(args[0]);
        int[] preOrder = DataConvertor.toIntArray(args[1]);

        TreeNode root = constructTree(inOrder, preOrder);
        TreeWrapper.prettyPrintTree(root);
        System.out.println(postOrder);
    }

    List<Integer> postOrder = new ArrayList<>();
    Map<Integer, Integer> inOrderIdxMap;
    int preIdx;

    // TC: O(n)
    // SC: O(n) + RecO(d), where n is no. of nodes and d is depth of tree
    private TreeNode constructTree(int[] inOrder, int[] preOrder) {
        int len = inOrder.length;
        inOrderIdxMap = new HashMap<>();
        preIdx = 0;
        for(int i=0; i<inOrder.length; i++){
            inOrderIdxMap.put(inOrder[i], i);
        }

        return helper(0, len-1, preOrder);
    }

    private TreeNode helper(int startInIdx, int endInIdx, int[] preOrder) {
        if(startInIdx > endInIdx){
            return null;
        }

        int rootValue = preOrder[preIdx++];
        TreeNode root = new TreeNode(rootValue);
        int inOrderIdx = inOrderIdxMap.get(rootValue);

        root.left = helper(startInIdx, inOrderIdx-1, preOrder);
        root.right = helper(inOrderIdx+1, endInIdx, preOrder);

        postOrder.add(rootValue);

        return root;
    }

}
