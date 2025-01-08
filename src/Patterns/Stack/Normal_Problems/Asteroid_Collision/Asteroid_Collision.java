package Patterns.Stack.Normal_Problems.Asteroid_Collision; 
 
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 

/*
https://leetcode.com/problems/asteroid-collision
We are given an array asteroids of integers representing asteroids in a row. 
The indices of the asteriod in the array represent their relative position in space.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). 
Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. 
If two asteroids meet, the smaller one will explode. 
If both are the same size, both will explode. 
Two asteroids moving in the same direction will never meet.

Input: asteroids = [5,10,-5]
Output: [5,10]
Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.

Input: asteroids = [8,-8]
Output: []
Explanation: The 8 and -8 collide exploding each other.

Input: asteroids = [10,2,-5]
Output: [10]
Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.

// Sol1: Using Deque to track +ve star, and list to track -ve star.
// Sol2: Using Stack to track both +ve, -ve star.. if -ve in stack don't touch it.
 */
public class Asteroid_Collision extends ProblemSolver { 
    public static void main(String[] args) { 
        new Asteroid_Collision().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] asteroids = DataConvertor.toIntArray(args[0]); 
 
        int[] res1 = asteroidCollision(asteroids);
        int[] res2 = asteroidCollision_Optimized(asteroids);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2)); 
    } 
 
    // Using Deque + list
    // TC: O(n+n)
    // SC: O(n+n)
    public int[] asteroidCollision(int[] asteroids) {
        List<Integer> list = new ArrayList<Integer>();
        
        Deque<Integer> deque = new ArrayDeque<Integer>();
        for(int star: asteroids){
            if(star > 0){
                deque.offerLast(star);
            }
            else{
                // stack.peek() is less than star
                while(!deque.isEmpty() && deque.peekLast() < Math.abs(star)){
                    deque.pollLast();
                }
                
                // stack.peek() is equal to star
                if(!deque.isEmpty() && deque.peekLast() == Math.abs(star)){
                    deque.pollLast();
                }
                else if(deque.isEmpty()){
                    //track -ve's 
                    list.add(star);
                }
            }
        }

        while(!deque.isEmpty()){
            list.add(deque.pollFirst());
        }

        return list.stream().mapToInt(i->i).toArray();
    }

    // Using Stack
    // TC: O(n+n)
    // SC: O(n)
    public int[] asteroidCollision_Optimized(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<Integer>();

        for(int star: asteroids){
            if(star > 0){
                stack.push(star);
            }
            else{
                while(!stack.isEmpty() && stack.peek() >= 0 && stack.peek() < Math.abs(star)){
                    stack.pop();
                }
                
                if(stack.isEmpty() || stack.peek() < 0){
                    stack.push(star);
                }
                else if(stack.peek() == Math.abs(star)){
                    stack.pop();
                }
            }
        }

        int[] res = new int[stack.size()];

        for(int i=stack.size()-1; i>=0; i--){
            res[i] = stack.pop();
        }

        return res;
    }
 
} 
