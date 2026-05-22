package Patterns.Concurrency;

import java.util.concurrent.Semaphore;

public class Print100Numbers {
    static volatile int x = 1;
    private final static Semaphore oddSemaphore = new Semaphore(1);
    private final static Semaphore evenSemaphore = new Semaphore(0);
    public static void main(String[] args) {

        Thread oddThread = new Thread(() -> {
            while (true) {
                try {
                    oddSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (x > 100) {
                    evenSemaphore.release();
                    break;
                }
                System.out.println(x);
                x++;
                evenSemaphore.release();
            }
        });

        Thread evenThread = new Thread(() -> {
            while (true) {
                try {
                    evenSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (x > 100) {
                    oddSemaphore.release();
                    break;
                }
                System.out.println(x);
                x++;
                oddSemaphore.release();
            }
        });

        oddThread.start();
        evenThread.start();
    }
}
