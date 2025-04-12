package Patterns.Recursion.Subsequences_Patterns.Combination_Sum_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Combination_Sum_2 extends ProblemSolver {

    public static void main(String[] args) {
        new Combination_Sum_2().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        List<List<Integer>> res1 = combinationSum2_method1(nums, target);
        List<List<Integer>> res2 = combinationSum2_method2(nums, target);
        System.out.println(res1);
        System.out.println(res2);
    }

    private List<List<Integer>> combinationSum2_method1(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        helper1(nums, 0, target, new ArrayList<>(), res);
        return res;
    }

    private void helper1(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res) {
        if (target == 0) {
            if (!res.contains(list)) { // ⏲️ instead of this we can add "if(i > idx && nums[i] == nums[i-1]) continue;" inside for loop which will save some time
                res.add(new ArrayList<>(list));
            }
            return;
        }

        if (target < 0) {
            return;
        }

        for (int i = idx; i < nums.length; i++) {
            // if(i > idx && nums[i] == nums[i-1]) continue;
            list.add(nums[i]);
            helper1(nums, i + 1, target - nums[i], list, res);
            list.removeLast();
        }
    }

    // Recursion using Binary Decision (take or not-take)
    private List<List<Integer>> combinationSum2_method2(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        helper2(nums, 0, target, new ArrayList<>(), res);
        return res;
    }

    private void helper2(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res) {
        if (target == 0) {
            if(!res.contains(list)){
                res.add(new ArrayList<>(list));
            }
            return;
        }
        if (idx >= nums.length || target < 0) {
            return;
        }

        list.add(nums[idx]);
        helper2(nums, idx + 1, target - nums[idx], list, res);

        list.removeLast();
        helper2(nums, idx + 1, target, list, res);
    }

}
