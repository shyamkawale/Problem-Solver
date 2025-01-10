package Patterns.Stack.Monotonic_Stack.ms5_PSE_NSE_OnePass_Solution; 
 
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
/*
PSE & NSEE One-Pass
PSEE & NSE One-Pass

concept: if we traverse from left to right, we will get PSE but no NSE
solution: calculate PSE and NSE when popping.. 💪💪💪
logic: when we pop for the popped element, current element is NSE and stack.peek() is PSE. 💡💡💡
if stack is stil empty then for every poped element length is NSE and stack.peek() is PSE 💡💡💡💡
 */
public class PSE_NSE_OnePass_Solution extends ProblemSolver { 
    public static void main(String[] args) { 
        new PSE_NSE_OnePass_Solution().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        pse_nsee_onePass(arr);
        psee_nse_onePass(arr);
    }
 
    private void pse_nsee_onePass(int[] arr) { 
        int len = arr.length;
        
        int[] pseIdx = new int[len];
        int[] nseeIdx = new int[len];

        Deque<Integer> stack = new ArrayDeque<Integer>();

        for(int i=0; i<len; i++){
            int currElem = arr[i];

            if(stack.isEmpty()){
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] >= currElem){
                    int topElem = stack.pop();
                    pseIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
                    nseeIdx[topElem] = i;
                }

                stack.push(i);
            }
        }

        while(!stack.isEmpty()){
            int topElem = stack.pop();
            pseIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
            nseeIdx[topElem] = len;
        }

        System.out.println("PSE:  "+Arrays.toString(pseIdx) + " \t\t\t " + "NSEE: " + Arrays.toString(nseeIdx));
    } 

    private void psee_nse_onePass(int[] arr) { 
        int len = arr.length;
        
        int[] pseeIdx = new int[len];
        int[] nseIdx = new int[len];

        Deque<Integer> stack = new ArrayDeque<Integer>();

        for(int i=0; i<len; i++){
            int currElem = arr[i];

            if(stack.isEmpty()){
                stack.push(i);
            }
            else{
                while(!stack.isEmpty() && arr[stack.peek()] > currElem){
                    int topElem = stack.pop();
                    pseeIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
                    nseIdx[topElem] = i;
                }

                stack.push(i);
            }
        }

        while(!stack.isEmpty()){
            int topElem = stack.pop();
            pseeIdx[topElem] = stack.isEmpty() ? -1 : stack.peek();
            nseIdx[topElem] = len;
        }

        System.out.println("PSEE: "+Arrays.toString(pseeIdx) + " \t\t\t " + "NSE:  " + Arrays.toString(nseIdx));
    }
 
} 
