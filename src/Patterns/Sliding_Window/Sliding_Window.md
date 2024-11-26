# Sliding Window Pattern

The sliding window pattern is an efficient technique used to solve problems that involve iterating through subsets of data, like arrays or strings, to find results based on a fixed or variable range of elements.

## 1. Fixed Size Window

### Fixed Size Window Description:
Given: 1) array/string 2) winSize
Identification: 

### Template:
```java
public int slidingWindowFixedSize(int[] arr, int winSize) {
    int start = 0;
    int end = 0;
    for (end < arr.length) {
        // calculations when current window size < winSize

        if(end-start+1 == winSize){
            // calculations when current window size == winSize
            start++;
        }
        end++;
    }
}
```

### Problems 
1. [Maximum Subarray of size k](https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/description/)
2. [First Negative in every window of size k](https://www.geeksforgeeks.org/problems/first-negative-integer-in-every-window-of-size-k3345/1)
3. [Count Occurrences of anagrams](https://www.geeksforgeeks.org/problems/count-occurences-of-anagrams5839/)
4. [Maximum of all subarray of size k](https://www.interviewbit.com/problems/sliding-window-maximum/)

## 2. Variable Size Window

### Variable Size Window Description:
Given:
Identification:

### Template:
```java
```

### Problems 
1. []()


