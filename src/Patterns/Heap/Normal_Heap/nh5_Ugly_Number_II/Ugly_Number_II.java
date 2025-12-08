package Patterns.Heap.Normal_Heap.nh5_Ugly_Number_II;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/ugly-number-ii/

An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
Given an integer n, return the nth ugly number.

Example 1:
Input: n = 10
Output: 12
Explanation: [1, 2, 3, 4, 5, 6, 8, 9, 10, 12] is the sequence of the first 10 ugly numbers.

Example 2:
Input: n = 1
Output: 1
Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.

solution: 
*/
public class Ugly_Number_II extends ProblemSolver {

    public static void main(String[] args) {
        new Ugly_Number_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);

        int res = nthUglyNumber(n);
        System.out.println(res);
    }

    public int nthUglyNumber(int n) {
        int[] primeFactors = {2, 3, 5};
        Set<Integer> vis = new HashSet<>();
        Queue<Integer> queue = new PriorityQueue<>();

        queue.offer(1);
        vis.add(1);
        int currentUglyNum = -1;

        while(n > 0 && !queue.isEmpty()) {
            currentUglyNum = queue.poll();

            for(int pf: primeFactors) {
                if(!vis.contains(currentUglyNum*pf)) {
                    queue.offer(currentUglyNum*pf);
                    vis.add(currentUglyNum*pf);
                }
            }
            n--;
        }

        return currentUglyNum;
    }
}
