package Patterns.Graphs.G5_Matrix_Graph.Flood_Fill;

import java.util.ArrayDeque;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Matrix.MatrixWrapper;

/*
https://leetcode.com/problems/flood-fill/

You are given an image represented by an m x n grid of integers image, 
where image[i][j] represents the pixel value of the image. 
You are also given three integers sr, sc, and color. 
Your task is to perform a flood fill on the image starting from the pixel image[sr][sc].

To perform a flood fill:

Begin with the starting pixel and change its color to color.
Perform the same process for each pixel that is directly adjacent (pixels that share a side with the original pixel, 
either horizontally or vertically) and shares the same color as the starting pixel.
Keep repeating this process by checking neighboring pixels of the updated pixels and modifying their color if 
it matches the original color of the starting pixel.
The process stops when there are no more adjacent pixels of the original color to update.
Return the modified image after performing the flood fill.

Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
Output: [[2,2,2],[2,2,0],[2,0,1]]
*/
public class Flood_Fill extends ProblemSolver {

    public static void main(String[] args) {
        new Flood_Fill().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] image = DataConvertor.to2DIntArray(args[0]);
        int sr = DataConvertor.toInt(args[1]);
        int sc = DataConvertor.toInt(args[2]);
        int color = DataConvertor.toInt(args[3]);

        int[][] res = floodFill(image, sr, sc, color);
        MatrixWrapper.printMatrix(res);
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int row = image.length;
        int col = image[0].length;
        
        int origColor = image[sr][sc];
        if (color == origColor) {
            return image;
        }

        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        Queue<int[]> queue = new ArrayDeque<>();

        queue.offer(new int[] { sr, sc });
        image[sr][sc] = color;

        while (!queue.isEmpty()) {
            int[] polledNode = queue.poll();
            for (int i = 0; i < dir.length; i++) {
                int nr = polledNode[0] + dir[i][0];
                int nc = polledNode[1] + dir[i][1];

                if (isValidIdx(nr, row) && isValidIdx(nc, col) && image[nr][nc] == origColor) {
                    queue.offer(new int[] { nr, nc });
                    image[nr][nc] = color;
                }
            }
        }

        return image;
    }

    public boolean isValidIdx(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }
}
