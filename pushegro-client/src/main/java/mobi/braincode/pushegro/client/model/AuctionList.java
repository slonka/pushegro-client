package mobi.braincode.pushegro.client.model;

import java.util.List;

/**
 * Created by Grzegorz Miejski (SG0221133) on 3/14/2015.
 */
public class AuctionList {

    List<Auction> auctionPredicates;

    public AuctionList(List<Auction> auctions) {
        this.auctionPredicates = auctions;
    }

    public AuctionList() {
    }

    public List<Auction> getAuctionPredicates() {
        return auctionPredicates;
    }

    public void setAuctionPredicates(List<Auction> auctionPredicates) {
        this.auctionPredicates = auctionPredicates;
    }
}
