package Patterns.Recursion.Subsequences_Patterns.Subsets_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Subsets_2 extends ProblemSolver {
    public static void main(String[] args) {
        new Subsets_2().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        List<List<Integer>> res1 = findSubsetsWithDup(nums);
        System.out.println(res1);
    }

    private List<List<Integer>> findSubsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        helper(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void helper(int[] nums, int idx, List<Integer> list, List<List<Integer>> res) {
        res.add(new ArrayList<>(list));

        if(idx >= nums.length){
            return;
        }

        for(int i=idx; i<nums.length; i++){
            if(i>idx && nums[i] == nums[i-1]) continue;
            list.add(nums[i]);
            helper(nums, i+1, list, res);
            list.removeLast();
        }
    }

}
