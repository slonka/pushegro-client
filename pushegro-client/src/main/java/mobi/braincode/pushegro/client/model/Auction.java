package mobi.braincode.pushegro.client.model;

public class Auction {
    private long auctionId;
    private String title;
    private float price;
    private long endDateTime;

    public Auction(long auctionId, String title) {
        this.auctionId = auctionId;
        this.title = title;
    }

    public long getAuctionId() {
        return auctionId;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public long getEndDateTime() {
        return endDateTime;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setEndDateTime(long endDateTime) {
        this.endDateTime = endDateTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        if (auctionId != auction.auctionId) return false;
        if (endDateTime != auction.endDateTime) return false;
        if (Float.compare(auction.price, price) != 0) return false;
        if (title != null ? !title.equals(auction.title) : auction.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (int) (endDateTime ^ (endDateTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", endDateTime=" + endDateTime +
                '}';
    }

}
