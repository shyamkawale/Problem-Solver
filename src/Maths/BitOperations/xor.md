# XOR (Exclusive OR) Bitwise Operation

## Definition
XOR is a binary operation that returns `true` (1) when the two bits are **different**, and `false` (0) when they are the **same**.

### Truth Table
| A | B | A ^ B |
|---|---|-------|
| 0 | 0 |   0   |
| 0 | 1 |   1   |
| 1 | 0 |   1   |
| 1 | 1 |   0   |

## Java XOR Operator
In Java, XOR is represented by the `^` symbol.

```java
int a = 5;      // Binary: 0101
int b = 3;      // Binary: 0011
int result = a ^ b;  // Result: 0110 (6)
```

## Key Properties of XOR

### 1. **Commutative Property**
Order doesn't matter: `A ^ B = B ^ A`

```java
5 ^ 3 = 3 ^ 5  // Both equal 6
```

### 2. **Associative Property**
Grouping doesn't matter: `(A ^ B) ^ C = A ^ (B ^ C)`

```java
(5 ^ 3) ^ 2 = 5 ^ (3 ^ 2)  // Both equal 4
```

### 3. **Identity Property**
XOR with 0 returns the number itself: `A ^ 0 = A`

```java
5 ^ 0 = 5  // 0101 ^ 0000 = 0101
```

### 4. **Self-Inverse Property (Self-Canceling)**
XOR a number with itself returns 0: `A ^ A = 0`

```java
5 ^ 5 = 0  // 0101 ^ 0101 = 0000
```

### 5. **Involution Property**
XOR twice returns the original: `(A ^ B) ^ B = A`

```java
(5 ^ 3) ^ 3 = 5  // 0110 ^ 0011 = 0101
```

### 6. **Bit Flip Property**
XOR with all 1s flips all bits (same as NOT operation)

```java
5 ^ 7 = 2  // 0101 ^ 0111 = 0010
```

## Common Use Cases

### 1. **Find Unique Element in Array**
In an array where all elements appear twice except one:

```java
public static int findUnique(int[] arr) {
    int result = 0;
    for (int num : arr) {
        result ^= num;  // All duplicates cancel out (A ^ A = 0)
    }
    return result;
}
// Example: [1, 2, 1, 3, 2] -> 3
```

### 2. **Swap Two Numbers Without Temp Variable**
```java
a = a ^ b;
b = a ^ b;  // b = (a ^ b) ^ b = a
a = a ^ b;  // a = (a ^ b) ^ a = b
```

### 3. **Check if Bits are Different**
```java
if ((a ^ b) > 0) {
    // a and b have different bits
}
```

### 4. **Count Number of Different Bits**
```java
public static int countDifferentBits(int a, int b) {
    int xor = a ^ b;
    int count = 0;
    while (xor > 0) {
        count += xor & 1;
        xor >>= 1;
    }
    return count;
}
// Or using Integer.bitCount()
return Integer.bitCount(a ^ b);
```

### 5. **Toggle Specific Bits**
```java
int num = 5;      // 0101
int toggled = num ^ 3;  // Toggle bits at positions 0 and 1 -> 0110 (6)
```

## Competitive Programming Tricks & Properties

### **Trick 1: Eliminate All Pairs (Frequency of 2)**
When all elements appear exactly twice except one, XOR all elements to get the unique one.

**Property Used:** `A ^ A = 0` and `A ^ 0 = A`

```java
// Problem: Array with one unique element, rest appear twice
int[] arr = {4, 2, 1, 2, 4};
int result = 0;
for (int num : arr) {
    result ^= num;  // All duplicates cancel: 4^4=0, 2^2=0, 1 remains
}
System.out.println(result);  // Output: 1
```

### **Trick 2: Find Two Unique Elements (Frequency 2 Each)**
When exactly two elements are unique and rest appear twice:

**Property Used:** Commutative + Associative

```java
public static void findTwoUnique(int[] arr) {
    int xorAll = 0;
    for (int num : arr) {
        xorAll ^= num;  // xorAll = a ^ b (where a, b are unique)
    }
    
    // Find any set bit in xorAll
    int setBit = xorAll & (-xorAll);  // Isolates rightmost set bit
    
    int first = 0, second = 0;
    for (int num : arr) {
        if ((num & setBit) != 0) {
            first ^= num;
        } else {
            second ^= num;
        }
    }
    System.out.println("First: " + first + ", Second: " + second);
}
```

### **Trick 3: Detect Power of Two**
Check if a number is a power of 2 using XOR:

**Property Used:** Self-inverse `A ^ A = 0`

```java
// A power of 2 has only one bit set (e.g., 8 = 1000)
// n & (n-1) removes the rightmost set bit
public static boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
// Alternative using XOR:
public static boolean isPowerOfTwoXOR(int n) {
    return n > 0 && ((n ^ (n - 1)) == (2 * n - 1));
}
```

### **Trick 4: Toggle Specific Bit at Position**
Flip a bit at a given position:

**Property Used:** Bit-flip property

```java
public static int toggleBit(int num, int pos) {
    return num ^ (1 << pos);  // XOR with 1 at position 'pos'
}
// Example: Toggle bit at position 2
int result = toggleBit(5, 2);  // 0101 -> 0001 = 1
```

### **Trick 5: Check if Bit is Set at Position**
Determine if a specific bit is set:

```java
public static boolean isBitSet(int num, int pos) {
    return ((num & (1 << pos)) != 0);
}
// OR using XOR for comparison:
public static boolean isBitSetXOR(int num, int pos) {
    return ((num ^ (num | (1 << pos))) == 0);
}
```

### **Trick 6: Find Single Number (Frequency of 3)**
When all elements appear 3 times except one:

**Properties Used:** Associative, multiple XOR operations

```java
public static int singleNumber3Times(int[] arr) {
    int ones = 0;   // Bits that appeared 1 or 4 times (mod 3)
    int twos = 0;   // Bits that appeared 2 times (mod 3)
    
    for (int num : arr) {
        twos |= ones & num;  // Add to twos if already in ones
        ones ^= num;         // Toggle in ones
        int threes = ones & twos;  // Find bits that appeared 3 times
        ones &= ~threes;     // Remove from ones
        twos &= ~threes;     // Remove from twos
    }
    return ones;
}
```

### **Trick 7: XOR of Range [L, R]**
Calculate XOR of all numbers in a range efficiently:

**Property Used:** Identity & Self-inverse

```java
public static int xorRange(int l, int r) {
    // Calculate XOR from 1 to n
    auto xor1toN = (n) -> {
        switch (n % 4) {
            case 0: return n;
            case 1: return 1;
            case 2: return n + 1;
            case 3: return 0;
        }
        return 0;
    };
    // XOR(L to R) = XOR(1 to R) ^ XOR(1 to L-1)
    return xor1toN.apply(r) ^ xor1toN.apply(l - 1);
}
```

### **Trick 8: Check if Two Numbers Have Different Bits at Specific Positions**
```java
public static boolean hasDifferentBits(int a, int b, int mask) {
    return ((a ^ b) & mask) != 0;  // Check if XOR has 1s where mask has 1s
}
```

### **Trick 9: Subset XOR Problem**
Find if subset with given XOR sum exists:

```java
public static boolean hasSubsetXOR(int[] arr, int target) {
    Set<Integer> possible = new HashSet<>();
    possible.add(0);
    
    for (int num : arr) {
        Set<Integer> newPossible = new HashSet<>();
        for (int xor : possible) {
            newPossible.add(xor ^ num);  // Include or exclude current element
        }
        possible.addAll(newPossible);
    }
    return possible.contains(target);
}
```

### **Trick 10: Gray Code (Binary Reflected Gray Code)**
Convert binary to Gray code and vice versa:

```java
public static int binaryToGray(int n) {
    return n ^ (n >> 1);  // Each bit is XOR of bit and next bit
}

public static int grayToBinary(int gray) {
    int binary = gray;
    gray >>= 1;
    while (gray > 0) {
        binary ^= gray;
        gray >>= 1;
    }
    return binary;
}
```

## Time Complexity
- **Time:** O(1) - Bitwise operations are executed in constant time
- **Space:** O(1) - Only uses constant extra space

## Important Notes
- XOR works on individual bits, so it's very efficient
- Useful in problems involving pairs, duplicates, or bit manipulation
- Commonly used in cryptography and error detection
- The self-inverse property makes it ideal for finding unique elements
- XOR-based problems often have O(n) time and O(1) space solutions
- Key to solving "find unique element" variants with different frequencies

## Java Examples

```java
public class XORDemo {
    public static void main(String[] args) {
        // Basic XOR
        System.out.println(5 ^ 3);  // Output: 6
        
        // XOR is commutative
        System.out.println(5 ^ 3 == 3 ^ 5);  // Output: true
        
        // Self-inverse
        System.out.println((5 ^ 3) ^ 3);  // Output: 5
        
        // Find unique element
        int[] arr = {1, 2, 1, 3, 2};
        int unique = 0;
        for (int num : arr) {
            unique ^= num;
        }
        System.out.println("Unique element: " + unique);  // Output: 3
    }
}
```

## Related Bitwise Operations
- **AND (`&`)**: Returns 1 only if both bits are 1
- **OR (`|`)**: Returns 1 if at least one bit is 1
- **NOT (`~`)**: Flips all bits
- **LEFT SHIFT (`<<`)**: Multiply by 2^n
- **RIGHT SHIFT (`>>`)**: Divide by 2^n
