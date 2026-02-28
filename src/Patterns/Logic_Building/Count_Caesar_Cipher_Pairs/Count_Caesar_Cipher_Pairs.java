package Patterns.Logic_Building.Count_Caesar_Cipher_Pairs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/count-caesar-cipher-pairs/description/

You are given an array words of n strings. Each string has length m and contains only lowercase English letters.

Two strings s and t are similar if we can apply the following operation any number of times (possibly zero times) so that s and t become equal.

Choose either s or t.
Replace every letter in the chosen string with the next letter in the alphabet cyclically. The next letter after 'z' is 'a'.
Count the number of pairs of indices (i, j) such that:

1. i < j
2. words[i] and words[j] are similar.
Return an integer denoting the number of such pairs

Example 1:
Input: words = ["fusion","layout"]
Output: 1

Example 2:
Input: words = ["ab","aa","za","aa"]
Output: 2
*/
public class Count_Caesar_Cipher_Pairs extends ProblemSolver {

    public static void main(String[] args) {
        new Count_Caesar_Cipher_Pairs().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String words[] = DataConvertor.toStringArray(args[0]);

        long res1 = countPairs_BruteForce(words);
        long res2 = countPairs_BruteForce(words);
        System.out.println(res1 + " " + res2);
    }

    // TC: O(n*n*m)
    public long countPairs_BruteForce(String[] words) {
        int n = words.length;
        long ans = 0L;
        Set<Integer> vis = new HashSet<>();

        for(int i=0; i<n; i++) {
            if(vis.contains(i)) continue;
            vis.add(i);
            
            String s = words[i];
            long cnt = 0L;
            for(int j=i+1; j<n; j++) {
                if(vis.contains(j)) continue;
                String t = words[j];

                if(isSimilar(s, t)) {
                    vis.add(j);
                    cnt++;
                }
            }

            // System.out.println(i + " : " + cnt + " " + vis);
            ans = ans + (cnt*(cnt+1))/2;
        }

        return ans;
    }

    private boolean isSimilar(String s, String t) {
        int diff = (t.charAt(0) - s.charAt(0) + 26)%26;
        for(int k=1; k<s.length(); k++) {
            int currDiff = (t.charAt(k) - s.charAt(k) + 26)%26;
            if(diff != currDiff) {
                return false;
            }
        }

        return true;
    }

    /*
    APPROACH 2: Using freqMap and logic:

    If we find s[0]-s[1], s[1]-s[2],...... for any string then similar string will be same..

    Example:
    If Word1 = [p, q, r, s, ....]
    If Word2 = [p+10, q+10, r+10, s+10, .....]

    Word1 -> [p-q,q-r,r-s...]
    Word2 -> [(p+10)-(q+10),(q+10)-(r+10),(r+10)-(s+10)..] => [p-q,q-r,r-s..]
    */
    // TC: O(n*m)
    public long countPairs(String[] words) {
        int n = words.length;
        long ans = 0L;
        Map<String, Integer> freqMap = new HashMap<>();

        for(String word: words) {
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<word.length()-1; i++) {
                char ch = (char) ((word.charAt(i) - word.charAt(i+1) + 26) % 26 + 'a');

                sb.append(ch);
            }

            freqMap.put(sb.toString(), freqMap.getOrDefault(sb.toString(), 0)+1);
        }

        for(int freq: freqMap.values()) {
            if(freq > 1) {
                ans = ans + (long) freq * ((long) freq - 1) / 2;
            }
        }

        return ans;
    }
}
