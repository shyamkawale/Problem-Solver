package Patterns.Trees.Binary_Trees.BT3_DFS.Distribute_Coins_in_Binary_Tree;

import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/distribute-coins-in-binary-tree/

You are given the root of a binary tree with n nodes where each node in the tree has node.val coins. 
There are n coins in total throughout the whole tree.

In one move, we may choose two adjacent nodes and move one coin from one node to another. 
A move may be from parent to child, or from child to parent.

Return the minimum number of moves required to make every node have exactly one coin.

Example 1:
Input: root = [3,0,0]
Output: 2
Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right child.

Example 2:
Input: root = [0,3,0]
Output: 3
Explanation: From the left child of the root, we move two coins to the root [taking two moves]. 
Then, we move one coin from the root of the tree to the right child.

Constraints:
The number of nodes in the tree is n.
1 <= n <= 100
0 <= Node.val <= n
The sum of all Node.val is n.
*/
public class Distribute_Coins_in_Binary_Tree extends ProblemSolver {

    public static void main(String[] args) {
        new Distribute_Coins_in_Binary_Tree().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);

        int res = distributeCoins(root);
        System.out.println(res);
    }

    private int totMoves;
    public int distributeCoins(TreeNode root) {
        totMoves = 0;
        dfs(root);
        return totMoves;
    }

    // returns the balance of coins at the current node
    // positive(+ve) means the current node has more coins than needed
    // negative(-ve) means the current node has less coins than needed
    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftBalance = dfs(root.left);
        int rightBalance = dfs(root.right);

        // Ex: if leftBalance is -1, it means parent needs to give 1 coin to the left child
        // Ex: if rightBalance is +2, it means parent needs to take 2 coin from the right child
        // So, the total moves for a node means total moves between parent and its children.. (|leftBal| + |rightBal|)

        // The moves across the edges to the left and right children
        totMoves += Math.abs(leftBalance) + Math.abs(rightBalance);

        // The balance to pass up to the parent
        return root.val + leftBalance + rightBalance - 1;
    }

}
