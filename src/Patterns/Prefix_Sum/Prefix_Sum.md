# Prefix Sum
When to use Sliding Window and When to use prefix Sum approach??


Explaination:
```
      idx =  0  1   2   3   4   5
     nums = [5, 3,  4,  7,  2,  3]
           ↗ ↓↗ ↓↗ ↓↗  ↓↗ ↓↗  ↓
prefix = [0, 5, 8, 12, 19, 21, 24]
   idx =  0  1  2   3   4   5   6  
```
```math
P[i] = nums[0] + nums[1] + nums[2] + .... + nums[i-1];
```
```math
P[i+1] = P[i] + nums[i];
```
```math
P[end] - P[start] = arr[start] + .... + arr[end-1];
```
This can be visualized as:
```
this means:
P:    [start, .........., end]
nums: [start, ..., end-1]
so, length of subarray = end-start
```

```math
P[end] - P[start] = Sum Of Subarray
```
```math
P[end] = P[start] + sum
```
Finally,
```math
P[start] = P[end] - sum
```
This above equation is used most commonly in questions.
which means that 
> *for every `start` idx, store `P[start]` in map with its frequency. So that when further we find `P[end] - sum` in map that means we have subarray **[start---end-1]** such that its sum = `sum`.*

Visualization:
```
            start    
<==P[start]==> ↓ <====SUM=====>|  
+---------------------------------------+
|   |   |   |   |   |   |   |   |   |   |
+---------------------------------------+
<===========P[end]===========> ↑
                              end

>  P[start]  =  P[end] - sum
(store this)   (search for this)
 (1st step)       (2nd step)

(for every start we will store "P[start]" and further we will check if we get "P[end]-sum" matching to this...)

(for "end" do we have "start" satisfying)
```


Approaches:
1. with prefix(P[]) array
```java
public int subarraySum_usingPrefixSumArray(int[] nums, int sum) {
    int len = nums.length;
    int[] P = new int[len+1];
    for(int i=0; i<len; i++){
        P[i+1] = P[i] + nums[i];
    }
    
    int subArrCnt = 0;
    Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

    // P[end] - sum = P[start]
    for(int ps : P) { // 🔥🔥 for-loop on prefix array
        // ps as endElem
        subArrCnt = subArrCnt + freqMap.getOrDefault(ps - sum, 0);

        // ps as startElem
        freqMap.put(ps, freqMap.getOrDefault(ps, 0)+1);
    }
    
    return subArrCnt;
} 
```
2. without prefix(P[]) array
```java
public int subarraySum_usingPrefixSum(int[] nums, int sum) {
    int subArrCnt = 0;
    Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();

    // P[start] = P[end] - sum
    int ps = 0;
    freqMap.put(ps, 1); // store first prefix sum (P[0] one)
    for(int n: nums) { // 🔥🔥 for-loop on nums array
        ps = ps + n;
        // ps as endElem
        subArrCnt = subArrCnt + freqMap.getOrDefault(ps-sum, 0);

        // ps as startElem
        freqMap.put(ps, freqMap.getOrDefault(ps, 0)+1);
    }
    
    return subArrCnt;
} 
```

Other techniques:
- Try convert problem into:
Everything related to End + Constant = Everything related to Start
or
End-Part + Constant = Start-Part
example1:
```
P[end] - sum = P[start]
```
example2:
```
(P[end] - P[start])*2 = end - start
2*P[end] - end = 2*P[start] - start
End-Part = Start-Part
```

1. freqMap: when count of subarray is required
2. idxMap: when length of subarray is required


## Constant Sum(sum given in question) winSum == sum

1. [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k)
2. [Longest Subarray with Sum K](https://www.geeksforgeeks.org/problems/longest-sub-array-with-sum-k0809/1) => variety => Minimum size subarray sum(only +ve elements => so sliding window)
3. [Contiguous Array](https://leetcode.com/problems/contiguous-array/description/)

## Dynamic Sum(condition on sum is given in question) => winSum <= or >= target, maxSum/minSum with len condition

1. [Subarray Sums Divisible by K](https://leetcode.com/problems/subarray-sums-divisible-by-k)
2. Longest Subarray with Sum Divisible by K
3. [Max Subarray Sum with Length Divisible by K](https://leetcode.com/problems/maximum-subarray-sum-with-length-divisible-by-k) **
4. [Shortest Subarray with Sum at Least K](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k) ***
6. https://leetcode.com/problems/make-sum-divisible-by-p/description/ TODO **
7. https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/description/ (done)
8. https://leetcode.com/problems/minimum-average-difference/description/ (done)
9. https://leetcode.com/problems/longest-subsequence-with-limited-sum/description/ (done)
10. https://leetcode.com/problems/maximum-subarray/submissions/
11. [Find Maximum Balanced XOR Subarray Length](https://leetcode.com/problems/find-maximum-balanced-xor-subarray-length/) [Prefix XOR, Prefix Even-Odd-Count]

## Does not look Prefix Sum

1. https://leetcode.com/problems/product-of-array-except-self/

## 2D prefix sum

1. https://leetcode.com/problems/range-sum-query-2d-immutable/ + Design
2. https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/description/ TODO
3. https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/description/ TODO
4. https://leetcode.com/problems/matrix-block-sum/description/ TODO

## Design

1. https://leetcode.com/problems/random-pick-with-weight/

## with slidingWindow

1. [Binary Subarrays With Sum](https://leetcode.com/problems/binary-subarrays-with-sum/) (dsw9)
2. [Count Number of Nice Subarrays](https://leetcode.com/problems/count-number-of-nice-subarrays/) (dsw8)

## Easy

1. https://leetcode.com/problems/find-pivot-index/

## With different concept

1. https://leetcode.com/problems/largest-sum-of-averages/ => DP
