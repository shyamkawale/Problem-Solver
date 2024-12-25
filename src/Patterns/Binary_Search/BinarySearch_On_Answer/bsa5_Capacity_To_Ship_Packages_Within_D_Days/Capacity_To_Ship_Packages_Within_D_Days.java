package Patterns.Binary_Search.BinarySearch_On_Answer.bsa5_Capacity_To_Ship_Packages_Within_D_Days;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/capacity-to-ship-packages-within-d-days
A conveyor belt has packages that must be shipped from one port to another within days days.
The ith package on the conveyor belt has a weight of weights[i]. 
Each day, we load the ship with packages on the conveyor belt (in the order given by weights). 
We may not load more weight than the maximum weight capacity of the ship.

Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within days days.
Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5
Output: 15
Explanation: A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
1st day: 1, 2, 3, 4, 5
2nd day: 6, 7
3rd day: 8
4th day: 9
5th day: 10
Note that the cargo must be shipped in the order given, so using a ship of capacity 14 and splitting the packages into parts like (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) is not allowed.

Concept: BinarySearch on Answer
 */
public class Capacity_To_Ship_Packages_Within_D_Days extends ProblemSolver{
    public static void main(String[] args) {
        new Capacity_To_Ship_Packages_Within_D_Days().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] weights = DataConvertor.toIntArray(args[0]);
        int days = DataConvertor.toInt(args[1]);
        int res = shipWithinDays(weights, days);
        System.out.println(res);

    }

    // TC: O(log(sum-maxElem)*n)
    public int shipWithinDays(int[] weights, int days) {
        int min = Integer.MIN_VALUE;
        int max = 0;

        for(int n: weights){
            min = Math.max(min, n); // min is the maximum weight of any pkg
            max = max + n;          // max is the sum of all weights we have
        }
        int left = min;
        int right = max;

        // min(left) * * * * * * * * * max(right)
        // F> F> F> T(==) T< T< T< T<
        // while(left <= right){
        //     if(isPossibleToShip(weights, left, days)) return left;
        //     left++;
        // }

        int res = -1;
        // F> F> F> T(==) T< T< T< T<
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);

            if(isPossibleToShip(weights, mid, days)){ //right boolean
                res = mid;
                right = mid - 1;
            }
            else{ // left boolean
                left = mid + 1;
            }
        }

        return res;
    }

    // is it possible to ship all weights with minWeight in maxDays days
    // Sliding window(where windows are not overlapping => no need to maintain start)
    public boolean isPossibleToShip(int[] arr, int minWeight, int maxDays){
        int sum = 0;
        int count = 0;

        // int end = 0;
        // while(end < arr.length){
        //     sum = sum + arr[end];
        //     if(sum <= minWeight){
        //         end++;
        //     }
        //     else if(sum > minWeight){
        //         count++;
        //         sum = 0;
        //     }
        // }

        for(int end=0; end<arr.length; end++){
            sum = sum + arr[end];
            if(sum > minWeight){
                count++;
                sum = 0;
                end--;
            }
        }
        count++;

        return count <= maxDays;
    }
}
