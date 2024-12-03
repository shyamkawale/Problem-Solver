package Patterns.Sliding_Window.Minimum_Window_Substring;

import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

// https://leetcode.com/problems/minimum-window-substring/description/
public class Minimum_Window_Substring extends ProblemSolver{
    public static void main(String[] args) {
        new Minimum_Window_Substring().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        String t = DataConvertor.toString(args[1]);

        String res = minWindow(s, t);
        System.out.println(res);
    }

    public String minWindow(String s, String t) {
        if(t.length() > s.length()) return "";

        Map<Character, Integer> tMap = new HashMap<Character, Integer>();
        for(char tCh: t.toCharArray()){
            tMap.put(tCh, tMap.getOrDefault(tCh, 0)+1);
        }

        Map<Character, Integer> sMap = new HashMap<Character, Integer>();
        int start = 0;
        int end = 0;
        String minWindow = "";
        int minWindowLen = s.length();
        int matched = 0;

        while(end < s.length()){
            char endChar = s.charAt(end);

            sMap.put(endChar, sMap.getOrDefault(endChar, 0)+1);
            if(tMap.containsKey(endChar) && sMap.get(endChar).equals(tMap.get(endChar))) matched++;

            if(matched < tMap.size()){
                end++;
            }
            else if(matched >= tMap.size()){
                while(matched >= tMap.size()){
                    if(end-start+1 <= minWindowLen){
                        minWindowLen = end-start+1;
                        minWindow = s.substring(start, end+1);
                    }
                    char startChar = s.charAt(start);

                    sMap.put(startChar, sMap.get(startChar)-1);
                    if(sMap.get(startChar) <= 0){
                        sMap.remove(startChar);
                    }

                    if(tMap.containsKey(startChar) && sMap.getOrDefault(startChar, 0) < tMap.get(startChar)){
                        matched--;
                    }
                    start++;
                }
                end++;
            }
        }
        return minWindow;
    }
}
