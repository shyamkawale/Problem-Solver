package Patterns.Design;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/*
https://leetcode.com/problems/design-a-food-rating-system/

Design a food rating system that can do the following:

Modify the rating of a food item listed in the system.
Return the highest-rated food item for a type of cuisine in the system.

Implement the FoodRatings class:

1. FoodRatings(String[] foods, String[] cuisines, int[] ratings) 
    - Initializes the system. 
    - The food items are described by foods, cuisines and ratings, all of which have a length of n.
        1. foods[i] is the name of the ith food,
        2. cuisines[i] is the type of cuisine of the ith food, and
        3. ratings[i] is the initial rating of the ith food.
2. void changeRating(String food, int newRating) 
    - Changes the rating of the food item with the name food.
3. String highestRated(String cuisine) 
    - Returns the name of the food item that has the highest rating for the given type of cuisine. 
    - If there is a tie, return the item with the lexicographically smaller name.
*/
public class FoodRatingSystem {

    static class FoodInfo implements Comparable<FoodInfo>{
        String food;
        String cuisine;
        int rating;

        FoodInfo(String food, String cuisine, int rating) {
            this.food = food;
            this.cuisine = cuisine;
            this.rating = rating;
        }

        @Override
        public int compareTo(FoodInfo other) {
            if(this.rating != other.rating) {
                return Integer.compare(other.rating, this.rating);
            }

            return this.food.compareTo(other.food);
        }

        // @Override
        // public boolean equals(Object obj) {
        //     if (this == obj) return true;
        //     if (obj == null || this.getClass() != obj.getClass()) return false;
        //     FoodInfo foodInfo = (FoodInfo) obj;
        //     return this.food.equals(foodInfo.food) && this.cuisine.equals(foodInfo.cuisine);
        // }
    }

    Map<String, TreeSet<FoodInfo>> cuisineFoodInfoMap;
    Map<String, FoodInfo> foodInfoMap;

    public FoodRatingSystem(String[] foods, String[] cuisines, int[] ratings) {
        cuisineFoodInfoMap = new HashMap<>();
        foodInfoMap = new HashMap<>();

        int n = foods.length;

        for(int i=0; i<n; i++) {
            String cuisine = cuisines[i];
            String food = foods[i];
            int rating = ratings[i];

            cuisineFoodInfoMap.putIfAbsent(cuisine, new TreeSet<>());

            FoodInfo foodInfo = new FoodInfo(food, cuisine, rating);
            foodInfoMap.put(food, foodInfo);
            cuisineFoodInfoMap.get(cuisine).add(foodInfo);
        }
    }
    
    public void changeRating(String food, int newRating) {
        FoodInfo oldFoodInfo = foodInfoMap.get(food);
        String cuisine = oldFoodInfo.cuisine;
        cuisineFoodInfoMap.get(cuisine).remove(oldFoodInfo);

        FoodInfo newFoodInfo = new FoodInfo(food, cuisine, newRating);
        foodInfoMap.put(food, newFoodInfo);
        cuisineFoodInfoMap.get(cuisine).add(newFoodInfo);
    }
    
    public String highestRated(String cuisine) {
        return cuisineFoodInfoMap.get(cuisine).first().food;
    }
}

/**
 * Your FoodRatingSystem object will be instantiated and called as such:
 * FoodRatingSystem obj = new FoodRatingSystem(foods, cuisines, ratings);
 * obj.changeRating(food,newRating);
 * String param_2 = obj.highestRated(cuisine);
 */
