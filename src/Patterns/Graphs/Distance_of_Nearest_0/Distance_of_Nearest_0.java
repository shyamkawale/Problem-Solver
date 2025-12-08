package Patterns.Graphs.Distance_of_Nearest_0;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

public class Distance_of_Nearest_0 extends ProblemSolver {

    public static void main(String[] args) {
        new Distance_of_Nearest_0().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] mat = DataConvertor.to2DIntArray(args[0]);

        // int[][] res1 = updateMatrix1(mat); // commented as gives memoryoverflow error for big inputs
        int[][] res2 = updateMatrix2(mat);
        int[][] res3 = updateMatrix3(mat);
        int[][] res4 = updateMatrix4(mat);

        // MatrixWrapper.printMatrix(res1);
        MatrixWrapper.printMatrix(res2);
        System.out.println();
        MatrixWrapper.printMatrix(res3);
        System.out.println();
        MatrixWrapper.printMatrix(res4);
    }

    // Sol1: BFS on every 1 to find distance till 0
    // very less efficient....
    public int[][] updateMatrix1(int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;
        int[][] res = new int[row][col];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (mat[r][c] == 1) {
                    res[r][c] = bfs(r, c, mat);
                }
            }
        }

        return res;
    }

    public int bfs(int r, int c, int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

        queue.offer(col * r + c);
        visited.add(col * r + c);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            for (int i = 0; i < size; i++) {
                int polledNode = queue.poll();
                for (int j = 0; j < dir.length; j++) {
                    int nr = polledNode / col + dir[j][0];
                    int nc = polledNode % col + dir[j][1];

                    if (isValidIdx(nr, row) && isValidIdx(nc, col)) {
                        if (mat[nr][nc] == 0) {
                            return level;
                        }
                        if (!visited.contains(col * nr + nc)) {
                            queue.offer(col * nr + nc);
                        }
                    }
                }
            }
        }

        return -1;
    }

    public boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

    // Sol 2: MultiSource BFS + Pair to store steps(Storing all 0s and then travelling to update 1s with steps)
    public int[][] updateMatrix2(int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;
        int[][] res = new int[row][col];

        Set<Integer> visited = new HashSet<>(row*col);
        Queue<Pair> queue = new ArrayDeque<>(row*col);
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(mat[r][c] == 0) {
                    queue.offer(new Pair(col*r+c, 0));
                    visited.add(col*r+c);
                }
            }
        }

        while(!queue.isEmpty()) {
            Pair polledNode = queue.poll();
            for(int j=0; j<dir.length; j++) {
                int nr = polledNode.vertex/col + dir[j][0];
                int nc = polledNode.vertex%col + dir[j][1];
                int steps = polledNode.steps;

                if(isValidIdx(nr, row) && isValidIdx(nc, col) && !visited.contains(col*nr+nc) && mat[nr][nc] == 1) {
                    res[nr][nc] = steps+1;
                    queue.offer(new Pair(col*nr+nc, steps+1));
                    visited.add(col*nr+nc);
                }
            }
        }

        return res;
    }

    public static class Pair {
        int vertex;
        int steps;

        public Pair(int vertex, int steps) {
            this.vertex = vertex;
            this.steps = steps;
        }
    }

    // Sol3: MultiSource BFS with steps as 0 for initial 0s
    public int[][] updateMatrix3(int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;

        int[] visited = new int[row*col];
        Queue<Integer> queue = new ArrayDeque<>(row*col);
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(mat[r][c] == 0) {
                    queue.offer(col*r+c);
                    visited[col*r+c] = 1;
                }
            }
        }

        while(!queue.isEmpty()) {
            int polledNode = queue.poll();
            for(int j=0; j<dir.length; j++) {
                int nr = polledNode/col + dir[j][0];
                int nc = polledNode%col + dir[j][1];
                int steps = mat[polledNode/col][polledNode%col];

                if(isValidIdx(nr, row) && isValidIdx(nc, col) && visited[col*nr+nc]==0 && mat[nr][nc] == 1) {
                    mat[nr][nc] = steps+1;
                    queue.offer(col*nr+nc);
                    visited[col*nr+nc] = 1;
                }
            }
        }

        return mat;
    }

    // Sol4: MultiSourrce BFS + works with input other than 1s
    public int[][] updateMatrix4(int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;

        int[] visited = new int[row*col];
        Queue<Integer> queue = new ArrayDeque<>(row*col);
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for(int r=0; r<row; r++) {
            for(int c=0; c<col; c++) {
                if(mat[r][c] == 0) {
                    queue.offer(col*r+c);
                    visited[col*r+c] = 1;
                }
                else{
                    mat[r][c] = row*col;
                }
            }
        }

        while(!queue.isEmpty()) {
            int polledNode = queue.poll();
            for(int j=0; j<dir.length; j++) {
                int nr = polledNode/col + dir[j][0];
                int nc = polledNode%col + dir[j][1];
                int steps = mat[polledNode/col][polledNode%col];

                if(isValidIdx(nr, row) && isValidIdx(nc, col) && visited[col*nr+nc]==0 && mat[nr][nc] > steps) {
                    mat[nr][nc] = steps+1;
                    queue.offer(col*nr+nc);
                    visited[col*nr+nc] = 1;
                }
            }
        }

        return mat;
    }

}
