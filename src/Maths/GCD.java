package Maths;

// GCD and HCF is same
// Greatest Number that divides both numbers n1, n2
public class GCD {
    public static void main(String[] args) {
        int n1 = 9;
        int n2 = 12;

        int gcd1 = gcd1(n1, n2);
        int gcd2 = gcd2(n1, n2);
        int gcd3 = gcd3(n1, n2);
        System.out.println(gcd1 + " " + gcd2 + " " + gcd3);
    }

    // Euclidian Algorithm => GCD(n1, n2) = GCD(n1-n2, n2) if n1 > n2
    // TC ~ O(n)
    public static int gcd1(int n1, int n2){
        if(n1 == 0) return n2;
        if(n2 == 0) return n1;

        if(n1 > n2){
            return gcd1(n1-n2, n2);
        }
        return gcd1(n2-n1, n1);
    }

    // Euclidian + Modification => => GCD(n1, n2) = GCD(n1%n2, n2) if n1 > n2
    // TC: O(log-phi(min(n1, n2)))
    // Recursive
    public static int gcd2(int n1, int n2){
        if(n1 == 0) return n2;
        if(n2 == 0) return n1;

        if(n1 > n2){
            return gcd2(n1%n2, n2);
        }
        return gcd2(n2%n1, n1);
    }

    // TC: O(log-phi(min(n1, n2)))
    // Iterative
    public static int gcd3(int n1, int n2){
        while(n1 > 0 && n2 > 0){
            if(n1 > n2){
                n1 = n1%n2;
            }
            else{
                n2 = n2%n1;
            }
        }

        if(n1 == 0) return n2;
        return n1;
    }

    // TO REVIEW
    // 💡recursive GCD algo 
    // TC => O(log(min(a, b))), 
    // SC => O(log(min(a, b))) because each recursive call adds a new frame to the call stack, and the recursion depth is logarithmic in the size of the smaller number.
    // private int getGCD(int a, int b){
    //     if (b == 0) {
    //         return a;
    //     }
    //     return getGCD(b, a % b);
    // }
}
