package Patterns.Heap.Design;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

@SuppressWarnings({ "unchecked", "unused" })
public class MyPriorityQueue<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private Comparator<E> comparator;
    private int size;

    public MyPriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    public MyPriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }
    
    public MyPriorityQueue(Comparator<E> comparator){
        this(DEFAULT_CAPACITY, comparator);
    }

    public MyPriorityQueue(int initialCapacity, Comparator<E> comparator){
        if(initialCapacity < 0){
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.elements = new Object[initialCapacity];
        this.comparator = comparator;
        this.size = 0;
    }

    public E peek(){
        if(size == 0){
            throw new NoSuchElementException("Priority queue is empty");
        }
        return (E) elements[0];
    }

    // insert
    public boolean offer(E elem){
        if(elem == null){
            throw new NullPointerException();
        }

        ensureCapacity();
        int idx = size;
        elements[size++] = elem;
        heapifyUp(idx, elem);

        return true;
    }

    // extractMin
    public E poll(){
        if(size == 0){
            throw new NoSuchElementException("Priority queue is empty");
        }
        
        E topElem = (E) elements[0];

        elements[0] = elements[size-1];
        elements[size-1] = null;

        size--;
        heapifyDown(0);
        return topElem;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private E getParent(int idx){
        return (E) elements[(idx-1)/2];
    }

    private void heapifyUp(int idx, E elem){
        while(idx>0 && compare(getParent(idx), elem) > 0){
            int parentIdx = (idx-1)/2;
            swap(parentIdx, idx);
            idx = parentIdx;
        }
    }

    private void heapifyDown(int currIdx){
        int leftChildIdx = 2*currIdx + 1;
        int rightChildIdx = 2*currIdx + 2;
        int smallestElemIdx = currIdx;

        if(leftChildIdx < size && compare(((E)elements[leftChildIdx]), (E)elements[smallestElemIdx]) < 0){
            smallestElemIdx = leftChildIdx;
        }
        if(rightChildIdx < size && compare(((E)elements[rightChildIdx]), (E)elements[smallestElemIdx]) < 0){
            smallestElemIdx = rightChildIdx;
        }

        if(smallestElemIdx != currIdx){
            swap(smallestElemIdx, currIdx);
            heapifyDown(smallestElemIdx);
        }
    }

    private int compare(E e1, E e2){
        if(comparator != null){
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }

    private void swap(int idx1, int idx2){
        E elem1 = (E) elements[idx1];
        elements[idx1] = elements[idx2];
        elements[idx2] = elem1;
    }

    private void ensureCapacity(){
        if(size >= elements.length){
            int newCapacity = 2 * elements.length;
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }


    public class Main{
        public static void main(String[] args) {
            MyPriorityQueue<Integer> minHeapPQ = new MyPriorityQueue<Integer>();

            minHeapPQ.offer(36);
            minHeapPQ.offer(45);
            minHeapPQ.offer(18);
            minHeapPQ.offer(27);
            minHeapPQ.offer(9);
            minHeapPQ.offer(50);
            System.out.println(Arrays.toString(minHeapPQ.elements));

            while(!minHeapPQ.isEmpty()){
                int polled = minHeapPQ.poll();
                System.out.println(polled);
            }

            System.out.println("***************************************************");

            class DescIntComparator implements Comparator<Integer>{
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }

                // @Override
                // public int compare(Integer o1, Integer o2) {
                //     return o2 - o1;
                // }

                // @Override
                // public int compare(Integer o1, Integer o2) {
                //     if(o1 < o2){
                //         return 1; // we want descending so replace -1 to 1 here
                //     }
                //     else if(o1 > o2){
                //         return -1; // we want descending so replace 1 o -1 here
                //     }
                //     return 0;
                // }
            }
            MyPriorityQueue<Integer> maxHeapPQ = new MyPriorityQueue<Integer>(new DescIntComparator());
            // MyPriorityQueue<Integer> maxHeapPQ = new MyPriorityQueue<Integer>((o1, o2) -> o2.compareTo(o1)); // we want o2 to win(descending order)
            // MyPriorityQueue<Integer> maxHeapPQ = new MyPriorityQueue<Integer>((o1, o2) -> o2 - o1); // we want o2 to win(descending order)
            // MyPriorityQueue<Integer> maxHeapPQ = new MyPriorityQueue<Integer>(Collections.reverseOrder()); // readymade comparator given by Collections util class

            maxHeapPQ.offer(36);
            maxHeapPQ.offer(45);
            maxHeapPQ.offer(18);
            maxHeapPQ.offer(27);
            maxHeapPQ.offer(9);
            maxHeapPQ.offer(50);
            System.out.println(Arrays.toString(maxHeapPQ.elements));

            while(!maxHeapPQ.isEmpty()){
                int polled = maxHeapPQ.poll();
                System.out.println(polled);
            }
        }
    }
}

