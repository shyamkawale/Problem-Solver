package Patterns.Recursion;

public class RecursionTemp {

    public static void main(String[] args) {
        Integer n = new Integer(0);
        String str = new String("shyam");
        helper(n, str);
        System.out.println(n);
        System.out.println(str);
    }

    private static void helper(Integer n, String str) {
        for(int i=0; i<10; i++){
            n = n+1;
        }
        str = new String("abc");
    }
}
