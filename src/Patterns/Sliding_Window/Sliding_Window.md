# Sliding Window Pattern

The sliding window pattern is an efficient technique used to solve problems that involve iterating through subsets of data, like arrays or strings, to find results based on a fixed or variable range of elements.

## 1. Fixed Size Window

### Fixed Size Window Description

Given: 1) array/string 2) window-Size

Identification:

### BruteForce to Fixed Sliding Window

```java
// find all window of size k
public int bruteforce(int[] arr, int winSize){
    for(int start=0; start<arr.length-winSize+1; start++){
        for(int end=start; end<start+winSize; end++){
            // some calculation
        }
        //store answer
    }
}
```

### Template

```java
public int slidingWindowFixedSize(int[] arr, int winSize) {
    int start = 0;
    int end = 0;
    for (end < arr.length) {
        // some calculations

        if(end-start+1 < winSize){ // when current window size < winSize
            end++;
        }
        else if(end-start+1 == winSize){ //when current window size == winSize
            // store ans 

            //remove start from data-structure(queue, hashmap, freqArr, count*)
            start++; //maintain window
            end++;
        }
    }
}
```

### Problems

1. [Maximum Sum of Distinct Subarrays of Size K](https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/description/)
2. [First Negative in Every Window of Size k](https://www.geeksforgeeks.org/problems/first-negative-integer-in-every-window-of-size-k3345/1)
3. [Count Occurrences of anagrams](https://www.geeksforgeeks.org/problems/count-occurences-of-anagrams5839/1)
4. [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum) (+ prefix sum)**
5. [Maximum Number of Occurrences of a Substring](https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring)
6. [Maximum Points You Can Obtain from Cards](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards)


#### Techniques used:
1. Fixed Sliding Window Template
2. `map.size() == winSize` : to find if all elements of window are distinct
3. `Deque (Monotonic stack)` : to track maximum/ minimum of window as we go forward.
4. `freqMap` : to store frequencies of charaters (of string)
5. `freqMap + uniqueCount` : to track frequencies of characters also track Unique characters count.
    - Alternate of `freqMap + uniqueCount` is `hashMap + hashMap.size()`

## 2. Variable Size Window

### Variable Size Window Description

Given: 1) array/string 2) condition
Identification:

### Template

```java
public int slidingWindowVariableSize(int[] arr, int cond) {
    int start = 0;
    int end = 0;
    while (end < arr.length) {
        // some calculations

        if(sum < cond){ // when sum < cond (PRE-WIN)
            end++;
        }
        else if(sum == cond){ // when sum == cond (VALID-WIN)
            // store ans
            end++;
        }
        else if(sum > cond){ // when sum > cond(no scope going forward) (POST-WIN)
            while(sum > cond && start <= end){ 
                // remove some content of window from start till sum <= cond
                start++;
            }

            if(start > end){ // in some cases (dsw13)
                end++;
            }
            else{
                // remove end to reevaluate window as after removing start(s) we have become sum == cond
            }
        }
    }
}
```

### Problems

#### Find Maximum Subarray

1. [Longest Substring with K Uniques](https://www.geeksforgeeks.org/problems/longest-k-unique-characters-substring0853/1)
2. [Fruits into Baskets](https://leetcode.com/problems/fruit-into-baskets)
3. [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters) **(only2condition with little variation)
4. [Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii)
5. [Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement) [Optimized is not clear]❓❓
6. [Longest Turbulent Subarray](https://leetcode.com/problems/longest-turbulent-subarray) ** same-same but different
7. 2. https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/description/ => 2 ways of using sliding window. (#TOWRITE 📅)

#### Find Minimum Subarray

7. [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring)
8. [Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum) [ONLY FOR POSITIVE NUMBERS], prefixSum approach 📆

#### Count all valid subarray

9. [Count Number of Nice Subarrays](https://leetcode.com/problems/count-number-of-nice-subarrays)
10. [Binary Subarrays With Sum](https://leetcode.com/problems/binary-subarrays-with-sum) [PREFIX SUM also]
11. [Number of Substrings Containing All Three Characters](https://leetcode.com/problems/number-of-substrings-containing-all-three-characters)
12. [Subarray Product Less Than K](https://leetcode.com/problems/subarray-product-less-than-k/) [If -ve then O(n^2) only 📆]

### Count all valid subarray [Venn Diagram Appraoch]
13. [Subarrays with K Different Integers](https://leetcode.com/problems/subarrays-with-k-different-integers)

#### #TODO

#TOWRITE 📅 Venn Diagram Approach


# Venn Diagram Approach (To read later, from chatgpt)

The Venn diagram approach in sliding window problems is often used when:

You are counting subarrays/strings that match exactly some condition, like "exactly k distinct elements".

It's difficult to directly count “exactly”, so you count “at most” or “at least”, then subtract to get the exact count:

"Exactly K" = "At most K" – "At most (K-1)"

✅ Core Pattern
This technique involves computing:

java
Copy
Edit
countExactlyK = countAtMostK(k) - countAtMostK(k - 1);
✅ Sliding Window Problems Using Venn (AtMostK - AtMostK-1) Trick:
1. Subarrays with K Different Integers
Count subarrays with exactly K distinct integers.

Classic problem using:
exactlyK = atMostK(K) - atMostK(K - 1)

2. Number of Substrings Containing Exactly K Distinct Characters
Same idea, but for substrings (not subarrays).

Venn diagram / two-window method works just as well.

3. Count Number of Nice Subarrays
Subarrays with exactly K odd numbers.

Translate to:
exactlyK = atMostK(K) - atMostK(K - 1), where K = #odd numbers.

4. [Subarrays with Exactly K Sum (custom variant)]
If you're counting subarrays where the sum = exactly K, and it's hard to track this directly, a similar trick may help:

Though not always "AtMost vs AtMost" — sometimes prefix sum + hashmap is better here.

⚠️ When to Use This Approach:
Use the "AtMost K - AtMost (K - 1)" trick when:

You're asked for exactly K of something.

It's easier to compute at most K using sliding window.

Counting can be cumulative (e.g., number of valid subarrays/strings).


