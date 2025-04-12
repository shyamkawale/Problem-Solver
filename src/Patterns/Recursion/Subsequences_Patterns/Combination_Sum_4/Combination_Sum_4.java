package Patterns.Recursion.Subsequences_Patterns.Combination_Sum_4;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/combination-sum-iv/description/
Recursive solution will give me Memory Limit Exceeded
Optimized Solution: Dynamic Programming problem
 */
public class Combination_Sum_4 extends ProblemSolver {

    public static void main(String[] args) {
        new Combination_Sum_4().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        int res1 = combinationSum4(nums, target);

        System.out.println(res1);
    }

    public int combinationSum4(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        helper(nums, 0, target, new ArrayList<Integer>(), res);
        return res.size();
    }

    private void helper(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res){
        if(target < 0){
            return;
        }

        if(target == 0){
            res.add(list);
        }

        for(int i=0; i<nums.length; i++){
            list.add(nums[i]);
            helper(nums, i, target-nums[i], list, res);
            list.removeLast();
        }
    }

}
