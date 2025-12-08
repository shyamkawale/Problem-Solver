package Patterns.Graphs.G8_DisJointSets;

public class DisjointSetMain {
    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(7);
        ds.unionBySize(0, 1);
        ds.unionBySize(1, 2);
        ds.unionBySize(3, 4);
        ds.unionBySize(5, 6);
        ds.unionBySize(4, 5);

        // if 2 and 6 same or not
        if (ds.findUPar(2) == ds.findUPar(6)) {
            System.out.println("Same");
        } 
        else {
            System.out.println("Not Same");
        }

        ds.unionBySize(2, 6);

        if (ds.findUPar(2) == ds.findUPar(6)) {
            System.out.println("Same");
        } 
        else {
            System.out.println("Not Same");
        }
    }
}
