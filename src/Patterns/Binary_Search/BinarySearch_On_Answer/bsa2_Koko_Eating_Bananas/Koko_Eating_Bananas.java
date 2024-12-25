package Patterns.Binary_Search.BinarySearch_On_Answer.bsa2_Koko_Eating_Bananas;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/koko-eating-bananas
A monkey is given ‘n’ piles of bananas, whereas the 'ith' pile has ‘piles[i]’ bananas. 
An integer ‘h’ is also given, which denotes the time (in hours) for all the bananas to be eaten.

Each hour, the monkey chooses a non-empty pile of bananas and eats ‘k’ bananas. 
If the pile contains less than ‘k’ bananas, then the monkey consumes all the bananas and won’t eat any more bananas in that hour.

Find the minimum number of bananas ‘k’ to eat per hour so that the monkey can eat all the bananas within ‘h’ hours.
Example 1:
Input: piles = [3,6,7,11], h = 8
Output: 4

Example 2:
Input: piles = [30,11,23,4,20], h = 5
Output: 30

Example 3:
Input: piles = [30,11,23,4,20], h = 6
Output: 23

Concept: Binary Search On Answer
Return: Minimum Maximum ans to complete in T time
 */
public class Koko_Eating_Bananas extends ProblemSolver {

    @Override
    public void processParameters(String[] args) {
        int[] bananaPiles = DataConvertor.toIntArray(args[0]);
        int hr = DataConvertor.toInt(args[1]);

        int minBananasPerHour = minEatingSpeed(bananaPiles, hr);
        System.out.println(minBananasPerHour);
    }

    public static void main(String[] args) {
        new Koko_Eating_Bananas().readInput();
    }

    // TC: O(log(maxElem) * n) maxElem => maximum elem of array
    public int minEatingSpeed(int[] piles, int hr) {
        // max bananas Koko can eat will be max(piles)
        int maxBananasPerHr = findMaxBananasPerHr(piles);

        int left = 1; // minimum Bananas per hour
        int right = maxBananasPerHr; // maximum Bananas per hour
        int res = -1;


        while (left <= right) {
            int mid = left + (int) Math.floor((right - left) / 2);

            if(canKokoEatBeforeTime(piles, mid, hr)){
                res = mid;
                right = mid-1;
            }
            else{
                left = left+1;
            }
        }
        return res;
    }

    // with mid bananas per hour can Koko eat all piles before time ??
    private boolean canKokoEatBeforeTime(int[] piles, int mid, int hr) {
        int eatingTime = 0;
        for(int n: piles){
            eatingTime = eatingTime + (int)Math.ceil((double)n/mid);
        }
        
        if(eatingTime <= hr){
            return true;
        }

        return false;
    }

    private int findMaxBananasPerHr(int[] arr) {
        int max = arr[0];
        for (int n : arr) {
            max = Math.max(max, n);
        }
        return max;
    }
}
