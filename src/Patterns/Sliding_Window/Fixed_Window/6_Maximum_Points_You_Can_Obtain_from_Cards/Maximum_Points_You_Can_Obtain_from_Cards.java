package Patterns.Sliding_Window.Fixed_Window.Maximum_Points_You_Can_Obtain_from_Cards; 
 
import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/
There are several cards arranged in a row, and each card has an associated number of points. The points are given in the integer array cardPoints.
In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.
Your score is the sum of the points of the cards you have taken.
Given the integer array cardPoints and the integer k, return the maximum score you can obtain.
Input: cardPoints = [1,2,3,4,5,6,1], k = 3
Output: 12
Explanation: After the first step, your score will always be 1. However, choosing the rightmost card first will maximize your total score. 
The optimal strategy is to take the three cards on the right, giving a final score of 1 + 6 + 5 = 12.


Concept: Fixed_SlidingWindow but with not normal
 */
public class Maximum_Points_You_Can_Obtain_from_Cards extends ProblemSolver { 
    public static void main(String[] args) { 
        new Maximum_Points_You_Can_Obtain_from_Cards().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] cardPoints = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        int res = maxScore(cardPoints, k); 
        System.out.println(res); 
    } 
 
    //TC: O(k + k) => O(2k)
    //SC: O(1)
    public int maxScore(int[] cardPoints, int k) {
        int len = cardPoints.length;
        int start = 0;
        int end = 0;
        int sum = 0;

        while(end-start+1 <= k){ // increment end to set window first (i.e winSize < k)
            sum = sum + cardPoints[end];
            end++;
        }
        end--;

        int maxScore = sum;
        while(end >= 0){ // window is set now => so maintain it
            sum = sum - cardPoints[end];
            end--;
            start = (start == 0) ? len-1 : start-1;
            sum = sum + cardPoints[start];

            maxScore = Math.max(maxScore, sum);
        }

        return maxScore;
    }
 
} 
