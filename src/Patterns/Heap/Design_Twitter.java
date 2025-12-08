package Patterns.Heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Design_Twitter {
    public static class Tweet {
        int tweetId;
        int userId;

        public Tweet(int tweetId, int userId) {
            this.tweetId = tweetId;
            this.userId = userId;
        }
    }

    public static class User {
        int userId;
        Set<Integer> followers;

        public User(int userId) {
            this.userId = userId;
            this.followers = new HashSet<Integer>();
        }

        public void addFollower(int followerId) {
            this.followers.add(followerId);
        }

        public void removeFollower(int followerId) {
            this.followers.remove(followerId);
        }
    }

    public static class Twitter {
        List<Tweet> tweets;
        Map<Integer, User> users;

        public Twitter() {
            this.tweets = new ArrayList<Tweet>();
            this.users = new HashMap<Integer, User>();
        }

        public void postTweet(int userId, int tweetId) {
            Tweet tweet = new Tweet(tweetId, userId);
            addUser(userId);

            tweets.add(tweet);
        }

        public List<Integer> getNewsFeed(int userId) {
            addUser(userId);
            Set<Integer> followers = users.get(userId).followers;
            List<Integer> tweetFeed = new ArrayList<Integer>();

            for (int i = tweets.size() - 1; i >= 0 && tweetFeed.size() < 10; i--) {
                Tweet tweet = tweets.get(i);
                if (followers.contains(tweet.userId) || tweet.userId == userId) {
                    tweetFeed.add(tweet.tweetId);
                }
            }

            return tweetFeed;
        }

        public void follow(int followerId, int followeeId) {
            addUser(followerId, followeeId);
            users.get(followerId).addFollower(followeeId);
        }

        public void unfollow(int followerId, int followeeId) {
            addUser(followerId, followeeId);
            users.get(followerId).removeFollower(followeeId);
        }

        private void addUser(int... userIds) {
            for (int userId : userIds) {
                if (!users.containsKey(userId)) {
                    User user = new User(userId);
                    users.put(userId, user);
                }
            }
        }
    }


    public static void main(String[] args) {
        Twitter obj = new Twitter();
        obj.postTweet(1,5);
        List<Integer> param_2 = obj.getNewsFeed(1);
        obj.follow(2,1);
        obj.unfollow(2,1);
    }
}
