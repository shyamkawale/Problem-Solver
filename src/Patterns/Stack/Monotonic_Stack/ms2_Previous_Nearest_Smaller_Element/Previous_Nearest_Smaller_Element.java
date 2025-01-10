package Patterns.Stack.Monotonic_Stack.ms2_Previous_Nearest_Smaller_Element; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
/*
Previous Smaller Element (PSE)

arr traversal => from left to right (as we want previous)

stack nature => strictly increasing => bcz we are keeping strictly smaller elems in stack
while loop logic => remove greater or equal
after while loop, peek elem is PSE!

For PSEE
stack nature => increasing => bcz we are keeping smaller elems in stack
while loop logic => remove greater
after while loop, peek elem is PSEE!

 */
public class Previous_Nearest_Smaller_Element extends ProblemSolver { 
    public static void main(String[] args) { 
        new Previous_Nearest_Smaller_Element().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int[] res = nextSmallerToLeft(arr); 
        System.out.println(Arrays.toString(res)); 
    } 
 
    private int[] nextSmallerToLeft(int[] arr) { 
        int len = arr.length;
        int[] res = new int[len];
        
        //              (see smaller elements)
        // -------------------------  <-- IN
        // - 1, 3, 4, 5, 7              
        // -------------------------  --> OUT
        //                      (TOP)
        Deque<Integer> incStack = new ArrayDeque<Integer>();

        for(int i=0; i<len; i++){
            int currElem = arr[i];

            if(incStack.isEmpty()){ // initial condition
                res[i] = -1;
                incStack.push(currElem);
            }
            else{
                // person at currElem Tower ko Smaller building dekhni hai => SO remove bigger one.
                // (i.e remove greater or equal)
                while(!incStack.isEmpty() && incStack.peek() >= currElem){ // for PSEE remove only smaller elems
                    incStack.pop();
                }

                res[i] = incStack.isEmpty() ? -1 : incStack.peek(); // now Stack contains all smaller building (top => curr ke pass wala)

                incStack.push(currElem);
            }
        }

        return res;
    } 
 
} 
