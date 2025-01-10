package Patterns.Stack.Monotonic_Stack.ms1_Previous_Nearest_Greater_Element; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 

/*
Previous Greatest Element (PGE)

arr traversal => from left to right (as we want previous)

stack nature => strictly decreasing => bcz we are keeping strictly greater elems in stack
while loop logic => remove smaller or equal
after while loop, peek elem is PGE!

For PGEE
stack nature => decreasing => bcz we are keeping greater elems in stack
while loop logic => remove smaller
after while loop, peek elem is PGEE!

*/
public class Previous_Nearest_Greater_Element extends ProblemSolver { 
    public static void main(String[] args) { 
        new Previous_Nearest_Greater_Element().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int[] res = nextGreaterToLeft(arr); 
        System.out.println(Arrays.toString(res)); 
    } 
 
    private int[] nextGreaterToLeft(int[] arr) { 
        int len = arr.length;
        int[] res = new int[len];

        //              (see greater elements)
        // -------------------------  <-- IN
        // - 7, 5, 4, 2, 1              
        // -------------------------  --> OUT
        //                      (TOP)
        Deque<Integer> decStack = new ArrayDeque<Integer>();

        for(int i=0; i<len; i++){
            int currElem = arr[i];

            if(decStack.isEmpty()){ // initial condition
                res[i] = -1;
                decStack.push(currElem);
            }
            else{
                // person at currElem Tower ko Bigger building dekhni hai => SO remove smaller one.
                // (i.e remove smaller or equal from stack)
                while(!decStack.isEmpty() && decStack.peek() <= currElem){ // for NGEE remove only smaller ones
                    decStack.pop();
                }

                res[i] = decStack.isEmpty() ? -1 : decStack.peek(); // now Stack contains all bigger buildings (top => curr ke pass wala)

                decStack.push(currElem); // push curr to Stack
            }
        }

        return res;
    } 
 
} 
