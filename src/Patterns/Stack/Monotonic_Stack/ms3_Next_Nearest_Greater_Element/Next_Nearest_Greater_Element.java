package Patterns.Stack.Monotonic_Stack.ms3_Next_Nearest_Greater_Element; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor; 
import Helpers.ProblemSolver; 

/*
Next Greater Element (NGE)

arr traversal => from right to left (as we want next)

stack nature => strictly decreasing => bcz we are keeping strictly greater elems in stack
while loop logic => remove smaller or equal
after while loop, peek elem is NGE!

For NGEE
stack nature => decreasing => bcz we are keeping greater elems in stack
while loop logic => remove smaller
after while loop, peek elem is NGEE!
*/ 
public class Next_Nearest_Greater_Element extends ProblemSolver { 
    public static void main(String[] args) { 
        new Next_Nearest_Greater_Element().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int[] res = nextGreaterToRight(arr); 
        System.out.println(Arrays.toString(res)); 
    } 
 
    private int[] nextGreaterToRight(int[] arr) { 
        int len = arr.length;
        int[] res = new int[len];

        //              (see greater elements) => 
        //      IN =>     --------------------------
        //                          1, 3, 4, 5, 7  -    
        //      OUT =>    --------------------------
        //              (TOP)
        Deque<Integer> decStack = new ArrayDeque<Integer>();

        for(int i=len-1; i>=0; i--){
            int currElem = arr[i];

            if(decStack.isEmpty()){
                res[i] = -1;
                decStack.push(currElem);
            }
            else{
                // remove smaller or equal
                while(!decStack.isEmpty() && decStack.peek() <= currElem){ // for NGEE remove only greater elems
                    decStack.pop();
                }

                res[i] = decStack.isEmpty() ? -1 : decStack.peek();

                decStack.push(currElem);
            }
        }

        return res;
    } 
 
} 
