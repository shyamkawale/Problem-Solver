package Patterns.Design;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeSet;

public class DesignLFUCache {
    /*
     * APPROACH 1: TREESET-BASED LFU CACHE
     * Uses a TreeSet to maintain elements sorted by frequency and timestamp for LFU eviction.
     * Each Data object holds key, value, frequency, and timestamp; TreeSet ensures O(log N) ordering.
     * TIME COMPLEXITY: O(log N) for get and put operations due to TreeSet add/remove.
     * SPACE COMPLEXITY: O(N) for the cache and TreeSet.
     * DISADVANTAGES: TreeSet operations are logarithmic; custom Data class with timestamp management adds complexity; not as efficient for high-frequency updates.
     * FIX: Use separate frequency buckets with LinkedHashSet for O(1) frequency updates and LRU within same frequency.
     */
    static class LFUCache_TreeSet {
        public static class Data implements Comparable<Data>{
            private static long time = 0L;

            int key;
            int val;
            long freq;
            long timestamp;

            public Data(int key, int val) {
                this.key = key;
                this.val = val;
                this.freq = 1L;
                this.timestamp = time++;
            }

            public void incrementFreq() {
                this.freq++;
            }

            public void incrementTimeStamp() {
                this.timestamp = time++;
            }

            @Override
            public int compareTo(Data other) {
                if (this.freq != other.freq) {
                    return Long.compare(this.freq, other.freq);
                }

                return Long.compare(this.timestamp, other.timestamp);
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || this.getClass() != obj.getClass()) return false;
                Data other = (Data) obj;
                return (this.key == other.key);
            }
        }

        private final TreeSet<Data> dataSet;
        private final Map<Integer, Data> cache;
        private final int capacity;

        public LFUCache_TreeSet(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>();
            dataSet = new TreeSet<>();
        }
        
        // TC: O(log N) - because of TreeSet operations (add, remove)
        public int get(int key) {
            if(!cache.containsKey(key)) {
                return -1;
            }

            Data data = cache.get(key);
            dataSet.remove(data);

            data.incrementFreq();
            data.incrementTimeStamp();

            dataSet.add(data);

            return data.val;
        }
        
        // TC: O(log N) - because of TreeSet operations (add, remove)
        public void put(int key, int value) {
            if(capacity <= 0) return;

            if(!cache.containsKey(key)) {
                if(cache.size() == capacity) {
                    Data toRemove = dataSet.pollFirst();
                    cache.remove(toRemove.key);
                }

                Data data = new Data(key, value);
                dataSet.add(data);
                cache.put(key, data);
            }
            else {
                Data data = cache.get(key);
                dataSet.remove(data);

                data.val = value;
                data.incrementFreq();
                data.incrementTimeStamp();
                
                dataSet.add(data);
            }
        }

        public static void main(String[] args) {
            System.out.println("LFU Cache using TreeSet");
            LFUCache_TreeSet lfuCache = new LFUCache_TreeSet(2);
            lfuCache.put(1, 1); // cache is {1=1}
            lfuCache.put(2, 2); // cache is {1=1, 2=2}
            System.out.println(lfuCache.get(1));    // return 1
            lfuCache.put(3, 3); // evicts key 2, cache is {1=1, 3=3}
            System.out.println(lfuCache.get(2));    // 
            System.out.println(lfuCache.get(3));    // 
            lfuCache.put(4, 4); // evicts key 3, cache is {4=4, 1=1}
            System.out.println(lfuCache.get(1));    // return 1
            System.out.println(lfuCache.get(3));    // return -1 (not found)
            System.out.println(lfuCache.get(4));    // return 4
        }
    }

    /*
     * APPROACH 2: FREQUENCY BUCKETS WITH LINKEDHASHSET
     * Uses HashMap for key-value storage and frequency maps, with LinkedHashSet per frequency for LRU order within same frequency.
     * Maintains minFreq for efficient eviction of least frequently used items.
     * TIME COMPLEXITY: O(1) amortized for get and put (HashMap operations are O(1), LinkedHashSet remove/add are O(1)).
     * SPACE COMPLEXITY: O(N) for all maps and sets.
     * DISADVANTAGES: Slightly more complex data structure management; requires careful handling of frequency updates.
     * This approach optimizes for performance and is suitable for production use.
     */
    static class LFUCache_DLL {

        private final Map<Integer, Integer> cache; // key -> value
        private final Map<Integer, Integer> keyFreqMap; // key -> freq
        private final HashMap<Integer, LinkedHashSet<Integer>> freqKeysMap; // freq -> keys with this freq in LRU order
        private final int capacity;
        private int minFreq;

        public LFUCache_DLL(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>(capacity);
            keyFreqMap = new HashMap<>(capacity);
            freqKeysMap = new HashMap<>();
            minFreq = -1;
        }
        
        public int get(int key) {
            if(!cache.containsKey(key)) {
                return -1;
            }

            int val = cache.get(key);
            int freq = keyFreqMap.get(key);

            freqKeysMap.get(freq).remove(key);
            if(freq == minFreq && freqKeysMap.get(freq).isEmpty()) {
                minFreq++;
            }

            int newFreq = freq + 1;
            freqKeysMap.putIfAbsent(newFreq, new LinkedHashSet<>());
            freqKeysMap.get(newFreq).add(key);
            keyFreqMap.put(key, newFreq);

            return val;
        }

        public void put(int key, int value) {
            if(capacity <= 0) return;

            if(!cache.containsKey(key)) {
                if(cache.size() == capacity) {
                    int toRemoveKey = freqKeysMap.get(minFreq).removeFirst();

                    cache.remove(toRemoveKey);
                    keyFreqMap.remove(toRemoveKey);

                    if(freqKeysMap.get(minFreq).isEmpty()) {
                        minFreq++;
                    }
                }

                cache.put(key, value);
                minFreq = 1;
                keyFreqMap.put(key, minFreq);
                freqKeysMap.putIfAbsent(minFreq, new LinkedHashSet<>());
                freqKeysMap.get(minFreq).add(key);
            }
            else {
                cache.put(key, value);
                get(key);
            }
        }

        public static void main(String[] args) {
            System.out.println("LFU Cache using LinkedHashSet");
            LFUCache_DLL lfuCache = new LFUCache_DLL(2);
            lfuCache.put(1, 1); // cache is {1=1}
            lfuCache.put(2, 2); // cache is {1=1, 2=2}
            System.out.println(lfuCache.get(1));    // return 1
            lfuCache.put(3, 3); // evicts key 2, cache is {1=1, 3=3}
            System.out.println(lfuCache.get(2));    // 
            System.out.println(lfuCache.get(3));    // 
            lfuCache.put(4, 4); // evicts key 3, cache is {4=4, 1=1}
            System.out.println(lfuCache.get(1));    // return 1
            System.out.println(lfuCache.get(3));    // return -1 (not found)
            System.out.println(lfuCache.get(4));    // return 4
        }
    }
}

/*
 * SUMMARY OF APPROACHES:
 *
 * 1 vs 2: TreeSet (O(log N) operations with built-in ordering) vs Frequency Buckets (O(1) operations with manual LRU).
 * - TreeSet approach is simpler to implement but slower due to logarithmic time for insertions/deletions.
 * - Frequency Buckets approach is more efficient for high-throughput scenarios, maintaining O(1) access and updates.
 *
 * PRODUCTION-LEVEL APPROACH: Approach 2 (LFUCache_DLL)
 * - Why? It achieves O(1) time complexity for both get and put operations, which is crucial for cache performance in real-world applications.
 * - Suitable for systems requiring fast cache operations, like web servers or databases.
 * - Approach 1 is better for learning or small-scale use where simplicity outweighs performance.
 */
