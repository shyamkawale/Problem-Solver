package Patterns.Prefix_Sum.Maximum_Subarray_Sum_With_Length_Divisible_by_K; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
// INCOMPLETE SOLUTION
public class Maximum_Subarray_Sum_With_Length_Divisible_by_K extends ProblemSolver { 
    public static void main(String[] args) { 
        new Maximum_Subarray_Sum_With_Length_Divisible_by_K().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] nums = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res = maxSubarraySum(nums, k); 
        System.out.println(res); 
    } 
 
    public int maxSubarraySum(int[] nums, int k) {
        int len = nums.length;
        int[] P = new int[len+1];
        for(int i=0; i<len; i++){
            P[i+1] = P[i] + nums[i];
        }

        // P[end] - P[start] = maxSum where (end-start)%k = 0
        // P[start] = P[end] - maxSum, end%k = start%k
        Map<Integer, Integer> remIdxMap = new HashMap<Integer, Integer>(); // rem(idx%k) -> idx
        int maxSum = Integer.MIN_VALUE;
        for(int i=0; i<len+1; i++){
            // ps as End
            int ps = P[i];
            int endRem = i%k;

            if(remIdxMap.containsKey(endRem)){
                int startIdx = remIdxMap.get(endRem);
                int winSum = ps - P[startIdx];
                maxSum = Math.max(maxSum, winSum);
            }

            // ps as Start
            remIdxMap.putIfAbsent(endRem, i);
        }

        return maxSum;
    } 
} 
