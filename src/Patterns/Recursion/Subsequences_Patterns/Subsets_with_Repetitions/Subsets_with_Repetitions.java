package Patterns.Recursion.Subsequences_Patterns.Subsets_with_Repetitions;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;


/*
Combination Sum I (https://leetcode.com/problems/combination-sum/description/)
Input: Distinct + Non-Zero Positive Elements
Output: Subsets + Repetitions
 */
public class Subsets_with_Repetitions extends ProblemSolver {

    public static void main(String[] args) {
        new Subsets_with_Repetitions().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        List<List<Integer>> res1 = findSubsetsWithRepetitionTarget1(nums, target);
        List<List<Integer>> res2 = findSubsetsWithRepetitionTarget2(nums, target);
        System.out.println(res1);
        System.out.println(res2);
    }

    private List<List<Integer>> findSubsetsWithRepetitionTarget1(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        helper1(nums, 0, target, new ArrayList<>(), res);
        return res;
    }

    private void helper1(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res) {
        if(target == 0){
            if(!res.contains(list)){
                res.add(new ArrayList<>(list));
            }
            return;
        }

        if(target < 0){
            return;
        }

        for(int i=idx; i<nums.length; i++){
            list.add(nums[i]);
            helper1(nums, i, target-nums[i], list, res);
            list.removeLast();
        }
    }

    // Diagram in book (little hard to visualize as now level n will not have n include/exclude thing as right brnahces will repeat..)
    private List<List<Integer>> findSubsetsWithRepetitionTarget2(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        helper2(nums, 0, target, new ArrayList<>(), res);
        return res;
    }

    private void helper2(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res) {
        if(idx >= nums.length || target < 0){
            return;
        }
        
        if(target == 0){
            res.add(new ArrayList<>(list));
            return;
        }

        list.add(nums[idx]);
        helper2(nums, idx, target-nums[idx], list, res);

        list.removeLast();
        helper2(nums, idx+1, target, list, res);
    }

}
