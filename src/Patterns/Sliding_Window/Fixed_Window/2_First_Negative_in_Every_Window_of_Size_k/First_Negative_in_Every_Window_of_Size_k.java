package Patterns.Sliding_Window.Fixed_Window.First_Negative_in_Every_Window_of_Size_k; 
 
import java.util.*;

import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 
 
/*
https://www.geeksforgeeks.org/problems/first-negative-integer-in-every-window-of-size-k3345/1
Given an array arr[] and a positive integer k, find the first negative integer for each and every window(contiguous subarray) of size k.
Input: arr[] = [-8, 2, 3, -6, 10] , k = 2
Output: [-8, 0, -6, -6]
Explanation:
Window {-8, 2}: First negative integer is -8.
Window {2, 3}: No negative integers, output is 0.
Window {3, -6}: First negative integer is -6.
Window {-6, 10}: First negative integer is -6.
*/
public class First_Negative_in_Every_Window_of_Size_k extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new First_Negative_in_Every_Window_of_Size_k().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
        int k = DataConvertor.toInt(args[1]);
 
        List<Integer> res = firstNegativeInteger(arr, k); 
        System.out.println(res); 
    } 
 
    //TC: O(n)
    //SC: O(n) => need queue
    public List<Integer> firstNegativeInteger(int arr[], int k) {
        List<Integer> result = new ArrayList<Integer>();
        Deque<Integer> queue = new ArrayDeque<Integer>(); // to store -ve numbers
        
        int start = 0;
        int end = 0;
        while(end < arr.length){
            if(arr[end] < 0){ // add elem in queue if negative
                queue.offer(arr[end]);
            }

            if(end-start+1 < k){ // window size < k
                end++;
            }
            else if(end-start+1 == k){ // window is set
                if(queue.isEmpty()){
                    result.add(0); //store ans
                } else{
                    result.add(queue.peek()); // store ans
                    if(queue.peek() == arr[start]){
                        queue.poll();
                    }
                }
                start++;
                end++;
            }
        }
        
        return result;
    } 
 
} 
