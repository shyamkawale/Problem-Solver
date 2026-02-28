package Patterns.Logic_Building.Maximum_Manhattan_Distance_After_K_Changes;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/maximum-manhattan-distance-after-k-changes/

You are given a string s consisting of the characters 'N', 'S', 'E', and 'W', where s[i] indicates movements in an infinite grid:

'N' : Move north by 1 unit.
'S' : Move south by 1 unit.
'E' : Move east by 1 unit.
'W' : Move west by 1 unit.
Initially, you are at the origin (0, 0). You can change at most k characters to any of the four directions.

Find the maximum Manhattan distance from the origin that can be achieved at any time while performing the movements in order.

The Manhattan Distance between two cells (xi, yi) and (xj, yj) is |xi - xj| + |yi - yj|.

*/
public class Maximum_Manhattan_Distance_After_K_Changes extends ProblemSolver {

    public static void main(String[] args) {
        new Maximum_Manhattan_Distance_After_K_Changes().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String s = DataConvertor.toString(args[0]);
        int k = Integer.parseInt(args[1]);

        int res = maxDistance(s, k);
        System.out.println(res);
    }

    // Manhatten Distance = |x1 - x2| + |y1 - y2|
    // Maximum Manhattan Distance after n steps is X + Y.
    // If we want to improve 1 step in Manhattan Distance, we need to first reduce 1 step in opposite direction and then add 1 step in the desired direction.
    // that is why for every 1 step increase in Manhattan Distance, we need to use 2 changes.
    // TC: O(n), SC: O(1)
    public int maxDistance(String s, int k) {
        int y = 0;
        int x = 0;
        int maxDist = 0;
        int maxAns = 0;

        for(char ch: s.toCharArray()) {
            maxDist++;
            if(ch == 'N') {
                y++;
            }
            else if(ch == 'S') {
                y--;
            }
            else if(ch == 'E') {
                x++;
            }
            else if(ch == 'W') {
                x--;
            }

            int origDist = Math.abs(y)+Math.abs(x);

            maxAns = Math.max(maxAns, Math.min(maxDist, origDist + k*2));
        }

        return maxAns;
    }
}
