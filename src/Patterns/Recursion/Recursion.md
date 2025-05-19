# Recursion

- When a function calls itself until a specified condition is met.

    ```java
    f(){
        -----
        f();
    }
    ```

- Stack Space = Space where non-executed Recursive call is stored.
- If we have infinite recursion then we will get Segmentation-fault (Stack Overflow error).

- **Base Condition** = Condition which stops recursion after certain condition is met. (Generally at the start of recursive function in if condtion with return)

    ```java
    f(){
        if(condition){ // Base Condition
            return;
        }
        -----
        f();
    }
    ```

- Recursion Tree = Dry run representation of Recursion in short.
    > f(0) -> f(1) -> f(2) -> f(3) -> BC

- TC: Total no. of recursive call * Time taken by each recursive call
    > TC: Total no. of nodes in Recursion Tree * TC of recursion function
- SC: Overall Stack Space required * Space taken by each recursive call
    > SC: Depth(Height) of Recursion Tree * SC of recursive function

### Basic Problems

1. Print Name N times using Recursion
2. Print 1 to N recursively
3. Print N to 1 recursively

## Backtracking

- Recursion + Doing task after the function call.
- Doing task(BT) when we go back.

```java
       f(1) -> f(2) -> f(3) -> f(4).BC
out <-  BT  <-  BT  <-  BT  <-      
```

```java
f(i){
    if(i < 3){ // BC
        return;
    }
    ------ // Recursive(Forward) task
    f(i-1); // Recursive Call
    System.out.println(i); // Backtracking task(BT)
}
```

> Recursive arrow = Blue Pen, Bactracking arrow = Black Pen

### Types of Recursion

1. Parameterized/Tail Recursion

    ```java
    // Cal sum of first N digits
    public static int f(int n, int sum){
        if(n < 0){
            return sum;
        }

        return f(n-1, sum+n);
    }

    public static void main(String[] args){
        int sum = f(3, 0);
    }
    ```

    > f(3, 0) -> f(2, 3) -> f(1, 5) -> f(0, 6).BC

2. Functional Recursion

    - Problem is broken in smaller parts

        ```java
        // Cal sum of first N digits
        public static int f(int n){
            if(n == 0){
                return 0;
            }

            return n + f(n-1)
        }

        public static void main(String[] args){
            int sum = f(3);
        }
        ```

        > f(3) -> 3+f(2) -> 2+f(1) -> 1+f(0).BC

        For Factorial
        > f(3) -> 3\*f(2) -> 2\*f(1).BC

    Reverse Array

    ```java
    // Iterative way
    // reverseArray(arr);
    public static void reverseArray(int[] arr){
        int left = 0;
        int right = arr.length-1;

        while(left < right){
            swap(left, right, arr);
            left++;
            right--;
        }
    }

    // Recursive with 2 variables
    // reverseArray(0, arr.length-1, arr);
    public static void reverseArray(int left, int right, int[] arr){
        if(left >= right){
            return;
        }
        swap(left, right, arr);

        reverseArray(left+1, right-1, arr);
    }

    // Recursive with 1 variable
    // reverseArray(0, arr);
    public static void reverseArray(int left, int[] arr){
        if(left >= arr.length/2){
            return;
        }
        swap(left, arr.length-left-1, arr);

        reverseArray(left+1, arr);
    }
    ```

    IMP to identify which task will recursive function will do.. like here it is swapping

    Check if String is Palindrome

    ```java
    boolean isPalindrome(int left, String str){
        if(left >= str.length/2){
            return true;
        }

        if(str.charAt(left) != str.charAt(str.length()-left-1)){
            return false;
        }

        return isPalindrome(left+1, str);
    }
    ```

3. Functional Recursion with Multiple Function Call

    Fibonacci Number

    ```java
    f(n){
        if(n <= 1){
            return n;
        }

        return f(n-1) + f(n-2);
    }
    ```

    ```java
                fib(4)
                /      \
            fib(3)      fib(2) 
            /    \     /     \
        fib(2) fib(1) fib(1) fib(0)
        /    \
    fib(1)  fib(0)
    ```

    TC: O(2^n) [every time for each function call 2 more function calls are called]
    SC: O(n) [Depth of Binary Tree]

Recursion on Subsequences

Subsequence = A contigous / non-contigous sequences, which follows the **order**.

[1, 2, 3] => [], [1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]

// This pattern can have input as array OR first n integers(1,2,3,...n)

1. Find All Subsequences
2. Find All Subsequences with Target
3. Find Any One Subsequence with Target
4. Count number of Subsequences with Target

5. Find All Combinations
6. Find All Combinations with Target

7. Subsets with Repetitions (Combination Sum - 1)
8. Subsets with Repetitions with Duplicate Input (Combination Sum - 2)
9. Subsets without Repetitions with Duplicate Input (Subset - 2)

10. Count subsets with Repetitions (Combination Sum - 4)

11. Find All Permutations
12. Find All Permutations with Duplicates

13. Generate all Binary Strings
14. Generate Parenthesis

15. Find All Partitions (String + Array)

16. [Finding 3 Digit Even Number](https://leetcode.com/problems/finding-3-digit-even-numbers/description/) - Permutation of 3 length with even number condition with duplicate input and distinct output
- Check other solutions


Technique: 
Duplicate + Permutation technique (see 16 problem) - brainstorm
