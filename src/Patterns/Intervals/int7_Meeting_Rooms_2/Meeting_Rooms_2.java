package Patterns.Intervals.int7_Meeting_Rooms_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://takeuforward.org/plus/dsa/problems/meeting-rooms-ii
https://cses.fi/problemset/task/1164/  (Room Allocation)

Given n meetings with their start and end times, find:
 1. The minimum number of rooms required to host all meetings.
 2. The room allocated to each meeting (in original input order) using the minimum
    number of rooms.

Note: A meeting [s, e] occupies the room during [s, e). i.e. if one meeting ends
at time t and another starts at time t, they can share the same room.
*/
public class Meeting_Rooms_2 extends ProblemSolver {

    public static void main(String[] args) {
        new Meeting_Rooms_2().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int[][] intervals = DataConvertor.to2DIntArray(args[0]);

        int minRooms = findMinRoomsRequired(intervals);
        System.out.println(minRooms);

        int[] allocation = findRoomAllocation(intervals);
        System.out.println(Arrays.toString(allocation));
    }

    // maximum overlapping intervals = minimum rooms required
    // Time Complexity: O(nlogn) due to sorting events
    // Space Complexity: O(n) for storing events list
    private int findMinRoomsRequired(int[][] intervals) {
        int n = intervals.length;
        List<int[]> events = new ArrayList<>(2 * n);

        for (int i = 0; i < n; i++) {
            events.add(new int[]{intervals[i][0], +1});  // meeting starts
            events.add(new int[]{intervals[i][1], -1});  // meeting ends
        }

        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0]; // Sort by time ascending

            // If times are equal, process end (-1) before start (+1)
            // because a meeting ending at t frees the room for one starting at t.
            // -1 should come before +1. In ascending order of type: -1 < 1.
            return a[1] - b[1];
        });

        int currRooms = 0;
        int maxRooms = 0;
        for (int[] evt : events) {
            currRooms = currRooms + evt[1];
            maxRooms = Math.max(maxRooms, currRooms);
        }

        return maxRooms;
    }

    // Allocate each meeting to a room using the minimum number of rooms.
    // Returns an int[] where res[i] is the room number (1-indexed) assigned to
    // intervals[i] in the original input order.
    // Time Complexity: O(nlogn) due to sorting + heap operations
    // Space Complexity: O(n) for the heap and the result array
    private int[] findRoomAllocation(int[][] intervals) {
        int n = intervals.length;

        // Track original index so we can map allocation back to input order.
        // order[i] = [start, end, originalIndex]
        int[][] order = new int[n][3];
        for (int i=0; i<n; i++) {
            order[i][0] = intervals[i][0];
            order[i][1] = intervals[i][1];
            order[i][2] = i;
        }

        // Sort meetings by start time ascending.
        Arrays.sort(order, (a, b) -> Integer.compare(a[0], b[0]));

        // Min-heap of [endTime, roomNumber] - the room that frees up earliest is on top.
        Queue<Room> heap = new PriorityQueue<>((a, b) -> Integer.compare(a.endTime, b.endTime));

        int[] allocation = new int[n];
        int roomCnt = 0;

        for (int i=0; i<n; i++) {
            int currStart = order[i][0];
            int currEnd = order[i][1];
            int currIdx = order[i][2];

            if (heap.isEmpty() && heap.peek().endTime > currStart) {
                // No free room available -> open a new one.
                roomCnt++;
                allocation[currIdx] = roomCnt;
                heap.offer(new Room(currEnd, roomCnt));
            } else {
                // Reuse the earliest-freed room
                Room freedRoom = heap.poll();
                allocation[currIdx] = freedRoom.roomNum;
                freedRoom.endTime = currEnd;
                heap.offer(freedRoom);
            }
        }

        return allocation;
    }

    private static class Room {
        int endTime;
        int roomNum;

        public Room(int endTime, int roomNum) {
            this.endTime = endTime;
            this.roomNum = roomNum;
        }
    }

}
