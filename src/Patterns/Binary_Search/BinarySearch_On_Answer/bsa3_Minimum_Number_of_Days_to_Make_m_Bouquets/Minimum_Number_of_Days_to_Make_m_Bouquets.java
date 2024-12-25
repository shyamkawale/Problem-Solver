package Patterns.Binary_Search.BinarySearch_On_Answer.bsa3_Minimum_Number_of_Days_to_Make_m_Bouquets;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets
You are given an integer array bloomDay, an integer m and an integer k.
You want to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.
The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one bouquet.
Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is impossible to make m bouquets return -1.

Input: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
Output: 12
Explanation: We need 2 bouquets each should have 3 flowers.
Here is the garden after the 7 and 12 days:
After day 7: [x, x, x, x, _, x, x]
We can make one bouquet of the first three flowers that bloomed. We cannot make another bouquet from the last three flowers that bloomed because they are not adjacent.
After day 12: [x, x, x, x, x, x, x]
It is obvious that we can make two bouquets in different ways.

Concept: Binary Search On Answer
Return: Minimum Maximum ans to complete in T time
 */
public class Minimum_Number_of_Days_to_Make_m_Bouquets extends ProblemSolver {
    public static void main(String[] args) {
        new Minimum_Number_of_Days_to_Make_m_Bouquets().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] bloomDay = DataConvertor.toIntArray(args[0]);
        int m = DataConvertor.toInt(args[1]);
        int k = DataConvertor.toInt(args[2]);

        int res = minDays(bloomDay, m, k);
        System.out.println(res);
    }

    // TC: O(log(maxElem-minElem)*n)
    public int minDays(int[] bloomDay, int m, int k) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int n : bloomDay) {
            max = Math.max(max, n);
            min = Math.min(min, n);
        }

        int res = -1;

        int left = min;
        int right = max;

        while (left <= right) {
            int mid = left + (int)Math.floor((right - left)/2);
            if(ifPossible(bloomDay, mid, m, k)){
                res = mid;
                right = mid - 1;
            }
            else{
                left = mid + 1;
            }
        }
        return res;
    }

    // on mid day is it possible to create a bouquet??
    public boolean ifPossible(int[] bloomDay, int day, int totBq, int adjFl){
        int adjCnt = 0;
        int bq = 0;
        for(int i=0; i<bloomDay.length; i++){
            int currElem = bloomDay[i];
            if(currElem <= day){
                adjCnt++;
            }
            else{
                bq = bq + adjCnt/adjFl;
                adjCnt = 0;
            }
        }
        bq = bq + adjCnt/adjFl;
        return bq >= totBq;
    }
}
