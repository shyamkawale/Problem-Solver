# Disjoint Sets

Find whether Node1 and Node2 are in same components??
- Using DFS/BFS => TC: O(V+E)
- Using Disjoint Sets => TC: O(constant time) 

- Usually used in Dynamic Graph(Graphs that keep on changing its configuration at any moment)


when problem demads merging, connecting....



we cannot unlink edges to remove nodes form disjoint set.. (not using degree array - becoz of cyclic graphs)

we can store every components nodes separetly like 
```java
// map Entry for each component: (Ult-parent -> {all nodes})
Map<Integer, Set<Integer>> map;
```

if we want to store some extra things in Disjoint Ste problems use different array:
like: `int[] status = new int[n];`

