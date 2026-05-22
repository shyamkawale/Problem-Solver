package Patterns.Graphs.G5_Topological_Sort.Course_Schedule;

import java.util.ArrayList;
import java.util.List;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;


/*
https://leetcode.com/problems/course-schedule/

There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. 
You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that 
you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.

Example 1:
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0. So it is possible.

Example 2:
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0, a
nd to take course 0 you should also have finished course 1. So it is impossible.


// To complete all Courses there should not be any cycle.. so just find if we have cycle?
*/
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

            adjList.get(v).add(u); // becoz for edge [u, v] -> question tells that edge is v->u as u depends on v
        }

        return adjList;
    }

}
