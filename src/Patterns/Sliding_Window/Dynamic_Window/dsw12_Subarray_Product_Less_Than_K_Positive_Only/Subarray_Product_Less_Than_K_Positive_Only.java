package Patterns.Sliding_Window.Dynamic_Window.dsw12_Subarray_Product_Less_Than_K_Positive_Only;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/subarray-product-less-than-k/
 */
public class Subarray_Product_Less_Than_K_Positive_Only extends ProblemSolver {
    public static void main(String[] args) {
        new Subarray_Product_Less_Than_K_Positive_Only().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int res = numSubarrayProductLessThanK(nums, k);
        System.out.println(res);
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if(k == 0) return 0;
        int len = nums.length;

        int cnt = 0;
        int start = 0;
        int end = 0;
        long product = 1;
        while(end < len){
            int endElem = nums[end];
            product = product*endElem;

            if(product < k){ // valid window
                cnt = cnt + (end-start+1); // count all subarrays from start to end
                end++;
            }
            else if(product >= k){ // invalid window
                while(start <= end && product >= k){
                    int startElem = nums[start];
                    product = product/startElem;
                    start++;
                }

                // a single element >= k then start will go ahead of end..
                if(start > end){
                    end++;
                }
                else{
                    product = product/endElem;
                }
            }
        }

        return cnt;
    }

}
