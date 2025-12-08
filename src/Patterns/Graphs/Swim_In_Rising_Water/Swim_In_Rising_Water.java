package Patterns.Graphs.Swim_In_Rising_Water;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Patterns.Graphs.G8_DisJointSets.DisjointSet;

public class Swim_In_Rising_Water extends ProblemSolver {

    public static void main(String[] args) {
        new Swim_In_Rising_Water().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] grid = DataConvertor.to2DIntArray(args[0]);

        int res1 = swimInWater_DFS(grid);
        int res2 = swimInWater_BFS(grid);
        int res3 = swimInWater_BinarySearch(grid);
        int res4 = swimInWater_DisjointSet(grid);
        System.out.println(res1 + " " + res2 + " " + res3 + " " + res4);
    }

    public int swimInWater_DFS(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[] src = new int[] { 0, 0 };
        int[] dest = new int[] { row - 1, col - 1 };

        int[][] minTime = new int[row][col];
        for (int r = 0; r < row; r++) {
            Arrays.fill(minTime[r], Integer.MAX_VALUE);
        }

        minTime[0][0] = grid[0][0];
        dfs(src, src, dest, minTime, grid);

        // MatrixWrapper.printMatrix(minTime);
        return minTime[row - 1][col - 1];
    }

    public void dfs(int[] curr, int[] parent, int[] dest, int[][] minTime, int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

        int largestNodeTillNow = Math.max(minTime[parent[0]][parent[1]], grid[curr[0]][curr[1]]);

        minTime[curr[0]][curr[1]] = Math.min(minTime[curr[0]][curr[1]], largestNodeTillNow);

        // System.out.println(Arrays.toString(curr));
        // MatrixWrapper.printMatrix(minTime);

        if (curr[0] == dest[0] && curr[1] == dest[1]) {
            return;
        }

        for (int i = 0; i < dir.length; i++) {
            int nr = curr[0] + dir[i][0];
            int nc = curr[1] + dir[i][1];

            if (isValid(nr, row) && isValid(nc, col) && minTime[nr][nc] > minTime[curr[0]][curr[1]]) {
                dfs(new int[] { nr, nc }, curr, dest, minTime, grid);
            }
        }
    }

    public boolean isValid(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

    // Dijktras Algorithm
    private int swimInWater_BFS(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[] src = new int[] { 0, 0 };
        int[] dest = new int[] { row - 1, col - 1 };
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

        int minTime = 0;
        // {r, c, maxTimeTillNow}
        Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        int[][] vis = new int[row][col];
        queue.offer(new int[] { src[0], src[1], grid[src[0]][src[1]] });
        vis[src[0]][src[1]] = 1;

        while (!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            int r = polledNode[0];
            int c = polledNode[1];
            int currMaxTime = polledNode[2];

            if (r == dest[0] && c == dest[1]) {
                return currMaxTime;
            }

            for (int i = 0; i < dir.length; i++) {
                int nr = r + dir[i][0];
                int nc = c + dir[i][1];

                if (isValid(nr, row) && isValid(nc, col) && vis[nr][nc] == 0) {
                    queue.offer(new int[] { nr, nc, Math.max(currMaxTime, grid[nr][nc]) });
                    vis[nr][nc] = 1;
                }
            }
        }

        return minTime;
    }

    private int swimInWater_BinarySearch(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[] src = new int[] { 0, 0 };
        int[] dest = new int[] { row - 1, col - 1 };

        int left = 0;
        int right = Integer.MIN_VALUE;
        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                right = Math.max(right, grid[r][c]);
            }
        }

        int res = -1;
        while(left <= right) {
            int mid =  left + (int) Math.floor((right - left) / 2);
            int[][] vis = new int[row][col];

            if(canReach(src, dest, mid, vis, grid)) {
                res = mid;
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }
        return res;
    }

    // check if all element in the any path from src to dest <= maxTime
    // i.e if we can reach from src to dest with any path which has all elements <= maxTime
    private boolean canReach(int[] curr, int[] dest, int maxTime, int[][] vis, int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int r = curr[0];
        int c = curr[1];
        vis[r][c] = 1;

        if(grid[r][c] > maxTime) {
            return false;
        }

        if(r == dest[0] && c == dest[1]) {
            return true;
        }

        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for(int i=0; i<dir.length; i++) {
            int nr = r + dir[i][0];
            int nc = c + dir[i][1];

            if(isValid(nr, row) && isValid(nc, col) && vis[nr][nc] == 0 && grid[nr][nc] <= maxTime) {
                if(canReach(new int[]{nr, nc}, dest, maxTime, vis, grid)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Using Disjoint Set
    private int swimInWater_DisjointSet(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        List<int[]> cells = new ArrayList<>();

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                cells.add(new int[]{col*r+c, grid[r][c]});
            }
        }

        Collections.sort(cells, (a,b) -> a[1]-b[1]);

        int[] vis = new int[row*col];
        DisjointSet ds = new DisjointSet(row*col);
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for(int[] cell: cells) {
            int curr = cell[0];
            int height = cell[1];

            vis[curr] = 1;
            int r = curr/col;
            int c = curr%col;

            for(int i=0; i<dir.length; i++) {
                int nr = r + dir[i][0];
                int nc = c + dir[i][1];
                int neighborCell = nr*col+nc;

                if(isValid(nr, row) && isValid(nc, col) && vis[neighborCell] == 1) {
                    ds.unionByRank(curr, neighborCell);
                }
            }

            if(ds.findUPar(0) == ds.findUPar(row*col-1)) {
                return height;
            }
        }

        return -1;
    }
}
