package Patterns.Heap.Normal_Heap.Rank_of_Elements_in_Array; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
public class Rank_of_Elements_in_Array extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Rank_of_Elements_in_Array().readInput(); 
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
