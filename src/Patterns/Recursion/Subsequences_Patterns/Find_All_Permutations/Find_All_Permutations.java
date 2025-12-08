package Patterns.Recursion.Subsequences_Patterns.Find_All_Permutations;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/permutations/


 */
public class Find_All_Permutations extends ProblemSolver {
    public static void main(String[] args) {
        new Find_All_Permutations().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        List<List<Integer>> res = findPermutations(nums);
        System.out.println(res);
    }

    private List<List<Integer>> findPermutations(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        helper(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void helper(int[] nums, int idx, List<Integer> list, List<List<Integer>> res) {
        if(list.size() == nums.length){
            res.add(new ArrayList<>(list));
        }

        if(idx > nums.length){
            return;
        }

        for(int i=0; i<nums.length; i++){
            if(list.contains(nums[i])) continue;
            list.add(nums[i]);
            helper(nums, i+1, list, res);
            list.removeLast();
        }
    }

}
