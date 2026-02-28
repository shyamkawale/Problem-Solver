package ToRemember;

public class FenwickTree {
    private int[] tree;

    public FenwickTree(int n) {
        tree = new int[n + 1];
    }

    // Adds element of index 'i'
    // Time: O(log N)
    public void update(int idx, int delta) {
        while (idx < tree.length) {
            tree[idx] += delta; // CHANGE: += becomes ^=
            idx += (idx & -idx);
        }
    }

    // Returns prefix sum from 1 to idx
    // TC: O(log N)
    public int prefix(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += tree[idx]; // CHANGE: += becomes ^=
            idx -= idx & (-idx); // Move to parent range
        }
        return sum;
    }

    // Returns sum of range [L, R]
    public int rangeSum(int L, int R) {
        return prefix(R) - prefix(L - 1); 
    }
}
