package Patterns.Logic_Building.Remove_Duplicates_from_Sorted_Array_II;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Remove_Duplicates_from_Sorted_Array_II extends ProblemSolver {

    public static void main(String[] args) {
        new Remove_Duplicates_from_Sorted_Array_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int res = removeDuplicates(nums);
        System.out.println(res);
    }

    public int removeDuplicates(int[] nums) {
        int uniqueCnt = 0;

        int i = 0;
        int cnt = 1;
        for (int j = 1; j < nums.length; j++) {
            cnt++;
            if (nums[i] == nums[j] && cnt == 2) {
                i++;
                nums[i] = nums[j];
            } else if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
                cnt = 1;
            }
        }

        uniqueCnt = i + 1;
        for (int j = i + 1; j < nums.length; j++) {
            nums[j] = 0;
        }

        return uniqueCnt;
    }

}
