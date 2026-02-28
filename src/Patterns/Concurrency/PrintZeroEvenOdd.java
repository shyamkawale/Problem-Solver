package Patterns.Concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * https://leetcode.com/problems/print-zero-even-odd
 * LeetCode 1116: Print Zero Even Odd
 * 
 * Problem Description:
 * You have a function printNumber that can be called with an integer parameter 
 * and prints it to the console.
 * 
 * You are given an instance of the class ZeroEvenOdd that has three functions: 
 * zero, even, and odd. The same instance of ZeroEvenOdd will be passed to three 
 * different threads:
 * 
 * - Thread A: calls zero() that should only output 0's
 * - Thread B: calls even() that should only output even numbers
 * - Thread C: calls odd() that should only output odd numbers
 * 
 * Modify the given class to output the series "0102030405..." where the length 
 * of the series must be 2n.
 * 
 * Example:
 * Input: n = 5
 * Output: "0102030405"
 * 
 * Constraints:
 * - 1 <= n <= 1000
 * 
 * Key Concepts:
 * - Thread synchronization
 * - Condition variables / Semaphores
 * - Producer-Consumer pattern variation
 */
public class PrintZeroEvenOdd {

    /**
     * Approach 1: Using Monitor Lock (synchronized + wait/notifyAll)
     * 
     * How it works:
     * - Uses Java's intrinsic lock mechanism with synchronized methods
     * - wait() releases the lock and puts thread to sleep until notified
     * - notifyAll() wakes up all waiting threads to re-check their conditions
     * 
     * State Variables:
     * - zeroPrinted: flag to indicate if zero was just printed
     * 
     * Synchronization Logic:
     * - zero(): waits until zeroPrinted is false, prints 0, sets zeroPrinted = true
     * - odd(): waits until zeroPrinted is true AND it's odd's turn
     * - even(): waits until zeroPrinted is true AND it's even's turn
     * 
     * Time Complexity: O(n) - each number printed once
     * Space Complexity: O(1) - only state variables
     * 
     * Pros: Simple, built into Java, no external libraries needed
     * Cons: notifyAll() wakes ALL threads (even those that can't proceed)
     */
    class ZeroEvenOdd_MonitorLock {
        private int n;               // Total numbers to print
        private boolean zeroPrinted; // Flag: true if 0 was just printed, waiting for odd/even
        private boolean isOddTurn;   // Flag: true if it's odd's turn, false if even's turn
        
        public ZeroEvenOdd_MonitorLock(int n) {
            this.n = n;
            this.zeroPrinted = false;  // Start with zero not printed
            this.isOddTurn = true;     // First number (1) is odd
        }

        // Called by Thread A - prints all zeros
        public synchronized void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i++) {
                // Wait until it's zero's turn (after odd/even has printed)
                while(zeroPrinted) {
                    wait();
                }
                printNumber.accept(0);
                zeroPrinted = true;    // Signal that zero was printed
                notifyAll();           // Wake up odd/even threads
            }
        }

        // Called by Thread B - prints even numbers (2, 4, 6, ...)
        public synchronized void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2; i <= n; i += 2) {
                // Wait until: 1) zero was printed AND 2) it's even's turn
                while(!zeroPrinted || isOddTurn) {
                    wait();
                }
                printNumber.accept(i);
                zeroPrinted = false;   // Reset flag for next zero
                isOddTurn = true;      // Next number is odd
                notifyAll();           // Wake up zero thread
            }
        }

        // Called by Thread C - prints odd numbers (1, 3, 5, ...)
        public synchronized void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i += 2) {
                // Wait until: 1) zero was printed AND 2) it's odd's turn
                while(!zeroPrinted || !isOddTurn) {
                    wait();
                }
                printNumber.accept(i);
                zeroPrinted = false;   // Reset flag for next zero
                isOddTurn = false;     // Next number is even
                notifyAll();           // Wake up zero thread
            }
        }
    }

    /**
     * Approach 2: Using ReentrantLock with Condition
     * 
     * How it works:
     * - Uses explicit Lock and Condition objects instead of intrinsic locks
     * - Provides more flexibility than synchronized (e.g., tryLock, multiple conditions)
     * - await() = wait(), signalAll() = notifyAll()
     * 
     * Key Differences from Monitor Lock:
     * - Lock must be explicitly acquired and released (use try-finally!)
     * - Can have multiple Condition objects for different wait conditions
     * - Offers more features like fair locking, interruptible locking
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Pros: More flexible, can have multiple conditions, fair locking option
     * Cons: More verbose, must remember to unlock in finally block
     */
    class ZeroEvenOdd_ReentrantLock {
        private int n;
        private boolean zeroPrinted;
        private boolean isOddTurn;    // Flag: true if it's odd's turn, false if even's turn
        private final Lock lock;
        private final Condition condition;
        
        public ZeroEvenOdd_ReentrantLock(int n) {
            this.n = n;
            this.zeroPrinted = false;
            this.isOddTurn = true;     // First number (1) is odd
            this.lock = new ReentrantLock();
            this.condition = lock.newCondition();
        }

        // Called by Thread A - prints all zeros
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i++) {
                lock.lock();
                try {
                    // Wait until it's zero's turn
                    while(zeroPrinted) {
                        condition.await();
                    }
                    printNumber.accept(0);
                    zeroPrinted = true;
                    condition.signalAll();
                } finally {
                    lock.unlock();  // Always unlock in finally block!
                }
            }
        }

        // Called by Thread B - prints even numbers
        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2; i <= n; i += 2) {
                lock.lock();
                try {
                    // Wait until zero printed AND it's even's turn
                    while(!zeroPrinted || isOddTurn) {
                        condition.await();
                    }
                    printNumber.accept(i);
                    zeroPrinted = false;
                    isOddTurn = true;      // Next number is odd
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }

        // Called by Thread C - prints odd numbers
        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i += 2) {
                lock.lock();
                try {
                    // Wait until zero printed AND it's odd's turn
                    while(!zeroPrinted || !isOddTurn) {
                        condition.await();
                    }
                    printNumber.accept(i);
                    zeroPrinted = false;
                    isOddTurn = false;     // Next number is even
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * Approach 3: Using Semaphores (RECOMMENDED - Cleanest Solution)
     * 
     * How it works:
     * - Uses three semaphores as "permits" to control which thread can proceed
     * - Semaphore(1) = 1 permit available (thread can proceed)
     * - Semaphore(0) = 0 permits (thread must wait)
     * - acquire() = take a permit (blocks if none available)
     * - release() = give back a permit (unblocks a waiting thread)
     * 
     * Semaphore States:
     * - zeroSemaphore(1): Zero thread starts with permission
     * - oddSemaphore(0): Odd thread waits initially
     * - evenSemaphore(0): Even thread waits initially
     * 
     * Flow for n=5:
     * 1. zero acquires(1→0), prints 0, releases oddSemaphore (i=1 is odd)
     * 2. odd acquires, prints 1, releases zeroSemaphore
     * 3. zero acquires, prints 0, releases evenSemaphore (i=2 is even)
     * 4. even acquires, prints 2, releases zeroSemaphore
     * ... and so on
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * 
     * Pros: 
     * - No spurious wakeups (each semaphore targets specific thread)
     * - Clean, easy to understand logic
     * - No condition checking needed - permits handle synchronization
     * Cons: Requires understanding of semaphore concept
     */
    class ZeroEvenOdd_Semaphore {
        private int n;

        private Semaphore zeroSemaphore;  // Controls when zero can print
        private Semaphore oddSemaphore;   // Controls when odd can print
        private Semaphore evenSemaphore;  // Controls when even can print
        
        public ZeroEvenOdd_Semaphore(int n) {
            this.n = n;
            zeroSemaphore = new Semaphore(1);  // Zero starts first (1 permit)
            oddSemaphore = new Semaphore(0);   // Odd waits (0 permits)
            evenSemaphore = new Semaphore(0);  // Even waits (0 permits)
        }

        // Called by Thread A - prints 0 before each number
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i++) {
                zeroSemaphore.acquire();  // Wait for permission to print 0

                printNumber.accept(0);

                // Signal the appropriate thread based on whether i is odd or even
                if(i % 2 == 0) {
                    evenSemaphore.release();  // Next number is even, wake even thread
                } else {
                    oddSemaphore.release();   // Next number is odd, wake odd thread
                }
            }
        }

        // Called by Thread B - prints even numbers (2, 4, 6, ...)
        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2; i <= n; i += 2) {
                evenSemaphore.acquire();      // Wait for zero to signal us
                printNumber.accept(i);
                zeroSemaphore.release();      // Let zero print next 0
            }
        }

        // Called by Thread C - prints odd numbers (1, 3, 5, ...)
        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i += 2) {
                oddSemaphore.acquire();       // Wait for zero to signal us
                printNumber.accept(i);
                zeroSemaphore.release();      // Let zero print next 0
            }
        }
    }

    /**
     * Approach 4: Lock-Free using AtomicInteger (Spin-Wait / Busy-Wait)
     * 
     * How it works:
     * - Uses AtomicInteger as a state machine to coordinate threads
     * - Threads spin (busy-wait) until it's their turn
     * - No blocking - threads continuously check the state
     * 
     * State Machine:
     * - State 0: Zero's turn (before printing odd number)
     * - State 1: Odd's turn
     * - State 2: Zero's turn (before printing even number)
     * - State 3: Even's turn
     * 
     * State Transitions:
     * 0 → 1 → 2 → 3 → 0 → 1 → 2 → 3 → ...
     * 
     * Flow for n=5:
     * State 0: zero prints 0, sets state=1
     * State 1: odd prints 1, sets state=2
     * State 2: zero prints 0, sets state=3
     * State 3: even prints 2, sets state=0
     * State 0: zero prints 0, sets state=1
     * State 1: odd prints 3, sets state=2
     * ... and so on
     * 
     * Time Complexity: O(n) - but with CPU spinning overhead
     * Space Complexity: O(1)
     * 
     * Pros: 
     * - No context switching overhead (no thread sleep/wake)
     * - Lower latency for short waits
     * - True lock-free (no locks, no blocking)
     * 
     * Cons: 
     * - CPU intensive (busy-waiting burns CPU cycles)
     * - Not suitable for long waits
     * - Thread.yield() is just a hint, not guaranteed
     * 
     * When to use:
     * - Very short wait times expected
     * - Low-latency requirements
     * - CPU resources are not a concern
     */
    class ZeroEvenOdd_LockFree {
        private int n;
        private AtomicInteger state;
        
        // State constants for readability
        private static final int ZERO_BEFORE_ODD = 0;
        private static final int ODD_TURN = 1;
        private static final int ZERO_BEFORE_EVEN = 2;
        private static final int EVEN_TURN = 3;
        
        public ZeroEvenOdd_LockFree(int n) {
            this.n = n;
            this.state = new AtomicInteger(ZERO_BEFORE_ODD);  // Start with zero's turn
        }

        // Called by Thread A - prints 0 before each number
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i++) {
                if(i % 2 == 1) {
                    // Before odd number: wait for state 0
                    while(state.get() != ZERO_BEFORE_ODD) {
                        Thread.yield();  // Hint to scheduler, reduces CPU burn
                    }
                    printNumber.accept(0);
                    state.set(ODD_TURN);  // Signal odd thread
                } else {
                    // Before even number: wait for state 2
                    while(state.get() != ZERO_BEFORE_EVEN) {
                        Thread.yield();
                    }
                    printNumber.accept(0);
                    state.set(EVEN_TURN);  // Signal even thread
                }
            }
        }

        // Called by Thread B - prints even numbers (2, 4, 6, ...)
        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2; i <= n; i += 2) {
                // Spin until it's even's turn
                while(state.get() != EVEN_TURN) {
                    Thread.yield();
                }
                printNumber.accept(i);
                state.set(ZERO_BEFORE_ODD);  // Signal zero (next is odd)
            }
        }

        // Called by Thread C - prints odd numbers (1, 3, 5, ...)
        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i += 2) {
                // Spin until it's odd's turn
                while(state.get() != ODD_TURN) {
                    Thread.yield();
                }
                printNumber.accept(i);
                state.set(ZERO_BEFORE_EVEN);  // Signal zero (next is even)
            }
        }
    }
}
