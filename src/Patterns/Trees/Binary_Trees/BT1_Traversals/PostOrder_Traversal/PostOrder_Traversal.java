package Patterns.Trees.Binary_Trees.BT1_Traversals.PostOrder_Traversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

public class PostOrder_Traversal extends ProblemSolver {
    public static void main(String[] args) {
        new PostOrder_Traversal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        List<Integer> res1 = new ArrayList<>();
        postOrderTraversal_Rec(root, res1);
        System.out.println(res1);

        List<Integer> res2 = postOrderTraversal_Itr1(root);
        System.out.println(res2);

        List<Integer> res3 = postOrderTraversal_Itr2(root);
        System.out.println(res3);
    }

    // Recursive Approach
    // TC: O(n), where n is no of nodes in tree
    // SC: RecO(h), where h is the height of the tree, log2(n+1)-1 <= h <= n
    private void postOrderTraversal_Rec(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        postOrderTraversal_Rec(root.left, res);
        postOrderTraversal_Rec(root.right, res);
        res.add(root.val);
    }

    // Iterative Approach (PreOrder + Reverse)
    // TC: O(n + n), where n is no of nodes in tree
    // SC: O(h), where h is the height of tree
    public List<Integer> postOrderTraversal_Itr1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack1 = new ArrayDeque<>();
        stack1.push(root);

        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            res.add(node.val);

            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }

        Collections.reverse(res);
        return res;
    }

    // Iterative Approach BETTER (TRICKY!!) 💡
    // TC: O(n), where n is no of nodes in tree
    // SC: O(h), where h is the height of tree
    public static List<Integer> postOrderTraversal_Itr2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        TreeNode lastVisited = null; // for right(i.e we went there for seeking right - tya right la jaun alo aahe apan)

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left; // Move to left subtree first
            }

            TreeNode peekNode = stack.peek();

            // If right child exists and is not processed yet, move to right subtree
            if (peekNode.right != null && peekNode.right != lastVisited) {
                curr = peekNode.right;
            } else {
                // Otherwise, process current node (postorder: left -> right -> root)
                res.add(peekNode.val);
                lastVisited = stack.pop();
            }

        }
        return res;
    }

}
