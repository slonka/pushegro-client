package mobi.braincode.pushegro.client.model;

import java.util.List;

/**
 * Created by Grzegorz Miejski (SG0221133) on 3/14/2015.
 */
public class AuctionList {

    List<Auction> auctions;

    public AuctionList(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public AuctionList() {
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
}
