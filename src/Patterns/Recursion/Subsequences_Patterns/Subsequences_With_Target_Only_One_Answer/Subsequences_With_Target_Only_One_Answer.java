package Patterns.Recursion.Subsequences_Patterns.Subsequences_With_Target_Only_One_Answer;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;


/*
Only change here is 
1. make helper function return boolean
2. if target condition found => return TRUE
3. if Base condition found => return FALSE
4. if any recusive call => return TRUE if it returns TRUE
5. at the end return FALSE
 */
public class Subsequences_With_Target_Only_One_Answer extends ProblemSolver {
    public static void main(String[] args) {
        new Subsequences_With_Target_Only_One_Answer().readInput();
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

    private boolean helper1(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res) {
        // Target/Goal Condition
        if(target == 0){
            res.add(new ArrayList<>(list));
            return true;
        }

        // Base Condition
        if(idx >= nums.length){
            return false;
        }

        for(int i=idx; i<nums.length; i++){
            list.add(nums[i]);
            if(helper1(nums, i+1, target-nums[i], list, res)){
                return true;
            }
            list.removeLast();
        }

        return false;
    }

    private List<List<Integer>> findSubsequencesWithTarget2(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        helper2(nums, 0, target, new ArrayList<>(), res);

        return res;
    }

    private boolean helper2(int[] nums, int idx, int target, List<Integer> list, List<List<Integer>> res) {
        if(idx == nums.length){
            if(target == 0){
                res.add(new ArrayList<>(list));
                return true;
            }
            return false;
        }

        list.add(nums[idx]);
        if(helper2(nums, idx+1, target-nums[idx], list, res)){
            return true;
        }

        list.removeLast();
        if(helper2(nums, idx+1, target, list, res)){
            return true;
        }

        return false;
    }

}
