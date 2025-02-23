package Maths;

// Prime Number => A prime number is a number that is only divisible by 1 and itself and the total number of divisors is 2.
// Ex: 2, 3, 5, 7, 11, .....
public class PrimeNumber {
    public static void main(String[] args) {
        int num = 7;
        boolean isPrimeNumber = isPrimeNumber(num);
        System.out.println("is "+num+" a Prime Number: "+isPrimeNumber);
    }

    // TC: O(sqrt(n)) becoz for loop is from 2,3,4,5,,,,,till n*n<=num(i.e till n<=sqrt(num))
    private static boolean isPrimeNumber(int num) {
        for(int i=2; i*i<=num; i++){
            if(num%i == 0) return false;
        }
        return true;
    }
}
