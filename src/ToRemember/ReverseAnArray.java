package ToRemember;

import java.util.Arrays;

public class ReverseAnArray {
    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5 };

        reverseArray(arr, 1, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
    
    // inplace reversing - reverses original array
    // start and end both index included
    private static void reverseArray(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;

            start++;
            end--;
        }
    }
}
