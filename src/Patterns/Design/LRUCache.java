package Patterns.Design;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {

    /*
     * ============================================================
     * APPROACH 1: Using LinkedHashMap (Built-in Java)
     * ============================================================
     * 
     * Time Complexity: O(1) for both get and put
     * Space Complexity: O(capacity)
     * 
     * LinkedHashMap with accessOrder=true maintains insertion order
     * and moves accessed elements to the end.
     * 
     * Pros: Simple, clean, less code
     * Cons: Less control, may not be accepted in interviews
     */
    public class LRUCache_LinkedHashMap {
        LinkedHashMap<Integer, Integer> cache;
        int capacity;

        public LRUCache_LinkedHashMap(int capacity) {
            this.capacity = capacity;
            cache = new LinkedHashMap<>(capacity, 0.75f, true);
        }

        public int get(int key) {
            return cache.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            cache.put(key, value);
            if (cache.size() > capacity) {
                cache.remove(cache.keySet().iterator().next());
            }
        }
    }

    /*
     * ============================================================
     * APPROACH 2: HashMap + Doubly Linked List (Interview Standard)
     * ============================================================
     * 
     * Time Complexity: O(1) for both get and put
     * Space Complexity: O(capacity)
     * 
     * HashMap: For O(1) key lookup
     * Doubly Linked List: For O(1) insertion/deletion and maintaining order
     * 
     * Key insight:
     * - Head (dummy) -> Most Recently Used
     * - Tail (dummy) -> Least Recently Used
     * - On access: move node to head
     * - On eviction: remove from tail
     * 
     * Pros: Full control, commonly expected in interviews
     * Cons: More code, need to handle pointers carefully
     */
    public class LRUCache_DoublyLinkedList {
        
        // Doubly Linked List Node
        class Node {
            int key;
            int value;
            Node prev;
            Node next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private Map<Integer, Node> cache;
        private int capacity;
        private Node head, tail; // Dummy nodes

        public LRUCache_DoublyLinkedList(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();
            
            // Initialize dummy head and tail
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            
            Node node = cache.get(key);
            // Move to front (most recently used)
            removeNode(node);
            addToFront(node);
            
            return node.value;
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                // Update existing node
                Node node = cache.get(key);
                node.value = value;
                removeNode(node);
                addToFront(node);
            } else {
                // Add new node
                if (cache.size() >= capacity) {
                    // Evict LRU (node before tail)
                    Node lru = tail.prev;
                    removeNode(lru);
                    cache.remove(lru.key);
                }
                
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                addToFront(newNode);
            }
        }

        // Remove node from its current position
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        // Add node right after head (most recently used position)
        private void addToFront(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
    }

    /*
     * ============================================================
     * APPROACH 3: HashMap + Doubly Linked List (Alternative - Add to Tail)
     * ============================================================
     * 
     * Same as Approach 2, but:
     * - Head (dummy) -> Least Recently Used
     * - Tail (dummy) -> Most Recently Used
     * - On access: move node to tail
     * - On eviction: remove from head
     * 
     * Just a different convention, both are valid
     */
    public class LRUCache_DoublyLinkedList_V2 {
        
        class Node {
            int key, value;
            Node prev, next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private Map<Integer, Node> cache;
        private int capacity;
        private Node head, tail;

        public LRUCache_DoublyLinkedList_V2(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();
            
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            
            Node node = cache.get(key);
            moveToTail(node);
            return node.value;
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                node.value = value;
                moveToTail(node);
            } else {
                if (cache.size() >= capacity) {
                    // Evict LRU (node after head)
                    Node lru = head.next;
                    removeNode(lru);
                    cache.remove(lru.key);
                }
                
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                addToTail(newNode);
            }
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToTail(Node node) {
            node.prev = tail.prev;
            node.next = tail;
            tail.prev.next = node;
            tail.prev = node;
        }

        private void moveToTail(Node node) {
            removeNode(node);
            addToTail(node);
        }
    }

    /*
     * ============================================================
     * SUMMARY & COMPARISON
     * ============================================================
     * 
     * | Approach            | Time (get/put) | Space  | Interview |
     * |---------------------|----------------|--------|-----------|
     * | LinkedHashMap       | O(1) / O(1)    | O(n)   | Maybe     |
     * | HashMap + DLL       | O(1) / O(1)    | O(n)   | Yes ✓     |
     * 
     * For interviews, ALWAYS use Approach 2 (HashMap + Doubly Linked List)
     * as it demonstrates understanding of data structure design.
     * 
     * Key Points to Remember:
     * 1. Use dummy head and tail to avoid null checks
     * 2. HashMap stores key -> Node for O(1) lookup
     * 3. DLL maintains order and allows O(1) remove/add
     * 4. Node must store key (needed when evicting to remove from HashMap)
     * 5. removeNode() + addToFront/Tail() = moveToFront/Tail()
     */
}

