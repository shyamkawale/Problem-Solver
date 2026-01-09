package Patterns.Logic_Building.Count_Special_Subsequences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Count_Special_Subsequences extends ProblemSolver {

    public static void main(String[] args) {
        new Count_Special_Subsequences().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] nums = DataConvertor.toIntArray(args[0]);

        long res1 = numberOfSubsequences_rec(nums);
        long res2 = numberOfSubsequences_bruteforce(nums);
        long res3 = numberOfSubsequences_meetinmiddle(nums);
        System.out.println(res1 + " " + res2 + " " + res3);
    }

    public long numberOfSubsequences_rec(int[] nums) {
        return helper(nums, 0, new ArrayList<>());
    }

    // TC: O(2^n)
    public long helper(int[] nums, int idx, List<Integer> list) {
        if (list.size() == 4) {
            if (list.get(0) * list.get(2) == list.get(1) * list.get(3)) {
                return 1;
            }
            return 0;
        }

        if (idx >= nums.length) {
            return 0;
        }

        long notPick = helper(nums, idx + 1, list);

        long pick = 0;
        list.add(nums[idx]);
        pick = pick + helper(nums, idx + 2, list);
        list.removeLast();

        return notPick + pick;
    }

    // TC: O(n^4)
    public long numberOfSubsequences_bruteforce(int[] nums) {
        int n = nums.length;
        long count = 0;

        for(int r=4; r<n-2; r++) {
            for(int s=r+2; s<n; s++) {
                int nr = nums[r];
                int ns = nums[s];

                for(int p=0; p<=r-4; p++) {
                    for(int q=p+2; q<=r-2; q++) {
                        int np = nums[p];
                        int nq = nums[q];

                        if(np*nr == nq*ns) {
                            count++;
                        }
                    }
                }
            }
        }
        
        return count;
    }

    // O(n^2te)
    public long numberOfSubsequences_meetinmiddle(int[] nums) {
        int n = nums.length;
        long count = 0;

        Map<Double, Integer> ratioCounts = new HashMap<>();

        for(int r=4; r<n-2; r++) {
            int q = r-2;
            for(int p=0; p<=q-2; p++) {
                int np = nums[p];
                int nq = nums[q];

                double key = (double)np/nq;

                ratioCounts.put(key, ratioCounts.getOrDefault(key, 0)+1);
            }

            for(int s=r+2; s<n; s++) {
                int ns = nums[s];
                int nr = nums[r];
                
                double key = (double)ns/nr;

                count = count + ratioCounts.getOrDefault(key, 0);
            }
        }
        
        return count;
    }

}
