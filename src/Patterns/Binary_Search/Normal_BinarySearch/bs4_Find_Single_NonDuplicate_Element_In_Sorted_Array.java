package Patterns.Binary_Search.Normal_BinarySearch;

public class bs4_Find_Single_NonDuplicate_Element_In_Sorted_Array {
    public static void main(String[] args) {
        int[] arr = {1,1,2,3,3,4,4,8,8}; // duplicates are only 2 times.. 
        int singleElem = singleNonDuplicate(arr);
        System.out.println("singleElement is: "+singleElem);
    }

    public static int singleNonDuplicate(int[] nums) {
        int res = -1;
        if(nums.length == 1) return nums[0];
        int left = 0;
        int right = nums.length-1;
        while(left <= right){
            int mid = left + (int)Math.floor((right-left)/2);
            if(mid-1 < 0 || mid+1 >= nums.length) return nums[mid]; //if we are at first or last element then that is the answer
            else if(nums[mid-1] != nums[mid] && nums[mid] != nums[mid+1]){ // element found
                return nums[mid];
            }
            else if((mid%2 == 0 && nums[mid] == nums[mid+1]) || (mid%2 != 0 && nums[mid] == nums[mid-1])){ // mid equal is in left => odd numbers in left => elem is in left
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }
        return res;
    }
}
