package Patterns.Recursion.Subsequences_Patterns.Subsequences_With_Target;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
input = Distinct + Duplicate (each elem is treated different)
output = No repetition allowed (exception with Duplicate elems as each elem is treated different)
 */
public class Subsequences_With_Target extends ProblemSolver {
    public static void main(String[] args) {
        new Subsequences_With_Target().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        List<List<Integer>> res1 = findSubsequencesWithTarget1(nums, target);
        List<List<Integer>> res2 = findSubsequencesWithTarget2(nums, target);
        System.out.println(res1);
        System.out.println(res2);
    }

    private List<List<Integer>> findSubsequencesWithTarget1(int[] nums, int target) {

        List<List<Integer>> res = new ArrayList<>();
        helper1(nums, 0, target, new ArrayList<>(), res);
        return res;
    }

    private void helper1(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res) {
        // Target/Goal Condition
        if(target == 0){
            res.add(new ArrayList<>(list));
        }

        // Base Condition
        if(idx >= nums.length){
            return;
        }

        for(int i=idx; i<nums.length; i++){
            list.add(nums[i]);
            helper1(nums, i+1, target-nums[i], list, res);
            list.removeLast();
        }
    }

    private List<List<Integer>> findSubsequencesWithTarget2(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        helper2(nums, 0, target, new ArrayList<>(), res);

        return res;
    }

    private void helper2(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res) {
        // Base Condition
        if(idx == nums.length){
            // Target Condition
            if(target == 0){
                res.add(new ArrayList<>(list));
            }
            return;
        }

        list.add(nums[idx]);
        helper2(nums, idx+1, target-nums[idx], list, res);

        list.removeLast();
        helper2(nums, idx+1, target, list, res);
    }


}
