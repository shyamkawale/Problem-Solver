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
                    if(lastElem == curr){
                        return curr.val;
                    }

                    removeNodeFromList(curr, prevNode);
                    pushNodeAtEnd(curr);

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
                    if(lastElem == curr){
                        return;
                    }

                    removeNodeFromList(curr, prevNode);
                    pushNodeAtEnd(curr);
                    return;
                }
                prevNode = curr;
                curr = curr.next;
            }

            // if no element found then push new node.
            Node newNode = new Node(key, value);
            if(size < capacity){
                pushNodeAtEnd(newNode);
                size++;
            }
            else{ 
                // remove LRU element(start element)
                Node LRUElem = firstElem;
                if(firstElem == lastElem){
                    firstElem = null;
                    lastElem = null;
                }
                else{
                    firstElem = firstElem.next;
                }
                LRUElem.next = null;

                // pushing at end
                pushNodeAtEnd(newNode);
            }
        }

        private void removeNodeFromList(Node currNode, Node prevNode){
            // remove currElem from cache
            if(firstElem == currNode){
                firstElem = currNode.next;
            }
            else{
                prevNode.next = currNode.next;
            }
            currNode.next = null;
        }

        private void pushNodeAtEnd(Node currNode){
            // push currElem in end
            if(firstElem == null && lastElem == null){
                firstElem  = currNode;
                lastElem = currNode;
            }
            else{
                lastElem.next = currNode;
                lastElem = currNode;
            }
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
