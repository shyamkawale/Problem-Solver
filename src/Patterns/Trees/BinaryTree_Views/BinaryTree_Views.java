package Patterns.Trees.BinaryTree_Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class BinaryTree_Views extends ProblemSolver {
    public static void main(String[] args) {
        new BinaryTree_Views().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        TreeWrapper.prettyPrintTree(root);
        List<Integer> leftViewRes = leftView(root);
        System.out.println("leftViewRes: "+leftViewRes);
        List<Integer> rightViewRes = rightView(root);
        System.out.println("rightViewRes: "+rightViewRes);
        List<Integer> topViewRes = topView(root);
        System.out.println("topViewRes: "+topViewRes);
        List<Integer> bottomViewRes = bottomView(root);
        System.out.println("bottomViewRes: "+bottomViewRes);
    }

    private List<Integer> leftView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        lsv(root, 0, res);
        return res;
    }

    private void lsv(TreeNode root, int depth, List<Integer> res) {
        if(root == null){
            return;
        }

        if(res.size() == depth){
            res.add(root.val);
        }

        lsv(root.left, depth+1, res);
        lsv(root.right, depth+1, res);
    }

    private List<Integer> rightView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        rsv(root, 0, res);
        return res;
    }

    private void rsv(TreeNode root, int depth, List<Integer> res) {
        if(root == null){
            return;
        }

        if(res.size() == depth){
            res.add(root.val);
        }

        rsv(root.right, depth+1, res);
        rsv(root.left, depth+1, res);
    }

    private List<Integer> topView(TreeNode root) {
        Map<Integer, Pair> map = new TreeMap<>();
        tv(root, 0, 0, map);
        return map.values()
                .stream()
                .map((pair) -> pair.data)
                .toList();
    }


    private void tv(TreeNode root, int hDist, int level, Map<Integer, Pair> map) {
        if(root == null){
            return;
        }

        if(!map.containsKey(hDist) || level < map.get(hDist).level){
            map.put(hDist, new Pair(root.val, level));
        }

        tv(root.left, hDist-1, level+1, map);
        tv(root.right, hDist+1, level+1, map);
    }

    private List<Integer> bottomView(TreeNode root) {
        Map<Integer, Pair> map = new TreeMap<>();
        helper(root, map, 0, 0);

        return map.values()
                .stream()
                .map((pair) -> pair.data)
                .toList();
    }

    private void helper(TreeNode root, Map<Integer, Pair> map, int hDist, int level) {
        if(root == null){
            return;
        }

        helper(root.left, map, hDist-1, level+1);
        helper(root.right, map, hDist+1, level+1);

        if(!map.containsKey(hDist) || map.get(hDist).level < level){
            map.put(hDist, new Pair(root.val, level));
        }
    }
}

class Pair {
    int data;
    int level;

    Pair(int data, int level) {
        this.data = data;
        this.level = level;
    }
}
