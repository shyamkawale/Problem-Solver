package Patterns.Trees.Traversals.Boundary_Traversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Boundary_Traversal extends ProblemSolver {
    public static void main(String[] args) {
        new Boundary_Traversal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        List<Integer> res = boundaryTraversal(root);
        System.out.println(res);
    }

    List<Integer> res;
    public List<Integer> boundaryTraversal(TreeNode root) {
        res = new ArrayList<>();
        if(root == null){
            return res;
        }

        TreeNode temp = root;
        while(!isLeaf(temp)){
            res.add(temp.val);
            if(temp.left != null){
                temp = temp.left;
            }
            else{
                temp = temp.right;
            }
        }

        addLeaves(root);

        temp = root.right;
        Deque<Integer> stack = new ArrayDeque<>();
        while(!isLeaf(temp)){
            stack.add(temp.val);
            if(temp.right != null){
                temp = temp.right;
            }
            else{
                temp = temp.left;
            }
        }

        while(!stack.isEmpty()){
            res.add(stack.pop());
        }

        return res;
    }

    public void addLeaves(TreeNode root){
        if(root == null){
            return;
        }

        if(root.left == null && root.right == null){
            res.add(root.val);
        }
        addLeaves(root.left);
        addLeaves(root.right);
    }

    public boolean isLeaf(TreeNode node){
        if(node == null){
            return true;
        }
        return node.left == null && node.right == null;
    }

}
