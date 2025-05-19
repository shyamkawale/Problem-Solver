package Patterns.Recursion.Finding_3Digit_Even_Numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Finding_3Digit_Even_Numbers extends ProblemSolver {

    public static void main(String[] args) {
        new Finding_3Digit_Even_Numbers().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] arr = DataConvertor.toIntArray(args[0]);

        int[] res = findEvenNumbers(arr);
        System.out.println(Arrays.toString(res));
    }

    List<Integer> res;
    Set<Integer> seen;
    public int[] findEvenNumbers(int[] digits) {
        res = new ArrayList<>();
        seen = new HashSet<>();
        Arrays.sort(digits);
        boolean[] visited = new boolean[digits.length];

        helper(digits, 0, 0, visited);

        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public void helper(int[] digits, int val, int length, boolean[] visited){
        if(length == 3){
            // res.contains(val) was slower O(n) always, giving TLE
            // seen.contains(val) is faster O(1) amortized.
            if(val % 2 == 0 && seen.add(val)){
                res.add(val);
            }
            return;
        }

        for(int i=0; i<digits.length; i++){
            // visitedIdx set could be used but slower (becoz of hasing behind the scene and dynamic length)
            // visited boolean is musch faster (fixed length, no hashing)
            if(visited[i]){
                continue;
            }
            if (length == 0 && digits[i] == 0) {
                continue;
            }
            // BONUS for optimization 🚀🚀 (very important !!)
            if (i > 0 && digits[i] == digits[i - 1] && !visited[i - 1]) {
                continue;
            }

            visited[i] = true;
            helper(digits, val*10 + digits[i], length+1, visited);
            visited[i] = false;
        }
    }

}
