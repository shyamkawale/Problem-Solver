package Contests.servers_problem;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/*
There are n servers for deploying applications. Server i can handle maximum max_req[i] requests, but currently has requests[i] requests to serve.

To balance the load, some requests must be redirected between servers. The latency when redirecting from server i to server j is |i - j|. The goal is to find the minimum possible maximum latency (min-max latency) when redirecting requests optimally, ensuring no server exceeds its capacity.

If it is impossible to serve all requests, return -1.

Example
n = 4
requests = [1, 3, 2, 4]
max_req = [2, 1, 5, 3]

Optimal redirections:

Redirect 2 requests from server 2 to server 3

Redirect 1 request from server 4 to server 3

Maximum latency = max(|2-3|, |4-3|) = 1
The answer is 1.

Function Description
Complete the function getMinLatency in the editor with the following parameter(s):

int requests[n]: the request counts currently assigned to each server

int max_req[n]: the maximum number of requests each server can serve

Returns

int: The minimal max_latency possible respecting the given conditions
*/
public class servers_problem extends ProblemSolver {

    public static void main(String[] args) {
        new servers_problem().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[] requests = DataConvertor.toIntArray(args[0]);
        int[] max_req = DataConvertor.toIntArray(args[1]);

        int res = getMinLatency(requests, max_req);
        System.out.println(res);
    }

    private int getMinLatency(int[] requests, int[] max_req) {
        int n = requests.length;

        // Check if total requests <= total capacity
        long totalReq = 0, totalCap = 0;
        for (int i = 0; i < n; i++) {
            totalReq += requests[i];
            totalCap += max_req[i];
        }
        if (totalReq > totalCap) return -1;

        // Check if no transfer needed (k=0 is valid)
        boolean noExcess = true;
        for (int i = 0; i < n; i++) {
            if (requests[i] > max_req[i]) {
                noExcess = false;
                break;
            }
        }
        if (noExcess) return 0;

        // Binary search on the answer
        int left = 1, right = n - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canBalance(requests, max_req, mid, n)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * Check if load can be balanced with maximum latency k.
     * Uses two-pass greedy approach:
     * Pass 1: Left to right - excess goes rightward
     * Pass 2: Right to left - remaining excess goes leftward
     */
    private boolean canBalance(int[] requests, int[] max_req, int k, int n) {
        long[] remaining = new long[n];
        Deque<long[]> buffer = new ArrayDeque<>(); // stores [originPosition, amount]

        // Pass 1: Left to right (send excess rightward)
        for (int i = 0; i < n; i++) {
            long diff = requests[i] - max_req[i];

            // Remove expired excess (can't reach current position going right)
            while (!buffer.isEmpty() && buffer.peekFirst()[0] + k < i) {
                long[] expired = buffer.pollFirst();
                remaining[(int) expired[0]] += expired[1];
            }

            if (diff < 0) {
                // Has spare capacity, absorb excess from buffer
                long cap = -diff;
                while (cap > 0 && !buffer.isEmpty() && buffer.peekFirst()[0] + k >= i) {
                    long[] front = buffer.peekFirst();
                    if (front[1] <= cap) {
                        cap -= front[1];
                        buffer.pollFirst();
                    } else {
                        front[1] -= cap;
                        cap = 0;
                    }
                }
                remaining[i] = -cap; // negative means remaining capacity
            } else if (diff > 0) {
                // Has excess, add to buffer for rightward propagation
                buffer.addLast(new long[]{i, diff});
            }
        }

        // Remaining items in buffer couldn't go right, mark as remaining excess
        while (!buffer.isEmpty()) {
            long[] item = buffer.pollFirst();
            remaining[(int) item[0]] += item[1];
        }

        // Pass 2: Right to left (send remaining excess leftward)
        buffer.clear();
        for (int i = n - 1; i >= 0; i--) {
            long rem = remaining[i];

            // Check for expired excess (can't reach current position going left)
            while (!buffer.isEmpty() && buffer.peekFirst()[0] - k > i) {
                return false; // Excess couldn't be absorbed within latency k
            }

            if (rem < 0) {
                // Has spare capacity, absorb excess from buffer
                long cap = -rem;
                while (cap > 0 && !buffer.isEmpty()) {
                    long[] front = buffer.peekFirst();
                    if (front[0] - k > i) break; // can't reach
                    if (front[1] <= cap) {
                        cap -= front[1];
                        buffer.pollFirst();
                    } else {
                        front[1] -= cap;
                        cap = 0;
                    }
                }
            } else if (rem > 0) {
                // Has remaining excess, add to buffer for leftward propagation
                buffer.addLast(new long[]{i, rem});
            }
        }

        return buffer.isEmpty(); // True if all excess was absorbed
    }

    private static boolean canSatisfy(int L, List<Integer> requests, List<Integer> max_req, int n) {
        // Use long for surplus and deficit to handle large capacities
        long[] surplus = new long[n];
        long[] deficit = new long[n];
        
        for (int i = 0; i < n; i++) {
            if (requests.get(i) > max_req.get(i)) {
                surplus[i] = (long) requests.get(i) - max_req.get(i);
            } else {
                deficit[i] = (long) max_req.get(i) - requests.get(i);
            }
        }
        
        int deficitPtr = 0;
        
        for (int i = 0; i < n; i++) {
            while (surplus[i] > 0) {
                // Ensure the deficit pointer respects the minimum latency bound (i - L)
                if (deficitPtr < i - L) {
                    deficitPtr = i - L;
                }
                
                // Skip servers with no remaining capacity
                while (deficitPtr < n && deficit[deficitPtr] == 0) {
                    deficitPtr++;
                }
                
                // If the nearest available capacity is out of our current max latency (i + L)
                if (deficitPtr > i + L || deficitPtr >= n) {
                    return false;
                }
                
                // Transfer as many requests as possible
                long transfer = Math.min(surplus[i], deficit[deficitPtr]);
                surplus[i] -= transfer;
                deficit[deficitPtr] -= transfer;
            }
        }
        
        return true;
    }

}
