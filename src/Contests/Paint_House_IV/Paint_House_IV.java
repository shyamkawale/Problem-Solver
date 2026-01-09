package Contests.Paint_House_IV;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Paint_House_IV extends ProblemSolver {

    public static void main(String[] args) {
        new Paint_House_IV().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] cost = DataConvertor.to2DIntArray(args[1]);

        long res = minCost(n, cost);
        System.out.println(res);
    }

    public long minCost(int n, int[][] cost) {
        return 0L;
    }
}
