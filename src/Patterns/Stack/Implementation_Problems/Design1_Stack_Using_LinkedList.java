package Patterns.Stack.Implementation_Problems;

public class Design1_Stack_Using_LinkedList {
    public static class MyStack {
        private class StackNode {
            int data;
            StackNode next;
            public StackNode(int d) {
                data = d;
                next = null;
            }
        }

        StackNode top;
        int size;

        public MyStack() {
            this.top = null;
            this.size = 0;
        }

        public void stackPush(int x) {
            StackNode element = new StackNode(x);
            element.next = top;
            top = element;
            System.out.println("Element pushed");
            size++;
        }

        public int stackPop() {
            if (top == null){
                return -1;
            }
            int topData = top.data;
            top = top.next;
            return topData;
        }

        public int stackSize() {
            return size;
        }

        public boolean stackIsEmpty() {
            return top == null;
        }

        public void printStack() {
            StackNode current = top;
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println();
        }
    }

    public static class Main {
        public static void main(String args[]) {
          MyStack s = new MyStack();
          s.stackPush(10);
          s.stackPush(20);
          s.printStack();
          System.out.println("Element popped " + s.stackPop());
          System.out.println("Stack size: " + s.stackSize());
          System.out.println("Is Stack empty?: " + s.stackIsEmpty());
      
        }
      }
}
