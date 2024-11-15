package Maths.BasicMaths;

import java.util.Arrays;

public class BasicMaths{

    public static void main(String[] args) {
        int minValue = Integer.MIN_VALUE; // -2147483648
        int maxValue = Integer.MAX_VALUE; // 2147483647
        showModulusUses();
    }

    private static void showModulusUses() {
        System.out.println("*** Modulus operations and Uses: ***");

        // Modulo
        // % is used to find the remainder when one number is divided by another
        // a%b = c i.e when a is divided by b then rem is c

        //Common Modulus Operations
        int result = 11 % 3; // 2, 2 % 3 = 2;
        int result1 = -11 % 3; // -2
        int result11 = (-11 % 3 + 3) % 3; // 2 (mathematical right answer of -11%3)
        int result2 = 11 % -3; // 2
        double result3 = 5.5 % 2.2; // 1.1

        //Check Divisibility
        System.out.println("Check Divisibility");
        int a = 10;
        int b = 5;
        if (a % b == 0) {
            System.out.println(a + " is divisible by " + b);
        }
        else{
            System.out.println(a + " is NOT divisible by " + b);
        }
        System.out.println();

        //Even or Odd Check
        System.out.println("Even or Odd Check");
        int num = 7;
        if (num % 2 == 0) {
            System.out.println(num + " is even");
        } else {
            System.out.println(num + " is odd");
        }
        System.out.println();

        //Finding Last Digits of a Number
        System.out.println("Finding Last Digits of a Number");
        int num1 = 456;
        int lastDigit = num1 % 10;
        System.out.println("last digit of " + num1 + " is: " + lastDigit);
        System.out.println();

        //Limit values around within a range, 
        //For example, if you want a number to always stay within the range [0, n)
        System.out.println("Limit values around within a range");
        for(int i=0; i<12; i++){
            int x = i % 5; //x will always be in range for any a i.e x will be 0,1,2,3,4 i.e [0,5)
            System.out.print(x+ " ");
        }
        System.out.println();
        System.out.println();

        //Circular Buffers
        System.out.println("Circular Buffers");
        int[] array = new int[]{1,2,3,4,5,6,7};
        int len = array.length; // buffer size
        int currentIndex = 6;
        int index = (currentIndex + 1) % len; // Move to the next index, wrap around when it reaches the end
        System.out.println("For array with len 7, next index after 6 is "+index);
        System.out.println();

        //Handling Large Numbers in Modular Arithmetic
        //When dealing with very large numbers, to avoid overflow or work within specific constraints, modular arithmetic is often used
        System.out.println("Handling Large Numbers in Modular Arithmetic");
        int mod = 1000000007;
        int bigNumber = Integer.MAX_VALUE; //2147483647
        int afterAddingToBigNumber = (bigNumber + 1) % mod;
        System.out.println("After adding bigNumber: "+afterAddingToBigNumber);
        System.out.println();

        //Working with Time
        System.out.println("Working with Time");
        int currentHour = 23;
        int hoursToAdd = 5;
        int finalHour = (currentHour + hoursToAdd) % 24; // 24-hour clock
        System.out.println(hoursToAdd+" hourse after currenthour("+currentHour+") is "+finalHour); // Output will be 15
        System.out.println();

        //Rotate Array (Cyclic Rotation)
        System.out.println("Rotate Array (Cyclic Rotation)");
        //A common problem is rotating an array by k positions. Using modulus simplifies index wrapping:
        // public int[] rotateArray(int[] arr, int k) {
            int rotPosition = 2; // rotate to left or right??
            int[] rotated = new int[len];
            for (int i = 0; i < len; i++) {
                rotated[(i + rotPosition) % len] = array[i];
            }
            System.out.println(Arrays.toString(rotated));
        // }
        System.out.println();

    }
}
