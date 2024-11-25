# Sliding Window Pattern

The sliding window pattern is an efficient technique used to solve problems that involve iterating through subsets of data, like arrays or strings, to find results based on a fixed or variable range of elements.

## Two Types:

### 1. Fixed Size Window

#### Fixed Size Window Description:
Given:
Identification:

#### Template:
```java
public int slidingWindowFixedSize(int[] arr, int k) {

    for (int i = 0; i < arr.length; i++) {
        windowSum += arr[i]; // Add the next element to the window
        
        if (i >= k - 1) { // Check if the window has reached size k
            maxSum = Math.max(maxSum, windowSum); // Update maxSum
            windowSum -= arr[i - (k - 1)]; // Slide the window forward
        }
    }
    return maxSum;
}
```java

#### Problems //[Problem_Name](problem_url) - [solution](solution_url)
1. [Maximum Subarray of size k](https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/description/)
2. [First Negative in every window of size k](https://www.geeksforgeeks.org/problems/first-negative-integer-in-every-window-of-size-k3345/1)
3. [Count Occurrences of anagrams](https://www.geeksforgeeks.org/problems/count-occurences-of-anagrams5839/)
4. [Maximum of all subarray of size k](https://www.interviewbit.com/problems/sliding-window-maximum/)


