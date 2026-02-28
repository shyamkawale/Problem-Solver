package Patterns.Design;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Design a Ride Sharing System
 * 
 * Requirements:
 * - Riders can request rides
 * - Drivers can register availability
 * - System matches drivers with riders (FIFO order)
 * - Riders can cancel their ride requests
 * 
 * Two versions based on whether a rider can request multiple times.
 */
public class DesignRideSharingSystem {

    /**
     * Approach 1: Simple Version - Rider can request only once at a time
     * 
     * Data Structures:
     * - riderQueue: FIFO queue of rider IDs waiting for a ride
     * - driverQueue: FIFO queue of available driver IDs
     * - cancelledRider: Set of rider IDs who cancelled (for lazy removal)
     * 
     * Key Insight: Lazy Deletion
     * - Instead of removing from queue (O(n)), we mark as cancelled in Set (O(1))
     * - During matching, we skip cancelled riders
     * 
     * TC: addRider O(1), addDriver O(1), matchDriverWithRider O(k) where k = cancelled riders at front
     * SC: O(n + m) where n = riders, m = drivers
     */
    class RideSharingSystem_V1 {
        Set<Integer> cancelledRider;    // Tracks cancelled rider IDs for lazy removal
        Deque<Integer> riderQueue;      // FIFO queue of waiting riders
        Deque<Integer> driverQueue;     // FIFO queue of available drivers

        public RideSharingSystem_V1() {
            riderQueue = new ArrayDeque<>();
            driverQueue = new ArrayDeque<>();
            cancelledRider = new HashSet<>();
        }

        // Add a rider to the waiting queue
        // If rider previously cancelled, remove from cancelled set (re-requesting)
        public void addRider(int riderId) {
            cancelledRider.remove(riderId);
            riderQueue.offer(riderId);
        }

        // Add a driver to the available queue
        public void addDriver(int driverId) {
            driverQueue.offer(driverId);
        }

        // Match the first available driver with first valid rider
        // Skip any cancelled riders at the front of queue (lazy deletion)
        public int[] matchDriverWithRider() {
            // Lazy removal: skip cancelled riders at front of queue
            while (!riderQueue.isEmpty() && cancelledRider.contains(riderQueue.peek())) {
                cancelledRider.remove(riderQueue.poll()); // Clean up cancelled set too
            }

            // No match possible if either queue is empty
            if (riderQueue.isEmpty() || driverQueue.isEmpty()) {
                return new int[] { -1, -1 };
            }

            return new int[] { driverQueue.poll(), riderQueue.poll() };
        }

        // Mark rider as cancelled (lazy deletion - don't remove from queue)
        public void cancelRider(int riderId) {
            cancelledRider.add(riderId);
        }
    }

    /**
     * Your RideSharingSystem object will be instantiated and called as such:
     * RideSharingSystem obj = new RideSharingSystem();
     * obj.addRider(riderId);
     * obj.addDriver(driverId);
     * int[] param_3 = obj.matchDriverWithRider();
     * obj.cancelRider(riderId);
     */

    /**
     * Approach 2: Advanced Version - Rider can request multiple times
     * 
     * Problem with V1: If same rider requests multiple times, we can't distinguish
     * which request to cancel. Cancelling removes ALL pending requests.
     * 
     * Solution: Version-based tracking
     * - Each request has a version number
     * - Only the latest version is valid
     * - Cancelling increments version, invalidating old requests
     * 
     * Data Structures:
     * - rideRequestQueue: FIFO queue of (riderId, version) pairs
     * - driverQueue: FIFO queue of available driver IDs
     * - rideVersionMap: Map<riderId, latestVersion> - tracks current valid version
     * 
     * Key Insight: Version Mismatch = Invalid Request
     * - When rider cancels, we remove from map (or could increment version)
     * - During matching, if request version != map version, it's stale
     * 
     * TC: addRider O(1), addDriver O(1), matchDriverWithRider O(k) where k = stale requests at front
     * SC: O(n + m) where n = total requests, m = drivers
     */
    class RideSharingSystem_V2 {
        static class RideRequest {
            private final int riderId;
            private final int version;  // Version when this request was created

            public RideRequest(int riderId, int version) {
                this.riderId = riderId;
                this.version = version;
            }
        }

        Map<Integer, Integer> rideVersionMap;   // riderId -> current valid version
        Deque<RideRequest> rideRequestQueue;    // FIFO queue of ride requests
        Deque<Integer> driverQueue;             // FIFO queue of available drivers

        public RideSharingSystem_V2() {
            rideRequestQueue = new ArrayDeque<>();
            driverQueue = new ArrayDeque<>();
            rideVersionMap = new HashMap<>();
        }

        // Add a new ride request with incremented version
        // This invalidates any previous pending requests from same rider
        public void addRider(int riderId) {
            int newVersion = rideVersionMap.getOrDefault(riderId, 0) + 1;
            rideVersionMap.put(riderId, newVersion);
            rideRequestQueue.offer(new RideRequest(riderId, newVersion));
        }

        // Add a driver to the available queue
        public void addDriver(int driverId) {
            driverQueue.offer(driverId);
        }

        // Match the first available driver with first valid rider request
        // A request is valid only if: rider exists in map AND version matches
        public int[] matchDriverWithRider() {
            // Lazy removal: skip stale/cancelled requests at front of queue
            while (!rideRequestQueue.isEmpty()) {
                RideRequest req = rideRequestQueue.peek();
                int riderId = req.riderId;

                // Request is INVALID if:
                // 1. Rider not in map (cancelled), OR
                // 2. Request version != current version (stale request)
                if (!rideVersionMap.containsKey(riderId) || req.version != rideVersionMap.get(riderId)) {
                    rideRequestQueue.poll();  // Remove stale request
                } else {
                    break;  // Found valid request, exit loop
                }
            }

            // No match possible if either queue is empty
            if (rideRequestQueue.isEmpty() || driverQueue.isEmpty()) {
                return new int[] { -1, -1 };
            }

            // Match driver with rider
            int matchedRiderId = rideRequestQueue.poll().riderId;
            int matchedDriverId = driverQueue.poll();
            
            // Remove rider from version map (ride fulfilled)
            rideVersionMap.remove(matchedRiderId);

            return new int[] { matchedDriverId, matchedRiderId };
        }

        // Cancel all pending requests for this rider
        // Simply removing from map invalidates all their requests (version mismatch)
        public void cancelRider(int riderId) {
            rideVersionMap.remove(riderId);
        }
    }
}
