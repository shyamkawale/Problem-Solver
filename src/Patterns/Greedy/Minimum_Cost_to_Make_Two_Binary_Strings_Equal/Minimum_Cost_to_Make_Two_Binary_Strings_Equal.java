package Patterns.Greedy.Minimum_Cost_to_Make_Two_Binary_Strings_Equal;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*

*/
public class Minimum_Cost_to_Make_Two_Binary_Strings_Equal extends ProblemSolver {
    public static void main(String[] args) {
        new Minimum_Cost_to_Make_Two_Binary_Strings_Equal().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        String t = DataConvertor.toString(args[1]);
        int flipCost = DataConvertor.toInt(args[2]);
        int swapCost = DataConvertor.toInt(args[3]);
        int crossCost = DataConvertor.toInt(args[4]);

        long res = minimumCost(s, t, flipCost, swapCost, crossCost);
        System.out.println(res);
    }

    public long minimumCost(String s, String t, int flipCost, int swapCost, int crossCost) {
        int typeA = 0;
        int typeB = 0;
        int len = s.length();

        for(int i=0; i<len; i++) {
            char sch = s.charAt(i);
            char tch = t.charAt(i);

            if(sch == '0' && tch == '1') typeA++;
            else if(sch == '1' && tch == '0') typeB++;
        }

        int pairsCnt = Math.min(typeA, typeB);
        long pairsCost = (long) Math.min(2*flipCost, swapCost)* (long) pairsCnt;

        int remMis = Math.max(typeA, typeB) - Math.min(typeA, typeB);
        long remCost = 0L;
        if(2*flipCost > crossCost + swapCost) {
            remCost = (long) (crossCost + swapCost)* (long) (remMis/2) + (long) flipCost* (long) (remMis%2);
        }
        else {
            remCost = (long) remMis* (long) flipCost;
        }

        return (long) pairsCost + (long) remCost;
    }

}
