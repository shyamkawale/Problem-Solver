package ToRemember;

import java.util.Arrays;
import java.util.Stack;

public class ReverseAnArray {
    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5 };

        reverseArray(0, arr.length - 1, arr);
        System.out.println(Arrays.toString(arr));

        Stack<Integer> stack = new Stack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);

        Integer[] nums = (Integer[])stack.toArray();

    }
    
    private static void reverseArray(int start, int end, int[] arr) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;

            start++;
            end--;
        }
    }
}
