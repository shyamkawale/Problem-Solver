package Patterns.Recursion.Generate_All_Binary_Strings;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Generate_All_Binary_Strings extends ProblemSolver {
    public static void main(String[] args) {
        new Generate_All_Binary_Strings().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);

        List<String> res = generateBinaryStrings(n);
        System.out.println(res);
    }

    public List<String> generateBinaryStrings(int n) {
        List<String> res = new ArrayList<>();
        helper(n, "", res);
        return res;
    }

    private void helper(int n, String str, List<String> res) {
        if (n == 0) {
            res.add(str);
            return;
        }

        helper(n - 1, str + "0", res);

        helper(n - 1, str + "1", res);
    }

}
