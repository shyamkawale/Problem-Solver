package Patterns.Trees.Binary_Trees.Reconstruction_Of_Trees.InOrder_And_PostOrder;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

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
    }

    Map<Integer, Integer> inOrderIdxMap;
    Map<Integer, Integer> postOrderIdxMap;

    // TC: O(n)
    // SC: O(2n) + RecO(d)
    private TreeNode constructTree(int[] inOrder, int[] postOrder) {
        inOrderIdxMap = new HashMap<>();
        postOrderIdxMap = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            inOrderIdxMap.put(inOrder[i], i);
            postOrderIdxMap.put(i, postOrder[i]);
        }

        return helper(0, inOrder.length-1, 0, postOrder.length-1);
    }

    private TreeNode helper(int startInIdx, int endInIdx, int startPostIdx, int endPostIdx) {
        if(startInIdx > endInIdx || startPostIdx > endPostIdx){
            return null;
        }

        int rootValue = postOrderIdxMap.get(endPostIdx);
        TreeNode root = new TreeNode(rootValue);
        int inOrderIdx = inOrderIdxMap.get(rootValue);

        root.left = helper(startInIdx, inOrderIdx-1, startPostIdx, startPostIdx + (inOrderIdx-startInIdx-1));
        root.right = helper(inOrderIdx+1, endInIdx, endPostIdx-(endInIdx-inOrderIdx), endPostIdx-1);

        return root;
    }

}
