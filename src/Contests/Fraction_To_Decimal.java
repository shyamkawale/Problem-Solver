package Contests;

public class Fraction_To_Decimal {
    public static void main(String[] args) {
        String s = "-2/5";

        double res = solve(s);

        System.out.println(res);

        String s1 = "abcabc";

        boolean res1 = solve1(s1);

        System.out.println(res1);
    }

    private static boolean solve1(String s1) {
        return false;
    }

    private static double solve(String s) {
        boolean isNegative = s.charAt(0) == '-';
        if(isNegative) {
            s = s.substring(1);
        }
        int divisor = Integer.parseInt(s.split("/")[0]);
        int dividend = Integer.parseInt(s.split("/")[1]);

        double res = (double) divisor / dividend;

        return isNegative ? -res : res;
    }
}
