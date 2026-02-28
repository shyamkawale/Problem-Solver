package Patterns.Design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * https://leetcode.com/problems/insert-delete-getrandom-o1/
 * LeetCode 380: Insert Delete GetRandom O(1)
 * https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
 * LeetCode 381: Insert Delete GetRandom O(1) - Duplicates allowed
 * 
 * Key Insight: To achieve O(1) for all operations:
 * - ArrayList: O(1) random access + O(1) add/remove at end
 * - HashMap: O(1) lookup for existence check and index retrieval
 * 
 * Deletion Trick: Swap element to delete with last element, then remove last.
 * This avoids O(n) shifting when removing from middle of ArrayList.
 */
public class DesignInsertDeleteGetRandom {

    /**
     * Approach 1: RandomizedSet (No Duplicates)
     * 
     * Data Structures:
     * - list: ArrayList storing all values (for O(1) random access)
     * - idxMap: HashMap<value, index> for O(1) lookup of value's position
     * 
     * Key Operations:
     * - insert: Add to end of list, store index in map
     * - remove: Swap with last element, remove last, update map
     * - getRandom: Random index into list
     * 
     * TC: O(1) for all operations
     * SC: O(n) where n = number of elements
     */
    class RandomizedSet {
        List<Integer> list;           // Stores values for random access
        Map<Integer, Integer> idxMap; // value -> index in list
        Random rand;

        public RandomizedSet() {
            list = new ArrayList<>();
            idxMap = new HashMap<>();
            rand = new Random();
        }

        // Insert value if not present, return true if inserted
        // O(1)
        public boolean insert(int val) {
            if (idxMap.containsKey(val)) {
                return false;  // Already exists
            }
            idxMap.put(val, list.size());  // Map value to its index
            list.add(val);                  // Add at end
            return true;
        }

        // Remove value if present, return true if removed
        // Trick: Swap with last element to achieve O(1) removal
        // O(1)
        public boolean remove(int val) {
            if (!idxMap.containsKey(val)) {
                return false;  // Doesn't exist
            }
            
            int idxToRemove = idxMap.get(val);
            int lastElement = list.get(list.size() - 1);

            // Move last element to the position of element being removed
            list.set(idxToRemove, lastElement);
            idxMap.put(lastElement, idxToRemove);

            // Remove the last element from both structures
            list.remove(list.size() - 1);
            idxMap.remove(val);
            return true;
        }

        // Return a random element with equal probability
        // O(1)
        public int getRandom() {
            int randomIdx = rand.nextInt(list.size());
            return list.get(randomIdx);
        }
    }

    /**
     * Your RandomizedSet object will be instantiated and called as such:
     * RandomizedSet obj = new RandomizedSet();
     * boolean param_1 = obj.insert(val);
     * boolean param_2 = obj.remove(val);
     * int param_3 = obj.getRandom();
     */


    /**
     * Approach 2: RandomizedCollection with Duplicates (using TreeSet)
     * 
     * Difference from Approach 1:
     * - A value can appear multiple times, so we need to track ALL indices
     * - idxMap: value -> Set of indices (instead of single index)
     * 
     * Why TreeSet?
     * - TreeSet.removeLast() gives the HIGHEST index for a value
     * - This guarantees: if lastElement == val, then idxToRemove == lastIdx
     * - So the condition `lastElement != val` correctly identifies when swap is needed
     * 
     * TC: O(log k) for insert/remove where k = count of duplicates for that value
     *     (TreeSet operations are O(log k))
     * SC: O(n) where n = total elements including duplicates
     */
    class RandomizedCollection_V1 {

        List<Integer> list;                    // Stores all values (with duplicates)
        Map<Integer, TreeSet<Integer>> idxMap; // value -> sorted set of indices
        Random rand;

        public RandomizedCollection_V1() {
            list = new ArrayList<>();
            idxMap = new HashMap<>();
            rand = new Random();
        }

        // Insert value (duplicates allowed), return true if value was NOT present before
        // O(log k) where k = count of this value
        public boolean insert(int val) {
            boolean alreadyPresent = true;
            if (!idxMap.containsKey(val)) {
                idxMap.put(val, new TreeSet<>());
                alreadyPresent = false;
            }

            idxMap.get(val).add(list.size());  // Add current index to set
            list.add(val);

            return !alreadyPresent;
        }

        // Remove ONE occurrence of value, return true if removed
        // Uses removeLast() to get highest index - this is key for correctness!
        // O(log k) where k = count of this value
        public boolean remove(int val) {
            if (!idxMap.containsKey(val)) {
                return false;
            }

            // Get the HIGHEST index for this value (important for correctness!)
            // This ensures: if lastElement == val, then idxToRemove == lastIdx
            int idxToRemove = idxMap.get(val).removeLast();
            int lastIdx = list.size() - 1;
            int lastElement = list.get(lastIdx);

            // Only swap if we're not already removing the last element
            // Note: When lastElement == val, idxToRemove == lastIdx (due to removeLast)
            if (lastElement != val) {
                list.set(idxToRemove, lastElement);
                idxMap.get(lastElement).remove(lastIdx);
                idxMap.get(lastElement).add(idxToRemove);
            }

            // Remove the last element from list
            list.remove(lastIdx);
            
            // Clean up empty set from map
            if (idxMap.get(val).isEmpty()) {
                idxMap.remove(val);
            }
            return true;
        }

        // Return a random element (probability proportional to occurrence count)
        // O(1)
        public int getRandom() {
            int randomIdx = rand.nextInt(list.size());
            return list.get(randomIdx);
        }
    }

    /**
     * Your RandomizedCollection object will be instantiated and called as such:
     * RandomizedCollection obj = new RandomizedCollection();
     * boolean param_1 = obj.insert(val);
     * boolean param_2 = obj.remove(val);
     * int param_3 = obj.getRandom();
     */

    /**
     * Approach 3: RandomizedCollection with Duplicates (using HashSet) - TRUE O(1)
     * 
     * Why HashSet instead of TreeSet?
     * - HashSet operations are O(1) vs TreeSet's O(log k)
     * - We don't need sorted order, just any valid index
     * 
     * IMPORTANT: Unlike TreeSet version, we CANNOT use `lastElement != val` as condition!
     * - HashSet.iterator().next() returns arbitrary index (not necessarily highest)
     * - Must use `idxToRemove != lastIdx` to correctly identify when swap is needed
     * 
     * TC: O(1) amortized for all operations
     * SC: O(n) where n = total elements including duplicates
     */
    class RandomizedCollection_V2 {

        List<Integer> list;                 // Stores all values (with duplicates)
        Map<Integer, Set<Integer>> idxMap;  // value -> set of indices (unordered)
        Random rand;

        public RandomizedCollection_V2() {
            list = new ArrayList<>();
            idxMap = new HashMap<>();
            rand = new Random();
        }

        // Insert value (duplicates allowed), return true if value was NOT present before
        // O(1)
        public boolean insert(int val) {
            boolean alreadyPresent = true;
            if (!idxMap.containsKey(val)) {
                idxMap.put(val, new HashSet<>());
                alreadyPresent = false;
            }

            idxMap.get(val).add(list.size());
            list.add(val);

            return !alreadyPresent;
        }

        // Remove ONE occurrence of value, return true if removed
        // O(1) amortized
        public boolean remove(int val) {
            if (!idxMap.containsKey(val)) {
                return false;
            }

            // Get ANY index for this value (HashSet doesn't guarantee order)
            int idxToRemove = idxMap.get(val).iterator().next();
            idxMap.get(val).remove(idxToRemove);
            
            int lastIdx = list.size() - 1;
            int lastElement = list.get(lastIdx);

            // IMPORTANT: Must compare indices, NOT values!
            // Unlike TreeSet version, idxToRemove could be any index, not just the highest
            // Bug if using `lastElement != val`: fails when val appears multiple times
            // and we pick an index that's not lastIdx but lastElement happens to equal val
            if (idxToRemove != lastIdx) {
                list.set(idxToRemove, lastElement);
                idxMap.get(lastElement).remove(lastIdx);
                idxMap.get(lastElement).add(idxToRemove);
            }

            // Remove the last element from list
            list.remove(lastIdx);
            
            // Clean up empty set from map
            if (idxMap.get(val).isEmpty()) {
                idxMap.remove(val);
            }
            return true;
        }

        // Return a random element (probability proportional to occurrence count)
        // O(1)
        public int getRandom() {
            int randomIdx = rand.nextInt(list.size());
            return list.get(randomIdx);
        }
    }

}
