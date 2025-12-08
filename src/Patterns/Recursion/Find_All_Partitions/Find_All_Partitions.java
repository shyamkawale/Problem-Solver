package Patterns.Recursion.Find_All_Partitions;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Find_All_Partitions extends ProblemSolver {

    public static void main(String[] args) {
        new Find_All_Partitions().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String str = DataConvertor.toString(args[0]);
        int[] nums = DataConvertor.toIntArray(args[1]);

        List<List<String>> res = findALlParititions(str);
        List<List<List<Integer>>> res1 = findALlParititions(nums);
        System.out.println(res);
        System.out.println(res1);
    }

    private List<List<List<Integer>>> findALlParititions(int[] nums) {
        List<List<List<Integer>>> res = new ArrayList<>();
        helper(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void helper(int[] nums, int idx, List<List<Integer>> list, List<List<List<Integer>>> res) {
        if(idx >= nums.length){
            res.add(new ArrayList<>(list));
            return;
        }

        for(int i=idx; i<nums.length; i++){
            List<Integer> subList = new ArrayList<>();
            for(int j=idx; j<=i; j++){
                subList.add(nums[j]);
            }
            list.add(new ArrayList<>(subList));
            helper(nums, i+1, list, res);
            list.removeLast();
        }
    }

    private List<List<String>> findALlParititions(String s) {
        List<List<String>> res = new ArrayList<>();
        helper(s, 0, new ArrayList<>(), res);
        return res;
    }

    private void helper(String s, int idx, List<String> list, List<List<String>> res) {
        if (idx >= s.length()) {
            res.add(new ArrayList<>(list));
        }

        for (int i = idx; i < s.length(); i++) {
            list.add(s.substring(idx, i + 1));
            helper(s, i + 1, list, res);
            list.removeLast();
        }
    }
}
