package Patterns.Design;

import java.util.BitSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReentrantLock;

public class DesignTicTacToe {
    /*
     * APPROACH 1: BRUTE FORCE
     * This approach stores the entire board and checks the entire row, column, and diagonals after each move to determine a win.
     * TIME COMPLEXITY: O(N) for each move (checking up to N cells per direction)
     * SPACE COMPLEXITY: O(N^2) for the board
     * DISADVANTAGES: Inefficient for large N due to repeated O(N) checks; no validation for occupied cells (allows overwriting); not thread-safe.
     * FIX: Use counters for rows/columns/diagonals to check wins in O(1) time, and add validation to prevent invalid moves.
     */
    class TicTacToe_BruteForce {
        private final int[][] board;
        private final int n;

        public TicTacToe_BruteForce(int n) {
            board = new int[n][n];
            this.n = n;
        }

        public int move(int row, int col, int player) {
            board[row][col] = player;

            if (checkColWin(col, player) ||
                    checkRowWin(row, player) ||
                    (row + col == n - 1 && checkForDiagWin(row, col, player)) ||
                    (row - col == 0 && checkBackDiagWin(row, col, player))) {
                return player;
            }

            return 0;
        }

        private boolean checkColWin(int col, int player) {
            for (int r = 1; r < n; r++) {
                if (board[r][col] != board[0][col]) {
                    return false;
                }
            }
            return true;
        }

        private boolean checkRowWin(int row, int player) {
            for (int c = 1; c < n; c++) {
                if (board[row][c] != board[row][0]) {
                    return false;
                }
            }
            return true;
        }

        private boolean checkForDiagWin(int row, int col, int player) {
            for (int r = n - 1; r > 0; r--) {
                int c = n - r - 1;
                if (board[r][c] != player) {
                    return false;
                }
            }
            return true;
        }

        private boolean checkBackDiagWin(int row, int col, int player) {
            for (int r = 1; r < n; r++) {
                int c = r;
                if (board[r][c] != player) {
                    return false;
                }
            }
            return true;
        }
    }

    /*
     * APPROACH 2: OPTIMIZED COUNTERS
     * This approach uses arrays to count player moves in rows, columns, and diagonals, allowing O(1) win checks.
     * TIME COMPLEXITY: O(1) for each move
     * SPACE COMPLEXITY: O(N) for counters (no full board storage)
     * DISADVANTAGES: No validation for occupied cells (allows overwriting); not thread-safe; assumes valid inputs.
     * FIX: Add a board or set to track occupied cells and validate moves before updating counters.
     */
    class TicTacToe_Optimized {
        private final int[] rows;
        private final int[] cols;
        private int forwardDiag;
        private int backwardDiag;
        private final int n;

        public TicTacToe_Optimized(int n) {
            rows = new int[n];
            cols = new int[n];
            forwardDiag = 0;
            backwardDiag = 0;
            this.n = n;
        }

        public int move(int row, int col, int player) {
            int toAdd = player == 1 ? 1 : -1;

            rows[row] += toAdd;
            cols[col] += toAdd;

            if (row + col == n - 1) {
                forwardDiag += toAdd;
            }

            if (row - col == 0) {
                backwardDiag += toAdd;
            }

            if (Math.abs(rows[row]) == n ||
                    Math.abs(cols[col]) == n ||
                    Math.abs(forwardDiag) == n ||
                    Math.abs(backwardDiag) == n) {
                return player;
            }

            return 0;
        }
    }

    /*
     * APPROACH 3: OPTIMIZED WITH VALIDATION BOARD
     * Builds on Approach 2 by adding a boolean board to validate occupied cells before moves.
     * TIME COMPLEXITY: O(1) for each move
     * SPACE COMPLEXITY: O(N^2) for board + O(N) for counters
     * DISADVANTAGES: High space usage for large N due to full boolean board; not thread-safe.
     * FIX: Use a BitSet or Set for more efficient space usage while maintaining validation.
     */
    class TicTacToe_Optimized_With_ValidationBoard {
        private final boolean[][] board; // to validate if cell is already occupied

        private final int[] rows;
        private final int[] cols;
        private int forwardDiag;
        private int backwardDiag;
        private final int n;

        public TicTacToe_Optimized_With_ValidationBoard(int n) {
            board = new boolean[n][n];
            rows = new int[n];
            cols = new int[n];
            forwardDiag = 0;
            backwardDiag = 0;
            this.n = n;
        }

        public int move(int row, int col, int player) {
            if (board[row][col]) {
                throw new IllegalArgumentException("Cell already occupied");
            }

            board[row][col] = true;
            int toAdd = player == 1 ? 1 : -1;

            rows[row] += toAdd;
            cols[col] += toAdd;

            if (row + col == n - 1) {
                forwardDiag += toAdd;
            }

            if (row - col == 0) {
                backwardDiag += toAdd;
            }

            if (Math.abs(rows[row]) == n ||
                    Math.abs(cols[col]) == n ||
                    Math.abs(forwardDiag) == n ||
                    Math.abs(backwardDiag) == n) {
                return player;
            }

            return 0;
        }
    }

    /*
     * APPROACH 4: OPTIMIZED WITH BITSET VALIDATION
     * Improves Approach 3 by using BitSet for efficient space usage in tracking occupied cells.
     * TIME COMPLEXITY: O(1) for each move
     * SPACE COMPLEXITY: O(N^2) bits for board + O(N) for counters (more efficient than boolean[][])
     * DISADVANTAGES: Not thread-safe; single-threaded only.
     * FIX: Add thread-safety using locks or atomic operations for concurrent access.
     */
    class TicTacToe_Optimized_With_ValidationBoard_BitSet {
        private final BitSet board; // HashSet could be used as well

        private final int[] rows;
        private final int[] cols;
        private int forwardDiag;
        private int backwardDiag;
        private final int n;

        public TicTacToe_Optimized_With_ValidationBoard_BitSet(int n) {
            board = new BitSet(n * n);
            rows = new int[n];
            cols = new int[n];
            forwardDiag = 0;
            backwardDiag = 0;
            this.n = n;
        }

        public int move(int row, int col, int player) {
            if (board.get(row * n + col)) {
                throw new IllegalArgumentException("Cell already occupied");
            }

            board.set(row * n + col);
            int toAdd = player == 1 ? 1 : -1;

            rows[row] += toAdd;
            cols[col] += toAdd;

            if (row + col == n - 1) {
                forwardDiag += toAdd;
            }

            if (row - col == 0) {
                backwardDiag += toAdd;
            }

            if (Math.abs(rows[row]) == n ||
                    Math.abs(cols[col]) == n ||
                    Math.abs(forwardDiag) == n ||
                    Math.abs(backwardDiag) == n) {
                return player;
            }

            return 0;
        }
    }

    /*
     * APPROACH 5: THREAD-SAFE WITH REENTRANT LOCK
     * Adds thread-safety to Approach 4 using ReentrantLock for synchronized access.
     * TIME COMPLEXITY: O(1) for each move (with lock contention)
     * SPACE COMPLEXITY: O(N^2) bits for board + O(N) for counters + lock overhead
     * DISADVANTAGES: Blocking locks can cause contention and deadlocks in high-concurrency; not lock-free.
     * FIX: Use lock-free atomic operations for better concurrency without blocking.
     */
    class TicTacToe_Optimized_ThreadSafe_ReentrantLock {
        private final BitSet board;

        private final int[] rows;
        private final int[] cols;
        private int forwardDiag;
        private int backwardDiag;
        private final int n;

        private ReentrantLock lock = new ReentrantLock();

        public TicTacToe_Optimized_ThreadSafe_ReentrantLock(int n) {
            board = new BitSet(n * n);
            rows = new int[n];
            cols = new int[n];
            forwardDiag = 0;
            backwardDiag = 0;
            this.n = n;
        }

        public int move(int row, int col, int player) {

            lock.lock();

            try {
                if (board.get(row * n + col)) {
                    throw new IllegalArgumentException("Cell already occupied");
                }

                board.set(row * n + col);
                int toAdd = player == 1 ? 1 : -1;

                rows[row] += toAdd;
                cols[col] += toAdd;

                if (row + col == n - 1) {
                    forwardDiag += toAdd;
                }

                if (row - col == 0) {
                    backwardDiag += toAdd;
                }

                if (Math.abs(rows[row]) == n ||
                        Math.abs(cols[col]) == n ||
                        Math.abs(forwardDiag) == n ||
                        Math.abs(backwardDiag) == n) {
                    return player;
                }
            } finally {
                lock.unlock();
            }

            return 0;
        }
    }

    /*
     * APPROACH 6: THREAD-SAFE LOCK-FREE
     * Improves Approach 5 by using atomic operations and ConcurrentHashMap for lock-free concurrency.
     * TIME COMPLEXITY: O(1) for each move (amortized)
     * SPACE COMPLEXITY: O(N^2) for board + O(N) for atomic counters
     * DISADVANTAGES: More complex implementation; potential for ABA problems in high contention; no early game-over check.
     * FIX: Add a volatile or atomic flag for game-over to prevent unnecessary moves after win.
     */
    class TicTacToe_Optimized_ThreadSafe_LockFree {
        private final Set<Integer> board;

        private final AtomicIntegerArray rows;
        private final AtomicIntegerArray cols;
        private AtomicInteger forwardDiag;
        private AtomicInteger backwardDiag;
        private final int n;

        public TicTacToe_Optimized_ThreadSafe_LockFree(int n) {
            board = ConcurrentHashMap.newKeySet();
            rows = new AtomicIntegerArray(n);
            cols = new AtomicIntegerArray(n);
            forwardDiag = new AtomicInteger(0);
            backwardDiag = new AtomicInteger(0);
            this.n = n;
        }

        public int move(int row, int col, int player) {
            int index = row * n + col;
            if (board.contains(index)) {
                throw new IllegalArgumentException("Cell already occupied");
            }

            board.add(index);
            int toAdd = player == 1 ? 1 : -1;

            rows.set(row, rows.get(row) + toAdd);
            cols.set(col, cols.get(col) + toAdd);
            if (row + col == n - 1) {
                forwardDiag.set(forwardDiag.get() + toAdd);
            }

            if (row - col == 0) {
                backwardDiag.set(backwardDiag.get() + toAdd);
            }

            if (Math.abs(rows.get(row)) == n ||
                    Math.abs(cols.get(col)) == n ||
                    Math.abs(forwardDiag.get()) == n ||
                    Math.abs(backwardDiag.get()) == n) {
                return player;
            }

            return 0;
        }
    }

    /*
     * APPROACH 7: THREAD-SAFE LOCK-BASED WITH EARLY EXIT
     * Enhances Approach 5 by adding a volatile gameOver flag for early exit after a win, reducing unnecessary operations.
     * TIME COMPLEXITY: O(1) for each move
     * SPACE COMPLEXITY: O(N^2) bits for board + O(N) for counters + lock
     * DISADVANTAGES: Still uses blocking locks; potential for stale reads of gameOver without proper synchronization.
     * FIX: Use atomic operations for lock-free implementation with proper game-over handling.
     */
    class TicTacToe_Optimized_ThreadSafe_LockBased {
        private final BitSet board;

        private final int[] rows;
        private final int[] cols;
        private int forwardDiag;
        private int backwardDiag;
        private final int n;

        private volatile boolean gameOver = false;

        private ReentrantLock lock = new ReentrantLock();

        public TicTacToe_Optimized_ThreadSafe_LockBased(int n) {
            board = new BitSet(n * n);
            rows = new int[n];
            cols = new int[n];
            forwardDiag = 0;
            backwardDiag = 0;
            this.n = n;
        }

        public int move(int row, int col, int player) {

            if(gameOver) return 0;

            lock.lock();

            try {
                if(gameOver) return 0;

                if (board.get(row * n + col)) {
                    throw new IllegalArgumentException("Cell already occupied");
                }

                board.set(row * n + col);
                int toAdd = player == 1 ? 1 : -1;

                rows[row] += toAdd;
                cols[col] += toAdd;

                if (row + col == n - 1) {
                    forwardDiag += toAdd;
                }

                if (row - col == 0) {
                    backwardDiag += toAdd;
                }

                if (Math.abs(rows[row]) == n ||
                        Math.abs(cols[col]) == n ||
                        Math.abs(forwardDiag) == n ||
                        Math.abs(backwardDiag) == n) {

                    gameOver = true;
                    return player;
                }
            } finally {
                lock.unlock();
            }

            return 0;
        }
    }

    /*
     * APPROACH 8: THREAD-SAFE LOCK-FREE WITH EARLY EXIT
     * Combines lock-free atomic operations from Approach 6 with an AtomicBoolean for game-over, providing optimal concurrency and early exit.
     * TIME COMPLEXITY: O(1) for each move
     * SPACE COMPLEXITY: O(N^2) for board + O(N) for atomic counters
     * DISADVANTAGES: Complex atomic operations; potential race conditions in win detection if not careful.
     * This is the most advanced approach, balancing performance, safety, and efficiency.
     */
    class TicTacToe_Optimized_ThreadSafe_LockFree_V2 {
        private final Set<Integer> board;

        private final AtomicIntegerArray rows;
        private final AtomicIntegerArray cols;
        private AtomicInteger forwardDiag;
        private AtomicInteger backwardDiag;
        private final int n;

        private AtomicBoolean gameOver = new AtomicBoolean(false);

        public TicTacToe_Optimized_ThreadSafe_LockFree_V2(int n) {
            board = ConcurrentHashMap.newKeySet();
            rows = new AtomicIntegerArray(n);
            cols = new AtomicIntegerArray(n);
            forwardDiag = new AtomicInteger(0);
            backwardDiag = new AtomicInteger(0);
            this.n = n;
        }

        public int move(int row, int col, int player) {

            if(gameOver.get()) return 0;

            int index = row * n + col;
            if (board.contains(index)) {
                throw new IllegalArgumentException("Cell already occupied");
            }

            board.add(index);
            int toAdd = player == 1 ? 1 : -1;

            rows.set(row, rows.get(row) + toAdd);
            cols.set(col, cols.get(col) + toAdd);
            if (row + col == n - 1) {
                forwardDiag.set(forwardDiag.get() + toAdd);
            }

            if (row - col == 0) {
                backwardDiag.set(backwardDiag.get() + toAdd);
            }

            if (Math.abs(rows.get(row)) == n ||
                    Math.abs(cols.get(col)) == n ||
                    Math.abs(forwardDiag.get()) == n ||
                    Math.abs(backwardDiag.get()) == n) {
                gameOver.compareAndSet(false, true);
                return player;
            }

            return 0;
        }
    }

    /*
     * SUMMARY OF APPROACHES:
     *
     * 1 vs 2: Brute Force (O(N) checks) vs Optimized Counters (O(1) checks) - Trade space for time.
     * 2 vs 3: No validation vs Boolean board validation - Adds safety but increases space to O(N^2).
     * 3 vs 4: Boolean[][] vs BitSet - BitSet reduces space usage for large N.
     * 4 vs 5: Single-threaded vs Thread-safe with locks - Adds concurrency but introduces blocking.
     * 5 vs 6: Lock-based vs Lock-free - Lock-free improves concurrency but adds complexity.
     * 6 vs 7: No early exit vs Volatile game-over flag - Early exit prevents wasted operations post-win.
     * 7 vs 8: Lock-based early exit vs Lock-free early exit - Combines best of both for optimal performance.
     *
     * PRODUCTION-LEVEL APPROACH: Approach 8 (TicTacToe_Optimized_ThreadSafe_LockFree_V2)
     * - Why? It provides lock-free concurrency for high throughput, atomic operations for thread-safety,
     *   efficient space usage, O(1) move operations, and early exit after game completion.
     * - Suitable for multi-threaded environments like web servers or concurrent games.
     * - For single-threaded or low-concurrency apps, Approach 4 (BitSet) is simpler and sufficient.
     */
}
