package Patterns.Stack.Monotonic_Stack.ms7_Next_Nearest_Greater_Element_In_Cyclic_Array; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
NGE in cyclic Array

original arr: [5,4,3,2]

    <- <- <- <- <- <-
5 4 [3* 2 . 5 4 3] 2

trick: first traverse and store all NGE in stack & then traverse and find NGE for elements.
 */
public class Next_Nearest_Greater_Element_In_Cyclic_Array extends ProblemSolver { 
    public static void main(String[] args) { 
        new Next_Nearest_Greater_Element_In_Cyclic_Array().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int[] res = nextGreaterToRightInCyclicArray(arr); 
        System.out.println(Arrays.toString(res)); 
    } 
 
    private int[] nextGreaterToRightInCyclicArray(int[] arr) { 
        int len = arr.length;
        int[] res = new int[len];

        Deque<Integer> decStack = new ArrayDeque<Integer>();

        // traverse and store all NGE in stack
        for(int i=len-1; i>=0; i--){
            int currElem = arr[i];

            if(decStack.isEmpty()){
                decStack.push(currElem);
            }
            else{
                while(!decStack.isEmpty() && decStack.peek() <= currElem){
                    decStack.pop();
                }

                decStack.push(currElem);
            }
        }

        // traverse and find NGE for elements
        for(int i=len-1; i>=0; i--){
            int currElem = arr[i];

            while(!decStack.isEmpty() && decStack.peek() <= currElem){
                decStack.pop();
            }

            res[i] = decStack.isEmpty() ? -1 : decStack.peek();

            decStack.push(currElem);
        }

        return res;
    }
 
} 
