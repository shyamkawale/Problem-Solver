import java.util.*;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.LinkedList.*;
import Helpers.DataStructure.Trees.*;

public class DemoProblem extends ProblemSolver {
    @Override
    public void processParameters(String[] args) {
        int[] intArray = DataConvertor.toIntArray(args[0]);
        String string = args[1];
        int intNum = DataConvertor.toInt(args[2]);
        ListNode linkedList = ListWrapper.stringToListNode(args[3]);
        TreeNode tree = TreeWrapper.stringToTreeNode(args[4]);

        functionName(intArray, string, intNum, linkedList, tree);
    }

    public static void main(String[] args) {
        new DemoProblem().readInput();
    }

    public static void functionName(int[] arr, String string, int num, ListNode linkedList, TreeNode tree) {
        System.out.println(Arrays.toString(arr));
        System.out.println(string);
        System.out.println(num);
        ListWrapper.prettyPrintLinkedList(linkedList);
        TreeWrapper.prettyPrintTree(tree);
    }
}
