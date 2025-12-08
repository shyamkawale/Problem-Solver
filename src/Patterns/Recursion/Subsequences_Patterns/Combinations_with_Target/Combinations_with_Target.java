package Patterns.Recursion.Subsequences_Patterns.Combinations_with_Target;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
Real Combinations + target
 */
public class Combinations_with_Target extends ProblemSolver {
    public static void main(String[] args) {
        new Combinations_with_Target().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);
        int target = DataConvertor.toInt(args[2]);

        List<List<Integer>> res1 = findCombinationsWithTarget1(nums, k, target);
        List<List<Integer>> res2 = findCombinationsWithTarget2(nums, k, target);
        System.out.println(res1);
        System.out.println(res2);
    }

    private List<List<Integer>> findCombinationsWithTarget1(int[] nums, int k, int target) {
        List<List<Integer>> res = new ArrayList<>();
        helper1(nums, 0, k, target, new ArrayList<Integer>(), res);
        return res;
    }

    private void helper1(int[] nums, int idx, int k, int target, List<Integer> list, List<List<Integer>> res) {
        if(list.size() == k){
            if(target == 0){
                res.add(new ArrayList<>(list));
            }
            return;
        }

        for(int i=idx; i<nums.length; i++){
            list.add(nums[i]);
            helper1(nums, i+1, k, target-nums[i], list, res);
            list.removeLast();
        }
    }

    private List<List<Integer>> findCombinationsWithTarget2(int[] nums, int k, int target) {
        List<List<Integer>> res = new ArrayList<>();
        helper2(nums, 0, k, target, new ArrayList<Integer>(), res);
        return res;
    }

    private void helper2(int[] nums, int idx, int k, int target, List<Integer> list, List<List<Integer>> res) {
        if(list.size() == k){
            if(target == 0){
                res.add(new ArrayList<>(list));
            }
            return;
        }

        if(idx == nums.length){
            return;
        }

        list.add(nums[idx]);
        helper2(nums, idx+1, k, target-nums[idx], list, res);

        list.removeLast();
        helper2(nums, idx+1, k, target, list, res);
    }

}
