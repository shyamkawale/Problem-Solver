package Patterns.Stack.Implementation_Problems;

public class Design2_Queue_Using_LinkedList {
    public static class MyQueue {
        public class QueueNode {
            int val;
            QueueNode next;

            QueueNode(int data) {
                val = data;
                next = null;
            }
        }

        QueueNode front = null, rear = null;
        int size = 0;

        public boolean isEmpty() {
            return front == null;
        }

        public int peek() {
            if(isEmpty()){
                System.out.println("Queue is Empty");
                return -1;
            }
            return front.val;
        }

        public void enqueue(int value) {
            QueueNode temp = new QueueNode(value);

            if(front == null){
                front = temp;
                rear = temp;
            }
            else{
                rear.next = temp;
                rear = temp;
            }
            System.out.println(value + " Inserted into Queue ");
            size++;

        }

        public void dequeue() {
            if(front == null){
                System.out.println("Queue is Empty");
            }
            else{
                System.out.println(front.val + " Removed From Queue");
                front = front.next;
                size--;
            }
        }
    }

    public static class Main{
        public static void main(String[] args) {
            MyQueue queue = new MyQueue();
            queue.enqueue(10);
            queue.enqueue(20);
            queue.enqueue(30);
            queue.enqueue(40);
            queue.enqueue(50);
            queue.dequeue();
            System.out.println("The size of the Queue is "+queue.size);
            System.out.println("The Peek element of the Queue is "+queue.peek());
        }
    }
}
