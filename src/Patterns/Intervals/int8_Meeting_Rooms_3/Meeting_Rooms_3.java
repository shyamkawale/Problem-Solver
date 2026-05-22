package Patterns.Intervals.int8_Meeting_Rooms_3;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

/*
https://leetcode.com/problems/meeting-rooms-iii/

You are given an integer n. 
There are n rooms numbered from 0 to n-1.

You are given a 2D integer array meetings where meetings[i] = [starti, endi] means
that a meeting will be held during the half-closed time interval [starti, endi).

All the values of starti are unique.

Meetings are allocated to rooms in the following manner:

1. Each meeting will take place in the unused room with the lowest number.
2. If there are no available rooms, the meeting will be delayed until a room becomes free. 
   The delayed meeting should have the same duration as the original meeting.
3. When a room becomes unused, meetings that have an earlier original start time should be given the room.

Return the number of the room that held the most meetings. 
If there are multiple rooms, return the room with the lowest number.

A half-closed interval [a, b) is the interval between a and b including a and not including b.
*/
public class Meeting_Rooms_3 extends ProblemSolver {

    public static void main(String[] args) {
        new Meeting_Rooms_3().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        int n = DataConvertor.toInt(args[0]);
        int[][] meetings = DataConvertor.to2DIntArray(args[1]);

        int res = mostBooked(n, meetings);
        System.out.println(res);
    }

    // Two-heaps simulation: a min-heap of free rooms (by room number) and a min-heap
    // of busy rooms (by endTime, then roomNum). For each meeting (processed in start
    // order), first release any busy rooms whose endTime <= meeting start. Then:
    //  - if a free room exists, take the lowest-numbered one and book it for [s, e).
    //  - else the meeting is delayed: take the busy room that frees earliest (ties
    //    broken by lowest roomNum) and book it for [freeTime, freeTime + duration).
    // Track booking counts per room and return the room with max count (ties -> lowest).
    // Time Complexity: O(m log m + m log n) where m = meetings, n = rooms (sort + heap ops)
    // Space Complexity: O(n) for the heaps and frequency array
    public int mostBooked(int n, int[][] meetings) {
        int[] roomFreqs = new int[n];

        Arrays.sort(meetings, (a, b) -> {
            if(a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        Queue<Room> busyRoomQueue = new PriorityQueue<>((a, b) -> {
            if(a.endTime != b.endTime) return Long.compare(a.endTime, b.endTime);
            return Integer.compare(a.roomNum, b.roomNum);
        });

        Queue<Room> freeRoomQueue = new PriorityQueue<>((a, b) -> Integer.compare(a.roomNum, b.roomNum));

        for(int i=0; i<n; i++) {
            freeRoomQueue.offer(new Room(i, 0));
        }

        for(int[] meeting: meetings) {
            while(!busyRoomQueue.isEmpty() && busyRoomQueue.peek().endTime <= meeting[0]) {
                Room freedRoom = busyRoomQueue.poll();
                freedRoom.endTime = 0;
                freeRoomQueue.offer(freedRoom);
            }

            boolean needToWait = freeRoomQueue.isEmpty();
            Room room = needToWait ? busyRoomQueue.poll() : freeRoomQueue.poll();
            roomFreqs[room.roomNum]++;
            room.endTime = needToWait ? room.endTime + (meeting[1]-meeting[0]) : meeting[1];
            busyRoomQueue.offer(room);
        }

        int maxFreq = 0;
        int maxFreqIdx = 0;
        for(int i=0; i<n; i++) {
            if(roomFreqs[i] > maxFreq) {
                maxFreq = roomFreqs[i];
                maxFreqIdx = i;
            }
        }

        return maxFreqIdx;
    }

    public static class Room {
        int roomNum;
        long endTime;

        public Room(int roomNum, long endTime) {
            this.roomNum = roomNum;
            this.endTime = endTime;
        }
    }
}
