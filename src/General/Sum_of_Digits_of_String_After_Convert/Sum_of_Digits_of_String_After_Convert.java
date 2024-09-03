package General.Sum_of_Digits_of_String_After_Convert;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Sum_of_Digits_of_String_After_Convert extends ProblemSolver{

	@Override
	public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        int k = DataConvertor.toInt(args[1]);

        int result = getLucky(s, k);
        System.out.println(result);
	}

    public static void main(String[] args) {
        new Sum_of_Digits_of_String_After_Convert().readInput();
    }

    /* 
    https://leetcode.com/problems/sum-of-digits-of-string-after-convert/description/
    You are given a string s consisting of lowercase English letters, and an integer k.

    First, convert s into an integer by replacing each letter with its position in the alphabet (i.e., replace 'a' with 1, 'b' with 2, ..., 'z' with 26). Then, transform the integer by replacing it with the sum of its digits. Repeat the transform operation k times in total.

    For example, if s = "zbax" and k = 2, then the resulting integer would be 8 by the following operations:

    Convert: "zbax" ➝ "(26)(2)(1)(24)" ➝ "262124" ➝ 262124
    Transform #1: 262124 ➝ 2 + 6 + 2 + 1 + 2 + 4 ➝ 17
    Transform #2: 17 ➝ 1 + 7 ➝ 8
    Return the resulting integer after performing the operations described above. 
     */
    public int getLucky(String s, int k) {
        String numString = "";

        for(char ch : s.toCharArray()){
            numString += Integer.toString(ch - 'a' + 1);
        }

        while(k-- > 0){
            int sum = 0;
            for(char ch : numString.toCharArray()){
                sum += ch - '0';
            }
            numString = Integer.toString(sum);
        }

        return Integer.parseInt(numString);
    }
    
}
