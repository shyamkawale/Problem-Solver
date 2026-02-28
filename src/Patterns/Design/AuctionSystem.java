package Patterns.Design;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/*
https://leetcode.com/problems/design-auction-system/

You are asked to design an auction system that manages bids from multiple users in real time.

Each bid is associated with a userId, an itemId, and a bidAmount.

Implement the AuctionSystem class:​​​​​​​

1. AuctionSystem(): 
    - Initializes the AuctionSystem object.
2. void addBid(int userId, int itemId, int bidAmount): 
    - Adds a new bid for itemId by userId with bidAmount. 
    - If the same userId already has a bid on itemId, replace it with the new bidAmount.
3. void updateBid(int userId, int itemId, int newAmount): 
    - Updates the existing bid of userId for itemId to newAmount. 
    - It is guaranteed that this bid exists.
4. void removeBid(int userId, int itemId): 
    - Removes the bid of userId for itemId. 
    - It is guaranteed that this bid exists.
5. int getHighestBidder(int itemId): 
    - Returns the userId of the highest bidder for itemId. 
    - If multiple users have the same highest bidAmount, return the user with the highest userId. 
    - If no bids exist for the item, return -1.
 */
class AuctionSystem {
    private static class Bid implements Comparable<Bid>{
        int userId;
        int amount;

        public Bid(int userId, int amount) {
            this.userId = userId;
            this.amount = amount;
        }

        @Override
        public int compareTo(Bid other) {
            if(this.amount != other.amount) {
                return Integer.compare(other.amount, this.amount);
            }

            return Integer.compare(other.userId, this.userId);
        }

         @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Bid bid = (Bid) obj;
            return this.userId == bid.userId && this.amount == bid.amount;
        }
    }
    
    private Map<Integer, TreeSet<Bid>> itemBidsMap;
    private Map<Integer, Map<Integer, Integer>> userItemBidAmtMap;
    
    public AuctionSystem() {
        userItemBidAmtMap = new HashMap<>();
        itemBidsMap = new HashMap<>();
    }
    
    public void addBid(int userId, int itemId, int amount) {
        if(userItemBidAmtMap.containsKey(userId) && userItemBidAmtMap.get(userId).containsKey(itemId)) {
            removeBid(userId, itemId);
        }

        userItemBidAmtMap.putIfAbsent(userId, new HashMap<>());
        userItemBidAmtMap.get(userId).put(itemId, amount);

        itemBidsMap.putIfAbsent(itemId, new TreeSet<>());
        itemBidsMap.get(itemId).add(new Bid(userId, amount));
    }
    
    public void updateBid(int userId, int itemId, int newAmount) {
        removeBid(userId, itemId);
        addBid(userId, itemId, newAmount);
    }
    
    public void removeBid(int userId, int itemId) {
        Bid bid = new Bid(userId, userItemBidAmtMap.get(userId).get(itemId));

        itemBidsMap.get(itemId).remove(bid);

        userItemBidAmtMap.get(userId).remove(itemId);
    }
    
    public int getHighestBidder(int itemId) {
        TreeSet<Bid> bids = itemBidsMap.get(itemId);

        if(bids == null || bids.isEmpty()) {
            return -1;
        }

        return bids.first().userId;
    }
}
/**
 * Your AuctionSystem object will be instantiated and called as such:
 * AuctionSystem obj = new AuctionSystem();
 * obj.addBid(userId,itemId,amount);
 * obj.updateBid(userId,itemId,newAmount);
 * obj.removeBid(userId,itemId);
 * int param_4 = obj.getHighestBidder(itemId);
 */
