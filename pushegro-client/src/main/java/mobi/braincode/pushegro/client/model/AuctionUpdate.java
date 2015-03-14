package mobi.braincode.pushegro.client.model;

/**
 * Created by slonka on 14.03.15.
 */
public class AuctionUpdate {
    AuctionStatus auctionStatus;
    AuctionItem auctionItem;

    public AuctionUpdate(AuctionStatus auctionStatus, AuctionItem auctionItem) {
        this.auctionStatus = auctionStatus;
        this.auctionItem = auctionItem;
    }

    public AuctionStatus getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public AuctionItem getAuctionItem() {
        return auctionItem;
    }

    public void setAuctionItem(AuctionItem auctionItem) {
        this.auctionItem = auctionItem;
    }

    public void markNotViewed() {
        auctionItem.markNotViewed();
    }
}
