package Patterns.Stack.Prefix_Infix_PostFix_Problems.pip5_Prefix_To_Infix;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Prefix_To_Infix extends ProblemSolver{
    public static void main(String[] args) {
        new Prefix_To_Infix().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String exp = DataConvertor.toString(args[0]);

        String res = prefixToInfix(exp);
        System.out.println(res);
    }

    public String prefixToInfix(String exp) {
        Deque<String> stack = new ArrayDeque<String>();
        exp = new StringBuilder(exp).reverse().toString();

        for(Character ch: exp.toCharArray()){
            if(getPrecedence(ch) != -1){ // when ch is operator
                String lastElem = stack.pop();
                String secLastElem = stack.pop();
                String str = "(" + lastElem + "" + ch + "" + secLastElem + ")";
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
