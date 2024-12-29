package Patterns.Stack.Prefix_Infix_PostFix_Problems.pip1_Infix_To_Postfix;

import java.util.ArrayDeque;
import java.util.Deque;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Infix_To_Postfix extends ProblemSolver{
    public static void main(String[] args) {
        new Infix_To_Postfix().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String exp = DataConvertor.toString(args[0]);

        String res = infixToPostfix(exp);
        System.out.println(res);
    }

    public String infixToPostfix(String exp) {
        StringBuilder res = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<Character>();
        for(Character ch: exp.toCharArray()){
            if(ch.equals('(')){ // when ch is opening character
                stack.push(ch);
            }
            else if(ch.equals(')')){ // when ch is closing character
                while(!stack.peek().equals('(')){ // pop stack till Opening bracket
                    Character poped = stack.pop();
                    res.append(poped);
                }
                stack.pop(); // pop opening bracket
            }
            else if(getPrecedence(ch) != -1){ // when ch is operator

                // pop stack if stack.peek() precedence is greater than(>) ch precedence
                while(!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(ch)){
                    Character poped = stack.pop();
                    res.append(poped);
                }
                stack.push(ch); // after removing greater preceding operator => push current operator
            }
            else{ // when ch is operands
                res.append(ch);
            }
        }

        while(!stack.isEmpty()){ // pop stack operators in result
            res.append(stack.pop());
        }
        
        return res.toString();
    }
    
    public int getPrecedence(Character operator){
        switch(operator){
            case '-':
            case '+':
                return 1; // low precedence
            case '*':
            case '/':
                return 2;
            case '^':
                return 3; // high precedence
            default:
                return -1;
        }
    }
    
}
