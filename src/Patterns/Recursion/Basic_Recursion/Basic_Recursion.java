package Patterns.Recursion.Basic_Recursion; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
public class Basic_Recursion extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Basic_Recursion().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] arr = DataConvertor.toIntArray(args[0]); 
 
        int res = problem_code(arr); 
        System.out.println(res); 
    } 
 
    private int problem_code(int[] arr) { 
        return 0; 
    } 
 
} 
