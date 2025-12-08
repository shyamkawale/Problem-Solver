package General.Split_Array_With_Minimum_Difference;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Split_Array_With_Minimum_Difference extends ProblemSolver {

    public static void main(String[] args) {
        new Split_Array_With_Minimum_Difference().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        long res = splitArray(nums);
        System.out.println(res);
    }

    public long splitArray(int[] nums) {
        int len = nums.length;
        long lSum = 0;
        long rSum = 0;

        int idx = 0;
        int midIdx = 0;
        
        lSum = lSum + (long)nums[idx];
        idx++;

        while(idx < len-1 && nums[idx-1] < nums[idx]) {
            lSum = lSum + (long)nums[idx];
            idx++;
        }

        midIdx = idx-1;
        // System.out.println(midIdx + " " + nums[midIdx]);

        rSum = rSum + (long)nums[idx];
        idx++;
        while(idx < len && nums[idx-1] > nums[idx]) {
            rSum = rSum + (long)nums[idx];
            idx++;
        }

        if(idx != len) {
            return -1;
        }

        long case1 = (long) Math.abs(lSum - rSum);
         long case2 = (nums[len-2] < nums[len-1]) ? Long.MAX_VALUE : Math.abs(lSum - rSum - 2*(long)nums[midIdx]);
        return Math.min(case1, case2);
    }
}
