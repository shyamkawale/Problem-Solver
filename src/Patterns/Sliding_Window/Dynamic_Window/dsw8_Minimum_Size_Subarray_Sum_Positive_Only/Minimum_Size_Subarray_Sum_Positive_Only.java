package Patterns.Sliding_Window.Dynamic_Window.dsw8_Minimum_Size_Subarray_Sum_Positive_Only;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/minimum-size-subarray-sum/ [POSITIVE NUMBERS]

 */
public class Minimum_Size_Subarray_Sum_Positive_Only extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Size_Subarray_Sum_Positive_Only().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int target = DataConvertor.toInt(args[1]);

        int res = minSubArrayLen(nums, target);
        System.out.println(res);
    }

    public int minSubArrayLen(int[] nums, int target) {
        int start = 0;
        int end = 0;
        int minWinSize = Integer.MAX_VALUE;
        int sum = 0;

        while(end < nums.length){
            int endElem = nums[end];

            sum = sum + endElem;

            if(sum < target){
                end++;
            }
            else if(sum >= target){
                while(sum >= target){
                    minWinSize = Math.min(minWinSize, end-start+1);
                    int startElem = nums[start];
                    sum = sum - startElem;
                    start++;
                }
                end++;
            }
        }

        return minWinSize == Integer.MAX_VALUE ? 0 : minWinSize;
    }
}
