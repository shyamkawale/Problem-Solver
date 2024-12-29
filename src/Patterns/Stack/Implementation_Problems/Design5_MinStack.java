package Patterns.Stack.Implementation_Problems;

import java.util.ArrayDeque;
import java.util.Deque;

public class Design5_MinStack {

    // SC:
    // 1) for stack O(n) => this is required as we are designing stack
    // 2) for maintaining MinElem O(n) => minStack
    class MinStackUsing2Stack<E extends Comparable<E>> {
        Deque<E> stack;
        Deque<E> minStack;

        public MinStackUsing2Stack() {
            stack = new ArrayDeque<E>();
            minStack = new ArrayDeque<E>();
        }

        public void push(E val) {
            stack.push(val);
            if (minStack.isEmpty() || minStack.peek().compareTo(val) >= 1) {
                minStack.push(val);
            }
        }

        public void pop() {
            E popped = stack.pop();
            if (minStack.peek() == popped) {
                minStack.pop();
            }
        }

        public E top() {
            return stack.peek();
        }

        public E getMin() {
            return minStack.peek();
        }
    }

    class Pair<E> {
        public E elem;
        public E minElem;

        public Pair(E elem, E idx) {
            this.elem = elem;
            this.minElem = idx;
        }
    }

    /*############################################################################################################ */

    // SC:
    // 1) for stack O(n) => this is required as we are designing stack
    // 2) for maintaining MinElem O(n) => each element of stack(Pair) contains extra variable(minElem)
    class MinStackUsingPair<E extends Comparable<E>> {
        Deque<Pair<E>> stack;

        public MinStackUsingPair() {
            stack = new ArrayDeque<Pair<E>>();
        }

        public void push(E elem) {
            E minElem;
            if (stack.isEmpty()) {
                minElem = elem;
            } else {
                minElem = stack.peek().minElem.compareTo(elem) <= 0 ? stack.peek().minElem : elem;
            }
            Pair<E> newElem = new Pair<E>(elem, minElem);
            stack.push(newElem);
        }

        public void pop() {
            stack.pop();
        }

        public E top() {
            return stack.peek().elem;
        }

        public E getMin() {
            return stack.peek().minElem;
        }
    }

    /* ######################################################################################################### */

    // SC:
    // 1) for stack O(n) => this is required as we are designing stack
    // 2) for maintaining MinElem O(1) 🚀 => only one vairable(minElem) using HASH 💡
    class MinStackUsingHash {
        Deque<Integer> stack;
        int minElem;

        public MinStackUsingHash() {
            stack = new ArrayDeque<Integer>();
            minElem = Integer.MAX_VALUE;
        }

        public void push(int currElem) {
            if (stack.isEmpty()) {
                minElem = currElem;
                stack.push(currElem);
            } else {
                if (currElem < minElem) { // modify top as currElem < minElem
                    int hash = getHash(currElem, minElem);
                    stack.push(hash);
                    minElem = currElem;
                }
                else {
                    stack.push(currElem);
                }
            }
        }

        private int getHash(int elem, int minElem) {
            return 2*elem - minElem;
        }

        public void pop() {
            int topElem = stack.peek();
            if(topElem < minElem){ // modified(hashed) top
                minElem = getHash(minElem, topElem);
            }
            stack.pop();
        }

        public Integer top() {
            int topElem = stack.peek();
            if(topElem < minElem){ // modified(hashed) top
                return minElem;
            }
            return topElem;
        }

        public Integer getMin() {
            return minElem;
        }
    }
}
