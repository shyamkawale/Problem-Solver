package Patterns.Stack.Implementation_Problems;

import java.util.ArrayDeque;
import java.util.Deque;

/*
https://leetcode.com/problems/online-stock-span

Design an algorithm that collects daily price quotes for some stock and returns the span of that stock's price for the current day.

The span of the stock's price in one day is the maximum number of consecutive days (starting from that day and going backward) for which the stock price was less than or equal to the price of that day.

For example, if the prices of the stock in the last four days is [7,2,1,2] and the price of the stock today is 2, then the span of today is 4 because starting from today, the price of the stock was less than or equal 2 for 4 consecutive days.
Also, if the prices of the stock in the last four days is [7,34,1,2] and the price of the stock today is 8, then the span of today is 3 because starting from today, the price of the stock was less than or equal 8 for 3 consecutive days.
Implement the StockSpanner class:

StockSpanner() Initializes the object of the class.
int next(int price) Returns the span of the stock's price given that today's price is price.
 */
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
