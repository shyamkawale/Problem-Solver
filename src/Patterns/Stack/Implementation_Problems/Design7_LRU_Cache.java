package Patterns.Stack.Implementation_Problems;

import java.util.HashMap;
import java.util.Map;

public class Design7_LRU_Cache {
    public static class Node{
        public int key;
        public int val;
        public Node next;
        public Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.next = null;
            this.prev = null;
        }
    }
    public static class My_LRUCache{
        int capacity;

        Node firstElem;
        Node lastElem;
        Map<Integer, Node> cache;

        public My_LRUCache(int capacity) {
            this.capacity = capacity;
            firstElem = null;
            lastElem = null;
            cache = new HashMap<Integer, Node>(capacity);
        }

        public int get(int key) {
            //traverse if we get element with same key
            Node node = cache.get(key);
            if(node == null){
                return -1;
            }

            removeNodeFromList(node);
            addNodeToList(node, lastElem);

            return node.val;
        }
        
        public void put(int key, int value) {
            //traverse if we get element with same key
            Node node = cache.get(key);

            if(node != null){
                node.val = value;
                removeNodeFromList(node);
                addNodeToList(node, lastElem);
            }
            else{
                // if no element found then push new node.
                Node newNode = new Node(key, value);
                if(cache.size() < capacity){
                    addNodeToList(newNode, lastElem);
                }
                else{ 
                    // remove LRU element(start element)
                    int lRUKey = firstElem.key;
                    removeNodeFromList(firstElem);

                    // pushing at end
                    addNodeToList(newNode, lastElem);

                    cache.remove(lRUKey);
                }

                cache.put(key, newNode);
            }
        }

        private void removeNodeFromList(Node currNode){
            if(firstElem == null || currNode == null){ // if list is empty or currNode is null
                return;
            }

            if(firstElem == currNode){ // if currNode is the firstElem
                firstElem = firstElem.next;
                if(firstElem == null){ // only single Element was there.
                    lastElem = null;
                }
                else{ //
                    firstElem.prev = null;
                }
            }
            else if(lastElem == currNode){ // if currNode is the firstElem
                lastElem = lastElem.prev;
                if(lastElem != null){ // only single Element was there.
                    lastElem.next = null;
                }
            }
            else{ // currNode is in middle or end
                currNode.prev.next = currNode.next;  // Bypass the node
                currNode.next.prev = currNode.prev;
            }

            currNode.next = null; // remove unwanted next reference for currNode
            currNode.prev = null;
        }

        private void addNodeToList(Node newNode, Node prevNode){
            if(newNode == null){ // if newNode is null
                return;
            }
            
            if(firstElem == null){ // if list is empty(it doesnot matter where we are adding newNode)
                firstElem  = newNode;
                lastElem = newNode;
            }
            else if(prevNode == null){ // newNode to be added at start
                newNode.next = firstElem;
                firstElem.prev = newNode;
                firstElem = newNode;
            }
            else{ // newNode to be added at middle
                newNode.next = prevNode.next;
                newNode.prev = prevNode;

                if(prevNode.next != null){
                    prevNode.next.prev = newNode;
                }
                else{
                    lastElem = newNode;
                }
                prevNode.next = newNode;
            }
        }
    }

    public static void main(String[] args) {
        My_LRUCache myLRUCache = new My_LRUCache(3);

        myLRUCache.put(2,6);
        myLRUCache.put(4,7);
        myLRUCache.put(8,11);
        myLRUCache.put(7,10);

        System.out.println("Value of key 2: " + myLRUCache.get(2));
        System.out.println("Value of key 8: " + myLRUCache.get(8));
        System.out.println("Value of key 10: " + myLRUCache.get(10));

        myLRUCache.put(5,6);

        System.out.println("Value of key 7: " + myLRUCache.get(7));

        myLRUCache.put(5,7);
    }
}
