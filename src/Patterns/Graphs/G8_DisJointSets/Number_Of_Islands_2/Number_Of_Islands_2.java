package Patterns.Graphs.G8_DisJointSets.Number_Of_Islands_2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Patterns.Graphs.G8_DisJointSets.DisjointSet;

public class Number_Of_Islands_2 extends ProblemSolver {

    public static void main(String[] args) {
        new Number_Of_Islands_2().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int m = DataConvertor.toInt(args[1]);
        int[][] position = DataConvertor.to2DIntArray(args[2]);

        List<Integer> res = numOfIslands(n, m, position);
        System.out.println(res);
    }

    public List<Integer> numOfIslands(int row, int col, int[][] position) {
        int n = position.length;
        DisjointSet ds = new DisjointSet(n);
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        Set<Integer> vis = new HashSet<>();
        List<Integer> res = new ArrayList<>();
        
        for(int pos=0; pos<n; pos++) {
            int r = position[pos][0];
            int c = position[pos][1];
            int currNode = col*r+c;
            vis.add(currNode);

            for(int i=0; i<dir.length; i++) {
                int nr = r + dir[i][0];
                int nc = c + dir[i][1];
                int neighborNode = col*nr+nc;

                if(isValid(nr, row) && isValid(nc, col) && vis.contains(neighborNode)) {
                    int pu = ds.findUPar(currNode);
                    int pv = ds.findUPar(neighborNode);

                    ds.unionByRank(pu, pv);
                }
            }

            res.add(ds.getNoOfComp() - (n-pos) + 1);
        }

        return res;
    }

    private boolean isValid(int idx, int maxIdx) {
        return 0 <= idx && idx < maxIdx;
    }

}
