package Patterns.Trees.Binary_Trees.BT9_Graph.Amount_of_Time_for_Binary_Tree_to_Be_Infected;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper;

/*
https://leetcode.com/problems/amount-of-time-for-binary-tree-to-be-infected/

You are given the root of a binary tree with unique values, and an integer start. 
At minute 0, an infection starts from the node with value start.

Each minute, a node becomes infected if:

The node is currently uninfected.
The node is adjacent to an infected node.
Return the number of minutes needed for the entire tree to be infected.

Input: root = [1,5,3,null,4,10,6,9,2], start = 3
Output: 4
Explanation: The following nodes are infected during:
- Minute 0: Node 3
- Minute 1: Nodes 1, 10 and 6
- Minute 2: Node 5
- Minute 3: Node 4
- Minute 4: Nodes 9 and 2
It takes 4 minutes for the whole tree to be infected so we return 4.

Input: root = [1], start = 1
Output: 0
Explanation: At minute 0, the only node in the tree is infected so we return 0.
*/
public class Amount_of_Time_for_Binary_Tree_to_Be_Infected extends ProblemSolver {

    public static void main(String[] args) {
        new Amount_of_Time_for_Binary_Tree_to_Be_Infected().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int start = DataConvertor.toInt(args[1]);

        int res = amountOfTime(root, start);
        System.out.println(res);
    }

    Map<Integer, List<Integer>> adjList = new HashMap<>();

    public int amountOfTime(TreeNode root, int start) {
        createUndirectedGraph(root, null);

        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> vis = new HashSet<>();

        queue.offer(start);
        vis.add(start);

        int minute = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                int polledNode = queue.poll();

                for (int neighbor : adjList.get(polledNode)) {
                    if (!vis.contains(neighbor)) {
                        queue.offer(neighbor);
                        vis.add(neighbor);
                    }
                }
            }
            minute++;
        }

        return minute - 1;
    }

    private void createUndirectedGraph(TreeNode root, TreeNode parent) {
        if (root == null) {
            return;
        }

        adjList.putIfAbsent(root.val, new ArrayList<>());
        if (parent != null) {
            adjList.putIfAbsent(parent.val, new ArrayList<>());
            adjList.get(root.val).add(parent.val);
            adjList.get(parent.val).add(root.val);
        }

        createUndirectedGraph(root.left, root);
        createUndirectedGraph(root.right, root);
    }

}
