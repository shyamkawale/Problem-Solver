package Patterns.Stack.Implementation_Problems;

import java.util.ArrayDeque;
import java.util.Deque;

@SuppressWarnings("unused")
public class Design4_Queue_Using_Stack {
    public static class MyQueue<E> {
        Deque<E> priStack;
        Deque<E> secStack;
        public MyQueue() {
            priStack = new ArrayDeque<E>();
            secStack = new ArrayDeque<E>();
        }
        
        public void offer(E x) {
            while(!priStack.isEmpty()){
                secStack.push(priStack.pop());
            }
            priStack.push(x);
            while(!secStack.isEmpty()){
                priStack.push(secStack.pop());
            }
        }
        
        public E poll() {
            return priStack.pop();
        }
        
        public E peek() {
            return priStack.peek();
        }
        
        public boolean empty() {
            return priStack.isEmpty();
        }
    }

	public static void main(String[] args) {
        MyQueue<Integer> myqueue = new MyQueue<Integer>();

        myqueue.offer(10);
        myqueue.offer(20);
        myqueue.offer(30);

        System.out.println("Polled: " + myqueue.poll()); // Should print 10
        System.out.println("Peeked: " + myqueue.peek()); // Should print 20
        System.out.println("Is Empty: " + myqueue.empty()); // Should print false
    }
}
