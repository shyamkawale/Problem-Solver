package Patterns.Design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
https://leetcode.com/problems/design-twitter/description/

Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and is able to see the 10 most recent tweets in the user's news feed.

Implement the Twitter class:

1. Twitter() 
    - Initializes your twitter object.
2. void postTweet(int userId, int tweetId) 
    - Composes a new tweet with ID tweetId by the user userId. 
    - Each call to this function will be made with a unique tweetId.
3. List<Integer> getNewsFeed(int userId) 
    - Retrieves the 10 most recent tweet IDs in the user's news feed. 
    - Each item in the news feed must be posted by users who the user followed or by the user themself. 
    - Tweets must be ordered from most recent to least recent.
4. void follow(int followerId, int followeeId) 
    - The user with ID followerId started following the user with ID followeeId.
5. void unfollow(int followerId, int followeeId) 
    - The user with ID followerId started unfollowing the user with ID followeeId.
*/
class DesignTwitter {
    static class Tweet {
        public static long time = 0L;

        int tweetId;
        long timestamp;

        public Tweet(int tweetId) {
            this.tweetId = tweetId;
            this.timestamp = time++;
        }
    }

    Map<Integer, HashSet<Tweet>> userTweetMap;
    Map<Integer, HashSet<Integer>> userFollowerMap;

    public DesignTwitter() {
        userTweetMap = new HashMap<>();
        userFollowerMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!userFollowerMap.containsKey(userId)) {
            userFollowerMap.put(userId, new HashSet<>());
            userTweetMap.put(userId, new HashSet<>());

            userFollowerMap.get(userId).add(userId);
        }

        userTweetMap.get(userId).add(new Tweet(tweetId));
    }
    
    public List<Integer> getNewsFeed(int userId) {

        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a.timestamp, b.timestamp));

        for(int follower: userFollowerMap.getOrDefault(userId, new HashSet<>())) {
            for(Tweet tweet: userTweetMap.getOrDefault(follower, new HashSet<>())) {

                minHeap.offer(tweet);
                if(minHeap.size() > 10) {
                    minHeap.poll();
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        while(!minHeap.isEmpty()) {
            res.add(minHeap.poll().tweetId);
        }

        Collections.reverse(res);
        return res;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!userFollowerMap.containsKey(followerId)) {
            userFollowerMap.put(followerId, new HashSet<>());
            userTweetMap.put(followerId, new HashSet<>());

            userFollowerMap.get(followerId).add(followerId);
        }

        userFollowerMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        userFollowerMap.get(followerId).remove(followeeId);
    }
}

/**
 * Your DesignTwitter object will be instantiated and called as such:
 * DesignTwitter obj = new DesignTwitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
