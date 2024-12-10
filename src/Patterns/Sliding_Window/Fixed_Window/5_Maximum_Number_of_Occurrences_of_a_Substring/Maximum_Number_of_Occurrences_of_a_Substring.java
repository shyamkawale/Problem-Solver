package Patterns.Sliding_Window.Fixed_Window.Maximum_Number_of_Occurrences_of_a_Substring;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/description/
Given a string s, return the maximum number of occurrences of any substring under the following rules:
1) The number of unique characters in the substring must be less than or equal to maxLetters.
2) The substring size must be between minSize and maxSize inclusive.
Input: s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
Output: 2
Explanation: Substring "aab" has 2 occurrences in the original string.
It satisfies the conditions, 2 unique letters and size 3 (between minSize and maxSize).

Concept: Fixed_SlidingWindow + uniqueCount + freqMap
 */
public class Maximum_Number_of_Occurrences_of_a_Substring extends ProblemSolver{
    public static void main(String[] args) {
        new Maximum_Number_of_Occurrences_of_a_Substring().readInput();
    }
    @Override
    public void processParameters(String[] args) {
        String str = DataConvertor.toString(args[0]);
        int maxLetters = DataConvertor.toInt(args[1]); // max Unique Characters allowed in window
        int minSize = DataConvertor.toInt(args[2]); // min Size window possible
        int maxSize = DataConvertor.toInt(args[3]); // max Size window possible

        int result = maxFreq(str, maxLetters, minSize, maxSize);
        System.out.println(result);
    }
    
    //TC: O((maxSize-minSize+1)*n)
    //SC: O(26)[freqMap] + O(n)[eligibleSubstringCntMap] => O(26 + n) => O(n)
    public int maxFreq(String str, int maxLetters, int minSize, int maxSize) {
        int len = str.length();
        Map<String, Integer> eligibleSubstringCntMap = new HashMap<String, Integer>(); // to store occurences(count) of eligible substring
        int maxCount = 0;

        for(int k = minSize; k <= maxSize; k++){
            int[] freqArr = new int[26];
            int uniqueCount = 0;
            int start = 0;
            int end = 0;
            while(end < len){
                freqArr[str.charAt(end)-'a']++;
                if (freqArr[str.charAt(end) - 'a'] == 0) uniqueCount++;

                if(end-start+1 < k){ //window size < k
                    end++;
                } 
                else if(end-start+1 == k){ //window is set
                    String winStr = str.substring(start, end+1);
                    if(uniqueCount <= maxLetters){
                        eligibleSubstringCntMap.put(winStr, eligibleSubstringCntMap.getOrDefault(winStr, 0)+1);
                        maxCount = Math.max(maxCount, eligibleSubstringCntMap.get(winStr));
                    }

                    freqArr[str.charAt(start)-'a']--;
                    if (freqArr[str.charAt(start) - 'a'] == 0) uniqueCount--;
                    start++;
                    end++;
                }
            }
        }
        return maxCount;
    }
}
