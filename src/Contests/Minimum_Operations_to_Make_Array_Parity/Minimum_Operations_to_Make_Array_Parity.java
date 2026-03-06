package Contests.Minimum_Operations_to_Make_Array_Parity;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Minimum_Operations_to_Make_Array_Parity extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Operations_to_Make_Array_Parity().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        int[] res = makeParityAlternating(nums);
        System.out.println(Arrays.toString(res));
    }

    public int[] makeParityAlternating(int[] nums) {
        if(nums.length <= 1) {
            return new int[] {0, 0};
        }
        int[] res0 = evaluate(nums, 0);
        int[] res1 = evaluate(nums, 1);

        // System.out.println(Arrays.toString(res0));
        // System.out.println(Arrays.toString(res1));

        if(res0[0] < res1[0]) return res0;
        if(res1[0] < res0[0]) return res1;

        return res0[1] < res1[1] ? res0 : res1;
    }

    private int[] evaluate(int[] nums, int parity) {
        int len = nums.length;
        int ops = 0;
        int maxi = Integer.MIN_VALUE;
        int mini = Integer.MAX_VALUE;
        for(int n: nums) {
            maxi = Math.max(maxi, n);
            mini = Math.min(mini, n);
        }

        double mid = mini + (maxi - mini) / 2.0;
        
        int finalMax = Integer.MIN_VALUE;
        int finalMin = Integer.MAX_VALUE;

        // mini --------0--------maxi
        // System.out.println(mini + " " + maxi);
        for(int i=0; i<len; i++) {
            int curr = nums[i];
            int reqParity = (i%2 == 0) ? parity : 1 - parity;
            
            if(Math.abs(curr % 2) != reqParity) {
                ops++;
                if(curr < mid) {
                    curr++;
                }
                else {
                    curr--;
                }
            }
            
            finalMin = Math.min(finalMin, curr);
            finalMax = Math.max(finalMax, curr);
        }

        return new int[]{ops, finalMax-finalMin};
    }


    public int minimumBitwiseOr(int[][] grid) {
        int mask = 0;
        
        for (int i = 30; i >= 0; i--) {
            int testMask = mask | (1 << i);
            boolean allRowsValid = true;
            
            for (int[] row : grid) {
                boolean foundInRow = false;

                for (int val : row) {
                    if ((val & testMask) == 0) {
                        foundInRow = true;
                        break;
                    }
                }

                if (!foundInRow) {
                    allRowsValid = false;
                    break;
                }
            }

            if (allRowsValid) {
                mask = testMask;
            }
        }

        return Integer.MAX_VALUE ^ mask;
    }

}
