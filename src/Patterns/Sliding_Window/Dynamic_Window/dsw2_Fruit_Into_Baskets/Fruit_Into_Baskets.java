package Patterns.Sliding_Window.Dynamic_Window.dsw2_Fruit_Into_Baskets; 
 
import java.util.HashMap;
import java.util.Map;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
https://leetcode.com/problems/fruit-into-baskets/
You are visiting a farm that has a single row of fruit trees arranged from left to right. The trees are represented by an integer array fruits where fruits[i] is the type of fruit the ith tree produces.

You want to collect as much fruit as possible. However, the owner has some strict rules that you must follow:

You only have two baskets, and each basket can only hold a single type of fruit. There is no limit on the amount of fruit each basket can hold.
Starting from any tree of your choice, you must pick exactly one fruit from every tree (including the start tree) while moving to the right. The picked fruits must fit in one of your baskets.
Once you reach a tree with fruit that cannot fit in your baskets, you must stop.
Given the integer array fruits, return the maximum number of fruits you can pick.

Input: fruits = [1,2,3,2,2,45]
Output: 4
Explanation: We can pick from trees [2,3,2,2].
If we had started at the first tree, we would only pick from trees [1,2].

Concept: Sliding Window + hashMap
Return: Maximum Valid Window Length
 */
public class Fruit_Into_Baskets extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Fruit_Into_Baskets().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] fruits = DataConvertor.toIntArray(args[0]); 
 
        int res = totalFruit(fruits); 
        System.out.println(res); 
    } 
 
    //TC: O(n + n) => O(n)
    //SC: O(n)
    public int totalFruit(int[] fruits) {
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();
        int basketCnt = 2; // similar to uniqueCnt

        int start = 0;
        int end = 0;
        int maxCount = 0;

        while(end < fruits.length){
            int endElem = fruits[end];
            freqMap.put(endElem, freqMap.getOrDefault(endElem, 0)+1);

            if(freqMap.size() <= basketCnt){ // when cond < k & cond = k (Valid Window)
                maxCount = Math.max(maxCount, end-start+1);
                end++;
            }
            else if(freqMap.size() > basketCnt){ // when cond > k (Invalid Window)
                while(freqMap.size() > basketCnt){  // remove start till cond <= k
                    int startElem = fruits[start];
                    freqMap.put(startElem, freqMap.get(startElem)-1);
                    if(freqMap.get(startElem) <= 0) freqMap.remove(startElem);

                    start++;
                }

                // remove end to revaluate window (as now cond <= k)
                freqMap.put(endElem, freqMap.get(endElem)-1);
                if(freqMap.get(endElem) <= 0) freqMap.remove(endElem);
            }
        }
        return maxCount;
    }
 
} 
