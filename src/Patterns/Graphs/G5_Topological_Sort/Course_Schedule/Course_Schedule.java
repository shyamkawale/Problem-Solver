package Patterns.Graphs.G5_Topological_Sort.Course_Schedule;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Course_Schedule extends ProblemSolver {

    public static void main(String[] args) {
        new Course_Schedule().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int numCourses = DataConvertor.toInt(args[0]);
        int[][] prerequisites = DataConvertor.to2DIntArray(args[1]);

        boolean canFinishCourse = canFinish(numCourses, prerequisites);
        System.out.println(canFinishCourse);
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int n = numCourses;
        List<List<Integer>> adjList = createGraph(n, prerequisites);
        int[] vis = new int[n];
        int[] pathVis = new int[n];

        for(int i=0; i<n; i++) {
            if(vis[i] == 0) {
                if(!dfs(i, vis, pathVis, adjList)){
                    return false;
                }
            }
        }

        return true;
    }

    private boolean dfs(int node, int[] vis, int[] pathVis, List<List<Integer>> adjList) {
        if(pathVis[node] == 1) {
            return false;
        }

        vis[node] = 1;
        pathVis[node] = 1;

        for(int neighbor: adjList.get(node)) {
            if(vis[neighbor] == 0) {
                if(!dfs(neighbor, vis, pathVis, adjList)) {
                    return false;
                }
            }
            else if(pathVis[neighbor] == 1) {
                return false;
            }
        }

        pathVis[node] = 0;
        return true;
    }

    public List<List<Integer>> createGraph(int n, int[][] edges) {
        List<List<Integer>> adjList = new ArrayList<>();

        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }

        for(int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];

            adjList.get(v).add(u);
        }

        return adjList;
    }

}
