package Patterns.Stack.Prefix_Infix_PostFix_Problems.pip4_Prefix_To_Postfix;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Prefix_To_Postfix extends ProblemSolver{
    public static void main(String[] args) {
        new Prefix_To_Postfix().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String exp = DataConvertor.toString(args[0]);

        String res = prefixToPostfix(exp);
        System.out.println(res);
    }

    public String prefixToPostfix(String exp) {
        // Your code here
        Deque<String> stack = new ArrayDeque<String>();
        exp = new StringBuilder(exp).reverse().toString();

        for(Character ch: exp.toCharArray()){
            if(getPrecedence(ch) != -1){ // when ch is operator
                String lastElem = stack.pop();
                String secLastElem = stack.pop();
                String str = lastElem + "" + secLastElem + "" + ch;
                stack.push(str);
            }
            else{ // when ch is operands
                stack.push(String.valueOf(ch));
            }
        }
        
        return stack.pop();
    }
    
    public int getPrecedence(Character operator){
        switch(operator){
            case '-':
            case '+':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
    
}
