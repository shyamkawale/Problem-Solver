package Patterns.Graphs.G8_DisJointSets.Power_Grid_Maintenance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Graphs.GraphsWrapper;
import Patterns.Graphs.G8_DisJointSets.DisjointSet;

/*
Approach1: DFS
Approach2: Disjoint-Set
 */
public class Power_Grid_Maintenance extends ProblemSolver {

    public static void main(String[] args) {
        new Power_Grid_Maintenance().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] connections = DataConvertor.to2DIntArray(args[1]);
        int[][] queries = DataConvertor.to2DIntArray(args[2]);

        int[] res1 = processQueries_DFS(n, connections, queries);
        int[] res2 = processQueries_DisjointSet(n, connections, queries);
        System.out.println(Arrays.toString(res1) + " " + Arrays.toString(res2));
    }

    public int[] processQueries_DFS(int n, int[][] connections, int[][] queries) {
        List<List<Integer>> adjList = GraphsWrapper.createUndirectedGraph(n, connections);
        int[] status = new int[n+1];
        Arrays.fill(status, 1);

        List<Integer> res = new ArrayList<>();

        for(int[] query: queries) {
            int type = query[0];
            int u = query[1];

            if(type == 1) {
                if(status[u] == 1) {
                    res.add(u);
                }
                else {
                    int[] vis = new int[n+1];
                    dfs(u, status, vis, adjList);

                    // to find the minimum of all visited and online power-station
                    boolean foundFlag = false;
                    for(int i=1; i<n+1; i++) {
                        if(i == u) continue;
                        if(status[i] == 1 && vis[i] == 1) {
                            res.add(i);
                            foundFlag = true;
                            break;
                        }
                    }

                    // if not found then add -1
                    if(!foundFlag) {
                        res.add(-1);
                    }
                }
            }
            else if(type == 2) {
                status[u] = 0;
            }
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public void dfs(int node, int[] status, int[] vis, List<List<Integer>> adjList) {
        vis[node] = 1;

        for(int neighbor: adjList.get(node)) {
            if(vis[neighbor] == 0) {
                dfs(neighbor, status, vis, adjList);
            }
        }
    }

    public int[] processQueries_DisjointSet(int n, int[][] connections, int[][] queries) {
        int[] status = new int[n + 1]; // status of power-stations
        Arrays.fill(status, 1); // initially all active

        DisjointSet ds = new DisjointSet(n);
        for (int[] edge : connections) {
            int u = edge[0];
            int v = edge[1];

            ds.unionByRank(u, v);
        }

        // 🔥🔥🔥 (Tricky and if this is figured then all done..)
        // map Entry for each component: (Ult-parent -> {all nodes in ascending order})
        Map<Integer, TreeSet<Integer>> map = new HashMap<>();
        for(int i=1; i<n+1; i++) {
            int pu = ds.findUPar(i);
            map.computeIfAbsent(pu, k -> new TreeSet<>()).add(i);
        }

        List<Integer> res = new ArrayList<>();

        for (int[] query : queries) {
            int queryType = query[0];
            int u = query[1];

            if (queryType == 1) {
                if (status[u] == 1) {
                    res.add(u);
                }
                else {
                    int pu = ds.findUPar(u);
                    TreeSet<Integer> set = map.get(pu);
                    
                    if(set.isEmpty()) {
                        res.add(-1);
                    }
                    else {
                        res.add(set.first());
                    }
                }
            } else if (queryType == 2) {
                int pu = ds.findUPar(u);
                map.get(pu).remove(u); // once station become offline we cannot restore it to active(online) -> that's why this solution worked!!
                status[u] = 0;
            }
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }
}
