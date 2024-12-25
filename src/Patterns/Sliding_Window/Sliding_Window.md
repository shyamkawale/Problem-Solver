# Sliding Window Pattern

The sliding window pattern is an efficient technique used to solve problems that involve iterating through subsets of data, like arrays or strings, to find results based on a fixed or variable range of elements.

## 1. Fixed Size Window

### Fixed Size Window Description:
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
### Template:
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
4. [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum) **
5. [Maximum Number of Occurrences of a Substring](https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring)
6. [Maximum Points You Can Obtain from Cards](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards)


## 2. Variable Size Window

### Variable Size Window Description:
Given: 1) array/string 2) condition
Identification: 

### Template:
```java
public int slidingWindowVariableSize(int[] arr, int cond) {
    int start = 0;
    int end = 0;
    for (end < arr.length) {
        // some calculations

        if(sum < cond){ // when sum < cond
            end++;
        }
        else if(sum == cond){ // when sum == cond (VALID WINDOW)
            // store ans
            end++;
        }
        else if(sum > cond){ // when sum > cond(no scope going forward)
            while(sum > cond){ 
                // remove some content of window from start till sum <= cond
                start++;
            }
            // end++;
            // OR
            // remove end to reevaluate window as after removing start(s) we have become sum == cond

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
5. [Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement)
6. [Longest Turbulent Subarray](https://leetcode.com/problems/longest-turbulent-subarray) ** same-same but different

#### Find Minimum Subarray
7. [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring)

#### Count all valid subarray
7. [Count Number of Nice Subarrays](https://leetcode.com/problems/count-number-of-nice-subarrays)
8. [Binary Subarrays With Sum](https://leetcode.com/problems/binary-subarrays-with-sum)
10. [Number of Substrings Containing All Three Characters](https://leetcode.com/problems/number-of-substrings-containing-all-three-characters)
11. [Subarrays with K Different Integers](https://leetcode.com/problems/subarrays-with-k-different-integers)





