package Patterns.Trees.Binary_Trees.BT7_Ancestor.StepByStep_Directions_From_a_BinaryTree_Node_to_Another;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/

You are given the root of a binary tree with n nodes. 
Each node is uniquely assigned a value from 1 to n. 
You are also given an integer startValue representing the value of the start node s, 
and a different integer destValue representing the value of the destination node t.

Find the shortest path starting from node s and ending at node t. 
Generate step-by-step directions of such path as a string consisting of only the uppercase letters 'L', 'R', and 'U'. 
Each letter indicates a specific direction:

'L' means to go from a node to its left child node.
'R' means to go from a node to its right child node.
'U' means to go from a node to its parent node.
Return the step-by-step directions of the shortest path from node s to node t.

Input: root = [5,1,2,3,null,6,4], startValue = 3, destValue = 6
Output: "UURL"
Explanation: The shortest path is: 3 → 1 → 5 → 2 → 6.

Input: root = [2,1], startValue = 2, destValue = 1
Output: "L"
Explanation: The shortest path is: 2 → 1.
*/
public class StepByStep_Directions_From_a_BinaryTree_Node_to_Another extends ProblemSolver {

    public static void main(String[] args) {
        new StepByStep_Directions_From_a_BinaryTree_Node_to_Another().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int startValue = DataConvertor.toInt(args[1]);
        int destValue = DataConvertor.toInt(args[2]);

        // TreeWrapper.prettyPrintTree(root);

        String res1 = getDirections_lca(root, startValue, destValue);
        String res2 = getDirections_withoutlca(root, startValue, destValue);
        System.out.println(res1 + " " + res2);
    }

    public String getDirections_lca(TreeNode root, int startValue, int destValue) {
        TreeNode lca = lca(root, startValue, destValue);

        StringBuilder pathToStart = new StringBuilder();
        StringBuilder pathToDest = new StringBuilder();

        findPath(lca, startValue, pathToStart);
        findPath(lca, destValue, pathToDest);

        StringBuilder res = new StringBuilder("");
        for(int i=0; i<pathToStart.length(); i++) {
            res.append('U');
        }

        res.append(pathToDest);

        return res.toString();
    }

    private TreeNode lca(TreeNode root, int p, int q) {
        if(root == null || root.val == p || root.val == q) {
            return root;
        }

        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);

        if(left != null && right != null) {
            return root;
        }

        return (left != null) ? left : right;
    }

    private boolean findPath(TreeNode root, int target, StringBuilder path) {
        if(root == null) {
            return false;
        }

        if(root.val == target) {
            return true;
        }

        path.append('L');
        if(findPath(root.left, target, path)){
            return true;
        }
        path.deleteCharAt(path.length() - 1);
        
        path.append('R');
        if(findPath(root.right, target, path)){
            return true;
        }
        path.deleteCharAt(path.length() - 1);

        return false;
    }

    private String getDirections_withoutlca(TreeNode root, int startValue, int destValue) {
        StringBuilder pathToStart = new StringBuilder();
        StringBuilder pathToDest = new StringBuilder();

        findPath(root, startValue, pathToStart);
        findPath(root, destValue, pathToDest);

        int commonPathIdx = 0;

        while(commonPathIdx < pathToStart.length() 
            && commonPathIdx < pathToDest.length()
            && pathToStart.charAt(commonPathIdx) == pathToDest.charAt(commonPathIdx)) {
                commonPathIdx++;
        }

        StringBuilder res = new StringBuilder("");
        for(int i=0; i<pathToStart.length() - commonPathIdx; i++) {
            res.append('U');
        }

        res.append(pathToDest.substring(commonPathIdx));

        return res.toString();
    }
}
