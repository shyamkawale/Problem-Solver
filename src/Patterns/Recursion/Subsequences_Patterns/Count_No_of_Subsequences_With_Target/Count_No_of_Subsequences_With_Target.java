package Patterns.Recursion.Subsequences_Patterns.Count_No_of_Subsequences_With_Target;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Count_No_of_Subsequences_With_Target extends ProblemSolver {
    public static void main(String[] args) {
        new Count_No_of_Subsequences_With_Target().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        int res1 = countSubsequencesWithTarget1(nums, target);
        int res2 = countSubsequencesWithTarget2(nums, target);
        System.out.println(res1);
        System.out.println(res2);
    }

    private int countSubsequencesWithTarget1(int[] nums, int target) {
        return helper1(nums, 0, target);
    }

    private int helper1(int[] nums, int idx, int target) {
        int count = 0;
        // Target/Goal Condition
        if (target == 0) {
            count++;
        }

        // Base Condition
        if (idx >= nums.length) {
            return count;
        }

        for (int i = idx; i < nums.length; i++) {
            count += helper1(nums, i + 1, target - nums[i]);
        }

        return count;
    }

    private int countSubsequencesWithTarget2(int[] nums, int target) {
        return helper2(nums, 0, target);
    }

    private int helper2(int[] nums, int idx, int target) {
        // Base Condition
        if (idx == nums.length) {
            // Target Condition
            if (target == 0) {
                return 1;
            }
            return 0;
        }

        int cnt1 = helper2(nums, idx + 1, target - nums[idx]);
        int cnt2 = helper2(nums, idx + 1, target);

        return cnt1 + cnt2;
    }

}
