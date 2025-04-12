package Patterns.Stack.Implementation_Problems;

import java.util.ArrayDeque;
import java.util.Deque;

public class Design6_StockSpan {
    public class Pair{
        int key;
        int val;
        public Pair(int key, int val){
            this.key = key;
            this.val = val;
        }
    }
    class StockSpanner {
        Deque<Pair> stack;
        
        public StockSpanner() {
            stack = new ArrayDeque<Pair>();
        }
        
        public int next(int price) {
            int spanCnt = 1;
            if(stack.isEmpty()){
                stack.push(new Pair(price, spanCnt));
            }
            else{
                while(!stack.isEmpty() && stack.peek().key <= price){
                    spanCnt = spanCnt + stack.pop().val;
                }

                stack.push(new Pair(price, spanCnt));
            }

            return spanCnt;
        }
    }
}
