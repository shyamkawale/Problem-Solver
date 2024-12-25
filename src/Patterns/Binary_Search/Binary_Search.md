# Binary Search Pattern
Searching Algorith in O(logn) time.

## Basic Binary Search Template

### Iterative Binary Search
```java
public int binarySearch(int[] arr, int target){
    int left = 0;
    int right = arr.length-1;

    // < < < < == > > > > > >
    while(left<=right){
        int mid = left + (int)Math.floor((right-left)/2);

        if(arr[mid] == target){
            return mid;
        }
        else if(arr[mid] < target){ // mid is in left bool (i.e target is on right side of mid) => GO RIGHT
            left = mid + 1;
        }
        else if(arr[mid] > target){ // mid is in right bool (i.e target is on left side of mid) => GO LEFT
            right = mid - 1;
        }
    }
    return -1;
}
```

### Recursive Binary Search
```java
public int binarySearch(int[] arr, int target, int left, int right){s
    if(right < left) return -1; //base case
    int mid = left + (int)Math.floor((right-left)/2);

    if(arr[mid] == target){
        return mid;
    }
    else if(arr[mid] < target){ // mid is in left bool (i.e target is on right side of mid) => GO RIGHT
        return binarySearch(arr, target, mid + 1, right);
    }
    else if(arr[mid] > target){ // mid is in right bool (i.e target is on left side of mid) => GO LEFT
        return binarySearch(arr, target, left, mid - 1);
    }

    return -1; // not possible but still
}

int res = binarySearch(arr, 5, 0, arr.length-1);
```

## Problems
### Set 1 - Normal BS on 1D array
#### Sorted Array
1. Binary Search Bound Analysis => Total 7 cases/probs (Diagram in notebook)
#### Rotated Sorted Array
2. Rotated Sorted Array Problems
#### Un-Sorted Array
3. [Peak Element](https://leetcode.com/problems/find-peak-element)


### Set 2 - BS on answer
1. [Square root of number in logn](https://leetcode.com/problems/sqrtx)
2. [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas)
3. [Minimum days to make M bouquets](https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets)
4. [Aggressive Cows](https://takeuforward.org/data-structure/aggressive-cows-detailed-solution/) 
5. [Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days)
6. [Allocate Books](https://www.naukri.com/code360/problems/allocate-books_1090540)

Other very similar questions:
1. [Find the Smallest Divisor Given a Threshold](https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold)

``` java
public int binarySearchOnAnswer(int[] nums, int k) {
    int left = min; // min possible answer
    int right = max; // max possible answer
    int res = -1;

    // min(left) * * * * * * * * * max(right)
    // F> F> F> T(==) T< T< T< T<
    while(left <= right){
        int mid = left + (int)Math.floor((right-left)/2);
        if(isAnswerPossible(nums, mid, k)){ //right boolean
            res = mid;
            right = mid - 1;
        }
        else{ // left boolean
            left = mid + 1;
        }
    }

    return res;
}
```

### Set 3 - BS on 2D array
1. [Search in a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/)
2. [Search in a 2D Matrix II](https://leetcode.com/problems/search-a-2d-matrix-ii/)
3. [Row With Max 1s](https://www.geeksforgeeks.org/problems/row-with-max-1s0023/1?)
4. [Find a Peak Element in Matrix](https://leetcode.com/problems/find-a-peak-element-ii/)

