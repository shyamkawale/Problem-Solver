package Patterns.Stack.Implementation_Problems;

// initial LRU Cache ( with singly linked list and O(n) TC)
public class Design6_LRU_Cache {

    public static class My_LRUCache{
        public static class Node{
            public int key;
            public int val;
            public Node next;
    
            public Node(int key, int val) {
                this.key = key;
                this.val = val;
                this.next = null;
            }
        }

        int size;
        int capacity;

        Node firstElem;
        Node lastElem;

        public My_LRUCache(int capacity) {
            size = 0;
            this.capacity = capacity;
            firstElem = null;
            lastElem = null;
        }

        public int get(int key) {
            //traverse if we get element with same key
            Node prevNode = null;
            Node curr = firstElem;
            while(curr != null){
                if(curr.key == key){
                    removeNodeFromList(curr, prevNode);
                    addNodeToList(curr, lastElem);

                    return curr.val;
                }
                prevNode = curr;
                curr = curr.next;
            }
            return -1;
        }
        
        public void put(int key, int value) {
            //traverse if we get element with same key
            Node prevNode = null;
            Node curr = firstElem;
            while(curr != null){
                if(curr.key == key){
                    curr.val = value;
                    removeNodeFromList(curr, prevNode);
                    addNodeToList(curr, lastElem);
                    return;
                }
                prevNode = curr;
                curr = curr.next;
            }

            // if no element found then push new node.
            Node newNode = new Node(key, value);
            if(size < capacity){
                addNodeToList(newNode, lastElem);
            }
            else{ 
                // remove LRU element(start element)
                Node LRUElem = firstElem;
                removeNodeFromList(LRUElem, null);

                // pushing at end
                addNodeToList(newNode, lastElem);
            }
        }

        private void removeNodeFromList(Node currNode, Node prevNode){
            if(firstElem == null || currNode == null){ // if list is empty or currNode is null
                return;
            }

            if(firstElem == currNode){ // if currNode is the firstElem
                firstElem = firstElem.next;
                if(firstElem == null){ // only single Element was there.
                    lastElem = null;
                }
            }
            else{ // currNode is in middle or end
                prevNode.next = currNode.next;
                if(currNode == lastElem){ // currNode is the lastElem
                    lastElem = prevNode;
                }
            }

            currNode.next = null; // remove unwanted next reference for currNode

            size--;
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
                firstElem = newNode;
            }
            else{ // newNode to be added at middle
                newNode.next = prevNode.next;
                prevNode.next = newNode;
                if(prevNode == lastElem){ // newNode to be added at last
                    lastElem = newNode;
                }
            }

            size++;
        }
    }

    public static void main(String[] args) {
        My_LRUCache myLRUCache = new My_LRUCache(4);

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