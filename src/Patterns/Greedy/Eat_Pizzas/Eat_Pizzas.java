package Patterns.Greedy.Eat_Pizzas;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/eat-pizzas/description/

You are given an integer array pizzas of size n, where pizzas[i] represents the weight of the ith pizza. Every day, you eat exactly 4 pizzas. Due to your incredible metabolism, when you eat pizzas of weights W, X, Y, and Z, where W <= X <= Y <= Z, you gain the weight of only 1 pizza!

On odd-numbered days (1-indexed), you gain a weight of Z.
On even-numbered days, you gain a weight of Y.
Find the maximum total weight you can gain by eating all pizzas optimally.

Note: It is guaranteed that n is a multiple of 4, and each pizza can be eaten only once.

Example 1:
Input: pizzas = [1,2,3,4,5,6,7,8]
Output: 14

Example 2:
Input: pizzas = [2,1,1,1,1,1,1,1]
Output: 3
*/
public class Eat_Pizzas extends ProblemSolver {

    public static void main(String[] args) {
        new Eat_Pizzas().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] pizzas = DataConvertor.toIntArray(args[0]);

        long res = maxWeight(pizzas);
        System.out.println(res);
    }

    // critical thinking: try to maximize the weight picked on odd days first, then even days
    // pick 1st maximum for odd days first then alternate maximum for even days,
    public long maxWeight(int[] pizzas) {
        long sum = 0L;
        int n = pizzas.length;

        Arrays.sort(pizzas);

        int totDays = n/4;
        int oddDays = Math.ceilDiv(totDays, 2);
        int evenDays = totDays - oddDays;

        int idx = n-1;
        for(int od=0; od<oddDays; od++) {
            sum = sum + pizzas[idx];
            idx--;
        }

        idx--;
        for(int ed=0; ed<evenDays; ed++) {
            sum = sum + pizzas[idx];
            idx = idx-2;
        }

        return sum;
    }
}
