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

    // https://leetcode.com/problems/sum-of-digits-of-string-after-convert/description/
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
