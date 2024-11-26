package Patterns.Sliding_Window.Maximum_Number_of_Occurrences_of_a_Substring;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Maximum_Number_of_Occurrences_of_a_Substring extends ProblemSolver{

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        int maxLetters = DataConvertor.toInt(args[1]);
        int minSize = DataConvertor.toInt(args[2]);
        int maxSize = DataConvertor.toInt(args[3]);

        int result = maxFreq(s, maxLetters, minSize, maxSize);
        System.out.println(result);
    }

    public static void main(String[] args) {
        new Maximum_Number_of_Occurrences_of_a_Substring().readInput();
    }
    
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int len = s.length();
        Map<String, Integer> freqmap = new HashMap<String, Integer>();
        int maxCount = 0;
        for(int k = minSize; k <= maxSize; k++){
            int[] freqArr = new int[26];
            int uniqueCount = 0;
            int start = 0;
            int end = 0;
            while(end < len){
                freqArr[s.charAt(end)-'a']++;
                if (freqArr[s.charAt(end) - 'a'] == 0) uniqueCount++;
                if(end-start+1 == k){
                    String winStr = s.substring(start, end+1);
                    if(uniqueCount <= maxLetters){
                        freqmap.put(winStr, freqmap.getOrDefault(winStr, 0)+1);
                        maxCount = Math.max(maxCount, freqmap.get(winStr));
                    }
                    freqArr[s.charAt(start)-'a']--;
                    if (freqArr[s.charAt(start) - 'a'] == 0) uniqueCount--;
                    start++;
                }
                end++;
            }
        }
        return maxCount;
    }
}
