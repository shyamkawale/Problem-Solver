package General.Walking_Robot_Simulation;

import java.util.*;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/walking-robot-simulation/
A robot on an infinite XY-plane starts at point (0, 0) facing north. 
The robot can receive a sequence of these three possible types of commands:

-2: Turn left 90 degrees.
-1: Turn right 90 degrees.
1 <= k <= 9: Move forward k units, one unit at a time.

Some of the grid squares are obstacles. The ith obstacle is at grid point obstacles[i] = (xi, yi). 
If the robot runs into an obstacle, then it will instead stay in its current location and move on to the next command.

Return the maximum Euclidean distance that the robot ever gets from the origin squared (i.e. if the distance is 5, return 25). 
*/
public class Walking_Robot_Simulation extends ProblemSolver{

	@Override
	public void processParameters(String[] args) {
		int[] commands = DataConvertor.toIntArray(args[0]);
        int[][] obstacles = DataConvertor.to2DIntArray(args[1]);

        int result1 = robotSim(commands, obstacles);
        System.out.println(result1);
        int result2 = robotSimWithHashing(commands, obstacles);
        System.out.println(result2);
	}

    public static void main(String[] args) {
        new Walking_Robot_Simulation().readInput();
    }
    
    // O(no_of_obstacles + no_of_commands*(command_value)) => O(n+m*k) where k is constant(0 to 9) => O(n+m) => O(N)
    public int robotSim(int[] commands, int[][] obstacles) {
        int x = 0;
        int y = 0;
        int index = 0;
        int[][] dir = new int[][]{{0,1}, {1,0}, {0,-1}, {-1,0}};
        int maxEuclidianDistance = 0;

        Set<String> obs = new HashSet<String>();
        for(int[] ob: obstacles){
            obs.add(ob[0]+"."+ob[1]);
        }

        for(int cmd: commands){
            if(cmd == -1){
                index = (index + 1)%4;
            }
            else if(cmd == -2){
                index = (index-1 + 4)%4;
            }
            else{
                for(int i=0; i<cmd; i++){
                    int nextX = x + dir[index][0];
                    int nextY = y + dir[index][1];
                    if(obs.contains(nextX+"."+nextY)) break;
                    x = nextX;
                    y = nextY;
                }
            }
            int currentEuclidianDistance = x*x + y*y;
            maxEuclidianDistance = Math.max(maxEuclidianDistance, currentEuclidianDistance);
        }
        System.out.println("x: "+x + ", y: "+y);

        return maxEuclidianDistance;
    }

    // used hashing to identify cordinates uniquely in set. 
    // TC will be same just we are avoiding string operations so faster.
    public int robotSimWithHashing(int[] commands, int[][] obstacles) {
        int x = 0;
        int y = 0;
        int index = 0;
        int[][] dir = new int[][]{{0,1}, {1,0}, {0,-1}, {-1,0}};
        int maxEuclidianDistance = 0;

        Set<Integer> obs = new HashSet<Integer>();
        for(int[] ob: obstacles){
            obs.add(hashCordinates(ob[0], ob[1]));
        }

        for(int cmd: commands){
            if(cmd == -1){
                index = (index + 1)%4;
            }
            else if(cmd == -2){
                index = (index-1 + 4)%4;
            }
            else{
                for(int i=0; i<cmd; i++){
                    int nextX = x + dir[index][0];
                    int nextY = y + dir[index][1];
                    if(obs.contains(hashCordinates(nextX, nextY))) break;
                    x = nextX;
                    y = nextY;
                }
            }
            int currentEuclidianDistance = x*x + y*y;
            maxEuclidianDistance = Math.max(maxEuclidianDistance, currentEuclidianDistance);
        }
        System.out.println("x: "+x + ", y: "+y);
        return maxEuclidianDistance;
    }

    // hashfunction = x*hash+y
    // where hash comes from constraint of cordinates( -3*10^4 < x and y < +3*10^4)
    private int hashCordinates(int x, int y){
        int hashMultiplyer = 60013; // 30k*2 + 13(prime number - anything).
        return x*hashMultiplyer + y;
    }
}
