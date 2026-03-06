package Patterns.Graphs.G5_Matrix_Graph.Surrounded_Regions;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/surrounded-regions/
You are given an m x n matrix board containing letters 'X' and 'O', capture regions that are surrounded:

Connect: A cell is connected to adjacent cells horizontally or vertically.
Region: To form a region connect every 'O' cell.
Surround: A region is surrounded if none of the 'O' cells in that region are on the edge of the board. 
Such regions are completely enclosed by 'X' cells.

To capture a surrounded region, replace all 'O's with 'X's in-place within the original board. 
You do not need to return anything.

Sol: Traverse border cells do dfs on cell with 'O' and make all neighboring cells visited. At last make all cells 'X' except visited.
*/
public class Surrounded_Regions extends ProblemSolver {

    public static void main(String[] args) {
        new Surrounded_Regions().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        char[][] grid = DataConvertor.to2DCharArray(args[0]);

        solve(grid);
    }

    public void solve(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        int[][] vis = new int[row][col];
        
        for(int c=0; c<col; c++) {
            // first row..
            if(board[0][c] == 'O' && vis[0][c] == 0) {
                dfs(0, c, board, vis);
            }

            // last row..
            if(board[row-1][c] == 'O' && vis[row-1][c] == 0) {
                dfs(row-1, c, board, vis);
            }
        }

        for(int r=0; r<row; r++) {
            // first col..
            if(board[r][0] == 'O' && vis[r][0] == 0) {
                dfs(r, 0, board, vis);
            }

            // last col..
            if(board[r][col-1] == 'O' && vis[r][col-1] == 0) {
                dfs(r, col-1, board, vis);
            }
        }

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(board[r][c] == 'O' && vis[r][c] == 0) {
                    board[r][c] = 'X';
                }
            }
        }
    }

    public void dfs(int r, int c, char[][] board, int[][] vis) {
        int row = board.length;
        int col = board[0].length;
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        vis[r][c] = 1;

        for(int i=0; i<dir.length; i++) {
            int nr = r + dir[i][0];
            int nc = c + dir[i][1];

            if(validIdx(nr, row) && validIdx(nc, col) && vis[nr][nc] != 1 && board[nr][nc] == 'O') {
                vis[nr][nc] = 1;
                dfs(nr, nc, board, vis);
            }
        }
    }

    public boolean validIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

}
