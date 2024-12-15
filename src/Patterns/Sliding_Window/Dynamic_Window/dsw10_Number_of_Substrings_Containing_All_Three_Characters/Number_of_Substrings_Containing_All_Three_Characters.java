package Patterns.Sliding_Window.Dynamic_Window.dsw10_Number_of_Substrings_Containing_All_Three_Characters; 
 
import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/number-of-substrings-containing-all-three-characters
Given a string s consisting only of characters a, b and c.
Return the number of substrings containing at least one occurrence of all these characters a, b and c.

Example 1:
Input: s = "abcabc"
Output: 10
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again). 

Concept: Sliding Window(Count All Valid Windows + variation => all right of end are valid) + freqArr
Return: Total Count of Valid Windows

 */
public class Number_of_Substrings_Containing_All_Three_Characters extends ProblemSolver { 
    public static void main(String[] args) { 
        new Number_of_Substrings_Containing_All_Three_Characters().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String s = DataConvertor.toString(args[0]); 
 
        int res = numberOfSubstrings(s); 
        System.out.println(res); 
    } 
 
    // TC: O(n)
    // SC: O(3)
    public int numberOfSubstrings(String s) {
        int start = 0;
        int end = 0;
        int[] freqArr = new int[3]; //to store freq of a, b, c
        int subCnt = 0;

        while(end < s.length()){
            Character endCh = s.charAt(end);
            freqArr[endCh - 'a']++;

            if(freqArr[0] >= 1 && freqArr[1] >= 1 && freqArr[2] >= 1){ // Valid Window
                while(freqArr[0] >= 1 && freqArr[1] >= 1 && freqArr[2] >= 1){
                    subCnt = subCnt + s.length() - end; // bcz right of end will always be valid windows
                    Character startCh = s.charAt(start);
                    freqArr[startCh - 'a']--;
                    start++;
                }
                end++; // valid window case mai we have checked so no revaluation needed.
            }
            else{ // Invalid Window
                end++;
            }
        }

        return subCnt;
    }
 
} 
