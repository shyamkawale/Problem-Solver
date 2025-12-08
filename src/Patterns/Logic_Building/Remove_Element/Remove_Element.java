package Patterns.Logic_Building.Remove_Element;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Remove_Element extends ProblemSolver {

    public static void main(String[] args) {
        new Remove_Element().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int val = DataConvertor.toInt(args[1]);

        int res = removeElement(nums, val);
        System.out.println(res);
    }

    public int removeElement(int[] nums, int val) {
        int len = nums.length;
        int i=0;
        for(int j=0; j<len; j++) {
            if(nums[i] != val) {
                i++;
            }
            else if(nums[j] != val) {
                nums[i] = nums[j];
                nums[j] = val;
                i++;
            }
        }

        return i;
    }
}
