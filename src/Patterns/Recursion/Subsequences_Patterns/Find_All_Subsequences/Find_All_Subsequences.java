package Patterns.Recursion.Subsequences_Patterns.Find_All_Subsequences;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/subsets/description/

Sol1: Iteractive Cascading Approach
Sol2: Recursion with loop
Sol3: Recursion with Binary Decision
Sol4: BitMasking
 */
public class Find_All_Subsequences extends ProblemSolver {
    public static void main(String[] args) {
        new Find_All_Subsequences().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        List<List<Integer>> res1 = findSubsequences1(nums);
        List<List<Integer>> res2 = findSubsequences2(nums);
        List<List<Integer>> res3 = findSubsequences3(nums);
        List<List<Integer>> res4 = findSubsequences3(nums);
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
        System.out.println(res4);
    }

    // Cascading Technique
    // TC: O((2^n-1) * n)
    // for every nth element, inner for loop will run for 2^n,
    // so, for element at idx 0,    1,  2, ...... n-1
    // for loop will run      2^0   2^1 2^2 ..... 2^(n-1) = 2^n - 1
    // and for every iteration it takes O(n) bcz of list.addAll(Collection<> c)

    // SC: O(2^n * n)
    private List<List<Integer>> findSubsequences1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());

        for (int n : nums) {
            List<List<Integer>> newSubsets = new ArrayList<>();
            for (List<Integer> list : res) {
                List<Integer> temp = new ArrayList<>(list);
                temp.add(n);
                newSubsets.add(new ArrayList<>(temp));
            }

            res.addAll(newSubsets); //O(n)
        }

        return res;
    }

    // Recursion using for loop
    // TC: O(2^n) - how?? pattern identification (see book)
    // SC: O(2^n * n) + RecO(n) - max depth of recursion tree is n as n elements
    private List<List<Integer>> findSubsequences2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        helper2(nums, 0, new ArrayList<>(), res);

        return res;
    }

    private void helper2(int[] nums, int idx, List<Integer> list, List<List<Integer>> res) {
        res.add(new ArrayList<>(list));

        if(idx >= nums.length){
            return;
        }

        for(int i=idx; i<nums.length; i++){
            list.add(nums[i]);
            helper2(nums, i+1, list, res);
            list.removeLast();
        }
    }

    // Recursion using Binary decision (take or not-take)
    // TC: O(2^n) - forms a binary tree (where each level is associated with 1 element with 2 choices( take or not-take))
    // SC: O(2^n * n) + RecO(n) - depth of Binary tree will be n
    private List<List<Integer>> findSubsequences3(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        helper3(nums, 0, new ArrayList<>(), res);

        return res;
    }

    private void helper3(int[] nums, int idx, List<Integer> list, List<List<Integer>> res) {
        if(idx == nums.length){
            res.add(new ArrayList<>(list));
            return;
        }

        list.add(nums[idx]);
        helper3(nums, idx+1, list, res);

        list.removeLast();
        helper3(nums, idx+1, list, res);
    }

    // Bitmask approach
    public List<List<Integer>> findSubsequences4(int[] nums) {
        List<List<Integer>> output = new ArrayList<>();
        int n = nums.length;

        for (int i = (int) Math.pow(2, n); i < (int) Math.pow(2, n + 1); ++i) {
            // generate bitmask, from 0..00 to 1..11
            String bitmask = Integer.toBinaryString(i).substring(1);

            // append subset corresponding to that bitmask
            List<Integer> curr = new ArrayList<>();
            for (int j = 0; j < n; ++j) {
                if (bitmask.charAt(j) == '1') curr.add(nums[j]);
            }
            output.add(curr);
        }
        return output;
    }

}
