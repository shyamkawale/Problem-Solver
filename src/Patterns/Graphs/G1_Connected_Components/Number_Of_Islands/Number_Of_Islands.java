package Patterns.Graphs.G1_Connected_Components.Number_Of_Islands;

import java.util.HashSet;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/number-of-islands/

Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), 
return the number of islands.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
You may assume all four edges of the grid are all surrounded by water.

Example 1:
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1

Example 2:
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
*/
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

        Set<Integer> visited = new HashSet<>();
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
        int[][] dir = {{-1,0}, {0,1}, {1,0}, {0,-1}};

        visited.add(col*r+c);
        for(int i=0; i<dir.length; i++) {
            int nr = r+dir[i][0];
            int nc = c+dir[i][1];
            if(isValidIdx(nr, row) && isValidIdx(nc, col) && grid[nr][nc] == '1' && !visited.contains(col*nr+nc)) {
                dfs(nr, nc, grid, visited);
            }
        }
    }

    public boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }
}
