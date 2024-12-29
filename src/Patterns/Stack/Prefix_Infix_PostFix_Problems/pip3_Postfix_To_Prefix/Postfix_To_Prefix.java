package Patterns.Stack.Prefix_Infix_PostFix_Problems.pip3_Postfix_To_Prefix;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Postfix_To_Prefix extends ProblemSolver{
    public static void main(String[] args) {
        new Postfix_To_Prefix().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String exp = DataConvertor.toString(args[0]);

        String res = postfixToInfix(exp);
        System.out.println(res);
    }

    public String postfixToInfix(String exp) {
        Deque<String> stack = new ArrayDeque<String>();
        for(Character ch: exp.toCharArray()){
            if(getPrecedence(ch) != -1){ // when ch is operator
                String lastElem = stack.pop();
                String secLastElem = stack.pop();
                String str = ch + "" + secLastElem + "" + lastElem;
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
