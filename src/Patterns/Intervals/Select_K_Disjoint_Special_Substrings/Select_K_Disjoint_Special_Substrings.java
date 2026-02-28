package Patterns.Intervals.Select_K_Disjoint_Special_Substrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Select_K_Disjoint_Special_Substrings extends ProblemSolver {

    public static void main(String[] args) {
        new Select_K_Disjoint_Special_Substrings().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        int k = DataConvertor.toInt(args[1]);

        boolean res = maxSubstringLength(s, k);
        System.out.println(res);
    }

    public boolean maxSubstringLength(String s, int k) {
        if (k == 0) return true;
        int n = s.length();
        
        // 1. Precompute first and last occurrences
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, -1);
        
        for (int i = 0; i < n; i++) {
            int charIdx = s.charAt(i) - 'a';
            if (first[charIdx] == -1) {
                first[charIdx] = i;
            }
            last[charIdx] = i;
        }

        // 2. Generate all VALID intervals
        List<int[]> intervals = new ArrayList<>();
        
        // We only need to check intervals starting at first[char]
        // because a valid special substring MUST start at the first occurrence 
        // of its first character.
        for (int i = 0; i < 26; i++) {
            if (first[i] == -1) continue;
            
            int start = first[i];
            int end = last[i];
            boolean isValid = true;
            
            // Scan and expand
            for (int j = start; j <= end; j++) {
                int charIdx = s.charAt(j) - 'a';
                
                // If this character appears before our start, 
                // then our 'start' cannot be the true beginning of a special substring.
                if (first[charIdx] < start) {
                    isValid = false;
                    break;
                }
                
                // If this character appears after our current end, expand the interval
                end = Math.max(end, last[charIdx]);
            }
            
            // 3. Add to list if valid and not the whole string
            if (isValid) {
                if (end - start + 1 < n) { // Strict inequality: length < n
                    intervals.add(new int[]{start, end});
                }
            }
        }

        for(int[] interval: intervals) {
            System.out.println(Arrays.toString(interval));
        }
        
        // 4. Standard Greedy (Activity Selection Problem)
        // Sort by END time
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));
        
        int count = 0;
        int lastEnd = -1;
        
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            
            // If this interval starts after the previous one ended
            if (start > lastEnd) {
                count++;
                lastEnd = end;
            }
        }
        
        return count >= k;
    }

}
