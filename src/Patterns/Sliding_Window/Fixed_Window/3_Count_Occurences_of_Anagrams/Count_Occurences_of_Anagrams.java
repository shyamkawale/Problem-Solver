package Patterns.Sliding_Window.Fixed_Window.Count_Occurences_of_Anagrams;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://www.geeksforgeeks.org/problems/count-occurences-of-anagrams5839/1
Given a word pat and a text txt. Return the count of the occurrences of anagrams of the word in the text.
Input:
txt = forxxorfxdofr
pat = for
Output: 3
Explanation: for, orf and ofr appears in the txt, hence answer is 3.
 */
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

    //TC: O(pat)[for loop] + O(pat[if condition] + (txt-pat)*26[else if condition])
    //TC ~ O(pat + (txt-pat)*26) => ~ O(n)
    //SC: O(26 + 26)[2 freq array]
    int search(String pat, String txt) {
        int[] patChFreq = new int[26];
        
        for(char ch : pat.toCharArray()){
            patChFreq[ch - 'a']++;
        }
        
        int count = 0;
        
        int start = 0;
        int end = 0;
        int[] txtChFreq = new int[26];
        
        while(end < txt.length()){
            char endCh = txt.charAt(end);
            txtChFreq[endCh - 'a']++;
            
            if(end-start+1 < pat.length()){ // window size is < pat length
                end++;
            }
            else if(end-start+1 == pat.length()){ //window is set
                char startCh = txt.charAt(start);
                if(Arrays.equals(patChFreq, txtChFreq)){
                    count++;
                }

                txtChFreq[startCh - 'a']--;
                start++;
                end++;
            }
        }
        return count;
    }

}
