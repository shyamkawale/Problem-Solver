package Patterns.Trees.Binary_Trees.BT10_Other.All_Possible_Full_BinaryTrees;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

// NOT COMPLETED
public class All_Possible_Full_BinaryTrees extends ProblemSolver {
    public static void main(String[] args) {
        new All_Possible_Full_BinaryTrees().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);

        List<TreeNode> res = allPossibleFBT(n);
        for(TreeNode root: res){
            System.out.println(TreeWrapper.treeNodeToString(root));
        }
    }

    List<TreeNode> res = new ArrayList<>();
    TreeNode root = new TreeNode(0);

    public List<TreeNode> allPossibleFBT(int n) {
        if(n == 0 || n%2 == 0){
            return res;
        }

        helper(root, n-1);
        return res;
    }

    public void helper(TreeNode curr, int n){
        if(n == 0){ // base condition
            if(!res.contains(root)){
                TreeNode fbt = root;
                res.add(fbt);
            }
            return;
        }
        if(n == 1){
            return;
        }

        curr.left = new TreeNode(0);
        curr.right = new TreeNode(0);

        helper(curr.left, n-2);
        curr.left.left = null;
        curr.left.right = null;
        helper(curr.right, n-2);
        curr.right.left = null;
        curr.right.right = null;
    }
}
