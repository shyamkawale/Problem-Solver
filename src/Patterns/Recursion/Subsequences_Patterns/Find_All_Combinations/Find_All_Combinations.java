package Patterns.Recursion.Subsequences_Patterns.Find_All_Combinations;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
similar problem: https://leetcode.com/problems/combinations/
 */
public class Find_All_Combinations extends ProblemSolver {
    public static void main(String[] args) {
        new Find_All_Combinations().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        List<List<Integer>> res1 = findAllCombinations1(nums, k);
        List<List<Integer>> res2 = findAllCombinations2(nums, k);
        System.out.println(res1);
        System.out.println(res2);
    }

    private List<List<Integer>> findAllCombinations1(int[] nums, int k) {
        List<List<Integer>> res = new ArrayList<>();
        helper1(nums, 0, k, new ArrayList<Integer>(), res);
        return res;
    }

    private void helper1(int[] nums, int idx, int k, List<Integer> list, List<List<Integer>> res) {
        if(list.size() == k){
            res.add(new ArrayList<>(list));
            return;
        }

        for(int i=idx; i<nums.length; i++){
            list.add(nums[i]);
            helper1(nums, i+1, k, list, res);
            list.removeLast();
        }
    }

    private List<List<Integer>> findAllCombinations2(int[] nums, int k) {
        List<List<Integer>> res = new ArrayList<>();
        helper2(nums, 0, k, new ArrayList<Integer>(), res);
        return res;
    }

    private void helper2(int[] nums, int idx, int k, List<Integer> list, List<List<Integer>> res) {
        // Target + Base Condition
        if(list.size() == k){
            res.add(new ArrayList<>(list));
            return;
        }

        // Base Condition
        if(idx == nums.length){
            return;
        }

        list.add(nums[idx]);
        helper2(nums, idx+1, k, list, res);

        list.removeLast();
        helper2(nums, idx+1, k, list, res);
    }

}
