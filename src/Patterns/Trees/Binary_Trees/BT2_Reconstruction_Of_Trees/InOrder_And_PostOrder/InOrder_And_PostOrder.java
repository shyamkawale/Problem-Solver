package Patterns.Trees.Binary_Trees.BT2_Reconstruction_Of_Trees.InOrder_And_PostOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/

Given two integer arrays inorder and postorder where inorder is the inorder traversal of a binary tree and postorder is the postorder traversal of the same tree, construct and return the binary tree.
*/
public class InOrder_And_PostOrder extends ProblemSolver {
    public static void main(String[] args) {
        new InOrder_And_PostOrder().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] inOrder = DataConvertor.toIntArray(args[0]);
        int[] postOrder = DataConvertor.toIntArray(args[1]);

        TreeNode root = constructTree(inOrder, postOrder);
        TreeWrapper.prettyPrintTree(root);
        System.out.println(preOrder);
    }

    List<Integer> preOrder = new ArrayList<>();
    Map<Integer, Integer> inOrderIdxMap;
    int postIdx;

    public TreeNode constructTree(int[] inOrder, int[] postOrder) {
        int len = inOrder.length;
        inOrderIdxMap = new HashMap<>();
        postIdx = len-1;
        for(int i=0; i<inOrder.length; i++){
            inOrderIdxMap.put(inOrder[i], i);
        }

        return helper(0, len-1, postOrder);
    }

    private TreeNode helper(int startInIdx, int endInIdx, int[] postOrder) {
        if(startInIdx > endInIdx){
            return null;
        }

        int rootValue = postOrder[postIdx--];
        TreeNode root = new TreeNode(rootValue);
        int inOrderIdx = inOrderIdxMap.get(rootValue);

        root.right = helper(inOrderIdx+1, endInIdx, postOrder);
        root.left = helper(startInIdx, inOrderIdx-1, postOrder);

        preOrder.addFirst(rootValue); // becoz we are moving right first..
        return root;
    }

    // Map<Integer, Integer> inOrderIdxMap;
    // Map<Integer, Integer> postOrderIdxMap;

    // // TC: O(n)
    // // SC: O(2n) + RecO(d)
    // private TreeNode constructTree2(int[] inOrder, int[] postOrder) {
    //     int len = inOrder.length;
    //     inOrderIdxMap = new HashMap<>();
    //     postOrderIdxMap = new HashMap<>();
    //     for (int i = 0; i < inOrder.length; i++) {
    //         inOrderIdxMap.put(inOrder[i], i);
    //         postOrderIdxMap.put(i, postOrder[i]);
    //     }

    //     return helper(0, len-1, 0, len-1);
    // }

    // private TreeNode helper(int startInIdx, int endInIdx, int startPostIdx, int endPostIdx) {
    //     if(startInIdx > endInIdx || startPostIdx > endPostIdx){
    //         return null;
    //     }

    //     int rootValue = postOrderIdxMap.get(endPostIdx);
    //     TreeNode root = new TreeNode(rootValue);
    //     int inOrderIdx = inOrderIdxMap.get(rootValue);

    //     root.left = helper(startInIdx, inOrderIdx-1, startPostIdx, startPostIdx + (inOrderIdx-startInIdx-1));
    //     root.right = helper(inOrderIdx+1, endInIdx, endPostIdx-(endInIdx-inOrderIdx), endPostIdx-1);

    //     return root;
    // }
}
