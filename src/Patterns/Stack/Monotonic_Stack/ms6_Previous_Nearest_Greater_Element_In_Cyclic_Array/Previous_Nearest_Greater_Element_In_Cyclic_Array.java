package Patterns.Stack.Monotonic_Stack.ms6_Previous_Nearest_Greater_Element_In_Cyclic_Array; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
PGE in cyclic Array

original arr: [5,4,3,2]

-> -> -> -> -> ->
5 4 [3 2 . 5 4 3*] 2

trick: first traverse and store all PGE in stack & then traverse and find PGE for elements.
 */
public class Previous_Nearest_Greater_Element_In_Cyclic_Array extends ProblemSolver { 
    public static void main(String[] args) { 
        new Previous_Nearest_Greater_Element_In_Cyclic_Array().readInput(); 
    }
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int[] res = nextGreaterToLeftInCyclicArray(arr); 
        System.out.println(Arrays.toString(res)); 
    } 
 
    private int[] nextGreaterToLeftInCyclicArray(int[] arr) { 
        int len = arr.length;
        int[] res = new int[len];

        Deque<Integer> decStack = new ArrayDeque<Integer>();

        // traverse and store all PGE in stack
        for(int i=0; i<len; i++){
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

        // traverse and find PGE for elements
        for(int i=0; i<len; i++){
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
