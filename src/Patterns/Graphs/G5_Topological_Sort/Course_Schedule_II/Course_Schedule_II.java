package Patterns.Graphs.G5_Topological_Sort.Course_Schedule_II;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/course-schedule-ii/

There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. 
You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that 
you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return the ordering of courses you should take to finish all courses. If there are many valid answers, 
return any of them. If it is impossible to finish all courses, return an empty array.

Example 1:
Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0. So the correct course order is [0,1].

Example 2:
Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. 
Both courses 1 and 2 should be taken after you finished course 0.
So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].

Example 3:
Input: numCourses = 1, prerequisites = []
Output: [0]
*/
public class Course_Schedule_II extends ProblemSolver {
    public static void main(String[] args) {
        new Course_Schedule_II().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int numCourses = DataConvertor.toInt(args[0]);
        int[][] prerequisites = DataConvertor.to2DIntArray(args[1]);

        int[] res = findOrder(numCourses, prerequisites);
        System.out.println(Arrays.toString(res));
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int n = numCourses;
        List<List<Integer>> adjList = createGraph(n, prerequisites);

        return findTopoSort(n, adjList);
    }

    private int[] findTopoSort(int n, List<List<Integer>> adjList) {
        int[] res = new int[n];
        int k = n;
        int[] inDeg = new int[n];
        Queue<Integer> queue = new ArrayDeque<>();

        for (List<Integer> list : adjList) {
            for (int v : list) {
                inDeg[v]++;
            }
        }

        for (int i = 0; i < n; i++) {
            if (inDeg[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int polledNode = queue.poll();

            for (int neighbor : adjList.get(polledNode)) {
                if (inDeg[neighbor] > 0) { // redundant becoz particular vertex will come here only till no.of of
                                           // inDegree times..
                    inDeg[neighbor]--;
                    if (inDeg[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }

            res[--k] = polledNode;
        }

        return k == 0 ? res : new int[0];
    }

    public List<List<Integer>> createGraph(int n, int[][] edges) {
        List<List<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            adjList.get(u).add(v);
        }

        return adjList;
    }

}
