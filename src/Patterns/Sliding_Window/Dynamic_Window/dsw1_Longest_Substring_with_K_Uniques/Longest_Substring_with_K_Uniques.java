package Patterns.Sliding_Window.Dynamic_Window.dsw1_Longest_Substring_with_K_Uniques; 
 
import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 

/*
https://www.geeksforgeeks.org/problems/longest-k-unique-characters-substring0853/1
Given a string s, you need to print the size of the longest possible substring with exactly k unique characters. If no possible substring exists, print -1.
Input: s = "aabacbebebe", k = 3
Output: 7
Explanation: "cbebebe" is the longest substring with 3 distinct characters.

Concept: Sliding Window + freqArr + uniqueCnt
Return: Longest Window Length
 */
public class Longest_Substring_with_K_Uniques extends ProblemSolver { 
    public static void main(String[] args) { 
        new Longest_Substring_with_K_Uniques().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String str = DataConvertor.toString(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res = longestkSubstr(str, k); 
        System.out.println(res); 
    } 
 
    //TC: O(n + n) => O(n)
    //SC: O(26) => O(1)
    public int longestkSubstr(String s, int k) {
        int maxLen = -1;
        
        int[] charFreq = new int[26];
        int uniqueCnt = 0;
        int start = 0;
        int end = 0;
        while(end < s.length()){
            if(charFreq[s.charAt(end) - 'a'] == 0) uniqueCnt++;
            charFreq[s.charAt(end) - 'a']++;
            
            if(uniqueCnt < k){ // when cond < k
                end++;
            }
            if(uniqueCnt == k){ // when cond == k
                maxLen = Math.max(maxLen, end-start+1); //store ans
                end++;
            }
            if(uniqueCnt > k){ // when cond > k
                while(uniqueCnt > k){ // remove start till cond <= k
                    charFreq[s.charAt(start) - 'a']--;
                    if(charFreq[s.charAt(start) - 'a'] == 0) uniqueCnt--;
                    start++;
                }

                // remove end to revaluate window (as now cond <= k)
                charFreq[s.charAt(end) - 'a']--;
                if(charFreq[s.charAt(end) - 'a'] == 0) uniqueCnt--;
            }
        }
        return maxLen;
    }
} 
