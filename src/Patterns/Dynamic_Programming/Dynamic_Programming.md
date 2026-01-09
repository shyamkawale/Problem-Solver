# Dynamic Programming

Those who cannot remember the past are condemned to repeat it.

The two common dynamic programming approaches are:
1. Tabulation Way: Known as the “bottom-up ” dynamic programming, usually the problem is solved in the direction of solving the base cases to the main problem.

2. Memoization Way: Known as the “top-down” dynamic programming, usually the problem is solved in the direction of the main problem to the base cases.


Fibonacci Numbers
0 1 1 2 3 5 8 ...

using Recursion:

```java
public int fib(n) {
    if(n <= 1) {
        return n;
    }

    return fib(n-1) + fib(n-2);
}
```

here we are RE-COMPUTING already computed values!!!! => taking too much time...
we can avoid recomputing BECOZ once computed it will not be change (Overlapping sub-problems)

MEMOIZATION: tend to store the value of subproblem in some map/table

here we have 1 parameter to problem(n) => we can create 1D array.

Memoization Solution:
```java
public int fib2(int n, int[] dp) {
    if(n <= 1) {
        return n;
    }

    if(dp[n] != -1){
        return dp[n];
    }

    dp[n] = fib2(n-1, dp) + fib2(n-2, dp);
    return dp[n];
}
```
TC: O(n)
SC: recO(n) + O(n)

Tabulation Solution:
```java
public int fib3(int n) {
    int[] dp = new int[n+1];
    dp[0] = 0;
    dp[1] = 1;

    for(int i=2; i<=n; i++) {
        dp[i] = dp[i-1] + dp[i-2];
    }

    return dp[n];
}
```
TC: O(n)
SC: O(n)

Tabulation with Space Optimized:
```java
public int fib4(int n) {
    int prev1 = 1;
    int prev2 = 0;
    int curr = -1;

    for(int i=2; i<=n; i++) {
        curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }

    return curr;
}
```
TC: O(n)
SC: O(3)



## How to Identify a DP problem?

When we see a problem, it is very important to identify it as a dynamic programming problem. Generally (but not limited to) if the problem statement asks for the following:

1. Count the total number of ways
2. Given multiple ways of doing a task, which way will give the minimum or the maximum output.

We can try to apply recursion. Once we get the recursive solution, we can go ahead to convert it to a dynamic programming one.

## Steps To Solve The Problem After Identification

1. Try to represent the problem in terms of indexes.
1.2 Identify/Define subproblems. (Think in terms of subproblems)
2. Try all possible choices/ways at every index according to the problem statement.
3. If the question states
    - Count all the ways - return sum of all choices/ways.
    - Find maximum/minimum- return the choice/way with maximum/minimum output.

Flow:
1. Define the meaning of f(n) function.
2. Try to replicate meaning for all indexes f(0), f(1), f(2),...f(n) and see pattern.
3. Draw recursion tree, identify overlapping subproblems.
4. Start from end(n).
5. Go until the start(0) and define base cases.
6. Define edge cases if any.
7. Store answers while backtracking. (becoz f(idx) = ans from 0 to idx as if idx is last) [coming from 0 to n we can store value of idx in dp[idx] which cannot change later as we are coming from basecase 0----idx]
8. Return the final answer f(n) or dp[n].

more tips: 
1. Always write recursion that returns a value with basecase return ing value. (not using a global variable to track answers).
2. Keep your recursive function clean and self-contained.

tips for tabulation:
1. declare base case and add it into dp array manually.
2. we need to write a for loop now. (that will run from immediate earlier of basecase to n) - so identify immediate earlier that can be calculated using basecase.
3. copy the recurrence and write.
4. return final answer dp[n].

## Problems

1. [Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)
2. [Frog Jump](https://www.naukri.com/code360/problems/frog-jump_3621012)
3. [Frog Jump With K Distance](https://takeuforward.org/plus/dsa/dynamic-programming/1d-dp/frog-jump-with-k-distances)
4. [Maximum Sum Of NonAdjacent Elements](https://leetcode.com/problems/house-robber/)
5. [House Robber 2](https://leetcode.com/problems/house-robber-ii/)

6. [Min Cost Climbing Stairs](https://leetcode.com/problems/min-cost-climbing-stairs/)


## 2D dp
1. [Ninja's Training](https://www.naukri.com/code360/problems/ninja-s-training_3621003)
2. [Unique Paths](https://leetcode.com/problems/unique-paths/)
3. [Unique Paths with Obstacles](https://leetcode.com/problems/unique-paths-ii/)
4. [Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)
5. [Minimum Path Sum in Triangular Grid](https://leetcode.com/problems/triangle/)
6. [Minimum Falling PathSum](https://leetcode.com/problems/minimum-falling-path-sum/)
7. [Two Ninja Chocolate Pickup](https://www.naukri.com/code360/problems/ninja-and-his-friends_3125885)


## DP on Subsequences / Subsets
subsequence => follows order
subset => does not need to follow order


subsequence/subset + target

Step1: Express the problem in terms of indexes.
=> here (index, target)
f(n-1, target) => find whether there exists a subsequence in the array from index 0 to n-1, whose sum is equal to the target.

Step2: Try out all possible choices at a given index.
1st possibility: (Pick) element at index n is picked.
2nd possibility: (Not Pick) element at index n is not picked. 

Step3: return T/F with the basecase

here there are 2 variables => idx, target(becoz for each idx there will be modified target)

overlapping subproblems:
- for every depth we will have different n, and target changing to target - nums[idx], (so it looks like to naked eye that pair(, target) will always be unique)but it may be possible that if any element is 0 or there are duplicate elements then there will be ovrlapping subproblems.

1. [Subset Sum Equal to Target](https://www.naukri.com/code360/problems/subset-sum-equal-to-k_1550954)
2. [Does Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/description)
3. [Count Subsets with sum K](https://www.naukri.com/code360/problems/number-of-subsets_3952532)
4. [Count Subsets with sum K with Negative numbers](https://www.naukri.com/code360/problems/number-of-subsets_3952532)
5. [Count Partitions with given difference](https://www.naukri.com/code360/problems/partitions-with-given-difference_3751628)
6. [Partition 2 Subset with minimum absolute sum difference]()
7. [0 1 Knapsack](https://www.naukri.com/code360/problems/0-1-knapsack_920542)
8. [Unbounded Knapsack](https://www.naukri.com/code360/problems/unbounded-knapsack_1215029)
9. [Minimum Coin Change](https://leetcode.com/problems/coin-change/)
10. [Count Possible Coin Change](https://leetcode.com/problems/coin-change-ii/)


Minimum Coins:

if there are such statement:

1. Infinite supply
2. Multiple use

=> for pick, remain at the same index...


base case:

if you are starting form n-1 you go to n=0..

you always look at n=0, => start thinking in terms of single array of contaning a single element and possible target => think of edge cases here or fill dp[0][target] that can be prefilled without computing....


## DP on Strings

1. [Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/)
2. [Print Longest Common Subsequence]()
3. [Longest Palindromic Subsequence]()


### Shifting of indexes
In the recursive logic, we set the base case to `if(ind1<0 || ind2<0) `but we can’t set the dp array’s index to `-1`. Therefore a hack for this issue is to shift every index by 1 towards the right.

```
                   * 
Original Indexes: (-1), 0, 1, 2, 3, 4
                   ↓    ↓  ↓  ↓  ↓  ↓
Shifted Indexes:   0,   1, 2, 3, 4, 5
```

- Therefore, now the base case will be `if(ind1==0 || ind2==0)`.
- Similarly, we will implement the recursive code by keeping in mind the shifting of indexes, therefore `S1[ind1]` will be converted to `S1[ind1-1]`. Same for others.



## DP on Stocks

1. [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
2. [Best Time to Buy and Sell Stock II](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/)



## DP on LIS



Look at the recurrence and ask: Which index does the right-hand side use with a smaller value?

If dp[i][j] depends on dp[...][j-1], then iterate j (the second index) outer (in increasing order).

If dp[i][j] depends on dp[i-1][...], then i should be outer.




new problems:
https://leetcode.com/problems/ones-and-zeroes/description/
https://leetcode.com/problems/maximum-and-minimum-sums-of-at-most-size-k-subsequences/
https://leetcode.com/problems/paint-house-iv/description/



Greedy also possible:
[Maximize Points After Choosing K Tasks](https://leetcode.com/problems/maximize-points-after-choosing-k-tasks/) [Greedy is better]