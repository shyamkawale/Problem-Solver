package Patterns.Dynamic_Programming.DP_Using_Map.Longest_String_Chain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/longest-string-chain/

You are given an array of words where each word consists of lowercase English letters.

wordA is a predecessor of wordB if and only if we can insert exactly one letter anywhere in wordA 
without changing the order of the other characters to make it equal to wordB.

For example, "abc" is a predecessor of "abac", while "cba" is not a predecessor of "bcad".
A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1, where word1 is a predecessor of word2, 
word2 is a predecessor of word3, and so on. A single word is trivially a word chain with k == 1.

Return the length of the longest possible word chain with words chosen from the given list of words.

Example 1:
Input: words = ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: One of the longest word chains is ["a","ba","bda","bdca"].

Example 2:
Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"]
Output: 5
Explanation: All the words can be put in a word chain ["xb", "xbc", "cxbc", "pcxbc", "pcxbcf"].
*/
public class Longest_String_Chain extends ProblemSolver {

    public static void main(String[] args) {
        new Longest_String_Chain().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String[] words = DataConvertor.toStringArray(args[0]);

        int res1 = longestStrChain_DP1(words);
        int res2 = longestStrChain_DPlike(words);
        System.out.println(res1 + " " + res2);
    }

    public int longestStrChain_DP1(String[] words) {
        int ans = 0;
        Set<String> set = new HashSet<>();
        Map<String, Integer> map = new HashMap<>();

        for (String word : words) {
            set.add(word);
        }

        for (String word : words) {
            ans = Math.max(ans, helper(map, set, word));
        }
        return ans;
    }

    private int helper(Map<String, Integer> map, Set<String> set, String word) {
        if (map.containsKey(word)) {
            return map.get(word);
        }

        int cnt = 0;
        for (int idx = 0; idx < word.length(); idx++) {
            String potPredWord = word.substring(0, idx) + word.substring(idx + 1);
            if (set.contains(potPredWord)) {
                cnt = Math.max(cnt, helper(map, set, potPredWord));
            }
        }

        map.put(word, cnt + 1);
        return cnt + 1;
    }


    public int longestStrChain_DPlike(String[] words) {
        int len = words.length;

        Arrays.sort(words, (a, b) -> Integer.compare(a.length(), b.length()));

        Map<String, Integer> predCntMap = new HashMap<>();

        int bestPredCnt = 1;

        for(String word: words) {
            int currBestPredCnt = 1;
            for(int idx=0; idx<word.length(); idx++) {
                String potPredWord = word.substring(0, idx) + word.substring(idx+1);

                if(predCntMap.containsKey(potPredWord)) {
                    currBestPredCnt = Math.max(currBestPredCnt, predCntMap.get(potPredWord)+1);
                }
            }

            predCntMap.put(word, currBestPredCnt);

            bestPredCnt = Math.max(bestPredCnt, currBestPredCnt);
        }

        return bestPredCnt;
    }

}
