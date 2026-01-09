package Patterns.Greedy.Minimum_Cost_to_Acquire_Required_Items;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Minimum_Cost_to_Acquire_Required_Items extends ProblemSolver {

    public static void main(String[] args) {
        new Minimum_Cost_to_Acquire_Required_Items().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int cost1 = DataConvertor.toInt(args[0]);
        int cost2 = DataConvertor.toInt(args[1]);
        int costBoth = DataConvertor.toInt(args[2]);
        int need1 = DataConvertor.toInt(args[3]);
        int need2 = DataConvertor.toInt(args[4]);

        long res = minimumCost(cost1, cost2, costBoth, need1, need2);
        System.out.println(res);
    }

    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
    long minCost = 0L;

    // STEP 1: Determine if the "Combo Deal" is worth it for the shared needs.
    // Is buying one item of Type 3 cheaper than buying one of Type 1 AND one of Type 2?
    if (costBoth < cost1 + cost2) {
        
        // If yes, we use the Combo item to satisfy as much as we can for *both* needs simultaneously.
        int minNeed = Math.min(need1, need2);
        
        minCost = minCost + (long) minNeed * costBoth;

        // Reduce both needs as we have taken combo deal 
        need1 = need1 - minNeed;
        need2 = need2 - minNeed;
    }

    // STEP 2: Satisfy any remaining Type 1 requirement.
    // Even if we only need Type 1 now, we check: Is costBoth cheaper than cost1?
    minCost = minCost + (long) need1 * Math.min(cost1, costBoth);

    // STEP 3: Satisfy any remaining Type 2 requirement.
    // Similar logic: Use the cheaper option between the specific item and the combo item.
    minCost = minCost + (long) need2 * Math.min(cost2, costBoth);

    return minCost;
}

}
