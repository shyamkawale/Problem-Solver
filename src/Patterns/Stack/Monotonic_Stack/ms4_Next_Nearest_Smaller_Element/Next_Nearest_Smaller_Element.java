package Patterns.Stack.Monotonic_Stack.ms4_Next_Nearest_Smaller_Element; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
Next Smaller Element (NSE)

arr traversal => from right to left (as we want next)

stack nature => strictly increasing => bcz we are keeping strictly smaller elems in stack
while loop logic => remove greater or equal
after while loop, peek elem is NSE!

For NSEE
stack nature => increasing => bcz we are keeping smaller elems in stack
while loop logic => remove greater
after while loop, peek elem is NSEE!
 */
public class Next_Nearest_Smaller_Element extends ProblemSolver { 
    public static void main(String[] args) { 
        new Next_Nearest_Smaller_Element().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int[] res = nextSmallerToRight(arr); 
        System.out.println(Arrays.toString(res)); 
    } 
 
    private int[] nextSmallerToRight(int[] arr) { 
        int len = arr.length;
        int[] res = new int[len];

        //              (see smaller elements) => 
        //      IN =>     --------------------------
        //                          7, 5, 3, 2, 1  -    
        //      OUT =>    --------------------------
        //              (TOP)
        Deque<Integer> incStack = new ArrayDeque<Integer>();

        for(int i=len-1; i>=0; i--){
            int currElem = arr[i];

            if(incStack.isEmpty()){
                res[i] = -1;
                incStack.push(currElem);
            }
            else{
                // remove greater or equal
                while(!incStack.isEmpty() && incStack.peek() >= currElem){ // for NSEE remove only greater elems
                    incStack.pop();
                }

                res[i] = incStack.isEmpty() ? -1 : incStack.peek();

                incStack.push(currElem);
            }
        }

        return res;
    } 
 
} 
