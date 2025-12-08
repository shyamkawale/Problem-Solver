package Patterns.Heap.Design.Does_Array_Represent_MaxHeap;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Does_Array_Represent_MaxHeap extends ProblemSolver {

    public static void main(String[] args) {
        new Does_Array_Represent_MaxHeap().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        boolean res = doesArrayRepresentMaxHeap(nums);
        System.out.println(res);
    }

    private boolean doesArrayRepresentMaxHeap(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            long parent = nums[i];

            int leftChildIdx = 2 * i + 1;
            int rightChildIdx = 2 * i + 2;

            if ((long) leftChildIdx < n && nums[leftChildIdx] > parent) {
                return false;
            }

            if ((long) rightChildIdx < n && nums[rightChildIdx] > parent) {
                return false;
            }
        }

        return true;
    }

}
