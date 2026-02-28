package Patterns.Trees.Binary_Trees.BT2_Reconstruction_Of_Trees.InOrder_And_LevelOrder;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class InOrder_And_LevelOrder extends ProblemSolver {
    public static void main(String[] args) {
        new InOrder_And_LevelOrder().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] inOrder = DataConvertor.toIntArray(args[0]);
        int[] levelOrder = DataConvertor.toIntArray(args[1]);

        TreeNode root = constructTree(inOrder, levelOrder);
        TreeWrapper.prettyPrintTree(root);
    }

    Map<Integer, Integer> inOrderIdxMap;
    Map<Integer, Integer> levelOrderIdxMap;

    // TC: O(n*log2(n)) incase of Balanced BT, O(n*n) incae of skewed BT
    // SC: O(2n) + RecO(d)
    private TreeNode constructTree(int[] inOrder, int[] levelOrder) {
        inOrderIdxMap = new HashMap<>();
        levelOrderIdxMap = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            inOrderIdxMap.put(inOrder[i], i);
            levelOrderIdxMap.put(levelOrder[i], i);
        }

        return helper(0, inOrder.length - 1, inOrder, levelOrder);
    }

    private TreeNode helper(int startInIdx, int endInIdx, int[] inOrder, int[] levelOrder) {
        if(startInIdx > endInIdx){
            return null;
        }

        int rootLevelIdx = Integer.MAX_VALUE;
        for(int i=startInIdx; i<=endInIdx; i++){
            rootLevelIdx = Math.min(rootLevelIdx, levelOrderIdxMap.get(inOrder[i]));
        }
        int rootValue = levelOrder[rootLevelIdx];
        TreeNode root = new TreeNode(rootValue);
        int inOrderIdx = inOrderIdxMap.get(rootValue);

        root.left = helper(startInIdx, inOrderIdx-1, inOrder, levelOrder);
        root.right = helper(inOrderIdx+1, endInIdx, inOrder, levelOrder);

        return root;
    }

}
