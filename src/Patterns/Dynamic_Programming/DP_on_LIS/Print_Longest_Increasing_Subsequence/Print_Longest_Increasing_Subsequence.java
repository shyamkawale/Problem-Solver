package Patterns.Dynamic_Programming.DP_on_LIS.Print_Longest_Increasing_Subsequence;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Print_Longest_Increasing_Subsequence extends ProblemSolver {

    public static void main(String[] args) {
        new Print_Longest_Increasing_Subsequence().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        List<Integer> res1 = printLIS_rec1(nums);
        List<Integer> res2 = printLIS_rec2(nums);
        List<Integer> res3 = printLIS_memo(nums);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    private List<Integer> printLIS_rec1(int[] nums) {
        int len = nums.length;
        List<Integer> res = new ArrayList<>();
        helper1(0, -1, new ArrayList<>(), res, nums);
        return res;
    }

    private void helper1(int n, int prevIdx, List<Integer> temp, List<Integer> res, int[] nums) {
        if(n >= nums.length) {
            if(res.size() < temp.size()) {
                res.clear();
                res.addAll(temp);
            }
            return;
        }

        // not-pick
        helper1(n+1, prevIdx, temp, res, nums);

        // pick
        if(prevIdx == -1 || nums[n] > nums[prevIdx]) {
            temp.add(nums[n]);
            helper1(n+1, n, temp, res, nums);
            temp.remove(temp.size()-1);
        }
    }

    private List<Integer> printLIS_rec2(int[] nums) {
        int len = nums.length;
        return helper2(0, -1, nums);
    }

    private List<Integer> helper2(int n, int prevIdx, int[] nums) {
        if(n >= nums.length) {
            return new ArrayList<>();
        }

        List<Integer> notPick = helper2(n+1, prevIdx, nums);

        List<Integer> pick = new ArrayList<>();
        if(prevIdx == -1 || nums[n] > nums[prevIdx]) {
            pick.add(nums[n]);
            pick.addAll(helper2(n+1, n, nums));
        }

        return (pick.size() > notPick.size()) ? pick : notPick;
    }

    private List<Integer> printLIS_memo(int[] nums) {
        int len = nums.length;
        List<Integer>[][] dp = new ArrayList[len][len+1];
        return helper3(0, -1, nums);
    }

    private List<Integer> helper3(int n, int prevIdx, int[] nums) {
        if(n >= nums.length) {
            return new ArrayList<>();
        }

        List<Integer> notPick = helper3(n+1, prevIdx, nums);

        List<Integer> pick = new ArrayList<>();
        if(prevIdx == -1 || nums[n] > nums[prevIdx]) {
            pick.add(nums[n]);
            pick.addAll(helper3(n+1, n, nums));
        }

        return (pick.size() > notPick.size()) ? pick : notPick;
    }


}
