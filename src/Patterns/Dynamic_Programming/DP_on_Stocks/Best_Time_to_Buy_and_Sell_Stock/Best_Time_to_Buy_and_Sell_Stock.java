package Patterns.Dynamic_Programming.DP_on_Stocks.Best_Time_to_Buy_and_Sell_Stock; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
public class Best_Time_to_Buy_and_Sell_Stock extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Best_Time_to_Buy_and_Sell_Stock().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        int[] prices = DataConvertor.toIntArray(args[0]); 
 
        int res = maxProfit(prices); 
        System.out.println(res); 
    } 
 
    public int maxProfit(int[] prices) {
        int n = prices.length;

        int min = prices[0];
        int maxProfit = 0;

        for(int i=1; i<n; i++) {
            int profit = prices[i] - min;
            maxProfit = Math.max(maxProfit, profit);
            min = Math.min(min, prices[i]);
        }

        return maxProfit;
    }
 
} 
