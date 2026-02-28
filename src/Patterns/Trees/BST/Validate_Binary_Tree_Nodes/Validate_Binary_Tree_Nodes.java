package Patterns.Trees.BST.Validate_Binary_Tree_Nodes;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Validate_Binary_Tree_Nodes extends ProblemSolver {

    public static void main(String[] args) {
        new Validate_Binary_Tree_Nodes().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[] leftChild = DataConvertor.toIntArray(args[1]);
        int[] rightChild = DataConvertor.toIntArray(args[2]);
        boolean res = validateBinaryTreeNodes(n, leftChild, rightChild);
        System.out.println(res);
    }

    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        for(int i=0; i<n; i++) {
            if(leftChild[i] != -1) {
                if(parent[leftChild[i]] != -1) return false;
                parent[leftChild[i]] = i;

                int node = leftChild[i];
                while(parent[node] != -1) {
                    if(parent[node] == leftChild[i]) return false;
                    node = parent[node];
                }
            }
            if(rightChild[i] != -1) {
                if(parent[rightChild[i]] != -1) return false;
                parent[rightChild[i]] = i;

                int node = rightChild[i];
                while(parent[node] != -1) {
                    if(parent[node] == rightChild[i]) return false;
                    node = parent[node];
                }
            }
        }

        int parentLessNode = 0;
        for(int i=0; i<n; i++) {
            if(parent[i] == -1) {
                parentLessNode++;
            }
        }

        return parentLessNode == 1 ? true : false;
    }
}
