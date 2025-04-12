package Patterns.Trees.Boundary_Traversal_of_BinaryTree;

import java.util.List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class Boundary_Traversal_of_BinaryTree extends ProblemSolver {
    public static void main(String[] args) {
        new Boundary_Traversal_of_BinaryTree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        TreeWrapper.prettyPrintTree(root);
        List<Integer> res = boundaryTraversal(root);
        System.out.println(res);
    }

    private List<Integer> boundaryTraversal(TreeNode root) {
        return null;
    }

}
