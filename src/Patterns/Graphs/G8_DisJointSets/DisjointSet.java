package Patterns.Graphs.G8_DisJointSets;

public class DisjointSet {
    private int[] rank;
    private int[] parent;

    private int[] size; // if using unionBySize;
    private int noOfComp;

    public DisjointSet(int n) {
        rank = new int[n+1]; // by default it will be filled by 0.
        size = new int[n+1];

        parent = new int[n+1];

        noOfComp = n;

        for(int i=0; i<n+1; i++) {
            parent[i] = i; // each node is its own parent initially
            size[i] = 1;
        }
    }

    public void unionByRank(int u, int v) {
        int pu = findUPar(u);
        int pv = findUPar(v);

        if(pu == pv) {
            return;
        }
        noOfComp--;

        if(rank[pu] == rank[pv]) {
            rank[pu]++;
            parent[pv] = pu;
        }
        else if(rank[pu] > rank[pv]) {
            parent[pv] = pu; // attach pv to pu
        }
        else if(rank[pu] < rank[pv]) {
            parent[pu] = pv; // attach pu to pv
        }
    }

    public void unionBySize(int u, int v) {
        int pu = findUPar(u);
        int pv = findUPar(v);

        if(pu == pv) {
            return;
        }
        noOfComp--;

        if(size[pu] == size[pv]) {
            size[pu] = size[pu] + size[pv];
            parent[pv] = pu;
        }
        else if(size[pu] > size[pv]) {
            size[pu] = size[pu] + size[pv];
            parent[pv] = pu; // attach pv to pu
        }
        else if(size[pu] < size[pv]) {
            size[pv] = size[pv] + size[pu];
            parent[pu] = pv; // attach pu to pv
        }
    }

    public int findUPar(int node) {
        if(parent[node] == node) {
            return node;
        }

        parent[node] = findUPar(parent[node]);
        return parent[node];
    }

    // getters
    public int[] getParent() {
        return parent;
    }
    public int getNoOfComp() {
        return noOfComp;
    }
}
