package Patterns.Stack.Normal_Problems.Check_Balanced_Paranthesis; 
 
import java.util.Stack;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
public class Check_Balanced_Paranthesis extends ProblemSolver { 
    public static void main(String[] args) { 
        new Check_Balanced_Paranthesis().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String str = DataConvertor.toString(args[0]); 
 
        boolean res = isBalancedParanthesis(str); 
        System.out.println(res); 
    } 
 
    public boolean isBalancedParanthesis(String s) {
        if(s.length() == 1)return false;
        Stack<Character> stack = new Stack<>();
        for(Character ch: s.toCharArray()){
            if(ch == '{' || ch == '(' || ch == '['){ // Opening Brackets
                stack.push(ch);
            }
            if(ch == '}' || ch == ')' || ch == ']'){ // Closing Brackets
                if(stack.isEmpty()) {
                    return false;
                }
                if(ch == '}' && stack.peek() != '{'){
                    return false;
                } 
                else if(ch == ']' && stack.peek() != '['){
                    return false;
                } 
                else if(ch == ')' && stack.peek() != '('){
                    return false;
                } 
                stack.pop();
            }
        }

        return stack.isEmpty();
    }
 
} 
