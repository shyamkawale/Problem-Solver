package Patterns.Sliding_Window.Count_Occurences_of_Anagrams;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Count_Occurences_of_Anagrams extends ProblemSolver{

    @Override
    public void processParameters(String[] args) {
        String pat = DataConvertor.toString(args[0]);
        String txt = DataConvertor.toString(args[1]);

        int count = search(pat, txt);
        System.out.println(count);
    }

    public static void main(String[] args) {
        new Count_Occurences_of_Anagrams().readInput();
    }

    int search(String pat, String txt) {
        // code here
        int[] patChFreq = new int[26];
        
        for(char ch : pat.toCharArray()){
            patChFreq[ch - 'a']++;
        }
        
        int count = 0;
        
        int start = 0;
        int end = 0;
        int[] txtChFreq = new int[26];
        
        while(end < txt.length()){
            char startCh = txt.charAt(start);
            char endCh = txt.charAt(end);
            
            txtChFreq[endCh - 'a']++;
            
            if(end-start+1 == pat.length()){
                if(Arrays.equals(patChFreq, txtChFreq)){
                    count++;
                }
                txtChFreq[startCh - 'a']--;
                start++;
            }
            end++;
        }
        return count;
    }

}
