package Patterns.Binary_Search.BinarySearch_On_Answer.bsa4_Aggressive_Cows;

import java.util.Arrays;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://takeuforward.org/data-structure/aggressive-cows-detailed-solution/
You are given an array 'arr' of size 'n' which denotes the position of stalls.
You are also given an integer 'k' which denotes the number of aggressive cows.
You are given the task of assigning stalls to 'k' cows such that the minimum distance between any two of them is the maximum possible.
Find the maximum possible minimum distance.

Concept: Binary Search On Answer
Return: Minimum Maximum ans to complete in T time
 */
public class Aggressive_Cows extends ProblemSolver {

    public static void main(String[] args) {
        new Aggressive_Cows().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] stalls = DataConvertor.toIntArray(args[0]);
        int cows = DataConvertor.toInt(args[1]);

        int res = aggressiveCows(stalls, cows);
        System.out.println(res);
    }

    //TC: O(nlogn + log(maxdist)) where n=no of stalls , maxdist=maximum distance between stalls(max-min)
    private int aggressiveCows(int[] stalls, int cows) {
        int len = stalls.length;
        Arrays.sort(stalls); // nlogn

        int minDist = 1; // minimum distance between stalls
        int maxDist = stalls[len - 1] - stalls[0]; // maximum distance between stalls
        int res = -1;

        int left = minDist;
        int right = maxDist;

        // 1 2 3 4* 5 6 7 8 9 10
        // T T T T* F F F F F F
        // for (int i = right; i >= left; i--) {
        //     if(isPossibleToPlace(stalls, i, cows)){
        //         return i;
        //     }
        // }

        while(left <= right) {
            int mid = left + (int)Math.floor((right - left)/2);

            if(isPossibleToPlace(stalls, mid, cows)){
                res = mid;
                left = mid + 1;
            }
            else{
                right = right - 1;
            }
        }

        return res;
    }

    // is it possible to place cows with mid distance between them
    private boolean isPossibleToPlace(int[] stalls, int minDist, int cows) {
        int placedCowCnt = 1;
        int prevCowIdx = 0;
        for(int i=1; i<stalls.length; i++){
            if(stalls[i] - stalls[prevCowIdx] >= minDist){
                placedCowCnt++;
                prevCowIdx = i;
            }
        }

        return placedCowCnt >= cows;
    }

}
