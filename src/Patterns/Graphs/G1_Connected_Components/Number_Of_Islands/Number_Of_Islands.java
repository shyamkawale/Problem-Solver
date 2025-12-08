package Patterns.Graphs.G1_Connected_Components.Number_Of_Islands;

import java.util.HashSet;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Number_Of_Islands extends ProblemSolver {

    public static void main(String[] args) {
        new Number_Of_Islands().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        char[][] arr = DataConvertor.to2DCharArray(args[0]);

        int res = numIslands(arr);
        System.out.println(res);
    }

    public int numIslands(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        Set<Integer> visited = new HashSet<>(row*col);
        int count = 0;

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(grid[r][c] == '1' && !visited.contains(col*r+c)) {
                    count++;
                    dfs(r, c, grid, visited);
                }
            }
        }

        return count;
    }

    public void dfs(int r, int c, char[][] grid, Set<Integer> visited) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dir = {{0,1}, {0,-1}, {1,0}, {-1,0}};

        visited.add(col*r+c);
        for(int i=0; i<dir.length; i++) {
            int updR = r+dir[i][0];
            int updC = c+dir[i][1];
            if(isValidIdx(updR, row) && isValidIdx(updC, col) && grid[updR][updC] == '1' && !visited.contains(col*updR+updC)) {
                dfs(updR, updC, grid, visited);
            }
        }
    }

    public boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }
}
