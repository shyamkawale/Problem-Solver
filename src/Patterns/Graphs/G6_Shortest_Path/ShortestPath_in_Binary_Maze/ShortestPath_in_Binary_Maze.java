package Patterns.Graphs.G6_Shortest_Path.ShortestPath_in_Binary_Maze;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class ShortestPath_in_Binary_Maze extends ProblemSolver {

    public static void main(String[] args) {
        new ShortestPath_in_Binary_Maze().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] grid = DataConvertor.to2DIntArray(args[0]);

        int res1 = shortestPathBinaryMatrix(Arrays.stream(grid)
                                          .map(int[]::clone)
                                          .toArray(int[][]::new));
        System.out.println(res1);
    }
    
    // We can use Queue here as it is UnWeighted graph.. so BFS + Queue will always give shortest path first time..
    public int shortestPathBinaryMatrix(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[] srcNodeIdx = new int[] { 0, 0 };
        int[] destNodeIdx = new int[] { row - 1, col - 1 };

        if (grid[srcNodeIdx[0]][srcNodeIdx[1]] != 0) {
            return -1;
        }

        int[][] dir = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { srcNodeIdx[0], srcNodeIdx[1], 1 });
        grid[srcNodeIdx[0]][srcNodeIdx[1]] = 1; // to mark them visited

        while (!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int currNodeR = polledNode[0];
            int currNodeC = polledNode[1];
            int currPath = polledNode[2];

            if(currNodeR == destNodeIdx[0] && currNodeC == destNodeIdx[1]) {
                return currPath;
            }

            for (int i = 0; i < dir.length; i++) {
                int nr = currNodeR + dir[i][0];
                int nc = currNodeC + dir[i][1];

                if (isValid(nr, row) && isValid(nc, col) && grid[nr][nc] == 0) {
                    queue.offer(new int[] { nr, nc, currPath+1 });
                    grid[nr][nc] = 1;
                }
            }
        }

        return -1;
    }

    public boolean isValid(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

}
