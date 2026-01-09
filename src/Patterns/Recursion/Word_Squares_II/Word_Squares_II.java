package Patterns.Recursion.Word_Squares_II;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/word-squares-ii/

topic: BACTRACKING
*/
public class Word_Squares_II extends ProblemSolver {
    public static void main(String[] args) {
        new Word_Squares_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String[] words = DataConvertor.toStringArray(args[0]);

        List<List<String>> res = wordSquares(words);
        System.out.println(res);
    }

    List<List<String>> res = new ArrayList<>();
    public List<List<String>> wordSquares(String[] words) {
        Arrays.sort(words, (a, b) -> a.compareTo(b));
        int len = words.length;
        int[] vis = new int[len];

        for(int i=0; i<len; i++) {
            List<String> ans = new ArrayList<>();
            ans.add(words[i]);
            vis[i] = 1;
            findAns(i, words, vis, ans);
            vis[i] = 0; // backtrack
        }
        return res;
    }

    public void findAns(int idx, String[] words, int[] vis, List<String> ans) {
        if(ans.size() == 4) {
            res.add(new ArrayList<>(ans));
            return;
        }

        for(int i=0; i<words.length; i++) {
            if(vis[i] == 1) continue;

            String nextStr = words[i];
            boolean matched = false;

            if(ans.size() == 1) {
                if(ans.get(0).charAt(0) == nextStr.charAt(0)) {
                    matched = true;
                }
            }
            else if(ans.size() == 2) {
                if(ans.get(0).charAt(3) == nextStr.charAt(0)) {
                    matched = true;
                }
            }
            else if(ans.size() == 3) {
                if(ans.get(1).charAt(3) == nextStr.charAt(0) && ans.get(2).charAt(3) == nextStr.charAt(3)) {
                    matched = true;
                }
            }

            if(matched) {
                ans.add(nextStr);
                vis[i] = 1;
                findAns(i, words, vis, ans);
                vis[i] = 0; // backtrack
                ans.remove(ans.size()-1); // backtrack
            }
        }
    }

}
