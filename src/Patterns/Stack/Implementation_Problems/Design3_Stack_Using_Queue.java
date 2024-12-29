package Patterns.Stack.Implementation_Problems;

import java.util.ArrayDeque;
import java.util.Queue;

public class Design3_Stack_Using_Queue {
    public static class MyStack<E> {
        private Queue<E> primQueue;
        private Queue<E> secQueue;

        public MyStack() {
            primQueue = new ArrayDeque<E>();
            secQueue = new ArrayDeque<E>();
        }
        
        // PUSH TC: O(n+n), SC: O(n+n)
        public void push(E x) {
            while(!primQueue.isEmpty()){
                secQueue.offer(primQueue.poll());
            }
            primQueue.offer(x);
            while(!secQueue.isEmpty()){
                primQueue.offer(secQueue.poll());
            }
        }

        // Optimized PUSH TC: O(n), SC: O(n)
        public void push_withOnly1Queue(E elem){
            primQueue.offer(elem);
            for(int i=0; i<primQueue.size()-1; i++){
                primQueue.offer(primQueue.poll());
            }
        }
        
        public E pop() {
            return primQueue.poll();
        }
        
        public E peek() {
            return primQueue.peek();
        }
        
        public boolean empty() {
            return primQueue.isEmpty();
        }
    }

	public static void main(String[] args) {
        MyStack<Integer> mystack = new MyStack<Integer>();
        mystack.push(10);
        mystack.push(20);
        mystack.push(30);

        System.out.println("Popped: " + mystack.pop()); // Should print 30
        System.out.println("Peeked: " + mystack.peek()); // Should print 20
        System.out.println("Is Empty: " + mystack.empty()); // Should print false
    }
}
